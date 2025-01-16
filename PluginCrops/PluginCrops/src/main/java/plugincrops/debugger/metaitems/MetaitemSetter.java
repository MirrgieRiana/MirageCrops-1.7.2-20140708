package plugincrops.debugger.metaitems;

import ic2.api.crops.ICropTile;

import java.util.List;

import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.ModPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaitemSetter extends MetaitemPluginCrops
{

	public MetaitemSetter(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List itemStacks)
	{
		createItemStack(item, itemStacks, "growth", 1);
		createItemStack(item, itemStacks, "growth", -1);
		createItemStack(item, itemStacks, "gain", 1);
		createItemStack(item, itemStacks, "gain", -1);
		createItemStack(item, itemStacks, "resistance", 1);
		createItemStack(item, itemStacks, "resistance", -1);
		createItemStack(item, itemStacks, "scanLevel", 1);
		createItemStack(item, itemStacks, "scanLevel", -1);
	}

	private void createItemStack(Item item, List itemStacks, String variable, int delta)
	{
		ItemStack e = new ItemStack(item, 1, getMetaId());
		{
			NBTTagCompound nbt = new NBTTagCompound();

			nbt.setString("variable", variable);
			nbt.setInteger("delta", delta);

			e.setTagCompound(nbt);
		}
		itemStacks.add(e);
	}

	private String getVariable(ItemStack itemStack)
	{
		return getNBTString(itemStack, "variable", "");
	}

	private int getDelta(ItemStack itemStack)
	{
		return getNBTInteger(itemStack, "delta", 1);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (world.isRemote) return true;

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null) return true;
		if (!(tileEntity instanceof ICropTile)) return true;
		ICropTile cropTile = (ICropTile) tileEntity;

		String variable = getVariable(itemStack);
		int delta = getDelta(itemStack);

		int before;
		int after;

		if (variable.equals("growth")) {
			before = cropTile.getGrowth();
			after = before;
			after += delta;
			after = Math.min(Math.max(after, 0), 31);
			cropTile.setGrowth((byte) after);
		} else if (variable.equals("gain")) {
			before = cropTile.getGain();
			after = before;
			after += delta;
			after = Math.min(Math.max(after, 0), 31);
			cropTile.setGain((byte) after);
		} else if (variable.equals("resistance")) {
			before = cropTile.getResistance();
			after = before;
			after += delta;
			after = Math.min(Math.max(after, 0), 31);
			cropTile.setResistance((byte) after);
		} else if (variable.equals("scanLevel")) {
			before = cropTile.getScanLevel();
			after = before;
			after += delta;
			after = Math.min(Math.max(after, 0), 4);
			cropTile.setScanLevel((byte) after);
		} else {
			before = 0;
			after = 0;
		}

		ModPluginCrops.platform.message2(player, "%s: %s -> %s",
			variable,
			before,
			after);

		return true;
	}

	@Override
	public int getColorFromItemStack(ItemStack itemStack, int pass)
	{
		String variable = getVariable(itemStack);
		int delta = getDelta(itemStack);

		int color;

		if (variable.equals("growth")) color = 0x00aa00;
		else if (variable.equals("gain")) color = 0xffaa00;
		else if (variable.equals("resistance")) color = 0x00aaaa;
		else color = 0x888888;

		if (delta < 0) {
			color = (color >> 1) & 0x7f7f7f;
		}

		return color;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List strings, boolean isShift)
	{
		super.addInformation(itemStack, player, strings, isShift);
		strings.add("variable: " + getVariable(itemStack));
		strings.add("delta: " + getDelta(itemStack));
	}

}
