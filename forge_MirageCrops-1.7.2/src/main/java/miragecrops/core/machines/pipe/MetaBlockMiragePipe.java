package miragecrops.core.machines.pipe;

import java.util.List;

import miragecrops.api.machines.IMiragePipe;
import miragecrops.api.machines.IMiragePipeProvider;
import miragecrops.api.machines.MiragePipes;
import miragecrops.framework.block.BlockMeta;
import miragecrops.framework.block.MetaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaBlockMiragePipe extends MetaBlock implements IMiragePipeProvider
{

	public MetaBlockMiragePipe(BlockMeta blockMeta, int metaId)
	{
		super(blockMeta, metaId);
	}

	@Override
	public void addCollisionBoxesToList(World iba, int x, int y, int z, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
	{
		final float veryLow = 0F / 8F;
		final float low = 3F / 8F;
		final float high = 5F / 8F;
		final float veryHigh = 8F / 8F;

		float westValue = low;
		float eastValue = high;
		float downValue = low;
		float upValue = high;
		float southValue = low;
		float northValue = high;

		final boolean west = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x - 1, y, z);
		final boolean east = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x + 1, y, z);
		final boolean down = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x, y - 1, z);
		final boolean up = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x, y + 1, z);
		final boolean south = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x, y, z - 1);
		final boolean north = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x, y, z + 1);

		if (west) {
			westValue = veryLow;
		}
		if (east) {
			eastValue = veryHigh;
		}
		if (down) {
			downValue = veryLow;
		}
		if (up) {
			upValue = veryHigh;
		}
		if (south) {
			southValue = veryLow;
		}
		if (north) {
			northValue = veryHigh;
		}

		getBlock().setBlockBounds(low, low, low, high, high, high);
		super.addCollisionBoxesToList(iba, x, y, z, p_149743_5_, p_149743_6_, p_149743_7_);

		if (west || east)
		{
			getBlock().setBlockBounds(westValue, low, low, eastValue, high, high);
			super.addCollisionBoxesToList(iba, x, y, z, p_149743_5_, p_149743_6_, p_149743_7_);
		}
		if (down || up)
		{
			getBlock().setBlockBounds(low, downValue, low, high, upValue, high);
			super.addCollisionBoxesToList(iba, x, y, z, p_149743_5_, p_149743_6_, p_149743_7_);
		}
		if (south || north)
		{
			getBlock().setBlockBounds(low, low, southValue, high, high, northValue);
			super.addCollisionBoxesToList(iba, x, y, z, p_149743_5_, p_149743_6_, p_149743_7_);
		}

		getBlock().setBlockBounds(westValue, downValue, southValue, eastValue, upValue, northValue);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		final float veryLow = 0F / 8F;
		final float low = 3F / 8F;
		final float high = 5F / 8F;
		final float veryHigh = 8F / 8F;

		float westValue = low;
		float eastValue = high;
		float downValue = low;
		float upValue = high;
		float southValue = low;
		float northValue = high;

		final boolean west = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x - 1, y, z);
		final boolean east = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x + 1, y, z);
		final boolean down = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x, y - 1, z);
		final boolean up = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x, y + 1, z);
		final boolean south = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x, y, z - 1);
		final boolean north = HelperMiragePipe.canConnectMiragePipe(iba, x, y, z, x, y, z + 1);

		if (west) {
			westValue = veryLow;
		}
		if (east) {
			eastValue = veryHigh;
		}
		if (down) {
			downValue = veryLow;
		}
		if (up) {
			upValue = veryHigh;
		}
		if (south) {
			southValue = veryLow;
		}
		if (north) {
			northValue = veryHigh;
		}

		getBlock().setBlockBounds(westValue, downValue, southValue, eastValue, upValue, northValue);
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

	@Override
	public IMiragePipe getMiragePipe(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return MiragePipes.mirageAlloy;
	}

}
