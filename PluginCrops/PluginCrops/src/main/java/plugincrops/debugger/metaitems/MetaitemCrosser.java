package plugincrops.debugger.metaitems;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;

import java.util.ArrayList;
import java.util.List;

import plugincrops.debugger.HCrop;
import plugincrops.debugger.IC2VersionException;
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

public class MetaitemCrosser extends MetaitemPluginCrops
{

	public MetaitemCrosser(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List itemStacks)
	{
		createItemStack(item, itemStacks, "normal");
		createItemStack(item, itemStacks, "success");
		createItemStack(item, itemStacks, "mutate");
	}

	private void createItemStack(Item item, List itemStacks, String type)
	{
		ItemStack e = new ItemStack(item, 1, getMetaId());
		{
			NBTTagCompound nbt = new NBTTagCompound();

			nbt.setString("type", type);

			e.setTagCompound(nbt);
		}
		itemStacks.add(e);
	}

	private String getType(ItemStack itemStack)
	{
		return getNBTString(itemStack, "type", "");
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (world.isRemote) return true;

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null) return true;
		if (!(tileEntity instanceof ICropTile)) return true;
		ICropTile cropTile = (ICropTile) tileEntity;

		for (int i = 0; i < 500; i++) {
			cropTile.reset();

			try {
				HCrop.set_upgraded(cropTile, true);
				HCrop.invoke_attemptCrossing(cropTile);
			} catch (IC2VersionException e) {
				e.printStackTrace();
				return true;
			}

			if (getType(itemStack).equals("normal")) {

			} else if (getType(itemStack).equals("success")) {
				if (cropTile.getCrop() == null) continue;
			} else if (getType(itemStack).equals("mutate")) {
				if (cropTile.getCrop() == null) continue;
				if (!isMutated(cropTile.getCrop(), world, x, y, z)) continue;
			}

			if (cropTile.getCrop() == null) {
				ModPluginCrops.platform.message2(player, "%s (%s missed!)", null, i);
			} else {
				ModPluginCrops.platform.message2(player, "%s:%s (%s missed!) GrGaRe=(%s, %s, %s)",
					cropTile.getCrop().owner(),
					cropTile.getCrop().name(),
					i,
					cropTile.getGrowth(),
					cropTile.getGain(),
					cropTile.getResistance());
			}

			return true;
		}
		ModPluginCrops.platform.message2(player, "%s missed!", 500);

		return true;
	}

	private boolean isMutated(CropCard crop, World world, int x, int y, int z)
	{
		ArrayList<TileEntity> tileEntities = new ArrayList<TileEntity>();
		tileEntities.add(world.getTileEntity(x + 1, y, z));
		tileEntities.add(world.getTileEntity(x - 1, y, z));
		tileEntities.add(world.getTileEntity(x, y, z + 1));
		tileEntities.add(world.getTileEntity(x, y, z - 1));

		ArrayList<ICropTile> cropTiles = new ArrayList<ICropTile>();
		for (int i = 0; i < tileEntities.size(); i++) {
			if (tileEntities.get(i) instanceof ICropTile) {
				cropTiles.add((ICropTile) tileEntities.get(i));
			}
		}

		for (ICropTile cropTile : cropTiles) {
			if (crop == cropTile.getCrop()) return false;
		}

		return true;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List strings, boolean isShift)
	{
		super.addInformation(itemStack, player, strings, isShift);
		strings.add("type: " + getType(itemStack));
	}

	@Override
	public int getColorFromItemStack(ItemStack itemStack, int pass)
	{
		String type = getType(itemStack);

		if (type.equals("normal")) return 0x008800;
		else if (type.equals("success")) return 0x00aa00;
		else if (type.equals("mutate")) return 0x00ff00;
		else return 0x008800;
	}

}
