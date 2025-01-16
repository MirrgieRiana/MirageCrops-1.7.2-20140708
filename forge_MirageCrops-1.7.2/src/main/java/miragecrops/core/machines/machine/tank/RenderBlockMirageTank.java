package miragecrops.core.machines.machine.tank;

import miragecrops.api.framework.block.IBlockMeta;
import miragecrops.api.framework.block.IMetaBlock;
import miragecrops.core.machines.RenderBlockAbstract;
import miragecrops.core.machines.machine.TileEntityMirageTank;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockMirageTank extends RenderBlockAbstract
{

	public RenderBlockMirageTank(int renderId)
	{
		super(renderId);
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if (modelId == this.getRenderId())
		{
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

			renderer.setRenderBounds(0, 0, 0, 1, 3F / 16F, 1);
			renderCube(block, metadata, renderer, Tessellator.instance);
			renderer.setRenderBounds(0, 0, 13F / 16F, 1, 1, 1);
			renderCube(block, metadata, renderer, Tessellator.instance);
			renderer.setRenderBounds(13F / 16F, 0, 0, 1, 1, 1);
			renderCube(block, metadata, renderer, Tessellator.instance);
			renderer.setRenderBounds(0, 0, 0, 1, 1, 3F / 16F);
			renderCube(block, metadata, renderer, Tessellator.instance);
			renderer.setRenderBounds(0, 0, 0, 3F / 16F, 1, 1);
			renderCube(block, metadata, renderer, Tessellator.instance);

			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == this.getRenderId())
		{
			renderer.setRenderBounds(0, 0, 0, 1, 3F / 16F, 1);
			renderer.renderStandardBlock(block, x, y, z);

			MetaBlockMirageTank metaBlockTank = getMetaBlockMirageTank(world, x, y, z, block);

			if (metaBlockTank != null) {
				metaBlockTank.renderingEdge = true;
			}

			renderer.setRenderBounds(0, 0, 13F / 16F, 1, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(13F / 16F, 0, 0, 1, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0, 0, 0, 1, 1, 3F / 16F);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0, 0, 0, 3F / 16F, 1, 1);
			renderer.renderStandardBlock(block, x, y, z);

			if (metaBlockTank != null) {
				metaBlockTank.renderingEdge = false;
			}

			if (metaBlockTank != null) {

				TileEntity tileEntity = world.getTileEntity(x, y, z);
				if (tileEntity != null && tileEntity instanceof TileEntityMirageTank) {
					TileEntityMirageTank tileEntityMirageTank = (TileEntityMirageTank) tileEntity;

					if (!tileEntityMirageTank.getFluidTank().isEmpty()) {
						FluidStack fluidStack = tileEntityMirageTank.getFluidTank().getFluid();
						int limit = tileEntityMirageTank.getFluidTank().getCapacity();

						renderer.setRenderBounds(3F / 16F, 0, 3F / 16F, 13F / 16F, (3F + 12F * fluidStack.amount / 16000) / 16F, 13F / 16F);
						metaBlockTank.iconOverride = fluidStack.getFluid().getIcon(fluidStack);
						renderer.renderStandardBlock(block, x, y, z);
						metaBlockTank.iconOverride = null;
					}

				}

			}

			return true;
		}
		return false;
	}

	protected MetaBlockMirageTank getMetaBlockMirageTank(IBlockAccess world, int x, int y, int z, Block block)
	{
		if (block != null && block instanceof IBlockMeta) {
			IBlockMeta blockMeta = (IBlockMeta) block;

			IMetaBlock metaBlock = blockMeta.getMetaBlock(world.getBlockMetadata(x, y, z));
			if (metaBlock instanceof MetaBlockMirageTank) {

				return (MetaBlockMirageTank) metaBlock;

			}
		}

		return null;
	}

}
