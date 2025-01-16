package miragecrops.framework.worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public interface IWorldGeneratorXZ extends IWorldGenerator
{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider);

	public boolean isSpawnableAtCoord(World world, Random random, int x, int y, int z);

}
