package plugincrops.debugger.metaitems;

import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.ModPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class MetaitemHarvester extends MetaitemPluginCrops
{

	public MetaitemHarvester(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (world.isRemote) return true;

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null) return true;
		if (!(tileEntity instanceof ICropTile)) return true;
		ICropTile cropTile = (ICropTile) tileEntity;

		CropCard cropCard = cropTile.getCrop();

		if (cropCard != null) {

			if (cropCard.canBeHarvested(cropTile)) {

				ModPluginCrops.platform.message2(player, EnumChatFormatting.YELLOW + "[Harvest]" + EnumChatFormatting.RESET);

				float chance = cropCard.dropGainChance();
				ModPluginCrops.platform.message2(player, "dropGainChance: %s", chance);
				for (int i = 0; i < cropTile.getGain(); i++) {
					chance *= 1.03F;
				}
				ModPluginCrops.platform.message2(player, "dropGainChance&gain: %s", chance);

				chance -= world.rand.nextFloat();
				int drop = 0;
				for (; chance > 0.0F; chance -= world.rand.nextFloat()) {
					drop++;
				}
				ModPluginCrops.platform.message2(player, "drop count: %s", drop);

				ItemStack re[] = new ItemStack[drop];
				ModPluginCrops.platform.message2(player, "drop bonus rate: %s %%",
					cropTile.getGain() + 1);
				for (int i = 0; i < drop; i++) {
					re[i] = cropCard.getGain(cropTile);
					if (re[i] != null && world.rand.nextInt(100) <= cropTile.getGain()) {
						re[i].stackSize++;
						ModPluginCrops.platform.message2(player, "drop count+=1 bonus!");
					}
				}

				if (!world.isRemote && re.length > 0) {
					for (int i = 0; i < re.length; i++) {
						dropAsEntity(world, x, y, z, re[i]);
					}
				}
				return true;
			}

		}

		return true;
	}

	public static void dropAsEntity(World world, int x, int y, int z, ItemStack itemStack)
	{
		if (itemStack == null)
		{
			return;
		} else
		{
			double f = 0.69999999999999996D;
			double dx = world.rand.nextFloat() * f + (1.0D - f) * 0.5D;
			double dy = world.rand.nextFloat() * f + (1.0D - f) * 0.5D;
			double dz = world.rand.nextFloat() * f + (1.0D - f) * 0.5D;
			EntityItem entityItem = new EntityItem(world, x + dx, y + dy, z + dz, itemStack.copy());
			entityItem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityItem);
			return;
		}
	}

}
