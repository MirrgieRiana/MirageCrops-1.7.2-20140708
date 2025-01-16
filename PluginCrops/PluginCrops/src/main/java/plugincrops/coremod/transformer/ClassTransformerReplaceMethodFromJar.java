package plugincrops.coremod.transformer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.Level;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import plugincrops.api.DeobfuscatedName;
import plugincrops.coremod.util.HDeobf;
import cpw.mods.fml.common.FMLLog;

public class ClassTransformerReplaceMethodFromJar extends ClassTransformerSingleClass
{

	protected URL urlJar;
	protected String methodNameDeobfuscated;
	protected String signatureDeobfuscated;
	protected boolean addition = false;

	public ClassTransformerReplaceMethodFromJar(
		String targetClassName,
		URL urlJar,
		String methodNameDeobfuscated,
		String signatureDeobfuscated)
	{
		super(targetClassName);
		this.urlJar = urlJar;
		this.methodNameDeobfuscated = methodNameDeobfuscated;
		this.signatureDeobfuscated = signatureDeobfuscated;
	}

	public ClassTransformerReplaceMethodFromJar setAddition(boolean addition)
	{
		this.addition = addition;
		return this;
	}

	protected InputStream getInputStream()
	{
		InputStream inputStreamJar;
		try {
			inputStreamJar = urlJar.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		ZipInputStream zipInputStream = new ZipInputStream(inputStreamJar);

		String entryName = getTargetClassName().replaceAll("\\.", "/") + ".class";

		for (;;) {
			ZipEntry entry;
			try {
				entry = zipInputStream.getNextEntry();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (entry == null) {
				throw new RuntimeException("No such entry `" + entryName + "` in `" + urlJar.toString() + "`");
			}

			if (entry.getName().equals(entryName)) {
				return zipInputStream;
			}

		}
	}

	@Override
	public void transform(ClassNode classNode, String name2)
	{

		// load and apply replacement class
		MethodNode methodNodeReplacement;
		{
			InputStream inputStream = getInputStream();

			ClassNode classNodeReplacement = new ClassNode();
			{
				ClassReader classReaderReplacement;
				try {
					classReaderReplacement = new ClassReader(inputStream);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				classReaderReplacement.accept(classNodeReplacement, 0);
			}

			methodNodeReplacement = getTargetMethodNode(classNodeReplacement);

			if (methodNodeReplacement == null) {
				throw new RuntimeException("can not find `" + getIdentificationName() + "`");
			}

		}

		// remove default method
		a:
		{
			for (Iterator<MethodNode> iterator = classNode.methods.iterator(); iterator.hasNext();) {
				MethodNode methodNode = iterator.next();
				if (methodNode.name.equals(methodNodeReplacement.name)) {
					if (methodNode.signature == null || methodNode.signature.equals(methodNodeReplacement.signature)) {

						iterator.remove();

						break a;
					}
				}
			}
			if (!addition) {
				FMLLog.severe("No such method: %s", getIdentificationName());
			}
		}

		classNode.methods.add(methodNodeReplacement);

		FMLLog.log(Level.INFO, "Successfully replaced method: %s", getIdentificationName());
	}

	protected MethodNode getTargetMethodNode(ClassNode classNodeReplacement)
	{
		for (MethodNode methodNode : classNodeReplacement.methods) {
			String deobfuscatedName[] = getDeobfuscatedName(methodNode);

			if (deobfuscatedName != null) {
				if (deobfuscatedName[0].equals(methodNameDeobfuscated)) {
					if (deobfuscatedName[1].equals(signatureDeobfuscated)) {

						return methodNode;

					}
				}
			}
		}

		return null;
	}

	protected String[] getDeobfuscatedName(MethodNode methodNode)
	{
		String signatureAnnotation = "L" + DeobfuscatedName.class.getName().replace('.', '/') + ";";

		if (methodNode.invisibleAnnotations != null) {
			for (AnnotationNode annotationNode : methodNode.invisibleAnnotations) {
				if (annotationNode.desc.equals(signatureAnnotation)) {
					if (annotationNode.values.size() == 2) {
						if (annotationNode.values.get(0).equals("value")) {

							String str = (String) annotationNode.values.get(1);

							return new String[] {
								str.substring(0, str.indexOf("(")),
								str.substring(str.indexOf("("), str.length()),
							};
						}
					}
				}
			}
		}

		return null;
	}

	protected static Pattern patternClassSignature = Pattern.compile("L([^;]+);");

	@Deprecated
	public String getSuitableSignature()
	{
		if (HDeobf.isDeobfuscated()) {
			return signatureDeobfuscated;
		} else {
			StringBuffer sb = new StringBuffer();
			int index = 0;
			Matcher matcher = patternClassSignature.matcher(signatureDeobfuscated);

			while (matcher.find()) {
				sb.append(signatureDeobfuscated.substring(index, matcher.start()));
				sb.append("L" + unmap(matcher.group(1)) + ";");
				index = matcher.end();
			}

			sb.append(signatureDeobfuscated.substring(index, signatureDeobfuscated.length()));

			return sb.toString();
		}
	}

	public String getIdentificationName()
	{
		return getTargetClassName() + "#" + methodNameDeobfuscated + signatureDeobfuscated;
	}

}
