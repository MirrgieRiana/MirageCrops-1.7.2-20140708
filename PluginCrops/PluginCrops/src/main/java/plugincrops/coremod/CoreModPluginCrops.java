package plugincrops.coremod;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions("plugincrops.coremod")
public class CoreModPluginCrops implements IFMLLoadingPlugin
{

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] {
			TransformerPluginCropsCoreMod.class.getName(),
		};
	}

	@Override
	public String getModContainerClass()
	{
		return ModContainerPluginCropsCoreMod.class.getName();
	}

	@Override
	public String getSetupClass()
	{
		return FMLCallHookPluginCropsCoreMod.class.getName();
	}

	@Override
	public void injectData(Map<String, Object> data)
	{

	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}

}
