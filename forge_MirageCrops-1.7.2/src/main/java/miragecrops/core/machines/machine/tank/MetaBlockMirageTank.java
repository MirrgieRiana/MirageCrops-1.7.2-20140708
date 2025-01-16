package miragecrops.core.machines.machine.tank;

import miragecrops.core.machines.machine.framework.MetaBlockMirageMachine;
import miragecrops.framework.block.BlockMeta;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaBlockMirageTank extends MetaBlockMirageMachine
{

	public MetaBlockMirageTank(BlockMeta blockMeta, int metaId, ITileEntityProvider iTileEntityProvider)
	{
		super(blockMeta, metaId, iTileEntityProvider);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		if (iconOverride != null) return iconOverride;
		if (side == ForgeDirection.UP.ordinal()) return renderingEdge ? iconEdge : iconTop;
		if (side == ForgeDirection.DOWN.ordinal()) return iconBottom;
		return super.getIcon(iBlockAccess, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (iconOverride != null) return iconOverride;
		if (side == ForgeDirection.UP.ordinal()) return renderingEdge ? iconEdge : iconTop;
		if (side == ForgeDirection.DOWN.ordinal()) return iconBottom;
		return super.getIcon(side, meta);
	}

	///////////////////////////////

	public String iconNameEdge;
	public String iconNameTop;
	public String iconNameBottom;
	protected IIcon iconEdge;
	protected IIcon iconTop;
	protected IIcon iconBottom;

	protected IIcon iconOverride = null;
	protected boolean renderingEdge = false;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iIconRegister)
	{
		super.registerBlockIcons(iIconRegister);
		this.iconEdge = iIconRegister.registerIcon(iconNameEdge);
		this.iconTop = iIconRegister.registerIcon(iconNameTop);
		this.iconBottom = iIconRegister.registerIcon(iconNameBottom);
	}

}
