package miragecrops.framework;

import miragecrops.api.framework.block.IBlockMeta;
import miragecrops.api.framework.block.IMetaBlock;
import mirrg.mir34.modding.IMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiHandler extends GuiHandlerAbstract
{

	public final int guiId;
	public final IMod iMod;

	public GuiHandler(int guiId, IMod iMod)
	{
		this.guiId = guiId;
		this.iMod = iMod;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == guiId)
		{
			Block block = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);

			if (block instanceof IGuiProvider) {
				return ((IGuiProvider) block).createGui(player, world, x, y, z);
			}

			if (block instanceof IBlockMeta) {
				IBlockMeta iBlockMeta = (IBlockMeta) block;

				IMetaBlock metaBlock = iBlockMeta.getMetaBlock(meta);

				if (metaBlock instanceof IGuiProvider) {
					return ((IGuiProvider) metaBlock).createGui(player, world, x, y, z);
				}
			}
		}

		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == guiId)
		{
			Block block = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);

			if (block instanceof IGuiProvider) {
				IGuiProvider iGuiBlockMeta = (IGuiProvider) block;

				return iGuiBlockMeta.createContainer(player, world, x, y, z);
			}

			if (block instanceof IBlockMeta) {
				IBlockMeta iBlockMeta = (IBlockMeta) block;

				IMetaBlock metaBlock = iBlockMeta.getMetaBlock(meta);

				if (metaBlock instanceof IGuiProvider) {
					IGuiProvider iGuiBlockMeta = (IGuiProvider) metaBlock;

					return iGuiBlockMeta.createContainer(player, world, x, y, z);
				}
			}
		}

		return null;
	}

}

abstract class GuiHandlerAbstract implements IGuiHandler
{

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

}
