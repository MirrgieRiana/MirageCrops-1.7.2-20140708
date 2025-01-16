package miragecrops.core.machines.machine.framework;

import miragecrops.core.MirageCrops;
import miragecrops.framework.IGuiProvider;
import miragecrops.framework.block.BlockMeta;
import miragecrops.framework.block.MetaBlock;
import mirrg.h.struct.Tuple1;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaBlockMirageMachine extends MetaBlock implements ITileEntityProvider, IGuiProvider
{

	protected ITileEntityProvider iTileEntityProvider;

	public MetaBlockMirageMachine(BlockMeta blockMeta, int metaId, ITileEntityProvider iTileEntityProvider)
	{
		super(blockMeta, metaId);
		this.iTileEntityProvider = iTileEntityProvider;
	}

	/////////////////////////////////// ITileEntityProvider ///////////////////////////////////

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1)
	{
		return iTileEntityProvider.createNewTileEntity(arg0, arg1);
	}

	@Override
	public void breakBlock(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
	{
		{
			ITileEntityMirageMachine tileEntity = (ITileEntityMirageMachine) paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
			if (tileEntity != null) {
				tileEntity.onBroken();
			}
		}

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

	/////////////////////////////////// IGuiProvider ///////////////////////////////////

	@Override
	@SideOnly(Side.CLIENT)
	public Tuple1<? extends GuiContainer> createGui(EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null) return null;
		if (!(tileEntity instanceof IGuiProvider)) return null;

		return ((IGuiProvider) tileEntity).createGui(player, world, x, y, z);
	}

	@Override
	public Container createContainer(EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null) return null;
		if (!(tileEntity instanceof IGuiProvider)) return null;

		return ((IGuiProvider) tileEntity).createContainer(player, world, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
	{
		if (paramWorld.isRemote) {
			return true;
		}

		paramEntityPlayer.openGui(MirageCrops.guiHandler.iMod, MirageCrops.guiHandler.guiId, paramWorld, paramInt1, paramInt2, paramInt3);

		return true;
	}

}
