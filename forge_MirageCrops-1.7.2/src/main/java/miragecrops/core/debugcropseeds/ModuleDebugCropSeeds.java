package miragecrops.core.debugcropseeds;

import ic2.api.item.IC2Items;

import java.lang.reflect.Constructor;
import java.util.Map;

import miragecrops.api.framework.cropgainrecipe.CropGainRecipe;
import miragecrops.core.MirageCrops;
import miragecrops.framework.StaticsReflection;
import miragecrops.framework.item.ItemMeta;
import miragecrops.framework.item.MetaItem;
import mirrg.mir34.modding.IMod;
import mirrg.mir34.modding.IModule;
import mirrg.mir34.modding.ModuleAbstract;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModuleDebugCropSeeds extends ModuleAbstract
{

	public static CreativeTabs creativeTab = new CreativeTabs("debugCropSeeds") {

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return debugCropSeed;
		}

	};

	public static ItemDebugCropSeed debugCropSeed;
	public static ItemMeta multiItemDebugCrop;
	public static MetaItem debugCropAnalyzer;
	public static MetaItem debugCropPicker;
	public static MetaItem debugCropHarvester;
	public static MetaItem debugCropSandglass;
	public static MetaItem debugCropCrosser;
	public static MetaItem debugCropCrossingDumper;

	public ModuleDebugCropSeeds(IMod mod)
	{
		super(mod);
	}

	@Override
	public String getModuleName()
	{
		return "debugCropSeeds";
	}

	@Override
	public void handle(FMLPreInitializationEvent event)
	{

		{
			ItemDebugCropSeed item = new ItemDebugCropSeed();
			String unlocalizedName = "debugCropSeed";
			item.setUnlocalizedName(unlocalizedName);
			item.setTextureName(getMod().getModId() + ":" + unlocalizedName);
			item.setCreativeTab(creativeTab);
			GameRegistry.registerItem(item, unlocalizedName, getMod().getModId());
			debugCropSeed = item;
		}

		{
			ItemMeta item = new ItemMeta(getMod());
			String unlocalizedName = "multiItemDebugCrop";
			item.setUnlocalizedName(unlocalizedName);
			item.setCreativeTab(MirageCrops.creativeTab);
			GameRegistry.registerItem(item, unlocalizedName, getMod().getModId());
			multiItemDebugCrop = item;

			int id = 1;
			registerMetaItem(this, this.getClass(), null, multiItemDebugCrop, id++, "debugCropAnalyzer", ItemMetaDebugCrop.Analyzer.class);
			registerMetaItem(this, this.getClass(), null, multiItemDebugCrop, id++, "debugCropPicker", ItemMetaDebugCrop.Picker.class);
			registerMetaItem(this, this.getClass(), null, multiItemDebugCrop, id++, "debugCropHarvester", ItemMetaDebugCrop.Harvester.class);
			registerMetaItem(this, this.getClass(), null, multiItemDebugCrop, id++, "debugCropSandglass", ItemMetaDebugCrop.Sandglass.class);
			registerMetaItem(this, this.getClass(), null, multiItemDebugCrop, id++, "debugCropCrosser", ItemMetaDebugCrop.Crosser.class);
			registerMetaItem(this, this.getClass(), null, multiItemDebugCrop, id++, "debugCropCrossingDumper", ItemMetaDebugCrop.CrossingDumper.class);

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

	@Override
	public void handle(FMLInitializationEvent event)
	{

		try {
			Class<?> clazz = Class.forName("ic2.core.block.crop.CropMelon");
			if (clazz != null) {
				CropGainRecipe.cropGainRecipeList.put(clazz, CropGainRecipe.createCropGainRecipe(
					new ItemStack(Blocks.melon_block),
					new ItemStack(Items.melon)));
			}
		} catch (ClassNotFoundException e) {
		}

		try {
			Class<?> clazz = Class.forName("ic2.core.block.crop.CropPotato");
			if (clazz != null) {
				CropGainRecipe.cropGainRecipeList.put(clazz, CropGainRecipe.createCropGainRecipe(
					new ItemStack(Items.potato),
					new ItemStack(Items.poisonous_potato)));
			}
		} catch (ClassNotFoundException e) {
		}

		try {
			Class<?> clazz = Class.forName("ic2.core.block.crop.CropRedWheat");
			if (clazz != null) {
				CropGainRecipe.cropGainRecipeList.put(clazz, CropGainRecipe.createCropGainRecipe(
					new ItemStack(Items.redstone),
					new ItemStack(Items.wheat)));
			}
		} catch (ClassNotFoundException e) {
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClient(FMLPostInitializationEvent event)
	{

		new RecipeProviderCropGain(IC2Items.getItem("cropmatron"));
		new RecipeProviderCropBaseSeed(IC2Items.getItem("crop"));

	}

}
