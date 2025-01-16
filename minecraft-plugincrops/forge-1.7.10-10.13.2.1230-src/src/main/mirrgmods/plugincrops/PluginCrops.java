package mirrgmods.plugincrops;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import mirrgmods.plugincrops.framework.HelperDeobf;
import mirrgmods.plugincrops.framework.HelperException;
import mirrgmods.plugincrops.framework.HelperFile;
import mirrgmods.plugincrops.transform.ClassEntry;
import mirrgmods.plugincrops.transform.TransformEntryClass;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class PluginCrops implements IFMLLoadingPlugin
{

	public static PluginCrops instance = null;
	public static String VERSION = "1.1.2";

	public static class TransformEntryClassesOverride extends TransformEntryClass
	{

		public TransformEntryClassesOverride(ClassEntry classEntry)
		{
			super(classEntry);
		}

		@Override
		public byte[] onTransform(ClassEntry classEntry, byte[] bytes)
		{
			FMLLog.info("[PluginCrops] Class Overrider: %s", classEntry);
			String classAbsolutePath = "/" + PluginCrops.class.getName().replaceAll("\\.", "/") + ".class";
			String rootDirName = HelperFile.getResource(classAbsolutePath).toString().replace(classAbsolutePath, "");
			String path = rootDirName + "/classes/" + classEntry.name + ".class";

			try {
				URL url = new URL(path);
				return HelperFile.getBytes(url);
			} catch (HelperException e) {
				throw new RuntimeException(e);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}

		}

	}

	public PluginCrops()
	{
		synchronized (PluginCrops.class) {
			if (instance != null && instance != this) return;
			instance = this;
		}

		//

		FMLLog.info("[PluginCrops] HelperDeobf.isDeobfuscated: " + HelperDeobf.isDeobfuscated());

		TransformerCrops.entries.clear();

		String[] classNames = {
			"ic2.core.block.TileEntityCrop",
			"ic2.core.block.BlockCrop",
			"ic2.core.block.crop.CropStickReed",
		};

		for (String className : classNames) {
			TransformerCrops.entries.add(new TransformEntryClassesOverride(new ClassEntry(className.replaceAll("\\.", "/"))));
		}
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return instance == this ? new String[] {
			TransformerCrops.class.getName(),
		} : null;
	}

	@Override
	public String getModContainerClass()
	{
		return ModContainerCrops.class.getName();
	}

	@Override
	public String getSetupClass()
	{
		return null;
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
