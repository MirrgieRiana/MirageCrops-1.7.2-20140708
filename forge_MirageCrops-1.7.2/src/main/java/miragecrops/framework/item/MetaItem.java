package miragecrops.framework.item;

import java.util.ArrayList;
import java.util.List;

import miragecrops.framework.multiicon.MultiIcon;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaItem extends MetaItemImpl
{

	protected MultiIcon multiIcon;
	protected String description;

	public MetaItem(ItemMeta itemMeta, int metaId)
	{
		super(itemMeta, metaId);
	}

	public void setMultiIcon(MultiIcon multiIcon)
	{
		this.multiIcon = multiIcon;
	}

	public MultiIcon getMultiIcon()
	{
		return multiIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata)
	{
		if (multiIcon != null) return multiIcon.getLength();
		return super.getRenderPasses(metadata);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass)
	{
		if (multiIcon != null) return multiIcon.getIcon(pass);
		return super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		if (multiIcon != null) {
			multiIcon.register(par1IconRegister);
			return;
		}
		super.registerIcons(par1IconRegister);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int pass)
	{
		if (multiIcon != null) return multiIcon.getColor(pass);
		return super.getColorFromItemStack(par1ItemStack, pass);
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean debug)
	{
		if (description != null) par3List.add(description);
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, debug);
		if (debug) par3List.add("MultiIcon: " + (multiIcon != null));
		if (debug) par3List.add("UnlocalizedName: " + getUnlocalizedNamePlain());

		if (debug) {
			ItemStack itemStack = createItemStack();
			String[] oreNames = OreDictionary.getOreNames();

			for (String oreName : oreNames) {
				ArrayList<ItemStack> ores = OreDictionary.getOres(oreName);
				for (ItemStack ore : ores) {
					if (OreDictionary.itemMatches(ore, itemStack, false)) {
						par3List.add("OreName: " + oreName);
						break;
					}
				}
			}

		}

	}

}
