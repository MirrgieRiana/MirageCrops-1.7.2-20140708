package plugincrops.debugger;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.item.ItemStack;

public class HCrop
{

	public static void set_upgraded(ICropTile cropTile, boolean b) throws IC2VersionException
	{
		try {
			cropTile.getClass().getField("upgraded").set(cropTile, true);
		} catch (IllegalArgumentException e) {
			throw new IC2VersionException(e);
		} catch (IllegalAccessException e) {
			throw new IC2VersionException(e);
		} catch (NoSuchFieldException e) {
			throw new IC2VersionException(e);
		} catch (SecurityException e) {
			throw new IC2VersionException(e);
		}
	}

	public static void invoke_attemptCrossing(ICropTile cropTile) throws IC2VersionException
	{
		try {
			cropTile.getClass().getMethod("attemptCrossing").invoke(cropTile);
		} catch (IllegalAccessException e) {
			throw new IC2VersionException(e);
		} catch (IllegalArgumentException e) {
			throw new IC2VersionException(e);
		} catch (InvocationTargetException e) {
			throw new IC2VersionException(e);
		} catch (NoSuchMethodException e) {
			throw new IC2VersionException(e);
		} catch (SecurityException e) {
			throw new IC2VersionException(e);
		}
	}

	public static void invoke_tick(ICropTile cropTile) throws IC2VersionException
	{
		try {
			cropTile.getClass().getMethod("tick").invoke(cropTile);
		} catch (IllegalAccessException e) {
			throw new IC2VersionException(e);
		} catch (IllegalArgumentException e) {
			throw new IC2VersionException(e);
		} catch (InvocationTargetException e) {
			throw new IC2VersionException(e);
		} catch (NoSuchMethodException e) {
			throw new IC2VersionException(e);
		} catch (SecurityException e) {
			throw new IC2VersionException(e);
		}
	}

	public static int invoke_calculateRatioFor(ICropTile cropTile, CropCard newCrop, CropCard oldCrop) throws IC2VersionException
	{
		try {
			return (Integer) cropTile.getClass().getMethod("calculateRatioFor", CropCard.class, CropCard.class).invoke(cropTile, newCrop, oldCrop);
		} catch (IllegalAccessException e) {
			throw new IC2VersionException(e);
		} catch (IllegalArgumentException e) {
			throw new IC2VersionException(e);
		} catch (InvocationTargetException e) {
			throw new IC2VersionException(e);
		} catch (NoSuchMethodException e) {
			throw new IC2VersionException(e);
		} catch (SecurityException e) {
			throw new IC2VersionException(e);
		}
	}

	public static ItemStack getItemStackSeed() throws IC2VersionException
	{
		try {
			return (ItemStack) Class.forName("ic2.core.Ic2Items").getField("cropSeed").get(null);
		} catch (IllegalArgumentException e) {
			throw new IC2VersionException(e);
		} catch (IllegalAccessException e) {
			throw new IC2VersionException(e);
		} catch (NoSuchFieldException e) {
			throw new IC2VersionException(e);
		} catch (SecurityException e) {
			throw new IC2VersionException(e);
		} catch (ClassNotFoundException e) {
			throw new IC2VersionException(e);
		}
	}

}
