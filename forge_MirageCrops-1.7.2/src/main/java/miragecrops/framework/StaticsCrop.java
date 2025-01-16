package miragecrops.framework;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class StaticsCrop
{

	static final Field fieldUpgraded;
	static final Field fieldTicker;
	static final Field fieldDirty;
	static final Field fieldGrowthPoints;
	static final Method methodCalcGrowthRate;

	static {

		{
			Field field = null;
			try {
				field = Class.forName("ic2.core.block.TileEntityCrop").getField("upgraded");
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			fieldUpgraded = field;
		}

		{
			Field field = null;
			try {
				field = Class.forName("ic2.core.block.TileEntityCrop").getField("ticker");
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			fieldTicker = field;
		}

		{
			Field field = null;
			try {
				field = Class.forName("ic2.core.block.TileEntityCrop").getField("dirty");
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			fieldDirty = field;
		}

		{
			Field field = null;
			try {
				field = Class.forName("ic2.core.block.TileEntityCrop").getField("growthPoints");
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			fieldGrowthPoints = field;
		}

		{
			Method method = null;
			try {
				method = Class.forName("ic2.core.block.TileEntityCrop").getMethod("calcGrowthRate");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			methodCalcGrowthRate = method;
		}

	}

	public static boolean isUpgraded(ICropTile iCropTile)
	{
		if (fieldUpgraded != null) {
			try {
				return fieldUpgraded.getBoolean(iCropTile);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static void setUpgraded(ICropTile iCropTile, boolean value)
	{
		if (fieldUpgraded != null) {
			try {
				fieldUpgraded.setBoolean(iCropTile, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static char getTicker(ICropTile iCropTile)
	{
		if (fieldTicker != null) {
			try {
				return fieldTicker.getChar(iCropTile);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static void setTicker(ICropTile iCropTile, char value)
	{
		if (fieldTicker != null) {
			try {
				fieldTicker.setChar(iCropTile, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isDirty(ICropTile iCropTile)
	{
		if (fieldDirty != null) {
			try {
				return fieldDirty.getBoolean(iCropTile);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static void setDirty(ICropTile iCropTile, boolean value)
	{
		if (fieldDirty != null) {
			try {
				fieldDirty.setBoolean(iCropTile, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getGrowthPoints(ICropTile iCropTile)
	{
		if (fieldGrowthPoints != null) {
			try {
				return fieldGrowthPoints.getInt(iCropTile);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static void setGrowthPoints(ICropTile iCropTile, int value)
	{
		if (fieldGrowthPoints != null) {
			try {
				fieldGrowthPoints.setInt(iCropTile, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static int calcGrowthRate(ICropTile iCropTile)
	{
		if (methodCalcGrowthRate != null) {
			try {
				return Integer.parseInt(methodCalcGrowthRate.invoke(iCropTile).toString());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static void putCrop(int id, CropCard addition)
	{
		CropCard existing = Crops.instance.getCropList()[id];
		if (existing != null) throw new RuntimeException();

		Crops.instance.getCropList()[id] = addition;

	}

	public static void sendChatToPlayer(EntityPlayer player, String string)
	{
		if (player instanceof EntityPlayerMP) {
			IChatComponent msg = new ChatComponentTranslation(string);
			((EntityPlayerMP) player).addChatMessage(msg);
		}
	}

	public static void dropAsEntity(World world, int x, int y, int z, ItemStack itemStack)
	{
		if (world.isRemote) return;
		if (itemStack == null) return;

		double f = 0.7D;
		double dx = world.rand.nextFloat() * f + (1.0D - f) * 0.5D;
		double dy = world.rand.nextFloat() * f + (1.0D - f) * 0.5D;
		double dz = world.rand.nextFloat() * f + (1.0D - f) * 0.5D;

		EntityItem entityItem = new EntityItem(world, x + dx, y + dy, z + dz, itemStack.copy());
		entityItem.delayBeforeCanPickup = 10;
		world.spawnEntityInWorld(entityItem);
	}

}
