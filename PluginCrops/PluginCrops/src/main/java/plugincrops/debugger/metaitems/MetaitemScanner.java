package plugincrops.debugger.metaitems;

import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.ModPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MetaitemScanner extends MetaitemPluginCrops
{

	public MetaitemScanner(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (world.isRemote) return true;

		try {
			print(player, world, x, y, z, side, x2, y2, z2);
		} catch (Exception e) {
			ModPluginCrops.platform.message2(player, "[Exception]: %s, %s",
				e.getClass().getSimpleName(),
				e.getMessage());
			e.printStackTrace();
		}

		return true;
	}

	public void print(EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		ModPluginCrops.platform.message2(player, "§d[Scan]§r: §cpos§r=(%s,%s,%s), §cside§r=%s, §cpos2§r=(%.3f,%.3f,%.3f)",
			x,
			y,
			z,
			side,
			x2, y2, z2);
		Block block = world.getBlock(x, y, z);
		ModPluginCrops.platform.message2(player, "[Block]: §6%s§r",
			block);
		ModPluginCrops.platform.message2(player, "[Block]: §2%s§r, §cid§r=%s, §cmeta§r=%s, §chardness§r=%s",
			block.getLocalizedName(),
			Block.getIdFromBlock(block),
			world.getBlockMetadata(x, y, z),
			block.getBlockHardness(world, x, y, z));

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null) {
			ModPluginCrops.platform.message2(player, "[TE]: §6%s§r",
				tileEntity);

			if (tileEntity instanceof ICropTile) {
				ICropTile cropTile = (ICropTile) tileEntity;

				ModPluginCrops.platform.message2(player, "[TEC]: §cNuHuAi§r=(%s,%s,%s), §cNuHyWe§r=(%s,%s,%s)",
					cropTile.getNutrients(),
					cropTile.getHumidity(),
					cropTile.getAirQuality(),
					cropTile.getNutrientStorage(),
					cropTile.getHydrationStorage(),
					cropTile.getWeedExStorage());

				ModPluginCrops.platform.message2(player, "[TEC]: §cGrGaRe§r=(%s,%s,%s), §cscan§r=%s, §csize§r=%s",
					cropTile.getGrowth(),
					cropTile.getGain(),
					cropTile.getResistance(),
					cropTile.getScanLevel(),
					cropTile.getSize());

				CropCard cropCard = cropTile.getCrop();
				if (cropCard != null) {
					ModPluginCrops.platform.message2(player, "[Crop]: §6%s§r",
						cropCard);

					ModPluginCrops.platform.message2(player, "[Crop]: §2%s§r, (§cmod§r:§cname§r)=(%s:%s), §cby§r=%s",
						StatCollector.translateToLocal(cropCard.displayName()),
						cropCard.owner(),
						cropCard.name(),
						cropCard.discoveredBy());

					ModPluginCrops.platform.message2(player, "[Crop]: §cattrs§r=%s",
						join(", ", cropCard.attributes()));

					ModPluginCrops.platform.message2(player, "[Crop]: §cid§r=%s, §cmaxSize§r=%s, §ctier§r=%s, §cstat§r=(%s,%s,%s,%s,%s)",
						cropCard.getId(),
						cropCard.maxSize(),
						cropCard.tier(),
						cropCard.stat(0),
						cropCard.stat(1),
						cropCard.stat(2),
						cropCard.stat(3),
						cropCard.stat(4));

					ModPluginCrops.platform.message2(player, "[TEC+C]: §9can§r{§cbeHarvested§r=%s, §ccross§r=%s, §cgrow§r=%s}",
						cropCard.canBeHarvested(cropTile),
						cropCard.canCross(cropTile),
						cropCard.canGrow(cropTile));

					ModPluginCrops.platform.message2(player, "[TEC+C]: §crootslength§r=%s, §cgrowthDuration§r=%s, ",
						cropCard.getrootslength(cropTile),
						cropCard.growthDuration(cropTile));

					ModPluginCrops.platform.message2(player, "[TEC+C]: §coptimalHavestSize§r=%s, §csizeAfterHarvest§r=%s",
						cropCard.getOptimalHavestSize(cropTile),
						cropCard.getSizeAfterHarvest(cropTile));

					ModPluginCrops.platform.message2(player, "[TEC+C]: §9emit§r{§credstone§r=%s, §clight§r=%s}",
						cropCard.emitRedstone(cropTile),
						cropCard.getEmittedLight(cropTile));

					ModPluginCrops.platform.message2(player, "[TEC+C]: §9drop§r{§cgainChance§r=%.4f, §cseedChance§r=%.4f}",
						cropCard.dropGainChance(),
						cropCard.dropSeedChance(cropTile));

					ModPluginCrops.platform.message2(player, "[TEC+C]: §cisWeed§r=%s, §cweightInfluences§r=%s",
						cropCard.isWeed(cropTile),
						cropCard.weightInfluences(cropTile, cropTile.getHumidity(), cropTile.getNutrients(), cropTile.getAirQuality()));

					ItemStack gain = cropCard.getGain(cropTile);
					ModPluginCrops.platform.message2(player, "[Gain]: §6%s§r", gain);

					ItemStack seeds = cropCard.getSeeds(cropTile);
					ModPluginCrops.platform.message2(player, "[Seed]: §6%s§r", seeds);

				}

			}

		}

	}

	private String join(String separator, String[] strings)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strings.length; i++) {
			if (i != 0) sb.append(separator);
			sb.append(strings[i]);
		}
		return sb.toString();
	}

}
