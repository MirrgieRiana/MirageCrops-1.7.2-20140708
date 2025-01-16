package plugincrops.debugger.metaitems;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import plugincrops.debugger.HCrop;
import plugincrops.debugger.IC2VersionException;
import plugincrops.debugger.ModPluginCrops;

public class MetaitemSeedPicker extends MetaitemHarvester
{

	public MetaitemSeedPicker(IMetaitem _super, int metaid, Arguments arguments)
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

		boolean manual = true;

		if (cropCard != null) {

			ModPluginCrops.platform.message2(player, EnumChatFormatting.YELLOW + "[PickSeed]" + EnumChatFormatting.RESET);

			boolean bonus = cropCard.canBeHarvested(cropTile);
			ModPluginCrops.platform.message2(player, "manual: %s", manual);
			ModPluginCrops.platform.message2(player, "bonus: %s", bonus);
			float firstchance = cropCard.dropSeedChance(cropTile);
			ModPluginCrops.platform.message2(player, "firstchance: %s", firstchance);
			for (int i = 0; i < cropTile.getResistance(); i++) {
				firstchance *= 1.1F;
			}
			ModPluginCrops.platform.message2(player, "firstchance&resistance: %s", firstchance);

			int drop = 0;
			if (bonus) {
				ModPluginCrops.platform.message2(player, "firstchance bonus rate: min(" + EnumChatFormatting.RED + "%s" + EnumChatFormatting.RESET + ", 100) %%",
					(firstchance + 1.0F) * 0.8F * 100);
				if (world.rand.nextFloat() <= (firstchance + 1.0F) * 0.8F) {
					drop++;
					ModPluginCrops.platform.message2(player, "firstchance drop+=1 bonus!");
				}
				ModPluginCrops.platform.message2(player, "dropSeedChance: ", cropTile);
				float chance = cropCard.dropSeedChance(cropTile) + cropTile.getGrowth() / 100F;
				ModPluginCrops.platform.message2(player, "chance(second): ", chance);
				if (!manual) {
					chance *= 0.8F;
					ModPluginCrops.platform.message2(player, "manual=false!! drop chance(second) *= 0.8 penalty!");
				}
				ModPluginCrops.platform.message2(player, "chance(second): %s", chance);

				for (int i = 23; i < cropTile.getGain(); i++) {
					chance *= 0.95F;
				}
				ModPluginCrops.platform.message2(player, "chance(second)&gain: %s", chance);
				ModPluginCrops.platform.message2(player, "chance(second) bonus rate: min(" + EnumChatFormatting.RED + "%s" + EnumChatFormatting.RESET + ", 100) %%",
					chance * 100);

				if (world.rand.nextFloat() <= chance) {
					drop++;
					ModPluginCrops.platform.message2(player, "chance(second) drop+=1 bonus!");
				}

			} else {
				ModPluginCrops.platform.message2(player, "no bonus chance bonus rate: min(" + EnumChatFormatting.RED + "%s" + EnumChatFormatting.RESET + ", 100) %%",
					firstchance * 1.5F * 100);
				if (world.rand.nextFloat() <= firstchance * 1.5F) {
					drop++;
					ModPluginCrops.platform.message2(player, "bonus=false!! drop+=1");
				}
			}
			ModPluginCrops.platform.message2(player, "all drop count: %s", drop);

			ItemStack re[] = new ItemStack[drop];
			for (int i = 0; i < drop; i++) {
				re[i] = cropCard.getSeeds(cropTile);
			}

			if (!world.isRemote && re.length > 0) {
				for (int i = 0; i < re.length; i++) {
					try {
						if (re[i].getItem() != HCrop.getItemStackSeed().getItem()) {
							re[i].stackTagCompound = null;
						}
					} catch (IC2VersionException e) {
						e.printStackTrace();
						return true;
					}
					dropAsEntity(world, x, y, z, re[i]);
				}

			}

		}

		return true;
	}

}
