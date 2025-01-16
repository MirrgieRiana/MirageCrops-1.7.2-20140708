package mirrgmods.plugincrops;

import java.util.ArrayList;

import mirrgmods.plugincrops.transform.TransformEntry;
import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.Opcodes;

public class TransformerCrops implements IClassTransformer, Opcodes
{

	public static ArrayList<TransformEntry> entries = new ArrayList<TransformEntry>();

	@Override
	public byte[] transform(String name, String paramString2, byte[] bytes)
	{
		for (TransformEntry entry : entries) {
			bytes = entry.onTransform(name, paramString2, bytes);
		}

		return bytes;
	}

}
