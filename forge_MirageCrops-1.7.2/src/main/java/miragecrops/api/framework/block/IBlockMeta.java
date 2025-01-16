package miragecrops.api.framework.block;

import net.minecraft.block.Block;

public interface IBlockMeta
{

	public Block getBlock();

	public IMetaBlock getMetaBlock(int meta);

}
