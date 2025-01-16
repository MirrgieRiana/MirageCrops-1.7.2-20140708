package miragecrops.framework.item;

import java.util.List;

import miragecrops.api.framework.item.IMetaItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaItemImpl implements IMetaItem
{

	private final ItemMeta itemMeta;
	private final int metaId;
	protected String unlocalizedName;
	protected String iconString;
	protected ItemStack containerItem;
	protected IIcon itemIcon;

	public MetaItemImpl(ItemMeta itemMeta, int metaId)
	{
		this.itemMeta = itemMeta;
		this.metaId = metaId;
	}

	public int getId()
	{
		return Item.getIdFromItem(itemMeta);
	}

	@Override
	public ItemMeta getItem()
	{
		return itemMeta;
	}

	@Override
	public ItemStack createItemStack()
	{
		return createItemStack(1);
	}

	@Override
	public ItemStack createItemStack(int amount)
	{
		return new ItemStack(getItem(), amount, metaId);
	}

	@Override
	public String getUnlocalizedNamePlain()
	{
		return this.unlocalizedName;
	}

	public String getTextureName()
	{
		return ((this.iconString == null) ? "MISSING_ICON_ITEM_" + getId() + ":" + getMetaId() + "_"
			+ this.unlocalizedName : this.iconString);
	}

	// -----------------------------------------------------------------

	@SideOnly(Side.CLIENT)
	public void addInformation(
		ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean debug)
	{

	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack par1ItemStack)
	{
		return getIconFromDamage(par1ItemStack.getItemDamage());
	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World world,
		int x, int y, int z, int side, float x2, float y2, float z2)
	{
		return false;
	}

	@Override
	public int getMetaId()
	{
		return metaId;
	}

	public void setUnlocalizedName(String unlocalizedNameMeta)
	{
		this.unlocalizedName = unlocalizedNameMeta;
	}

	public String getUnlocalizedNameInefficiently(ItemStack par1ItemStack)
	{
		String s = getUnlocalizedName(par1ItemStack);
		return ((s == null) ? "" : StatCollector.translateToLocal(s));
	}

	public String getUnlocalizedName()
	{
		return "item." + this.unlocalizedName;
	}

	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return "item." + this.unlocalizedName;
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return 16777215;
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 0;
	}

	public String getItemStackDisplayName(ItemStack itemStack)
	{
		return ""
			+ StatCollector.translateToLocal(
				new StringBuilder().append(getUnlocalizedNameInefficiently(itemStack))
					.append(".name").toString()).trim();
	}

	public boolean isItemTool(ItemStack par1ItemStack)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return getIconFromDamage(par1);
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
	{
		p_150895_3_.add(new ItemStack(p_150895_1_, 1, metaId));
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(getIconString());
	}

	public void setTextureName(String textureName)
	{
		this.iconString = textureName;
	}

	@SideOnly(Side.CLIENT)
	protected String getIconString()
	{
		return ((this.iconString == null) ? "MISSING_ICON_ITEM_" + getId() + ":" + getMetaId() + "_"
			+ this.unlocalizedName : this.iconString);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		return getIcon(stack, renderPass);
	}

	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata)
	{
		return 1;
	}

	public ItemStack getContainerItem(ItemStack itemStack)
	{
		if (!(hasContainerItem(itemStack)))
		{
			return null;
		}
		return containerItem.copy();
	}

	public boolean hasContainerItem(ItemStack stack)
	{
		return (this.containerItem != null);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
	}

	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack)
	{
		return false;
	}

	public int getItemStackLimit(ItemStack stack)
	{
		return 64;
	}

}
