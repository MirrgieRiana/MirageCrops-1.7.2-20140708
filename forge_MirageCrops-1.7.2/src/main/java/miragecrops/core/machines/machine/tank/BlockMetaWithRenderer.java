package miragecrops.core.machines.machine.tank;

import miragecrops.framework.block.BlockMeta;
import mirrg.h.struct.Struct1;
import mirrg.mir34.modding.IMod;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaWithRenderer extends BlockMeta
{

	public BlockMetaWithRenderer(IMod iMod)
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
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
	{
		return true;
	}

	//

	private Struct1<Integer> renderType = null;

	public void setRenderType(Struct1<Integer> renderType)
	{
		this.renderType = renderType;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return renderType.getX();
	}

}
