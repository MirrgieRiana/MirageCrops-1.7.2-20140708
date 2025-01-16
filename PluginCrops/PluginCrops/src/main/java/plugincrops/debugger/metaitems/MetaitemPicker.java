package plugincrops.debugger.metaitems;

import static net.minecraft.util.EnumChatFormatting.*;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;

import java.util.List;

import plugincrops.debugger.HCrop;
import plugincrops.debugger.IC2VersionException;
import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.ModPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaitemPicker extends MetaitemPluginCrops
{

	public MetaitemPicker(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	private String getOwner(ItemStack itemStack)
	{
		return getNBTString(itemStack, "owner", "");
	}

	private String getName(ItemStack itemStack)
	{
		return getNBTString(itemStack, "name", "");
	}

	private int getGrowth(ItemStack itemStack)
	{
		return getNBTInteger(itemStack, "growth", 1);
	}

	private int getGain(ItemStack itemStack)
	{
		return getNBTInteger(itemStack, "gain", 1);
	}

	private int getResistance(ItemStack itemStack)
	{
		return getNBTInteger(itemStack, "resistance", 1);
	}

	private int getScanLevel(ItemStack itemStack)
	{
		return getNBTInteger(itemStack, "scanLevel", 1);
	}

	private int getSize(ItemStack itemStack)
	{
		return getNBTInteger(itemStack, "size", 1);
	}

	private static final int[][] table = {
		{
			0, 0, 0,
		},
		{
			31, 0, 0,
		},
		{
			0, 31, 0,
		},
		{
			0, 0, 31,
		},
		{
			23, 31, 0,
		},
		{
			31, 31, 31,
		},
	};

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List itemStacks)
	{
		super.getSubItems(item, creativeTabs, itemStacks);

		ItemStack cropSeed;
		try {
			cropSeed = HCrop.getItemStackSeed();
		} catch (IC2VersionException e) {
			return;
		}
		if (cropSeed != null) {

			for (int[] record : table) {
				for (CropCard cropCard : Crops.instance.getCrops()) {
					itemStacks.add(generateItemStackFromValues(
						cropCard, cropSeed, (byte) record[0], (byte) record[1], (byte) record[2], (byte) 4));
				}
				itemStacks.add(new ItemStack(Blocks.iron_block));
			}

		}
	}

	public static ItemStack generateItemStackFromValues(CropCard crop, ItemStack honzon, byte statGrowth, byte statGain, byte statResistance, byte scan)
	{
		ItemStack stack = new ItemStack(honzon.getItem());
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("owner", crop.owner());
		tag.setString("name", crop.name());
		tag.setByte("growth", statGrowth);
		tag.setByte("gain", statGain);
		tag.setByte("resistance", statResistance);
		tag.setByte("scan", scan);
		stack.setTagCompound(tag);
		return stack;
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (world.isRemote) return true;

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null) return true;
		if (!(tileEntity instanceof ICropTile)) return true;
		ICropTile cropTile = (ICropTile) tileEntity;

		if (player.isSneaking()) {

			CropCard cropCard = cropTile.getCrop();

			if (cropCard == null) {
				itemStack.setTagCompound(null);
			} else {
				NBTTagCompound nbt = new NBTTagCompound();

				nbt.setString("owner", cropCard.owner());
				nbt.setString("name", cropCard.name());
				nbt.setInteger("growth", cropTile.getGrowth());
				nbt.setInteger("gain", cropTile.getGain());
				nbt.setInteger("resistance", cropTile.getResistance());
				nbt.setInteger("scanLevel", cropTile.getScanLevel());
				nbt.setInteger("size", cropTile.getSize());

				itemStack.setTagCompound(nbt);
			}
		} else {

			String owner = getOwner(itemStack);
			String name = getName(itemStack);
			CropCard cropCard = Crops.instance.getCropCard(owner, name);

			if (cropCard != null) {
				cropTile.reset();

				cropTile.setCrop(cropCard);
				cropTile.setGrowth((byte) getGrowth(itemStack));
				cropTile.setGain((byte) getGain(itemStack));
				cropTile.setResistance((byte) getResistance(itemStack));
				cropTile.setScanLevel((byte) getScanLevel(itemStack));
				cropTile.setSize((byte) getSize(itemStack));

			} else {
				ModPluginCrops.platform.message2(player, "unknown crop: %s:%s",
					owner,
					name);
			}
		}

		return true;
	}

	@Override
	public int getColorFromItemStack(ItemStack itemStack, int pass)
	{
		String owner = getOwner(itemStack);
		String name = getName(itemStack);

		return (owner + ":" + name).hashCode() & 0xffffff;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List strings, boolean isShift)
	{
		super.addInformation(itemStack, player, strings, isShift);
		strings.add("Shift右クリック: " + BLUE + "コピー" + RESET);
		strings.add("右クリック: " + RED + "貼り付け" + RESET);
		strings.add("crop: " + getOwner(itemStack) + ":" + getName(itemStack));
		strings.add("grown: " + getGrowth(itemStack));
		strings.add("gain: " + getGain(itemStack));
		strings.add("resistance: " + getResistance(itemStack));
		strings.add("scanLevel: " + getScanLevel(itemStack));
		strings.add("size: " + getSize(itemStack));
	}

}
