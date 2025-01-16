package plugincrops.debugger.metaitems;

import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MetaitemBucket extends MetaitemPluginCrops
{

	public MetaitemBucket(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (world.isRemote) return true;

		int t = 0;

		for (int xi = -16; xi <= 16; xi++) {
			for (int yi = -5; yi <= 5; yi++) {
				for (int zi = -16; zi <= 16; zi++) {

					if (Math.abs(x + xi) % 8 == 3 && Math.abs(z + zi) % 8 == 3) {
						if (useBucket(itemStack, player, world, x + xi, y + yi, z + zi, side, false)) {
							t++;
						}
					}

				}
			}
		}

		return true;
	}

	private boolean useBucket(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, boolean mute)
	{
		if (world.getBlock(x, y, z) != Blocks.farmland) return false;
		if (!world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z)) return false;
		if (!world.getBlock(x, y + 2, z).isAir(world, x, y + 2, z)) return false;
		if (!world.getBlock(x, y + 3, z).isAir(world, x, y + 3, z)) return false;
		if (!world.getBlock(x, y + 4, z).isAir(world, x, y + 4, z)) return false;

		world.setBlock(x, y, z, Blocks.water);
		world.setBlock(x, y + 1, z, Blocks.waterlily);
		world.setBlock(x, y + 2, z, Blocks.air);
		world.setBlock(x, y + 3, z, Blocks.air);
		world.setBlock(x, y + 4, z, Blocks.glowstone);
		return true;
	}

}
