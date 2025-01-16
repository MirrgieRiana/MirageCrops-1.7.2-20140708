package miragecrops.framework.worldgen;

import java.util.Random;

import miragecrops.framework.block.MetaBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorXYZOre extends WorldGeneratorXYZOreAbstract
{

	private Block block;
	private int meta;

	public WorldGeneratorXYZOre(double numberOfBlocks, Block block)
	{
		this(numberOfBlocks, null, block);
	}

	public WorldGeneratorXYZOre(double numberOfBlocks, Block blockSoil, Block block)
	{
		this(numberOfBlocks, blockSoil, block, 0);
	}

	public WorldGeneratorXYZOre(double numberOfBlocks, ItemStack itemStack)
	{
		this(numberOfBlocks, null, ((ItemBlock) itemStack.getItem()).field_150939_a, itemStack.getItemDamage());
	}

	public WorldGeneratorXYZOre(double numberOfBlocks, Block blockSoil, ItemStack itemStack)
	{
		this(numberOfBlocks, blockSoil, itemStack.getItemDamage());
	}

	public WorldGeneratorXYZOre(double numberOfBlocks, Block block, int meta)
	{
		this(numberOfBlocks, null, block, meta);
	}

	public WorldGeneratorXYZOre(double numberOfBlocks, Block blockSoil, Block block, int meta)
	{
		super(numberOfBlocks, blockSoil);
		this.block = block;
		this.meta = meta;
	}

	public WorldGeneratorXYZOre(double numberOfBlocks, MetaBlock metaBlock)
	{
		this(numberOfBlocks, metaBlock.getBlock(), metaBlock.getMetaId());
	}

	public WorldGeneratorXYZOre(double numberOfBlocks, Block blockSoil, MetaBlock metaBlock)
	{
		this(numberOfBlocks, blockSoil, metaBlock.getBlock(), metaBlock.getMetaId());
	}

	@Override
	public void setBlock(World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random, int x, int y, int z)
	{
		world.setBlock(x, y, z, block, meta, 0);
	}

}
