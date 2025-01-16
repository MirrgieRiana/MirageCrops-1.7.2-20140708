package miragecrops.core.ores.toolcrafting;

import java.util.Set;

import miragecrops.framework.multiicon.IMultiIcon;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

class ItemToolMultiIcon extends ItemTool
{

	protected IMultiIcon iMultiIcon;

	public ItemToolMultiIcon(float damageVsEntity, ToolMaterial toolMaterial, Set<Block> efficiencyBlocks)
	{
		super(damageVsEntity, toolMaterial, efficiencyBlocks);
	}

	public void setMultiIcon(IMultiIcon iMultiIcon)
	{
		this.iMultiIcon = iMultiIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata)
	{
		if (iMultiIcon != null) return iMultiIcon.getLength();
		return super.getRenderPasses(metadata);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass)
	{
		if (iMultiIcon != null) return iMultiIcon.getIcon(pass);
		return super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		if (iMultiIcon != null) {
			iMultiIcon.register(par1IconRegister);
			return;
		}
		super.registerIcons(par1IconRegister);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int pass)
	{
		if (iMultiIcon != null) return iMultiIcon.getColor(pass);
		return super.getColorFromItemStack(par1ItemStack, pass);
	}

}
