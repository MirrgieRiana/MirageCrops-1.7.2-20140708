package miragecrops.framework.worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class FilterBiome implements IWorldGeneratorXZ
{

	private IWorldGeneratorXZ iWorldGeneratorXY;
	private String[] biomeNameRequired;

	public FilterBiome(IWorldGeneratorXZ iWorldGeneratorXY, String... biomeNameRequired)
	{
		this.iWorldGeneratorXY = iWorldGeneratorXY;
		this.biomeNameRequired = biomeNameRequired;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		iWorldGeneratorXY.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
	}

	@Override
	public boolean isSpawnableAtCoord(World world, Random random, int x, int y, int z)
	{
		String currentBiomeName = world.getBiomeGenForCoords(x, z).biomeName.toLowerCase();

		for (String biomeName : biomeNameRequired) {
			if (!currentBiomeName.contains(biomeName)) {
				return false;
			}
		}

		return iWorldGeneratorXY.isSpawnableAtCoord(world, random, x, y, z);
	}

}
