package miragecrops.framework.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockMeta extends ItemBlock
{
	protected final BlockMeta blockMeta;

	public ItemBlockMeta(Block p_i45346_1_, BlockMeta p_i45346_2_)
	{
		super(p_i45346_1_);

		this.blockMeta = p_i45346_2_;

		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int paramInt)
	{
		return this.blockMeta.getIcon(2, paramInt);
	}

	@Override
	public int getMetadata(int paramInt)
	{
		return paramInt;
	}

	@Override
	public String getUnlocalizedName(ItemStack paramItemStack)
	{
		return blockMeta.getUnlocalizedName(paramItemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		blockMeta.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		return blockMeta.getItemStackDisplayName(par1ItemStack);
	}

}
