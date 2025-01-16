package plugincrops.debugger.metaitems;

import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MetaitemPlacer extends MetaitemPluginCrops
{

	public MetaitemPlacer(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		Block block = world.getBlock(x, y, z);
		if (block == null) return false;
		if (block.isAir(world, x, y, z)) return false;

		int metadata = world.getBlockMetadata(x, y, z);
		if (block.hasTileEntity(metadata)) return false;

		int t = 0;

		for (int xi = -9; xi <= 9; xi++) {
			for (int yi = -0; yi <= 0; yi++) {
				for (int zi = -9; zi <= 9; zi++) {
					if (place(world, x + xi, y + yi, z + zi, block, metadata)) {
						t++;
					}
				}
			}
		}

		if (t > 0) {
			world.playSoundEffect(
				x + 0.5F, y + 0.5F, z + 0.5F,
				block.stepSound.getStepResourcePath(),
				(block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
			if (t > 10) {
				world.playSoundEffect(
					x + 0.5F, y + 0.5F, z + 0.5F,
					block.stepSound.getStepResourcePath(),
					(block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
			}
		}
		return true;
	}

	private boolean place(World world, int x, int y, int z, Block block, int metadata)
	{
		if (!world.isAirBlock(x, y, z)) return false;

		world.setBlock(x, y, z, block, metadata, 1);

		return true;
	}

}
