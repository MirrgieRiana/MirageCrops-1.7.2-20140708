package miragecrops.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import miragecrops.api.crops.ItemsModuleCrops;
import miragecrops.framework.GuiHandler;
import mirrg.mir34.modding.ILoaderModule;
import mirrg.mir34.modding.ModAbstract;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(
	modid = MirageCrops.MODID,
	version = MirageCrops.VERSION,
	name = MirageCrops.NAME,
	dependencies = MirageCrops.DEPENDENCIES)
public class MirageCrops extends ModAbstract
{

	public static final String MODID = "miragecrops";
	public static final String VERSION = "3.5.0";
	public static final String NAME = "MirageCrops";
	public static final String DEPENDENCIES =
		"required-after:IC2;" +
			"";

	@Instance(MirageCrops.MODID)
	public static MirageCrops instance;

	public static IIcon NULL_ICON_BLOCK;
	public static IIcon NULL_ICON_ITEM;
	public static ResourceLocation NULL_GUI_TEXTURE;

	public static CreativeTabs creativeTab;

	public static GuiHandler guiHandler;

	{
		NULL_GUI_TEXTURE = new ResourceLocation(getModId() + ":" + "textures/gui/NULL_GUI_TEXTURE.png");
	}

	@Override
	protected void loadModules()
	{
		creativeTab = new CreativeTabs("mirageCrops") {

			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem()
			{
				return ItemsModuleCrops.multiItemCropGain.getItem();
			}

		};

		try {
			Class<?> iLoaderModuleClass = Class.forName("miragecrops.core.LoaderModule");
			Constructor<?> constructor = iLoaderModuleClass.getConstructor();
			Object instance = constructor.newInstance();
			if (instance instanceof ILoaderModule) {
				ILoaderModule iLoaderModule = (ILoaderModule) instance;

				iLoaderModule.loadModule(this);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static Configuration configuration;

	@Override
	@EventHandler
	public void handle(FMLPreInitializationEvent event)
	{
		configuration = new Configuration(event.getSuggestedConfigurationFile());
		configuration.load();
		super.handle(event);
		configuration.save();
		configuration = null;

		MinecraftForge.EVENT_BUS.register(new Handler());
	}

	public class Handler
	{

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void handle(TextureStitchEvent.Pre event)
		{
			if (event.map.getTextureType() == BLOCKS) {
				NULL_ICON_BLOCK = event.map.registerIcon(getModId() + ":NULL_ICON");
			}
			if (event.map.getTextureType() == ITEMS) {
				NULL_ICON_ITEM = event.map.registerIcon(getModId() + ":NULL_ICON");
			}
		}

	}

	@Override
	@EventHandler
	public void handle(FMLInitializationEvent event)
	{
		super.handle(event);

		{
			guiHandler = new GuiHandler(1, this);
			NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
		}
	}

	@Override
	@EventHandler
	public void handle(FMLPostInitializationEvent event)
	{
		super.handle(event);
	}

	public static final int BLOCKS = 0;
	public static final int ITEMS = 1;

}
