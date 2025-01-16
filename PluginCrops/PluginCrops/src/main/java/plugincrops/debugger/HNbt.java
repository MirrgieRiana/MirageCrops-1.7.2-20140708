package plugincrops.debugger;

import net.minecraft.nbt.NBTTagCompound;

public class HNbt
{

	/**
	 * sample: getInteger(nbt, "directory/property")
	 */
	public static Integer getInteger(NBTTagCompound nbt, String path)
	{
		if (nbt == null) return null;

		String[] dirs = path.split("/");

		for (int i = 0; i < dirs.length - 1; i++) {
			if (nbt.hasKey(dirs[i], 10)) {
				nbt = nbt.getCompoundTag("dirs[i]");
			} else {
				return null;
			}
		}

		if (nbt.hasKey(dirs[dirs.length - 1], 3)) {
			return nbt.getInteger(dirs[dirs.length - 1]);
		} else {
			return null;
		}
	}

	/**
	 * sample: getInteger(nbt, "directory/property")
	 */
	public static String getString(NBTTagCompound nbt, String path)
	{
		if (nbt == null) return null;

		String[] dirs = path.split("/");

		for (int i = 0; i < dirs.length - 1; i++) {
			if (nbt.hasKey(dirs[i], 10)) {
				nbt = nbt.getCompoundTag("dirs[i]");
			} else {
				return null;
			}
		}

		if (nbt.hasKey(dirs[dirs.length - 1], 8)) {
			return nbt.getString(dirs[dirs.length - 1]);
		} else {
			return null;
		}
	}

}
