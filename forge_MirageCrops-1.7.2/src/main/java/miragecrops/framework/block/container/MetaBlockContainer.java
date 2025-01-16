package miragecrops.framework.block.container;

import miragecrops.framework.block.BlockMeta;
import miragecrops.framework.block.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class MetaBlockContainer extends MetaBlock
	implements ITileEntityProvider
{

	public MetaBlockContainer(BlockMeta blockMeta, int metaId)
	{
		super(blockMeta, metaId);
	}

	@Override
	public void breakBlock(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
	{
		super.breakBlock(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
		paramWorld.removeTileEntity(paramInt1, paramInt2, paramInt3);
	}

	@Override
	public boolean onBlockEventReceived(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
	{
		super.onBlockEventReceived(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
		TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
		if (localTileEntity != null) {
			return localTileEntity.receiveClientEvent(paramInt4, paramInt5);
		}
		return false;
	}

}
