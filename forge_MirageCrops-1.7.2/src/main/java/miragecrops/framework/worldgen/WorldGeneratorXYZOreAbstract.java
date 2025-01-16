package miragecrops.framework.worldgen;

import java.util.Random;

import miragecrops.framework.StaticsMath;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public abstract class WorldGeneratorXYZOreAbstract implements IWorldGeneratorXYZ
{

	public static final double NUMBER_DIRT = 32;
	public static final double NUMBER_GRAVEL = 32;
	public static final double NUMBER_COAL = 16;
	public static final double NUMBER_IRON = 8;
	public static final double NUMBER_GOLD = 8;
	public static final double NUMBER_REDSTONE = 7;
	public static final double NUMBER_DIAMOND = 7;
	public static final double NUMBER_LAPIS = 6;
	public static final double NUMBER_SILVERFISH = 8;
	public static final double NUMBER_NETHER_QUARTZ = 13;

	private double numberOfBlocks;
	private Block blockSoil;

	public WorldGeneratorXYZOreAbstract(double numberOfBlocks)
	{
		this(numberOfBlocks, null);
	}

	public WorldGeneratorXYZOreAbstract(double numberOfBlocks, Block blockSoil)
	{
		this.numberOfBlocks = numberOfBlocks;
		this.blockSoil = blockSoil == null ? Blocks.stone : blockSoil;
	}

	@Override
	public void generate(World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random, int x, int y, int z)
	{
		int numberOfBlocks = StaticsMath.floorRandom(this.numberOfBlocks, random);

		if (numberOfBlocks >= 4) {
			generate4(world, chunkGenerator, chunkProvider, random, x, y, z, numberOfBlocks);
		} else if (numberOfBlocks == 3) {
			generate3(world, chunkGenerator, chunkProvider, random, x, y, z);
		} else if (numberOfBlocks == 2) {
			generate2(world, chunkGenerator, chunkProvider, random, x, y, z);
		} else if (numberOfBlocks == 1) {
			generate1(world, chunkGenerator, chunkProvider, random, x, y, z);
		} else if (numberOfBlocks == 0) {
			new RuntimeException(
				getClass().getName() + "#generate: numberOfBlocks = 0, (" + x + ", " + y + ", " + z + ")")
				.printStackTrace();
		}

	}

	protected void generate1(World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random, int x, int y, int z)
	{
		tryPutBlockAtCoord(world, chunkGenerator, chunkProvider, random, x, y, z);
	}

	int[] TABLE_X = {
		1, -1, 0, 0, 0, 0,
	};
	int[] TABLE_Y = {
		0, 0, 1, -1, 0, 0,
	};
	int[] TABLE_Z = {
		0, 0, 0, 0, 1, -1,
	};

	protected void generate2(World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random, int x, int y, int z)
	{
		tryPutBlockAtCoord(world, chunkGenerator, chunkProvider, random, x, y, z);

		int i = random.nextInt(6);
		tryPutBlockAtCoord(world, chunkGenerator, chunkProvider, random, x + TABLE_X[i], y + TABLE_Y[i], z + TABLE_Z[i]);
	}

	protected void generate3(World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random, int x, int y, int z)
	{
		tryPutBlockAtCoord(world, chunkGenerator, chunkProvider, random, x, y, z);

		int i = random.nextInt(6);
		tryPutBlockAtCoord(world, chunkGenerator, chunkProvider, random, x + TABLE_X[i], y + TABLE_Y[i], z + TABLE_Z[i]);

		i = random.nextInt(6);
		tryPutBlockAtCoord(world, chunkGenerator, chunkProvider, random, x + TABLE_X[i], y + TABLE_Y[i], z + TABLE_Z[i]);
	}

	protected void generate4(World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random, int x, int y, int z, int numberOfBlocks)
	{
		float course = random.nextFloat() * (float) Math.PI;
		double minX = x + 8 + MathHelper.sin(course) * numberOfBlocks / 8.0F;
		double maxX = x + 8 - MathHelper.sin(course) * numberOfBlocks / 8.0F;
		double minZ = z + 8 + MathHelper.cos(course) * numberOfBlocks / 8.0F;
		double maxZ = z + 8 - MathHelper.cos(course) * numberOfBlocks / 8.0F;
		double minY = y + random.nextInt(3) - 2;
		double maxY = y + random.nextInt(3) - 2;

		for (int i = 0; i <= numberOfBlocks; ++i)
		{
			double vectX = minX + (maxX - minX) * i / numberOfBlocks;
			double vectY = minY + (maxY - minY) * i / numberOfBlocks;
			double vectZ = minZ + (maxZ - minZ) * i / numberOfBlocks;
			double d9 = random.nextDouble() * numberOfBlocks / 16.0D;
			double d10 = (MathHelper.sin(i * (float) Math.PI / numberOfBlocks) + 1.0F)
				* d9 + 1.0D;
			double d11 = (MathHelper.sin(i * (float) Math.PI / numberOfBlocks) + 1.0F)
				* d9 + 1.0D;
			int i1 = MathHelper.floor_double(vectX - d10 / 2.0D);
			int j1 = MathHelper.floor_double(vectY - d11 / 2.0D);
			int k1 = MathHelper.floor_double(vectZ - d10 / 2.0D);
			int l1 = MathHelper.floor_double(vectX + d10 / 2.0D);
			int i2 = MathHelper.floor_double(vectY + d11 / 2.0D);
			int j2 = MathHelper.floor_double(vectZ + d10 / 2.0D);

			for (int xi = i1; xi <= l1; ++xi)
			{
				double d12 = (xi + 0.5D - vectX) / (d10 / 2.0D);

				if (d12 * d12 < 1.0D)
				{
					for (int yi = j1; yi <= i2; ++yi)
					{
						double d13 = (yi + 0.5D - vectY) / (d11 / 2.0D);

						if (d12 * d12 + d13 * d13 < 1.0D)
						{
							for (int zi = k1; zi <= j2; ++zi)
							{
								double d14 = (zi + 0.5D - vectZ) / (d10 / 2.0D);

								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
									tryPutBlockAtCoord(world, chunkGenerator, chunkProvider, random, xi, yi, zi);
								}

							}
						}
					}
				}
			}
		}

	}

	protected void tryPutBlockAtCoord(World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		if (block != null) {
			if (block.isReplaceableOreGen(world, x, y, z, this.blockSoil)) {
				setBlock(world, chunkGenerator, chunkProvider, random, x, y, z);
			}
		}
	}

	public abstract void setBlock(World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random, int x, int y, int z);

}
