package miragecrops.core.machines.pipe;

import miragecrops.api.framework.block.IBlockMeta;
import miragecrops.api.framework.block.IMetaBlock;
import miragecrops.api.machines.IMiragePipe;
import miragecrops.api.machines.IMiragePipeProvider;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public class HelperMiragePipe
{

	/**
	 * 2つのブロックがお互いにcanConnectMiragePipeToであった場合にtrueとなる。
	 */
	public static boolean canConnectMiragePipe(IBlockAccess iBlockAccess, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		if (Math.abs(x1 - x2) + Math.abs(y1 - y2) + Math.abs(z1 - z2) != 1) {
			throw new RuntimeException(String.format("illegal argument magnitude((%d, %d, %d) - (%d, %d, %d)) != 1", x1, y1, z1, x2, y2, z2));
		}

		if (canConnectMiragePipeHalf(iBlockAccess, x1, y1, z1, x2, y2, z2)) {
			if (canConnectMiragePipeHalf(iBlockAccess, x2, y2, z2, x1, y1, z1)) {
				return true;
			}
		}

		return false;
	}

	private static boolean canConnectMiragePipeHalf(IBlockAccess iBlockAccess, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		IMiragePipe iMiragePipe = getMiragePipe(iBlockAccess, x1, y1, z1);
		if (iMiragePipe == null) return false;
		return iMiragePipe.canConnectMiragePipe(iBlockAccess, x1, y1, z1, x2, y2, z2);
	}

	public static IMiragePipe getMiragePipe(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		Block block = iBlockAccess.getBlock(x, y, z);
		if (block == null) return null;

		if (block instanceof IMiragePipe) {
			return ((IMiragePipe) block);
		}

		if (block instanceof IMiragePipeProvider) {
			return ((IMiragePipeProvider) block).getMiragePipe(iBlockAccess, x, y, z);
		}

		if (block instanceof IBlockMeta) {
			IMetaBlock iMetaBlock = ((IBlockMeta) block).getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z));
			if (iMetaBlock == null) return null;

			if (iMetaBlock instanceof IMiragePipe) {
				return ((IMiragePipe) iMetaBlock);
			}

			if (iMetaBlock instanceof IMiragePipeProvider) {
				return ((IMiragePipeProvider) iMetaBlock).getMiragePipe(iBlockAccess, x, y, z);
			}
		}

		return null;
	}

}