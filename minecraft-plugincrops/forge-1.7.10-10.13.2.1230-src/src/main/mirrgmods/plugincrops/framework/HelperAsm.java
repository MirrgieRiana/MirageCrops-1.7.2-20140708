package mirrgmods.plugincrops.framework;

import java.util.ListIterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class HelperAsm
{

	public static MethodNode getMethodNode(ClassNode cnode, String name, String desc) throws NoSuchMethodException
	{
		MethodNode mnode = null;
	
		for (MethodNode curMnode : cnode.methods) {
			if (name.equals(curMnode.name) && desc.equals(curMnode.desc)) {
				mnode = curMnode;
				break;
			}
		}
	
		if (mnode == null) throw new NoSuchMethodException(name + desc);
		return mnode;
	}

	public static MethodNode getMethodNode(byte[] bytes, String name, String desc) throws NoSuchMethodException
	{
		ClassNode cnode = new ClassNode();
		ClassReader reader = new ClassReader(bytes);
		reader.accept(cnode, 0);
	
		return getMethodNode(cnode, name, desc);
	}

	public static void print(MethodNode mnode)
	{
		ListIterator<AbstractInsnNode> iterator = mnode.instructions.iterator();
		while (iterator.hasNext()) {
			AbstractInsnNode node = iterator.next();
	
			String str = "";
	
			str += node.getClass().getSimpleName();
			str += "[";
			str += node.getOpcode();
			str += "] ";
	
			if (node instanceof VarInsnNode) {
				str += String.format("%d", ((VarInsnNode) node).var);
			} else if (node instanceof JumpInsnNode) {
				str += String.format("%s", ((JumpInsnNode) node).label.getLabel());
			} else if (node instanceof LabelNode) {
				str += String.format("%s", ((LabelNode) node).getLabel());
			} else if (node instanceof MethodInsnNode) {
				str += String.format("%s %s %s", ((MethodInsnNode) node).owner, ((MethodInsnNode) node).name, ((MethodInsnNode) node).desc);
			} else if (node instanceof InsnNode) {
				str += String.format("%d", ((InsnNode) node).getType());
			} else if (node instanceof IntInsnNode) {
				str += String.format("%d", ((IntInsnNode) node).operand);
			} else if (node instanceof FieldInsnNode) {
				str += String.format("%s %s %s", ((FieldInsnNode) node).owner, ((FieldInsnNode) node).name, ((FieldInsnNode) node).desc);
			} else if (node instanceof LdcInsnNode) {
				str += String.format("%s %s", ((LdcInsnNode) node).cst.getClass(), ((LdcInsnNode) node).cst);
			} else if (node instanceof LineNumberNode) {
				str += String.format("<<<<< %d >>>>>", ((LineNumberNode) node).line);
			} else {
				str += String.format("%s %d", node.toString(), node.getType());
			}
	
			System.out.println(str);
		}
	}

}
