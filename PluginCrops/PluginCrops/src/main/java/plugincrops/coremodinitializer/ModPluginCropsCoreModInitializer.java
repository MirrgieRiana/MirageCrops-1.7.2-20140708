package plugincrops.coremodinitializer;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModPluginCropsCoreModInitializer.MODID, version = ModPluginCropsCoreModInitializer.VERSION)
public class ModPluginCropsCoreModInitializer
{

	public static final String MODID = "PluginCrops_CoreModInitializer";
	public static final String VERSION = "0";

	@SidedProxy(
		clientSide = "plugincrops.coremodinitializer.ProxyClient",
		serverSide = "plugincrops.coremodinitializer.ProxyServer")
	public static Proxy proxy;

	@EventHandler
	public void handleClient(FMLPreInitializationEvent event)
	{
		proxy.init();
	}

}
