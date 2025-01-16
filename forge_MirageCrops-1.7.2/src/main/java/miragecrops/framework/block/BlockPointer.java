package miragecrops.framework.block;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPointer
{

	public final IBlockAccess iBlockAccess;
	public final int x;
	public final int y;
	public final int z;

	public BlockPointer(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		this.iBlockAccess = iBlockAccess;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Block getBlock()
	{
		return iBlockAccess.getBlock(x, y, z);
	}

	public int getBlockMetadata()
	{
		return iBlockAccess.getBlockMetadata(x, y, z);
	}

	public TileEntity getTileEntity()
	{
		return iBlockAccess.getTileEntity(x, y, z);
	}

	@SideOnly(Side.CLIENT)
	public BiomeGenBase getBiomeGen()
	{
		return iBlockAccess.getBiomeGenForCoords(x, z);
	}

}
