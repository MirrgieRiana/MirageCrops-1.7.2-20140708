package mirrgmods.plugincrops.transform;


public class TransformEntryClass extends TransformEntry
{

	protected final ClassEntry classEntry;

	public TransformEntryClass(ClassEntry classEntry)
	{
		this.classEntry = classEntry;
	}

	@Override
	public byte[] onTransform(String name, String paramString2, byte[] bytes)
	{
		if (!name.replaceAll("\\.", "/").equals(classEntry.name)) {
			return super.onTransform(name, paramString2, bytes);
		}

		bytes = onTransform(classEntry, bytes);

		return bytes;
	}

	public byte[] onTransform(ClassEntry classEntry2, byte[] bytes)
	{
		return bytes;
	}

}
