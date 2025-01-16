package plugincrops.coremod;

import java.io.File;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;
import plugincrops.coremod.ConfigurationPluginCrops.EnumFix;
import plugincrops.coremod.ConfigurationPluginCrops.RenderBlockCropsImpl.CrossingDefault;
import plugincrops.coremod.ConfigurationPluginCrops.RenderBlockCropsImpl.CrossingDefault.Patterns;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLCallHook;

public class FMLCallHookPluginCropsCoreMod implements IFMLCallHook
{

	@Override
	public Void call() throws Exception
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> map)
	{
		Configuration configuration = new Configuration(
			new File((File) map.get("mcLocation"), "config/PluginCropsCoreMod.cfg"));
		configuration.load();

		if (!FMLLaunchHandler.side().isServer()) {
			CrossingDefault.patternsDisplayName = new Patterns(configuration.get(
				"CoreMod_RenderBlockCropsImpl_CrossingDefault",
				"patternsDisplayName",
				new String[] {
					"Indigo",
					"OilBerry",
					"Argentia",
					"Plubilia",
				}).getStringList());
			CrossingDefault.patternsAttribute = new Patterns(configuration.get(
				"CoreMod_RenderBlockCropsImpl_CrossingDefault",
				"patternsAttribute",
				new String[] {
					"Weed",
					"Reed",
					"Stem",
					"Leaves",
					"Vine",
				}).getStringList());

			CrossingDefault.patternsNgDisplayName = new Patterns(configuration.get(
				"CoreMod_RenderBlockCropsImpl_CrossingDefault",
				"patternsNgDisplayName",
				new String[] {
					"Flax",
				}).getStringList());
			CrossingDefault.patternsNgAttribute = new Patterns(configuration.get(
				"CoreMod_RenderBlockCropsImpl_CrossingDefault",
				"patternsNgAttribute",
				new String[] {
				}).getStringList());
		}

		for (EnumFix fix : EnumFix.values()) {
			fix.enabled = configuration.get("CoreMod_Fixes", fix.name(), true).getBoolean();
		}

		configuration.save();

		ConfigurationPluginCrops.initialized = true;
	}

}
