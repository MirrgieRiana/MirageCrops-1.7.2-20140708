package miragecrops.framework.worldgen;

import java.util.Random;

import miragecrops.framework.StaticsMath;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorXZOre implements IWorldGeneratorXZ
{

	private double countPerChunk;
	private int minY;
	private int maxY;
	private IWorldGeneratorXYZ iWorldGeneratorXYZ;

	public WorldGeneratorXZOre(CountPer countPer, double count, int minY, int maxY, IWorldGeneratorXYZ iWorldGeneratorXYZ)
	{
		switch (countPer) {
			case CUBE:
				this.countPerChunk = count / 16 * (maxY - minY);
				break;
			case CHUNK:
				this.countPerChunk = count;
				break;
		}
		this.minY = minY;
		this.maxY = maxY;
		this.iWorldGeneratorXYZ = iWorldGeneratorXYZ;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		int count = StaticsMath.floorRandom(countPerChunk, random);

		for (int i = 0; i < count; i++) {
			int x = chunkX * 16 + random.nextInt(16);
			int y = random.nextInt(maxY - minY) + minY;
			int z = chunkZ * 16 + random.nextInt(16);

			if (isSpawnableAtCoord(world, random, x, y, z)) {
				iWorldGeneratorXYZ.generate(world, chunkGenerator, chunkProvider, random, x, y, z);
			}
		}
	}

	public static enum CountPer
	{
		CUBE,
		CHUNK,
	}

	@Override
	public boolean isSpawnableAtCoord(World world, Random random, int x, int y, int z)
	{
		return true;
	}

}
