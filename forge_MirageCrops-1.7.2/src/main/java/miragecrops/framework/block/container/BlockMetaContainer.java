package miragecrops.framework.block.container;

import miragecrops.framework.block.BlockMeta;
import miragecrops.framework.block.MetaBlock;
import mirrg.mir34.modding.IMod;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMetaContainer extends BlockMeta
	implements ITileEntityProvider
{

	public BlockMetaContainer(IMod iMod)
	{
		super(iMod);
		this.isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World paramWorld, int meta)
	{
		MetaBlock metaBlock = getMetaBlock(meta);
		if (metaBlock instanceof ITileEntityProvider) {
			((ITileEntityProvider) metaBlock).createNewTileEntity(paramWorld, meta);
		}
		return null;
	}

	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

}
