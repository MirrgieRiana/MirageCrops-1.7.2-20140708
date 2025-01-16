package plugincrops.debugger.metaitems;

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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MetaitemCrosser2 extends MetaitemPluginCrops
{

	public MetaitemCrosser2(IMetaitem _super, int metaid, Arguments arguments)
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

		Hashtable<String, Integer> hashTimes = new Hashtable<String, Integer>();

		int limit = 10000;

		for (int i = 0; i < limit; i++) {

			{
				cropTile.reset();

				try {
					HCrop.set_upgraded(cropTile, true);
					HCrop.invoke_attemptCrossing(cropTile);
				} catch (IC2VersionException e) {
					e.printStackTrace();
					return true;
				}

				String cropSignature;
				if (cropTile.getCrop() != null) {
					cropSignature = cropTile.getCrop().owner()
						+ ":"
						+ EnumChatFormatting.AQUA
						+ cropTile.getCrop().name()
						+ EnumChatFormatting.RESET
						+ " "
						+ EnumChatFormatting.GREEN
						+ StatCollector.translateToLocal(cropTile.getCrop().displayName())

						+ EnumChatFormatting.RESET;
				} else {
					cropSignature = EnumChatFormatting.YELLOW + "  missed" + EnumChatFormatting.RESET;
				}

				if (hashTimes.containsKey(cropSignature)) {
					hashTimes.put(cropSignature, hashTimes.get(cropSignature) + 1);
				} else {
					hashTimes.put(cropSignature, 1);
				}

			}

		}
		ArrayList<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(hashTimes.entrySet());

		Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2)
			{
				if (o1.getValue() < o2.getValue()) return 1;
				if (o1.getValue() > o2.getValue()) return -1;
				return 0;
			}
		});

		for (Entry<String, Integer> entry : entries) {
			ModPluginCrops.platform.message2(player, "%s (%.2f%%): %s",
				entry.getValue(),
				entry.getValue() * 100 / (double) limit,
				entry.getKey());
		}

		return true;
	}
}
