package plugincrops.debugger.metaitems;

import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MetaitemHoe extends MetaitemPluginCrops
{

	public MetaitemHoe(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		int t = 0;

		for (int xi = -15; xi <= 15; xi++) {
			for (int yi = -5; yi <= 5; yi++) {
				for (int zi = -15; zi <= 15; zi++) {
					if (useHoe(itemStack, player, world, x + xi, y + yi, z + zi, side, true)) {
						t++;
					}
				}
			}
		}

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
		return true;
	}

	public boolean useHoe(
		ItemStack itemStack, EntityPlayer player, World world,
		int x, int y, int z, int side, boolean mute)
	{
		if (!player.canPlayerEdit(x, y, z, side, itemStack)) {
			return false;
		}
		/*
		UseHoeEvent event = new UseHoeEvent(player, itemStack, world, x, y, z);
		if (MinecraftForge.EVENT_BUS.post(event)) {
			return false;
		}
		if (event.getResult() == cpw.mods.fml.common.eventhandler.Event.Result.ALLOW)
		{
			itemStack.damageItem(1, player);
			return true;
		}
		*/
		Block block = world.getBlock(x, y, z);
		if (side != 0 && (canHoe(world, x, y, z))) {
			Block farmland = Blocks.farmland;
			if ((block == Blocks.grass) || (block == Blocks.dirt)) {
				if (!mute) {
					world.playSoundEffect(
						x + 0.5F, y + 0.5F, z + 0.5F,
						farmland.stepSound.getStepResourcePath(),
						(farmland.stepSound.getVolume() + 1.0F) / 2.0F, farmland.stepSound.getPitch() * 0.8F);
				}
				if (world.isRemote) {
					return true;
				}
				world.setBlock(x, y, z, farmland);
				world.setBlockMetadataWithNotify(x, y, z, 7, 3);
				itemStack.damageItem(1, player);
				return true;
			}
			if ((block == farmland)) {
				if (world.isRemote) {
					return true;
				}
				world.setBlockMetadataWithNotify(x, y, z, 7, 3);
				itemStack.damageItem(1, player);
				return true;
			}
		}
		return false;
	}

	protected boolean canHoe(World world, int x, int y, int z)
	{
		return world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z);
	}

}
