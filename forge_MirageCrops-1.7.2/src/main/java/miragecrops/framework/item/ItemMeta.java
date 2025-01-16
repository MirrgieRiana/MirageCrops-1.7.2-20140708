package miragecrops.framework.item;

import java.util.List;

import miragecrops.api.framework.item.IItemMeta;
import mirrg.mir34.modding.IMod;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMeta extends Item implements IItemMeta
{

	public MetaItem[] metaItems = new MetaItem[512];
	public MetaItem metaItemDefault;

	public ItemMeta(IMod iMod)
	{
		metaItemDefault = new MetaItem(this, 0);
		metaItemDefault.setTextureName(iMod.getModId() + ":NULL_ICON");
		setMetaItem(metaItemDefault.getMetaId(), metaItemDefault);

		setHasSubtypes(true);
	}

	@Override
	public Item getItem()
	{
		return this;
	}

	public Integer setMetaItem(MetaItem metaItem)
	{
		for (int i = 0; i < metaItems.length; i++) {
			if (metaItems[i] == null) {
				metaItems[i] = metaItem;
				return i;
			}
		}
		return null;
	}

	public void setMetaItem(int id, MetaItem metaItem)
	{
		if (metaItems[id] == null) {
			metaItems[id] = metaItem;
		} else {
			throw new DuplicateMetaItemRegisterException(
				metaItem.getUnlocalizedName() + " -> " + metaItem.getItem());
		}
	}

	@Override
	public MetaItem getMetaItem(int meta)
	{
		MetaItem metaItem = metaItems[meta];
		if (metaItem == null) return metaItemDefault;
		return metaItem;
	}

	public MetaItem getMetaItem(ItemStack itemStack)
	{
		return getMetaItem(itemStack.getItemDamage());
	}

	// --------------------------------------------------------------------------

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
	{
		for (int i = 1; i < metaItems.length; i++) {
			if (metaItems[i] != null) {
				metaItems[i].getSubItems(p_150895_1_, p_150895_2_, p_150895_3_);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		for (int i = 0; i < metaItems.length; i++) {
			if (metaItems[i] != null) {
				metaItems[i].registerIcons(par1IconRegister);
			}
		}
	}

	// --------------------------------------------------------------------------

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(
		ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean debug)
	{
		getMetaItem(par1ItemStack).addInformation(par1ItemStack, par2EntityPlayer, par3List, debug);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return getMetaItem(par1).getIconFromDamage(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack itemStack)
	{
		return getMetaItem(itemStack).getIconIndex(itemStack);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World world,
		int x, int y, int z, int side, float x2, float y2, float z2)
	{
		return getMetaItem(itemStack).onItemUse(
			itemStack, par2EntityPlayer, world,
			x, y, z, side, x2, y2, z2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return getMetaItem(par1ItemStack).getColorFromItemStack(par1ItemStack, par2);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return getMetaItem(par1ItemStack).getMaxItemUseDuration(par1ItemStack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{
		return getMetaItem(itemStack).getItemStackDisplayName(itemStack);
	}

	@Override
	public boolean isItemTool(ItemStack par1ItemStack)
	{
		return getMetaItem(par1ItemStack).isItemTool(par1ItemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return getMetaItem(par1).getIconFromDamageForRenderPass(par1, par2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		return getMetaItem(stack).getIcon(stack, renderPass, player, usingItem, useRemaining);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata)
	{
		return getMetaItem(metadata).getRenderPasses(metadata);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		return getMetaItem(itemStack).getContainerItem(itemStack);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return getMetaItem(stack).hasContainerItem(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return getMetaItem(stack).getIcon(stack, pass);
	}

	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack)
	{
		return getMetaItem(itemStack).canHarvestBlock(par1Block, itemStack);
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return getMetaItem(stack).getItemStackLimit(stack);
	}

}
