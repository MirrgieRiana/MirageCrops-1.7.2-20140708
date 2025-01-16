package miragecrops.framework.worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public interface IWorldGeneratorXYZ
{

	public abstract void generate(World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider, Random random, int x, int y, int z);

}
