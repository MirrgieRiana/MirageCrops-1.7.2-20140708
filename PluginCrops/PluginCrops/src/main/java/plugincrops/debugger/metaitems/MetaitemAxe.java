package plugincrops.debugger.metaitems;

import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MetaitemAxe extends MetaitemPluginCrops
{

	public MetaitemAxe(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		int t = 0;

		for (int xi = -8; xi <= 8; xi++) {
			for (int yi = -8; yi <= 8; yi++) {
				for (int zi = -8; zi <= 8; zi++) {
					if (useAxe(itemStack, player, world, x + xi, y + yi, z + zi, side, false)) {
						t++;
					}
				}
			}
		}
		/*
				if (t > 0) {
					world.playSoundEffect(
						x + 0.5F, y + 0.5F, z + 0.5F,
						Blocks.farmland.stepSound.getStepResourcePath(),
						(Blocks.farmland.stepSound.getVolume() + 1.0F) / 2.0F, Blocks.farmland.stepSound.getPitch() * 0.8F);
					if (t > 10) {
						world.playSoundEffect(
							x + 0.5F, y + 0.5F, z + 0.5F,
							Blocks.farmland.stepSound.getStepResourcePath(),
							(Blocks.farmland.stepSound.getVolume() + 1.0F) / 2.0F, Blocks.farmland.stepSound.getPitch() * 0.8F);
					}
				}
				*/
		return true;
	}

	private boolean useAxe(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, boolean mute)
	{
		Block block = world.getBlock(x, y, z);
		if (block == Blocks.log
			|| block == Blocks.log2
			|| block == Blocks.leaves
			|| block == Blocks.leaves2
			|| block == Blocks.tallgrass
			|| block == Blocks.sapling
			|| block == Blocks.deadbush
			|| block == Blocks.red_flower
			|| block == Blocks.yellow_flower
			|| block == Blocks.double_plant) {
			block.breakBlock(world, x, y, z,
				block, world.getBlockMetadata(x, y, z));
			world.setBlockToAir(x, y, z);
			return true;
		}
		return false;
	}

}
