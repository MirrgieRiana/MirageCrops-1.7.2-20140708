package miragecrops.core.ores.toolcrafting;

import static miragecrops.api.framework.item.EnumToolMaterialHarvestLevels.*;
import static miragecrops.api.framework.material.MirageMaterialsManager.*;
import static miragecrops.core.ores.MultiIcons.*;

import java.util.ArrayList;
import java.util.Hashtable;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.IMirageMaterial;
import miragecrops.core.ores.ModuleOres;
import miragecrops.framework.StaticsString;
import miragecrops.framework.material.EnumMachineTier;
import miragecrops.framework.material.IMirageMaterialMachine;
import miragecrops.framework.multiicon.IMultiIcon;
import miragecrops.framework.multiicon.IMultiIconShape;
import miragecrops.framework.multiicon.MultiIcon;
import mirrg.h.struct.Tuple3;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModuleToolCrafting
{

	private static ArrayList<CraftingTool> craftingTools;
	private static ArrayList<MirageMaterialCraftingToolEntry> mirageMaterialCraftingToolEntries;

	public static void preInit()
	{

		registerCraftingTools();

		registerMirageMaterialCraftingToolEntries();

	}

	private static void registerCraftingTools()
	{
		craftingTools = new ArrayList<CraftingTool>();

		craftingTools.add(new CraftingToolWrench("wrench", 6, 256, 2, 7, WRENCH) {

			@Override
			public void registerRecipe(Item itemToolCrafting, ModuleOres moduleOres, IMirageMaterial iMirageMaterial)
			{
				String itemName = name + StaticsString.toUpperCaseHead(iMirageMaterial.name());

				GameRegistry.addRecipe(new ShapedOreRecipe(
					new ItemStack(itemToolCrafting),
					"X X",
					"XXX",
					" X ",
					'X', iMirageMaterial.getDictionaryName(EnumShape.ingot)));
			}

		});
		craftingTools.add(new CraftingTool("hardHammer", 6, 256, 0, 5, HAMMER) {

			@Override
			public void registerRecipe(Item itemToolCrafting, ModuleOres moduleOres, IMirageMaterial iMirageMaterial)
			{
				String itemName = name + StaticsString.toUpperCaseHead(iMirageMaterial.name());

				GameRegistry.addRecipe(new ShapedOreRecipe(
					new ItemStack(itemToolCrafting),
					"XX ",
					"XXY",
					"XX ",
					'X', iMirageMaterial.getDictionaryName(EnumShape.ingot),
					'Y', "stickWood"));
			}

		});
		craftingTools.add(new CraftingTool("file", 2, 256, 9, 2, FILE) {

			@Override
			public void registerRecipe(Item itemToolCrafting, ModuleOres moduleOres, IMirageMaterial iMirageMaterial)
			{
				String itemName = name + StaticsString.toUpperCaseHead(iMirageMaterial.name());

				GameRegistry.addRecipe(new ShapedOreRecipe(
					new ItemStack(itemToolCrafting),
					"X",
					"X",
					"Y",
					'X', iMirageMaterial.getDictionaryName(EnumShape.plate),
					'Y', "stickWood"));
			}

		});
		craftingTools.add(new CraftingTool("mortar", 2, 256, 6, 4, MORTAR) {

			@Override
			public void registerRecipe(Item itemToolCrafting, ModuleOres moduleOres, IMirageMaterial iMirageMaterial)
			{
				String itemName = name + StaticsString.toUpperCaseHead(iMirageMaterial.name());

				GameRegistry.addRecipe(new ShapedOreRecipe(
					new ItemStack(itemToolCrafting),
					" X ",
					"YXY",
					"YYY",
					'X', iMirageMaterial.getDictionaryName(EnumShape.ingot),
					'Y', "plankWood"));
			}

		});
		craftingTools.add(new CraftingTool("chisel", 0.25, 64, 8, 0, CHISEL) {

			@Override
			public void registerRecipe(Item itemToolCrafting, ModuleOres moduleOres, IMirageMaterial iMirageMaterial)
			{
				String itemName = name + StaticsString.toUpperCaseHead(iMirageMaterial.name());

				GameRegistry.addRecipe(new ShapedOreRecipe(
					new ItemStack(itemToolCrafting),
					"XF",
					"Y ",
					'X', iMirageMaterial.getDictionaryName(EnumShape.rod),
					'Y', "stickWood",
					'F', "craftingToolFile"));
			}

		});
		craftingTools.add(new CraftingTool("saw", 2, 512, 5, 5, SAW) {

			@Override
			public void registerRecipe(Item itemToolCrafting, ModuleOres moduleOres, IMirageMaterial iMirageMaterial)
			{
				String itemName = name + StaticsString.toUpperCaseHead(iMirageMaterial.name());

				GameRegistry.addRecipe(new ShapedOreRecipe(
					new ItemStack(itemToolCrafting),
					"RRR",
					"PPR",
					"FH ",
					'R', "stickWood",
					'P', iMirageMaterial.getDictionaryName(EnumShape.plate),
					'F', "craftingToolFile",
					'H', "craftingToolHardHammer"));
			}

		});
		craftingTools.add(new CraftingTool("cutter", 3, 256, 5, 5, CUTTER) {

			@Override
			public void registerRecipe(Item itemToolCrafting, ModuleOres moduleOres, IMirageMaterial iMirageMaterial)
			{
				String itemName = name + StaticsString.toUpperCaseHead(iMirageMaterial.name());

				GameRegistry.addRecipe(new ShapedOreRecipe(
					new ItemStack(itemToolCrafting),
					"PFP",
					" P ",
					"R R",
					'R', "stickWood",
					'P', iMirageMaterial.getDictionaryName(EnumShape.plate),
					'F', "craftingToolFile"));
			}

		});
	}

	private static void registerMirageMaterialCraftingToolEntries()
	{
		mirageMaterialCraftingToolEntries = new ArrayList<MirageMaterialCraftingToolEntry>();

		add(spinachium, 1F, 5F);
		add(bismuth, 2F, 4F);
		add(iron, 3F, 5F);
		add(nickel, 3F, 5F);
		add(mirageAlloy, 3F, 7F);
		add(glass, 6F, 0F);

		add(fluorite, 4F, 2F);
		add(apatite, 5F, 2F);
		add(certusQuartz, 7F, 3F);
		add(topaz, 8F, 3F);
		add(ruby, 9F, 4F);
		add(diamond, 10F, 4F);

		add(copper, 2F, 3F);
		add(gold, 0F, 2F);
		add(silver, 1F, 3F);
		add(bronze, 4F, 5F);
		add(brass, 3F, 5F);
		add(titanium, 6F, 7F);
		add(chrome, 8F, 7F);
		add(iridium, 9F, 8F);
		add(osmium, 9F, 9F);
		add(steel, 4F, 6F);
		add(stainlessSteel, 5F, 6F);
		add(tungstenSteel, 6F, 7F);
		add(aluminium, 3F, 4F);

	}

	private static void add(IMirageMaterial spinachium2, float y, float z)
	{
		mirageMaterialCraftingToolEntries.add(new MirageMaterialCraftingToolEntry(spinachium2, y, z));
	}

	private static abstract class CraftingToolWrench extends CraftingTool
	{

		public CraftingToolWrench(
			String name,
			double amountMaterial,
			float defaultDurability,
			float hardnessNeed,
			float toughnessNeed,
			IMultiIconShape iMultiIconShape)
		{
			super(name, amountMaterial, defaultDurability, hardnessNeed, toughnessNeed, iMultiIconShape);
		}

		@Override
		protected ItemToolCrafting createItem(ModuleOres moduleOres, IMirageMaterial iMirageMaterial, float hardness, float toughness)
		{
			return registerItemToolCrafting(
				moduleOres,
				name,
				iMirageMaterial,
				calcDurability(iMirageMaterial, hardness, toughness),
				1,
				new MultiIcon(iMultiIconShape, iMirageMaterial.getColor()),
				amountMaterial);
		}

	}

	private static abstract class CraftingTool
	{

		protected final String name;
		protected final double amountMaterial;
		protected final float defaultDurability;
		protected final float hardnessNeed;
		protected final float toughnessNeed;
		protected final IMultiIconShape iMultiIconShape;

		public CraftingTool(
			String name,
			double amountMaterial,
			float defaultDurability,
			float hardnessNeed,
			float toughnessNeed,
			IMultiIconShape iMultiIconShape)
		{
			this.name = name;
			this.amountMaterial = amountMaterial;
			this.defaultDurability = defaultDurability;
			this.hardnessNeed = hardnessNeed;
			this.toughnessNeed = toughnessNeed;
			this.iMultiIconShape = iMultiIconShape;
		}

		public abstract void registerRecipe(Item itemToolCrafting, ModuleOres moduleOres, IMirageMaterial iMirageMaterial);

		protected String getUnlocalizedName(IMirageMaterial iMirageMaterial)
		{
			return name + StaticsString.toUpperCaseHead(iMirageMaterial.name());
		}

		protected ItemToolCrafting createItem(ModuleOres moduleOres, IMirageMaterial iMirageMaterial, float hardness, float toughness)
		{
			return registerItemToolCrafting(
				moduleOres,
				name,
				iMirageMaterial,
				calcDurability(iMirageMaterial, hardness, toughness),
				1,
				new MultiIcon(iMultiIconShape, iMirageMaterial.getColor(), 0x896727),
				amountMaterial);
		}

		protected ItemToolCrafting registerItemToolCrafting(ModuleOres moduleOres, String toolKind, IMirageMaterial iMaterial, int maxDamage, int damageOnCrafting, IMultiIcon iMultiIcon, double amountMaterial)
		{
			ItemToolCrafting item = new ItemToolCrafting(2.0F, ToolMaterial.IRON, null, maxDamage, damageOnCrafting,
				iMaterial.copyItemStack(EnumShape.dustTiny, (int) (amountMaterial * 9 * 0.5)));
			String unlocalizedName = toolKind + StaticsString.toUpperCaseHead(iMaterial.name());
			item.setUnlocalizedName(unlocalizedName);
			item.setTextureName(moduleOres.getMod().getModId() + ":" + unlocalizedName);
			item.setCreativeTab(ModuleOres.creativeTabTools);
			item.setMultiIcon(iMultiIcon);
			item.setHarvestLevel(toolKind, IRON);
			item.setMaterialNameAndShapeName(iMaterial.name(), toolKind);
			GameRegistry.registerItem(item, unlocalizedName, moduleOres.getMod().getModId());
			OreDictionary.registerOre("craftingTool" + StaticsString.toUpperCaseHead(toolKind), new ItemStack(item, 1, 32767));
			return item;
		}

		protected final int calcDurability(IMirageMaterial iMirageMaterial, float hardness, float toughness)
		{
			EnumMachineTier machineTier = IMirageMaterialMachine.Helper.getMachineTier(iMirageMaterial);
			float durabilityFloat = defaultDurability;

			if (hardness < hardnessNeed) {
				durabilityFloat *= Math.pow(0.5F, hardnessNeed - hardness);
			} else {
				durabilityFloat *= Math.pow(1.2F, hardness - hardnessNeed);
			}

			if (toughness < toughnessNeed) {
				durabilityFloat *= Math.pow(0.5F, toughnessNeed - toughness);
			} else {
				durabilityFloat *= Math.pow(1.2F, toughness - toughnessNeed);
			}

			durabilityFloat *= Math.pow(3.0F, machineTier.ordinal() - EnumMachineTier.normal.ordinal());

			int durability = (int) durabilityFloat;
			if (durability < 1) durability = 1;
			if (durability > 32000) durability = 32000;
			return durability;
		}
	}

	private static class MirageMaterialCraftingToolEntry extends Tuple3<IMirageMaterial, Float, Float>
	{

		public MirageMaterialCraftingToolEntry(IMirageMaterial x, Float y, Float z)
		{
			super(x, y, z);
		}

		public IMirageMaterial getMirageMaterial()
		{
			return getX();
		}

		public float getHardness()
		{
			return getY();
		}

		public float getToughness()
		{
			return getZ();
		}

	}

	public static void registerItems(ModuleOres moduleOres, Hashtable<String, Item> itemToolCraftings)
	{

		for (CraftingTool iCraftingTool : craftingTools) {
			for (MirageMaterialCraftingToolEntry mirageMaterialCraftingToolEntry : mirageMaterialCraftingToolEntries) {

				itemToolCraftings.put(
					iCraftingTool.getUnlocalizedName(mirageMaterialCraftingToolEntry.getMirageMaterial()),
					iCraftingTool.createItem(
						moduleOres,
						mirageMaterialCraftingToolEntry.getMirageMaterial(),
						mirageMaterialCraftingToolEntry.getHardness(),
						mirageMaterialCraftingToolEntry.getToughness()));

			}
		}

	}

	public static void registerRecipes(ModuleOres moduleOres, Hashtable<String, Item> itemToolCraftings)
	{

		for (CraftingTool iCraftingTool : craftingTools) {
			for (MirageMaterialCraftingToolEntry mirageMaterialCraftingToolEntry : mirageMaterialCraftingToolEntries) {

				iCraftingTool.registerRecipe(
					itemToolCraftings.get(iCraftingTool.getUnlocalizedName(mirageMaterialCraftingToolEntry.getMirageMaterial())),
					moduleOres, mirageMaterialCraftingToolEntry.getMirageMaterial());

			}
		}

	}

}
