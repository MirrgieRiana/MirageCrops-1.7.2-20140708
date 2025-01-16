package mirrgmods.plugincrops.framework;


import org.objectweb.asm.tree.MethodNode;

import cpw.mods.fml.common.FMLLog;

public class HelperDeobf
{

	public static String dummyClassName = "mirrgmods.plugincrops.framework.DummyTileEntity";
	private static Boolean isDeobfuscated = null;

	public static boolean isDeobfuscated()
	{
		if (isDeobfuscated == null) {

			byte[] bytes;
			try {
				bytes = HelperFile.getBytesFromClassName(dummyClassName);
			} catch (HelperException e) {
				FMLLog.warning("[PluginCrops] helper error: file not found: '" + e + "'");
				if (e.getCause() != null) FMLLog.warning("[PluginCrops] " + e.getCause());
				isDeobfuscated = false;
				return isDeobfuscated;
			}

			MethodNode mnode;
			try {
				mnode = HelperAsm.getMethodNode(bytes, "markDirty", "()V");
			} catch (NoSuchMethodException e) {
				isDeobfuscated = false;
				return isDeobfuscated;
			}

			isDeobfuscated = true;
			return isDeobfuscated;
		}

		return isDeobfuscated;
	}

}
