package plugincrops.debugger.metaitems;

import static net.minecraft.util.EnumChatFormatting.*;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map.Entry;

import plugincrops.debugger.HCrop;
import plugincrops.debugger.IC2VersionException;
import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.ModPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MetaitemScanner2 extends MetaitemPluginCrops
{

	public MetaitemScanner2(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (!world.isRemote) return true;

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null) return true;
		if (!(tileEntity instanceof ICropTile)) return true;
		ICropTile cropTile = (ICropTile) tileEntity;

		ArrayList<CropCard> crops = new ArrayList<CropCard>(Crops.instance.getCrops());

		if (cropTile.getCrop() == null) return true;

		ModPluginCrops.platform.message2(player,
			YELLOW + "[Crossing List]" + RESET
				+ " Tier:" + YELLOW + "%s" + RESET
				+ ": %s:" + RED + "%s" + RESET
				+ " (" + DARK_GREEN + "%s" + RESET + ")",
			cropTile.getCrop().tier(),
			cropTile.getCrop().owner(),
			cropTile.getCrop().name(),
			StatCollector.translateToLocal(cropTile.getCrop().displayName()));
		ModPluginCrops.platform.message2(player, AQUA + "[To]" + RESET);
		{
			Hashtable<CropCard, Integer> hashCropCardToRatio = new Hashtable<CropCard, Integer>();
			for (CropCard cropCard : crops) {
				if (cropCard != cropTile.getCrop()) {
					try {
						int ratio = HCrop.invoke_calculateRatioFor(cropTile, cropCard, cropTile.getCrop());
						if (ratio != 0) {
							hashCropCardToRatio.put(cropCard, ratio);
						}
					} catch (IC2VersionException e) {}
				}
			}

			extracted(player, hashCropCardToRatio);
		}

		ModPluginCrops.platform.message2(player, AQUA + "[From]" + RESET);
		{
			Hashtable<CropCard, Integer> hashCropCardToRatio = new Hashtable<CropCard, Integer>();
			for (CropCard cropCard : crops) {
				if (cropCard != cropTile.getCrop()) {
					try {
						int ratio = HCrop.invoke_calculateRatioFor(cropTile, cropTile.getCrop(), cropCard);
						if (ratio != 0) {
							hashCropCardToRatio.put(cropCard, ratio);
						}
					} catch (IC2VersionException e) {}
				}
			}

			extracted(player, hashCropCardToRatio);
		}

		return true;
	}

	private void extracted(EntityPlayer player, Hashtable<CropCard, Integer> hashCropCardToRatio)
	{
		ArrayList<Entry<CropCard, Integer>> entries =
			new ArrayList<Entry<CropCard, Integer>>(hashCropCardToRatio.entrySet());

		Collections.sort(entries, new Comparator<Entry<CropCard, Integer>>() {
			@Override
			public int compare(Entry<CropCard, Integer> o1, Entry<CropCard, Integer> o2)
			{
				if (o1.getValue() > o2.getValue()) return -1;
				if (o1.getValue() < o2.getValue()) return 1;
				return 0;
			}
		});

		for (Entry<CropCard, Integer> entry : entries) {
			ModPluginCrops.platform.message2(player,
				"Rate:" + LIGHT_PURPLE + "%s" + RESET
					+ " Tier:" + YELLOW + "%s" + RESET
					+ ", %s:" + RED + "%s" + RESET
					+ "(" + DARK_GREEN + "%s" + RESET + ")",
				entry.getValue(),
				entry.getKey().tier(),
				entry.getKey().owner(),
				entry.getKey().name(),
				StatCollector.translateToLocal(entry.getKey().displayName()));
		}
	}
}
