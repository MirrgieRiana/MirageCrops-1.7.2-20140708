package miragecrops.core.machines;

import static miragecrops.api.framework.item.EnumToolMaterialHarvestLevels.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.IMirageMaterial;
import miragecrops.api.framework.material.MirageMaterialsManager;
import miragecrops.api.machines.ItemsModuleMachines;
import miragecrops.api.machines.RecipeFurnacefamily;
import miragecrops.api.machines.RecipesFurnacefamily;
import miragecrops.core.MirageCrops;
import miragecrops.core.machines.framework.MetaBlockFurnacefamily;
import miragecrops.core.machines.machine.LoaderMirageMachine;
import miragecrops.core.machines.mobs.SpiderSulfur;
import miragecrops.core.machines.pipe.LoaderMiragePipe;
import miragecrops.core.ores.MultiIcons;
import miragecrops.framework.StaticsRecipe;
import miragecrops.framework.StaticsReflection;
import miragecrops.framework.block.BlockMeta;
import miragecrops.framework.block.ItemBlockMeta;
import miragecrops.framework.block.MetaBlock;
import miragecrops.framework.block.UnionBlock;
import miragecrops.framework.block.container.BlockMetaContainer;
import miragecrops.framework.block.container.ItemBlockMetaContainer;
import miragecrops.framework.item.ItemMeta;
import miragecrops.framework.item.MetaItem;
import miragecrops.framework.multiicon.MultiIcon;
import mirrg.mir34.modding.IMod;
import mirrg.mir34.modding.IModule;
import mirrg.mir34.modding.ModuleAbstract;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModuleMachines extends ModuleAbstract
{

	public static RecipesFurnacefamily recipesMetaBlockFurnace = new RecipesFurnacefamily();

	public ModuleMachines(IMod mod)
	{
		super(mod);
	}

	@Override
	public String getModuleName()
	{
		return "machines";
	}

	@Override
	public void handle(FMLPreInitializationEvent event)
	{

		LoaderMiragePipe.preInit(this);

		registerItems();

		registerBlocks();

		LoaderMirageMachine.preInit(this);

		ItemsModuleMachines.spinachjuice = new Fluid("spinachjuice");
		FluidRegistry.registerFluid(ItemsModuleMachines.spinachjuice);

		MinecraftForge.EVENT_BUS.register(new Handler());

	}

	public class Handler
	{

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void handle(TextureStitchEvent.Pre event)
		{
			if (event.map.getTextureType() == MirageCrops.BLOCKS) {
				Fluid fluid = ItemsModuleMachines.spinachjuice;
				fluid.setIcons(
					event.map.registerIcon(getMod().getModId() + ":" + getModuleName() + "/" + fluid.getName() + "_still"),
					event.map.registerIcon(getMod().getModId() + ":" + getModuleName() + "/" + fluid.getName() + "_flow"));
			}
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClient(FMLInitializationEvent event)
	{
		SpiderSulfur.initClient(getMod());

		LoaderMiragePipe.registerRenderer(this);

		LoaderMirageMachine.registerRenderer(this);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClient(FMLPostInitializationEvent event)
	{
		new RecipeProviderFurnaceFamily(
			ItemsModuleMachines.machineMirageAlloyFurnace.createItemStack(),
			ModuleMachines.recipesMetaBlockFurnace);
	}

	@Override
	public void handle(FMLInitializationEvent event)
	{

		registerRecipes();

		SpiderSulfur.init(getMod());

	}

	/**
	 * preInit registerMultiIconShapes‚ÌŒã
	 */
	public void registerItems()
	{

		{
			ItemMeta item = new ItemMeta(getMod());
			String unlocalizedName = "multiItemMachines";
			item.setUnlocalizedName(unlocalizedName);
			item.setTextureName(getMod().getModId() + ":" + getModuleName() + "/" + unlocalizedName);
			item.setCreativeTab(MirageCrops.creativeTab);
			GameRegistry.registerItem(item, unlocalizedName, getMod().getModId());
			ItemsModuleMachines.multiItemMachines = item;

			int id = 1;
			MetaItem mi;
			mi = registerMetaItem(this, ItemsModuleMachines.class, null, item, id++, "sawbladeIron", MetaItem.class);
			mi = registerMetaItem(this, ItemsModuleMachines.class, null, item, id++, "spikeIron", MetaItem.class);
			mi = registerMetaItem(this, ItemsModuleMachines.class, null, item, id++, "bucketSpinachjuice", MetaItem.class);
			mi.setMultiIcon(new MultiIcon(MultiIcons.BUCKET, 0x105102, 0xffffff));

		}

	}

	private static MetaItem registerMetaItem(
		IModule module,
		Class<?> containerClazz,
		Map<String, ItemStack> containerOthers,
		ItemMeta itemMeta,
		int id,
		String dictionaryNameMeta,
		Class<? extends MetaItem> clazz)
	{
		MetaItem metaItem;
		try {
			Constructor<? extends MetaItem> constructor =
				clazz.getConstructor(ItemMeta.class, int.class);
			metaItem = constructor.newInstance(itemMeta, id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return registerMetaItem(module, containerClazz, containerOthers, itemMeta, id, dictionaryNameMeta, metaItem);
	}

	private static MetaItem registerMetaItem(
		IModule module,
		Class<?> containerClazz,
		Map<String, ItemStack> containerOthers,
		ItemMeta itemMeta,
		int id,
		String dictionaryNameMeta,
		MetaItem metaItem)
	{
		metaItem.setUnlocalizedName(dictionaryNameMeta);
		metaItem.setTextureName(module.getMod().getModId() + ":" + module.getModuleName() + "/" + dictionaryNameMeta);
		itemMeta.setMetaItem(id, metaItem);

		try {
			StaticsReflection.setStaticFieldValue(containerClazz, dictionaryNameMeta, metaItem);
		} catch (Exception e) {
			containerOthers.put(dictionaryNameMeta, metaItem.createItemStack());
		}

		OreDictionary.registerOre(dictionaryNameMeta, metaItem.createItemStack());

		return metaItem;
	}

	/**
	 * registerItems‚ÌŒã
	 */
	public void registerBlocks()
	{

		{
			String unlocalizedName = "superdirt";
			BlockSuperdirt block = new BlockSuperdirt();
			block.setHardness(4.0F);
			block.setResistance(20.0F);
			block.setStepSound(Block.soundTypeStone);
			block.setBlockName(unlocalizedName);
			block.setBlockTextureName(getMod().getModId() + ":" + getModuleName() + "/" + unlocalizedName);
			block.setCreativeTab(MirageCrops.creativeTab);
			block.setHarvestLevel("shovel", HAND);
			GameRegistry.registerBlock(block, unlocalizedName);
			ItemsModuleMachines.superdirt = block;
		}

		LoaderMiragePipe.registerBlocks(this);

		{
			UnionBlock<BlockMeta> ub = new UnionBlock<BlockMeta>(
				getMod(),
				BlockMeta.class,
				"BlockMachine",
				MirageCrops.creativeTab);
			ub.setClassItemBlockMeta(ItemBlockMeta.class);
			ItemsModuleMachines.unionBlockBlockMachine = ub;

			Class<?> c = ItemsModuleMachines.class;
			Hashtable<String, ItemStack> h = ItemsModuleMachines.materials;
			Class<MetaBlock> mc = MetaBlock.class;
			MetaBlock mb;

			mb = registerMetaBlock(this, c, h, ub, "blockMachineMirageAlloy", mc);
			mb.setTextureName(getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloy");
			mb.setBlockHardness(1.5F);
			mb.setHarvestLevel("wrench", IRON);

			mb = registerMetaBlock(this, c, h, ub, "blockMachineMirageAlloyFlat", mc);
			mb.setTextureName(getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyFlat");
			mb.setBlockHardness(1.5F);
			mb.setHarvestLevel("wrench", IRON);

			mb = registerMetaBlock(this, c, h, ub, "blockMachineMirageAlloyWild", mc);
			mb.setTextureName(getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyWild");
			mb.setBlockHardness(1.5F);
			mb.setHarvestLevel("wrench", IRON);

			mb = registerMetaBlock(this, c, h, ub, "blockMachineApatite", mc);
			mb.setTextureName(getMod().getModId() + ":" + getModuleName() + "/" + "machineApatite");
			mb.setBlockHardness(1.5F);
			mb.setHarvestLevel("wrench", IRON);

			mb = registerMetaBlock(this, c, h, ub, "blockMachineApatiteFlat", mc);
			mb.setTextureName(getMod().getModId() + ":" + getModuleName() + "/" + "machineApatiteFlat");
			mb.setBlockHardness(1.5F);
			mb.setHarvestLevel("wrench", IRON);

			mb = registerMetaBlock(this, c, h, ub, "blockMachineMirageAlloyHighly", mc);
			mb.setTextureName(getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyHighly");
			mb.setBlockHardness(1.5F);
			mb.setHarvestLevel("wrench", IRON);

		}

		LoaderMirageMachine.registerBlocks(this);

		{
			UnionBlock<BlockMetaContainer> ub = new UnionBlock<BlockMetaContainer>(
				getMod(),
				BlockMetaContainer.class,
				"Machine",
				MirageCrops.creativeTab);
			ub.setClassItemBlockMeta(ItemBlockMetaContainer.class);
			ItemsModuleMachines.unionBlockMachine = ub;

			Class<?> c = ItemsModuleMachines.class;
			Hashtable<String, ItemStack> h = ItemsModuleMachines.materials;
			Class<MetaBlock> mc = MetaBlock.class;
			MetaBlock mb;

			GameRegistry.registerTileEntity(TileEntityMetaBlockFurnace.class, "MetaBlockFurnace");
			GameRegistry.registerTileEntity(TileEntityMetaBlockMirageAlloyFurnace.class, "MetaBlockMirageAlloyFurnace");
			GameRegistry.registerTileEntity(TileEntityMetaBlockMirageAlloyCropHarvester.class, "MetaBlockMirageAlloyCropHarvester");
			GameRegistry.registerTileEntity(TileEntityMetaBlockMirageAlloySmallBlastFurnace.class, "MetaBlockMirageAlloySmallBlastFurnace");
			GameRegistry.registerTileEntity(TileEntityMetaBlockMirageAlloyController.class, "MetaBlockMirageAlloyController");

			{
				MetaBlockFurnacefamily mb2 = (MetaBlockFurnace) registerMetaBlock(this, c, h, ub, "machineMirageAlloyFurnace",
					new MetaBlockFurnace(ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto()) {

						@Override
						public TileEntity createNewTileEntity(World paramWorld, int paramInt)
						{
							return new TileEntityMetaBlockMirageAlloyFurnace();
						}

					});
				mb2.setBlockHardness(3.0F);
				mb2.setHarvestLevel("wrench", IRON);
				mb2.iconNameLit = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyFurnaceLit";
				mb2.iconNameSide = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyWild";
				mb2.iconNameTop = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyWild";
				mb2.iconNameBottom = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyWild";
			}

			{
				MetaBlockFurnacefamily mb2 = (MetaBlockFurnace) registerMetaBlock(this, c, h, ub, "machineMirageAlloyCropHarvester",
					new MetaBlockFurnace(ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto()) {

						@Override
						public TileEntity createNewTileEntity(World paramWorld, int paramInt)
						{
							return new TileEntityMetaBlockMirageAlloyCropHarvester();
						}

					});
				mb2.setBlockHardness(3.0F);
				mb2.setHarvestLevel("wrench", IRON);
				mb2.iconNameLit = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyCropHarvesterLit";
				mb2.iconNameSide = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloy";
				mb2.iconNameTop = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyFlat";
				mb2.iconNameBottom = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyFlat";
			}

			{
				MetaBlockFurnacefamily mb2 = (MetaBlockFurnace) registerMetaBlock(this, c, h, ub, "machineMirageAlloySmallBlastFurnace",
					new MetaBlockFurnace(ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto()) {

						@Override
						public TileEntity createNewTileEntity(World paramWorld, int paramInt)
						{
							return new TileEntityMetaBlockMirageAlloySmallBlastFurnace();
						}

					});
				mb2.setBlockHardness(3.0F);
				mb2.setHarvestLevel("wrench", IRON);
				mb2.iconNameLit = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloySmallBlastFurnaceLit";
				mb2.iconNameSide = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloy";
				mb2.iconNameTop = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyFlat";
				mb2.iconNameBottom = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyFlat";
			}

			{
				MetaBlockFurnacefamily mb2 = (MetaBlockFurnace) registerMetaBlock(this, c, h, ub, "machineMirageAlloyController",
					new MetaBlockMirageAlloyController(
						ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto()));
				mb2.setBlockHardness(3.0F);
				mb2.setHarvestLevel("wrench", IRON);
				mb2.iconNameLit = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloySmallBlastFurnaceLit";
				mb2.iconNameSide = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyHighly";
				mb2.iconNameTop = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyHighly";
				mb2.iconNameBottom = getMod().getModId() + ":" + getModuleName() + "/" + "machineMirageAlloyHighly";
			}

		}

	}

	private static MetaBlock registerMetaBlock(
		IModule module,
		Class<?> containerClazz,
		Map<String, ItemStack> containerOthers,
		UnionBlock unionBlock,
		String dictionaryNameMeta,
		Class<? extends MetaBlock> clazz)
	{
		MetaBlock metaBlock;
		try {
			Constructor<? extends MetaBlock> constructor =
				clazz.getConstructor(BlockMeta.class, int.class);
			metaBlock = constructor.newInstance(
				unionBlock.getNextBlockMetaThatShouldBeCreatedInto(),
				unionBlock.getNextMetaIdThatShouldBeCreatedInto());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return registerMetaBlock(module, containerClazz, containerOthers, unionBlock, dictionaryNameMeta, metaBlock);
	}

	@Deprecated
	public static MetaBlock registerMetaBlock(
		IModule module,
		Class<?> containerClazz,
		Map<String, ItemStack> containerOthers,
		UnionBlock unionBlock,
		String dictionaryNameMeta,
		MetaBlock metaBlock)
	{
		metaBlock.setUnlocalizedName(dictionaryNameMeta);
		metaBlock.setTextureName(module.getMod().getModId() + ":" + module.getModuleName() + "/" + dictionaryNameMeta);
		unionBlock.add(metaBlock);

		try {
			StaticsReflection.setStaticFieldValue(containerClazz, dictionaryNameMeta, metaBlock);
		} catch (Exception e) {
			containerOthers.put(dictionaryNameMeta, metaBlock.createItemStack());
		}

		OreDictionary.registerOre(dictionaryNameMeta, metaBlock.createItemStack());

		return metaBlock;
	}

	public static class TileEntityMetaBlockMirageAlloyFurnace extends TileEntityMetaBlockFurnace
	{

		@Override
		public String getDefaultInventoryName()
		{
			return "container.machineMirageAlloyFurnace";
		}

	}

	public static class TileEntityMetaBlockMirageAlloyCropHarvester extends TileEntityMetaBlockFurnace
	{

		@Override
		public String getDefaultInventoryName()
		{
			return "container.machineMirageAlloyCropHarvester";
		}

	}

	public static class TileEntityMetaBlockMirageAlloySmallBlastFurnace extends TileEntityMetaBlockFurnace
	{

		@Override
		public String getDefaultInventoryName()
		{
			return "container.machineMirageAlloySmallBlastFurnace";
		}

	}

	public static class TileEntityMetaBlockMirageAlloyController extends TileEntityMetaBlockFurnace
	{

		@Override
		public String getDefaultInventoryName()
		{
			return "container.machineMirageAlloyController";
		}

	}

	/**
	 * init
	 */
	public static void registerRecipes()
	{

		LoaderMiragePipe.registerRecipes();

		LoaderMirageMachine.registerRecipes();

		// #################### alloy

		{
			Hashtable<IMirageMaterial, Integer> alloyTable = new Hashtable<IMirageMaterial, Integer>();
			alloyTable.put(MirageMaterialsManager.bismuth, 1);
			alloyTable.put(MirageMaterialsManager.spinachium, 8);

			registerAlloy(MirageMaterialsManager.mirageAlloy, alloyTable);
		}

		// #################### machine <-> machineHull

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			MirageMaterialsManager.mirageAlloy.copyItemStack(EnumShape.machineHull),
			"machineMirageAlloyWild"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			MirageMaterialsManager.mirageAlloy.copyItemStack(EnumShape.machineHull),
			"machineMirageAlloyFlat"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			MirageMaterialsManager.mirageAlloy.copyItemStack(EnumShape.machineHull),
			"machineMirageAlloy"));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			ItemsModuleMachines.blockMachineMirageAlloyWild.createItemStack(),
			MirageMaterialsManager.mirageAlloy.getDictionaryName(EnumShape.machineHull)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			ItemsModuleMachines.blockMachineMirageAlloyFlat.createItemStack(2),
			MirageMaterialsManager.mirageAlloy.getDictionaryName(EnumShape.machineHull),
			MirageMaterialsManager.mirageAlloy.getDictionaryName(EnumShape.machineHull)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			ItemsModuleMachines.blockMachineMirageAlloy.createItemStack(3),
			MirageMaterialsManager.mirageAlloy.getDictionaryName(EnumShape.machineHull),
			MirageMaterialsManager.mirageAlloy.getDictionaryName(EnumShape.machineHull),
			MirageMaterialsManager.mirageAlloy.getDictionaryName(EnumShape.machineHull)));

		// #################### machineMirageAlloyFurnace

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.machineMirageAlloyFurnace.createItemStack(),
			"PWP",
			"PXP",
			"PMP",
			'P', "plateMirageAlloy",
			'W', "craftingToolWrench",
			'X', "craftingRawMachineTier-02",
			'M', Blocks.furnace));

		// ####################  machineMirageAlloySmallBlastFurnace

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.machineMirageAlloySmallBlastFurnace.createItemStack(),
			"PWP",
			"PMP",
			"PXP",
			'P', "plateMirageAlloy",
			'W', "craftingToolWrench",
			'M', ItemsModuleMachines.machineMirageAlloyFurnace.createItemStack(),
			'X', "craftingRawMachineTier00"));

		// ####################  machineMirageAlloyController

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.machineMirageAlloyController.createItemStack(),
			"PWP",
			"XSX",
			"PMP",
			'P', ItemsModuleMachines.spikeIron.createItemStack(),
			'W', "craftingToolWrench",
			'M', "craftingRawMachineTier01",
			'S', "spikeIron",
			'X', ItemsModuleMachines.miragePipeMirageAlloy.createItemStack()));

		// #################### mirage refine

		ModuleMachines.recipesMetaBlockFurnace.addRecipe(new RecipeFurnacefamily(
			MirageMaterialsManager.calcium.copyItemStack(EnumShape.ingot),
			MirageMaterialsManager.calcite.copyItemStack(EnumShape.dust).getItem(),
			MirageMaterialsManager.calcite.copyItemStack(EnumShape.dust).getItemDamage()));

		ModuleMachines.recipesMetaBlockFurnace.addRecipe(new RecipeFurnacefamily(
			MirageMaterialsManager.magnesium.copyItemStack(EnumShape.ingot),
			MirageMaterialsManager.magnesite.copyItemStack(EnumShape.dust).getItem(),
			MirageMaterialsManager.magnesite.copyItemStack(EnumShape.dust).getItemDamage()));

		ModuleMachines.recipesMetaBlockFurnace.addRecipe(new RecipeFurnacefamily(
			MirageMaterialsManager.iron.copyItemStack(EnumShape.ingot),
			MirageMaterialsManager.siderite.copyItemStack(EnumShape.dust).getItem(),
			MirageMaterialsManager.siderite.copyItemStack(EnumShape.dust).getItemDamage()));

		ModuleMachines.recipesMetaBlockFurnace.addRecipe(new RecipeFurnacefamily(
			MirageMaterialsManager.manganese.copyItemStack(EnumShape.ingot),
			MirageMaterialsManager.rhodochrosite.copyItemStack(EnumShape.dust).getItem(),
			MirageMaterialsManager.rhodochrosite.copyItemStack(EnumShape.dust).getItemDamage()));

		ModuleMachines.recipesMetaBlockFurnace.addRecipe(new RecipeFurnacefamily(
			MirageMaterialsManager.zinc.copyItemStack(EnumShape.ingot),
			MirageMaterialsManager.smithsonite.copyItemStack(EnumShape.dust).getItem(),
			MirageMaterialsManager.smithsonite.copyItemStack(EnumShape.dust).getItemDamage()));

		ModuleMachines.recipesMetaBlockFurnace.addRecipe(new RecipeFurnacefamily(
			MirageMaterialsManager.cobalt.copyItemStack(EnumShape.ingot),
			MirageMaterialsManager.sphaerocobaltite.copyItemStack(EnumShape.dust).getItem(),
			MirageMaterialsManager.sphaerocobaltite.copyItemStack(EnumShape.dust).getItemDamage()));

		ModuleMachines.recipesMetaBlockFurnace.addRecipe(new RecipeFurnacefamily(
			MirageMaterialsManager.nickel.copyItemStack(EnumShape.ingot),
			MirageMaterialsManager.gaspeite.copyItemStack(EnumShape.dust).getItem(),
			MirageMaterialsManager.gaspeite.copyItemStack(EnumShape.dust).getItemDamage()));

		ModuleMachines.recipesMetaBlockFurnace.addRecipe(new RecipeFurnacefamily(
			MirageMaterialsManager.cadmium.copyItemStack(EnumShape.ingot),
			MirageMaterialsManager.otavite.copyItemStack(EnumShape.dust).getItem(),
			MirageMaterialsManager.otavite.copyItemStack(EnumShape.dust).getItemDamage()));

		ModuleMachines.recipesMetaBlockFurnace.addRecipe(new RecipeFurnacefamily(
			MirageMaterialsManager.spinachium.copyItemStack(EnumShape.ingot),
			MirageMaterialsManager.spinatite.copyItemStack(EnumShape.dust).getItem(),
			MirageMaterialsManager.spinatite.copyItemStack(EnumShape.dust).getItemDamage()));

		// #################### others

		StaticsRecipe.addSmelting(
			MirageMaterialsManager.spinatite.copyItemStack(EnumShape.ore),
			MirageMaterialsManager.spinachium.copyItemStack(EnumShape.ingot),
			0);

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.sawbladeIron.createItemStack(),
			"FP ",
			"PHP",
			" P ",
			'F', "craftingToolFile",
			'H', "craftingToolHardHammer",
			'P', "plateIron"));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.spikeIron.createItemStack(9),
			"H ",
			"RF",
			'F', "craftingToolFile",
			'H', "craftingToolHardHammer",
			'R', "rodIron"));

		FluidContainerRegistry.registerFluidContainer(
			new FluidStack(ItemsModuleMachines.spinachjuice, 1000),
			ItemsModuleMachines.bucketSpinachjuice.createItemStack(),
			new ItemStack(Items.bucket));

	}

	public static void registerAlloy(IMirageMaterial iMaterial, Hashtable<IMirageMaterial, Integer> alloyTable)
	{
		EnumShape[] enumShapes = new EnumShape[] {
			EnumShape.dust,
			EnumShape.dustSmall,
			EnumShape.dustTiny,
		};

		for (EnumShape enumShape : enumShapes) {
			int length = 0;

			ArrayList<Object> objectList = new ArrayList<Object>();

			Set<Entry<IMirageMaterial, Integer>> entries = alloyTable.entrySet();
			for (Entry<IMirageMaterial, Integer> entry : entries) {
				for (int i = 0; i < entry.getValue(); i++) {
					objectList.add(entry.getKey().getDictionaryName(enumShape));
				}
			}

			if (objectList.size() > 9 || objectList.size() < 1) {
				throw new RuntimeException("alloy recipe length is invalid: " + length);
			}

			Object[] objectArray = objectList.toArray(new Object[objectList.size()]);

			GameRegistry.addRecipe(new ShapelessOreRecipe(
				iMaterial.copyItemStack(enumShape, objectList.size()),
				objectArray));
		}
	}

	public static class BlockSuperdirt extends Block
	{

		protected BlockSuperdirt()
		{
			super(Material.ground);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
		{
			return 0xFFFFFF;
		}

	}

}
