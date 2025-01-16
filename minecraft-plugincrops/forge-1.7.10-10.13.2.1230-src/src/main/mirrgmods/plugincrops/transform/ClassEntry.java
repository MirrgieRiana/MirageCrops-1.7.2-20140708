package mirrgmods.plugincrops.transform;

import org.objectweb.asm.commons.Remapper;

public class ClassEntry
{
	public final String name;

	/**
	 *
	 * @param name
	 *            example "java/lang/String"
	 */
	public ClassEntry(String name)
	{
		this.name = name;
	}

	public ClassEntry remap(Remapper remapper)
	{
		String name2 = remapper.map(name);

		return new ClassEntry(name2);
	}

	@Override
	public String toString()
	{
		return name;
	}

}
