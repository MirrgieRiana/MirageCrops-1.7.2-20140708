package miragecrops.core.biome;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ReflectionsThaumcraft
{

	public static Class classThaumcraftWorldGenerator;
	public static Class<? extends BiomeGenBase> classBiomeGenTaint;
	public static BiomeGenBase biomeTaint;
	public static Method methodDecorateSpecial;
	public static boolean thaumcraftOk = false;

	public static void init()
	{

		try {
			classThaumcraftWorldGenerator = Class.forName("thaumcraft.common.lib.world.ThaumcraftWorldGenerator");
			classBiomeGenTaint = (Class<? extends BiomeGenBase>) Class.forName("thaumcraft.common.lib.world.biomes.BiomeGenTaint");
			biomeTaint = (BiomeGenBase) classThaumcraftWorldGenerator.getField("biomeTaint").get(null);
			methodDecorateSpecial = classBiomeGenTaint.getMethod("decorateSpecial", World.class, Random.class, int.class, int.class);
			thaumcraftOk = true;
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (ClassNotFoundException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (NoSuchFieldException e) {
		}

	}

	public static boolean decorateSpecial(World world, Random random, int x, int z)
	{
		if (!thaumcraftOk) return false;
		try {
			methodDecorateSpecial.invoke(biomeTaint, world, random, x, z);
			return true;
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		return false;
	}

}
