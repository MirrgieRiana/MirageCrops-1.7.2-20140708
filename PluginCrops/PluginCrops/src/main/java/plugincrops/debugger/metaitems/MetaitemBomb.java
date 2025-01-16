package plugincrops.debugger.metaitems;

import java.util.List;

import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaitemBomb extends MetaitemPluginCrops
{

	private final boolean isFlaming;
	private final boolean isSmoking;

	public MetaitemBomb(IMetaitem _super, int metaid, Arguments arguments,
		boolean isFlaming, boolean isSmoking)
	{
		super(_super, metaid, arguments);
		this.isFlaming = isFlaming;
		this.isSmoking = isSmoking;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List itemStacks)
	{
		createItemStack(item, itemStacks, 5);
		createItemStack(item, itemStacks, 10);
		createItemStack(item, itemStacks, 30);
		createItemStack(item, itemStacks, 50);
	}

	private void createItemStack(Item item, List itemStacks, int explosionSize)
	{
		ItemStack e = new ItemStack(item, 1, getMetaId());
		{
			NBTTagCompound nbt = new NBTTagCompound();

			nbt.setInteger("explosionSize", explosionSize);

			e.setTagCompound(nbt);
		}
		itemStacks.add(e);
	}

	private int getExplosionSize(ItemStack itemStack)
	{
		return getNBTInteger(itemStack, "explosionSize", 10);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List strings, boolean isShift)
	{
		super.addInformation(itemStack, player, strings, isShift);
		strings.add("explosionSize: " + getExplosionSize(itemStack));
	}

	@Override
	public int getColorFromItemStack(ItemStack itemStack, int pass)
	{
		int explosionSize = getExplosionSize(itemStack);

		if (explosionSize == 5) return 0x000088;
		else if (explosionSize == 10) return 0x008800;
		else if (explosionSize == 30) return 0x888800;
		else if (explosionSize == 50) return 0x880000;

		return super.getColorFromItemStack(itemStack, pass);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (world.isRemote) return true;

		int explosionSize = getExplosionSize(itemStack);

		Explosion explosion = new Explosion(world, null, x, y, z, explosionSize);
		explosion.isFlaming = isFlaming;
		explosion.isSmoking = isSmoking;
		if (ForgeEventFactory.onExplosionStart(world, explosion)) {
			return true;
		}
		explosion.doExplosionA();
		explosion.doExplosionB(true); // パーティクルあり

		return true;
	}

}
