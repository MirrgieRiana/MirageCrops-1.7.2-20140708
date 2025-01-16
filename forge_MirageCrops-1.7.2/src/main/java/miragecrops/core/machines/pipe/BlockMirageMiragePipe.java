package miragecrops.core.machines.pipe;

import miragecrops.framework.block.BlockMeta;
import mirrg.mir34.modding.IMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMirageMiragePipe extends BlockMeta
{

	public BlockMirageMiragePipe(IMod iMod)
	{
		super(iMod);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return LoaderMiragePipe.renderBlockMiragePipe.getRenderId();
	}

}
