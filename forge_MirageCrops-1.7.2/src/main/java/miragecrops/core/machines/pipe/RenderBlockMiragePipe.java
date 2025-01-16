package miragecrops.core.machines.pipe;

import miragecrops.core.machines.RenderBlockAbstract;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockMiragePipe extends RenderBlockAbstract
{

	public RenderBlockMiragePipe(int renderId)
	{
		super(renderId);
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if (modelId == this.getRenderId())
		{
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

			renderer.setRenderBounds(3.0 / 8, 3.0 / 8, 0.0D, 5.0 / 8, 5.0 / 8, 1.0D);
			renderCube(block, metadata, renderer, Tessellator.instance);

			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == this.getRenderId())
		{
			if (block instanceof BlockMirageMiragePipe) {
				BlockMirageMiragePipe blockMiragePipe = (BlockMirageMiragePipe) block;

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

				final boolean west = HelperMiragePipe.canConnectMiragePipe(world, x, y, z, x - 1, y, z);
				final boolean east = HelperMiragePipe.canConnectMiragePipe(world, x, y, z, x + 1, y, z);
				final boolean down = HelperMiragePipe.canConnectMiragePipe(world, x, y, z, x, y - 1, z);
				final boolean up = HelperMiragePipe.canConnectMiragePipe(world, x, y, z, x, y + 1, z);
				final boolean south = HelperMiragePipe.canConnectMiragePipe(world, x, y, z, x, y, z - 1);
				final boolean north = HelperMiragePipe.canConnectMiragePipe(world, x, y, z, x, y, z + 1);

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

				renderer.setRenderBounds(low, low, low, high, high, high);
				renderer.renderStandardBlock(block, x, y, z);

				if (west || east)
				{
					renderer.setRenderBounds(westValue, low, low, eastValue, high, high);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (down || up)
				{
					renderer.setRenderBounds(low, downValue, low, high, upValue, high);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (south || north)
				{
					renderer.setRenderBounds(low, low, southValue, high, high, northValue);
					renderer.renderStandardBlock(block, x, y, z);
				}

				return true;
			}
		}
		return false;
	}

}
