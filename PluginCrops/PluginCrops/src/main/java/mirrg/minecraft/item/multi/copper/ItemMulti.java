package mirrg.minecraft.item.multi.copper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMulti extends Item
{

	public ArrayList<Metaitem> metaitems = new ArrayList<Metaitem>();

	public Metaitem getMetaitem(ItemStack itemStack)
	{
		return getMetaitem(itemStack.getItemDamage());
	}

	public Metaitem getMetaitem(int damage)
	{
		if (damage < 0) return getMetaitem(0);
		if (damage >= metaitems.size()) return getMetaitem(0);
		Metaitem metaitem = metaitems.get(damage);
		return metaitem != null ? metaitem : getMetaitem(0);
	}

	/**
	 * Itemでの実装と同じコードを書いた素体。
	 * 単にsuperを呼んでるだけ。
	 */
	public IMetaitem getSuper()
	{
		return new IMetaitem() {

			@Override
			public int getMetaId()
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public String getUnlocalizedName(ItemStack itemStack)
			{
				return ItemMulti.super.getUnlocalizedName(itemStack);
			}

			@Override
			@SideOnly(Side.CLIENT)
			public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> strings, boolean isShift)
			{
				ItemMulti.super.addInformation(itemStack, player, strings, isShift);
			}

			@Override
			@SideOnly(Side.CLIENT)
			public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> itemStacks)
			{
				throw new UnsupportedOperationException();
			}

			@SideOnly(Side.CLIENT)
			@Override
			public void registerIcons(IIconRegister iconRegister)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
			{
				return ItemMulti.super.onItemUse(itemStack, player, world, x, y, z, side, x2, y2, z2);
			}

			@SideOnly(Side.CLIENT)
			@Override
			public IIcon getIconFromDamage(int damage)
			{
				return ItemMulti.super.getIconFromDamage(damage);
			}

			@SideOnly(Side.CLIENT)
			@Override
			public int getColorFromItemStack(ItemStack itemStack, int pass)
			{
				return ItemMulti.super.getColorFromItemStack(itemStack, pass);
			}

		};
	}

	public ItemMulti()
	{
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List itemStacks)
	{
		for (int i = 0; i < metaitems.size(); i++) {
			if (metaitems.get(i) != null) {
				metaitems.get(i).getSubItems(item, creativeTabs, itemStacks);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		for (int i = 0; i < metaitems.size(); i++) {
			if (metaitems.get(i) != null) {
				metaitems.get(i).registerIcons(iconRegister);
			}
		}
		super.registerIcons(iconRegister);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		return getMetaitem(damage).getIconFromDamage(damage);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemStack, int pass)
	{
		return getMetaitem(itemStack).getColorFromItemStack(itemStack, pass);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		return getMetaitem(itemStack).onItemUse(itemStack, player, world, x, y, z, side, x2, y2, z2);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return getMetaitem(itemStack).getUnlocalizedName(itemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List strings, boolean isShift)
	{
		getMetaitem(itemStack).addInformation(itemStack, player, strings, isShift);
	}

}
