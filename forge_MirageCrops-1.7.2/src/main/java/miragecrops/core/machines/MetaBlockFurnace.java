package miragecrops.core.machines;

import miragecrops.core.machines.framework.MetaBlockFurnacefamily;
import miragecrops.framework.block.BlockMeta;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MetaBlockFurnace extends MetaBlockFurnacefamily
{

	protected MetaBlockFurnace(BlockMeta blockMeta, int metaId)
	{
		super(blockMeta, metaId);
	}

	@Override
	public TileEntity createNewTileEntity(World paramWorld, int paramInt)
	{
		return new TileEntityMetaBlockFurnace();
	}

}
