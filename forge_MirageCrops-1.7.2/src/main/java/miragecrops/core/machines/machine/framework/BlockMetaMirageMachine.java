package miragecrops.core.machines.machine.framework;

import miragecrops.framework.block.BlockMeta;
import miragecrops.framework.block.MetaBlock;
import mirrg.mir34.modding.IMod;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMetaMirageMachine extends BlockMeta implements ITileEntityProvider
{

	public BlockMetaMirageMachine(IMod iMod)
	{
		super(iMod);
	}

	@Override
	public TileEntity createNewTileEntity(World paramWorld, int meta)
	{
		System.out.println(2);
		MetaBlock metaBlock = getMetaBlock(meta);
		if (metaBlock instanceof ITileEntityProvider) {
			((ITileEntityProvider) metaBlock).createNewTileEntity(paramWorld, meta);
		}
		return null;
	}

}
