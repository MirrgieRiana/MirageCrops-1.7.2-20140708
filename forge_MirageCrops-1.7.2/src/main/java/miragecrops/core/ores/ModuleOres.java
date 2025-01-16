package miragecrops.core.ores;

import static miragecrops.api.framework.item.EnumToolMaterialHarvestLevels.*;
import static miragecrops.core.ores.MultiIcons.*;
import ic2.api.item.IC2Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.MirageMaterialsManager;
import miragecrops.api.ores.ItemsModuleOres;
import miragecrops.core.MirageCrops;
import miragecrops.core.ores.material.MirageMaterialAlloy;
import miragecrops.core.ores.material.MirageMaterialCrystal;
import miragecrops.core.ores.material.MirageMaterialGlass;
import miragecrops.core.ores.material.MirageMaterialMetal;
import miragecrops.core.ores.material.MirageMaterialOre;
import miragecrops.core.ores.materials.MirageMaterialsManagerImpl;
import miragecrops.core.ores.toolcrafting.ModuleToolCrafting;
import miragecrops.framework.block.BlockMeta;
import miragecrops.framework.item.MetaItem;
import miragecrops.framework.item.SItemStack;
import miragecrops.framework.material.EnumMachineTier;
import mirrg.mir34.modding.IMod;
import mirrg.mir34.modding.ModuleAbstract;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModuleOres extends ModuleAbstract
{

	public static CreativeTabs creativeTabMaterials = new CreativeTabs("mirageCrops.ores.materials") {

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			Item item = ItemsModuleOres.multiItemIngot.getItem();
			return item != null ? item : Items.iron_ingot;
		}

	};

	public static CreativeTabs creativeTabTools = new CreativeTabs("mirageCrops.ores.tools") {

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			Item item = ItemsModuleOres.itemToolCraftings.get("sawSpinachium");
			return item != null ? item : Items.iron_pickaxe;
		}

	};

	public ModuleOres(IMod mod)
	{
		super(mod);
	}

	private MirageMaterialsManagerImpl mirageMaterialsManagerImpl;

	@Override
	public String getModuleName()
	{
		return "ores";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handlePreClient(FMLPreInitializationEvent event)
	{
		MultiIcons.registerMultiIconShapes(getMod().getModId());
	}

	public static class Configurations
	{
		public static boolean replaceVanillaRecipe;
	}

	@Override
	public void handle(FMLPreInitializationEvent event)
	{

		Configurations.replaceVanillaRecipe =
			MirageCrops.configuration.get(getModuleName(), "replaceVanillaRecipe", true).getBoolean();

		{
			MirageMaterialsManagerImpl mmmi = createMaterials();

			ItemsModuleOres.multiItemGem = mmmi.registerItemsOfShape(EnumShape.gem, GEM);
			ItemsModuleOres.multiItemIngot = mmmi.registerItemsOfShape(EnumShape.ingot, INGOT);
			((MetaItem) ItemsModuleOres.multiItemIngot.getMetaItem(0)).setTextureName(
				getMod().getModId() + ":" + getModuleName() + "/" + "gemFluorite");
			ItemsModuleOres.multiItemNugget = mmmi.registerItemsOfShape(EnumShape.nugget, NUGGET);
			ItemsModuleOres.multiItemPlate = mmmi.registerItemsOfShape(EnumShape.plate, PLATE);
			ItemsModuleOres.multiItemRod = mmmi.registerItemsOfShape(EnumShape.rod, ROD);
			ItemsModuleOres.multiItemWire = mmmi.registerItemsOfShape(EnumShape.wire, WIRE);
			ItemsModuleOres.multiItemDust = mmmi.registerItemsOfShape(EnumShape.dust, DUST);
			ItemsModuleOres.multiItemDustSmall = mmmi.registerItemsOfShape(EnumShape.dustSmall, DUST_SMALL);
			ItemsModuleOres.multiItemDustTiny = mmmi.registerItemsOfShape(EnumShape.dustTiny, DUST_TINY);
			ItemsModuleOres.multiItemMachineHull = mmmi.registerItemsOfShape(EnumShape.machineHull, MACHINE_HULL);

			ItemsModuleOres.unionBlockBlock = mmmi.registerBlocksOfShepe(BlockMeta.class, EnumShape.block).toAPI();
			ItemsModuleOres.unionBlockOre = mmmi.registerBlocksOfShepe(BlockMeta.class, EnumShape.ore).toAPI();

			MirageMaterialsManager.instance = mmmi;
			mirageMaterialsManagerImpl = mmmi;
		}

		ModuleToolCrafting.preInit();

		registerItems();

		registerBlocks();

		ModuleWorldGen.registerWorldgen();

	}

	@Override
	public void handle(FMLInitializationEvent event)
	{

		mirageMaterialsManagerImpl.registerRecipe();

		registerRecipes();

	}

	@Override
	public void handle(FMLPostInitializationEvent event)
	{

		if (Configurations.replaceVanillaRecipe) {
			FMLLog.info("[replace vannila recipe] begin");
			replaceRecipes();
			FMLLog.info("[replace vannila recipe] end");
		} else {
			FMLLog.info("[replace vannila recipe] SKIPPED");
		}

	}

	private MirageMaterialsManagerImpl createMaterials()
	{
		MirageMaterialsManagerImpl mirageMaterialsManagerImpl = new MirageMaterialsManagerImpl(this);

		registerMirageMaterials(mirageMaterialsManagerImpl);
		registerShapes(mirageMaterialsManagerImpl);

		return mirageMaterialsManagerImpl;
	}

	private static void registerMirageMaterials(MirageMaterialsManagerImpl mmm)
	{

		mmm.addMirageMaterial(new MirageMaterialCrystal("calcite", 0xDBE0BE, "CaCO3", WOOD, EnumMachineTier.unusable, false, true));
		mmm.addMirageMaterial(new MirageMaterialOre("magnesite", 0xDAE1F0, "MgCO3", STONE));
		mmm.addMirageMaterial(new MirageMaterialOre("siderite", 0x91A42C, "FeCO3", STONE));
		mmm.addMirageMaterial(new MirageMaterialOre("rhodochrosite", 0xE594BB, "MnCO3", STONE));
		mmm.addMirageMaterial(new MirageMaterialOre("smithsonite", 0x97DDBF, "ZnCO3", IRON));
		mmm.addMirageMaterial(new MirageMaterialOre("sphaerocobaltite", 0xC0278B, "CoCO3", IRON));
		mmm.addMirageMaterial(new MirageMaterialOre("gaspeite", 0x406F2F, "NiCO3", IRON));
		mmm.addMirageMaterial(new MirageMaterialOre("otavite", 0x95ABE8, "CdCO3", EMERALD));

		mmm.addMirageMaterial(new MirageMaterialMetal("calcium", 0xE0E0D5, "Ca", WOOD, EnumMachineTier.unusable, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("magnesium", 0xD8A9A9, "Mg", STONE, EnumMachineTier.unusable, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("iron", 0xA5A5A5, "Fe", IRON, EnumMachineTier.cheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("manganese", 0xC8C0C0, "Mn", STONE, EnumMachineTier.veryCheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("zinc", 0xE5DBF2, "Zn", STONE, EnumMachineTier.veryCheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("cobalt", 0x4242CF, "Co", STONE, EnumMachineTier.veryCheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("nickel", 0x96A4FF, "Ni", IRON, EnumMachineTier.cheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("cadmium", 0x2F2F38, "Cd", EMERALD, EnumMachineTier.advanced, false, false));

		mmm.addMirageMaterial(new MirageMaterialGlass("glass", 0xEEEEEE, "SiO2", STONE, EnumMachineTier.veryVeryCheap));

		mmm.addMirageMaterial(new MirageMaterialMetal("bismuth", 0x597ABA, "Bi", STONE, EnumMachineTier.veryCheap, true, true));
		mmm.addMirageMaterial(new MirageMaterialOre("spinatite", 0x137F18, "SpINaCH", STONE));
		mmm.addMirageMaterial(new MirageMaterialMetal("spinachium", 0x00C610, "Sp", WOOD, EnumMachineTier.veryCheap, true, false));
		mmm.addMirageMaterial(new MirageMaterialAlloy("mirageAlloy", 0x548327, "Sp8Bi", IRON, EnumMachineTier.cheap, true));

		mmm.addMirageMaterial(new MirageMaterialCrystal("talc", 0xD3E2D7, "Mg3Si4O10(OH)2", WOOD, EnumMachineTier.unusable, false, false));
		mmm.addMirageMaterial(new MirageMaterialCrystal("gypsum", 0xEAEAEA, "CaSO4", WOOD, EnumMachineTier.unusable, false, false));
		mmm.addMirageMaterial(new MirageMaterialCrystal("fluorite", 0x1BE86A, "CaF2", STONE, EnumMachineTier.veryCheap, true, true));
		mmm.addMirageMaterial(new MirageMaterialCrystal("apatite", 0x5BB5FF, "Ca5(PO4)3OH", STONE, EnumMachineTier.veryCheap, true, true));
		mmm.addMirageMaterial(new MirageMaterialCrystal("orthoclase", 0xFFE2B7, "KAlSi3O8", STONE, EnumMachineTier.unusable, false, false));
		mmm.addMirageMaterial(new MirageMaterialCrystal("certusQuartz", 0xC6D0FF, "(SiO2)?", STONE, EnumMachineTier.veryCheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialCrystal("topaz", 0xFFC46D, "Al2SiO4(F,OH)2", IRON, EnumMachineTier.cheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialCrystal("ruby", 0xE40000, "(Al2O3)49(AlCrO3)", IRON, EnumMachineTier.cheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialCrystal("diamond", 0x33EBCB, "C", IRON, EnumMachineTier.normal, false, false));

		mmm.addMirageMaterial(new MirageMaterialCrystal("sulfur", 0xFFE727, "S", STONE, EnumMachineTier.unusable, false, false));

		mmm.addMirageMaterial(new MirageMaterialMetal("copper", 0xEF7351, "Cu", STONE, EnumMachineTier.veryCheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("tin", 0xCEDBEA, "Sn", STONE, EnumMachineTier.veryCheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("gold", 0xFFFF0B, "Au", WOOD, EnumMachineTier.veryVeryCheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("silver", 0xD8F4FF, "Ag", STONE, EnumMachineTier.veryCheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("lead", 0x5929AD, "Pb", STONE, EnumMachineTier.veryCheap, false, false));
		mmm.addMirageMaterial(new MirageMaterialAlloy("bronze", 0xFF6A00, "Cu3Sn", IRON, EnumMachineTier.cheap, false));
		mmm.addMirageMaterial(new MirageMaterialAlloy("brass", 0xEABE2E, "Cu3Zn", IRON, EnumMachineTier.cheap, false));
		mmm.addMirageMaterial(new MirageMaterialAlloy("electrum", 0xFFFF8C, "AgAu", STONE, EnumMachineTier.cheap, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("mercury", 0xD5B7B7, "Hg", STONE, EnumMachineTier.unusable, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("titanium", 0xD197FF, "Ti", IRON, EnumMachineTier.advanced, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("chrome", 0xFFAAD5, "Cr", IRON, EnumMachineTier.advanced, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("iridium", 0xE0FFE0, "Ir", IRON, EnumMachineTier.veryAdvanced, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("osmium", 0x4B00CE, "Os", IRON, EnumMachineTier.veryAdvanced, false, false));
		mmm.addMirageMaterial(new MirageMaterialAlloy("steel", 0x74749E, "Fe49C", IRON, EnumMachineTier.normal, false));
		mmm.addMirageMaterial(new MirageMaterialAlloy("stainlessSteel", 0x9EEDBB, "Fe6NiCrMn", IRON, EnumMachineTier.normal, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("tungsten", 0x333346, "W", IRON, EnumMachineTier.normal, false, false));
		mmm.addMirageMaterial(new MirageMaterialAlloy("tungstenSteel", 0x3636A0, "Fe49CW50", IRON, EnumMachineTier.advanced, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("platinum", 0xFFF6DD, "Pt", IRON, EnumMachineTier.veryAdvanced, false, false));
		mmm.addMirageMaterial(new MirageMaterialMetal("aluminium", 0xC0D3F7, "Al", IRON, EnumMachineTier.cheap, false, false));

	}

	private static void registerShapes(MirageMaterialsManagerImpl mmm)
	{

	}

	/**
	 * preInit registerMultiIconShapes‚ÌŒã
	 */
	private void registerItems()
	{

		OreDictionary.registerOre("ingotIron", Items.iron_ingot);
		OreDictionary.registerOre("craftingToolCutter", Items.shears);
		OreDictionary.registerOre("craftingToolCutter", new ItemStack(IC2Items.getItem("cutter").getItem(), 1, 32767));

		ModuleToolCrafting.registerItems(this, ItemsModuleOres.itemToolCraftings);

	}

	/**
	 * registerItems‚ÌŒã
	 */
	private void registerBlocks()
	{

	}

	/**
	 * init
	 */
	private void registerRecipes()
	{

		ModuleToolCrafting.registerRecipes(this, ItemsModuleOres.itemToolCraftings);

	}

	/**
	 * post init
	 */
	private void replaceRecipes()
	{
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		List<IRecipe> recipeListAdditional = new ArrayList<IRecipe>();

		Iterator<IRecipe> iterator = recipeList.iterator();
		while (iterator.hasNext()) {
			IRecipe recipe = iterator.next();

			if (SItemStack.isOre(recipe.getRecipeOutput(), "plankWood")) {
				if (recipe instanceof ShapedRecipes) {
					ShapedRecipes shapedRecipe = (ShapedRecipes) recipe;
					if (shapedRecipe.recipeItems.length == 1) {
						if (SItemStack.isOre(shapedRecipe.recipeItems[0], "logWood")) {

							FMLLog.info("removed recipe(logWood -> 4 plankWood)");
							iterator.remove();
							recipeListAdditional.add(new ShapedOreRecipe(new ItemStack(
								recipe.getRecipeOutput().getItem(),
								4,
								recipe.getRecipeOutput().getItemDamage()),
								"S",
								"X",
								'S', "craftingToolSaw",
								'X', shapedRecipe.recipeItems[0]));
							recipeListAdditional.add(new ShapedOreRecipe(new ItemStack(
								recipe.getRecipeOutput().getItem(),
								2,
								recipe.getRecipeOutput().getItemDamage()),
								"X",
								'X', shapedRecipe.recipeItems[0]));

							continue;
						}
					}
				}
			}

			if (SItemStack.isOre(recipe.getRecipeOutput(), "stickWood")) {
				if (recipe instanceof ShapedOreRecipe) {
					ShapedOreRecipe shapedOreRecipe = (ShapedOreRecipe) recipe;
					if (shapedOreRecipe.getInput().length == 2) {
						if (SItemStack.isOre(shapedOreRecipe.getInput()[0], "plankWood")) {
							if (SItemStack.isOre(shapedOreRecipe.getInput()[1], "plankWood")) {

								FMLLog.info("removed recipe(plankWood -> 4 stickWood)");
								iterator.remove();
								recipeListAdditional.add(new ShapedOreRecipe(new ItemStack(
									recipe.getRecipeOutput().getItem(),
									4,
									recipe.getRecipeOutput().getItemDamage()),
									"S",
									"X",
									"X",
									'S', "craftingToolSaw",
									'X', "plankWood"));
								recipeListAdditional.add(new ShapedOreRecipe(new ItemStack(
									recipe.getRecipeOutput().getItem(),
									2,
									recipe.getRecipeOutput().getItemDamage()),
									"X",
									"X",
									'X', "plankWood"));

								continue;
							}
						}
					}
				}
			}

		}

		for (IRecipe recipe : recipeListAdditional) {
			recipeList.add(recipe);
		}

	}

}
