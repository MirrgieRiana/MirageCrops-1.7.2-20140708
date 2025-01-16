package plugincrops.coremod.transformer;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.FMLLaunchHandler;

public abstract class ClassTransformerSingleClass implements IClassTransformer, Opcodes
{

	protected String targetClassName;
	public boolean client = true;
	public boolean server = true;

	public ClassTransformerSingleClass(String targetClassName)
	{
		this.targetClassName = targetClassName;
	}

	protected boolean isSideValid()
	{
		if (client && FMLLaunchHandler.side().isClient()) return true;
		if (server && FMLLaunchHandler.side().isServer()) return true;
		return false;
	}

	protected String unmap(String className)
	{
		return FMLDeobfuscatingRemapper.INSTANCE.unmap(className);
	}

	public String getTargetClassName()
	{
		return unmap(targetClassName);
	}

	@Override
	public byte[] transform(String name, String paramString2, byte[] bytes)
	{
		if (!isSideValid()) return bytes;
		if (!name.equals(targetClassName)) return bytes;

		ClassNode classNode = new ClassNode();
		{
			ClassReader classReader = new ClassReader(bytes);
			classReader.accept(classNode, 0);
		}

		transform(classNode, paramString2);

		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		classNode.accept(classWriter);
		bytes = classWriter.toByteArray();

		return bytes;
	}

	public abstract void transform(ClassNode classNode, String name2);

	public ClassTransformerSingleClass setClient(boolean client)
	{
		this.client = client;
		return this;
	}

	public ClassTransformerSingleClass setServer(boolean server)
	{
		this.server = server;
		return this;
	}

}
