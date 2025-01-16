package mirrg.minecraft.item.multi.copper;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class Metaitem implements IMetaitem
{

	public final IMetaitem _super;
	public final int metaId;

	public Metaitem(IMetaitem _super, int metaId)
	{
		this._super = _super;
		this.metaId = metaId;
	}

	@Override
	public int getMetaId()
	{
		return metaId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> itemStacks)
	{
		itemStacks.add(new ItemStack(item, 1, getMetaId()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		return _super.getIconFromDamage(damage);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemStack, int pass)
	{
		return _super.getColorFromItemStack(itemStack, pass);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		return _super.onItemUse(itemStack, player, world, x, y, z, side, x2, y2, z2);
	}

}
