package plugincrops.debugger.metaitems;

import ic2.api.crops.ICropTile;

import java.util.List;

import plugincrops.debugger.HCrop;
import plugincrops.debugger.IC2VersionException;
import plugincrops.debugger.MetaitemPluginCrops;
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

public class MetaitemHourglass extends MetaitemPluginCrops
{

	public MetaitemHourglass(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List itemStacks)
	{
		createItemStack(item, itemStacks, 1, 0);
		createItemStack(item, itemStacks, 10, 0);
		createItemStack(item, itemStacks, 100, 0);
		createItemStack(item, itemStacks, 1000, 0);
		createItemStack(item, itemStacks, 1, 1);
		createItemStack(item, itemStacks, 10, 1);
		createItemStack(item, itemStacks, 100, 1);
		createItemStack(item, itemStacks, 1000, 1);
		createItemStack(item, itemStacks, 1, 2);
		createItemStack(item, itemStacks, 10, 2);
		createItemStack(item, itemStacks, 100, 2);
		createItemStack(item, itemStacks, 1000, 2);
		createItemStack(item, itemStacks, 1, 4);
		createItemStack(item, itemStacks, 10, 4);
		createItemStack(item, itemStacks, 100, 4);
		createItemStack(item, itemStacks, 1000, 4);
	}

	private void createItemStack(Item item, List itemStacks, int times, int reach)
	{
		ItemStack e = new ItemStack(item, 1, getMetaId());
		{
			NBTTagCompound nbt = new NBTTagCompound();

			nbt.setInteger("times", times);
			nbt.setInteger("reach", reach);

			e.setTagCompound(nbt);
		}
		itemStacks.add(e);
	}

	private int getTimes(ItemStack itemStack)
	{
		return getNBTInteger(itemStack, "times", 1);
	}

	private int getReach(ItemStack itemStack)
	{
		return getNBTInteger(itemStack, "reach", 1);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (world.isRemote) return true;

		int reach = getReach(itemStack);
		int times = getTimes(itemStack);
		for (int xi = -reach; xi <= reach; xi++) {
			for (int yi = -1; yi <= 1; yi++) {
				for (int zi = -reach; zi <= reach; zi++) {

					TileEntity tileEntity = world.getTileEntity(x + xi, y + yi, z + zi);
					if (tileEntity == null) continue;
					if (!(tileEntity instanceof ICropTile)) continue;
					ICropTile cropTile = (ICropTile) tileEntity;

					for (int i = 0; i < times; i++) {

						try {
							HCrop.invoke_tick(cropTile);
						} catch (IC2VersionException e) {
							e.printStackTrace();
							return true;
						}

					}

					if (!player.isSneaking()) {
						world.playAuxSFX(2005, x + xi, y + yi, z + zi, 0);
					}

				}
			}
		}

		return true;
	}

	@Override
	public int getColorFromItemStack(ItemStack itemStack, int pass)
	{
		int times = getTimes(itemStack);
		int reach = getReach(itemStack);

		if (times == 1) times = 0x00;
		else if (times == 10) times = 0x66;
		else if (times == 100) times = 0xaa;
		else if (times == 1000) times = 0xff;

		if (reach == 0) reach = 0x00;
		else if (reach == 1) reach = 0x66;
		else if (reach == 2) reach = 0xaa;
		else if (reach == 4) reach = 0xff;

		return (times << 8) | reach;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List strings, boolean isShift)
	{
		super.addInformation(itemStack, player, strings, isShift);
		strings.add("スニーク中はパーティクルを制限します。");
		strings.add("times: " + getTimes(itemStack));
		strings.add("reach: " + getReach(itemStack));
	}

}
