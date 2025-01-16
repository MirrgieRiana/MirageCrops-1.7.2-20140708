package plugincrops.debugger.metaitems;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import plugincrops.debugger.HCrop;
import plugincrops.debugger.IC2VersionException;
import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.ModPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MetaitemCrossingRateTable extends MetaitemPluginCrops
{

	public MetaitemCrossingRateTable(IMetaitem _super, int metaid, Arguments arguments)
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

		Collections.sort(crops, new Comparator<CropCard>() {
			@Override
			public int compare(CropCard o1, CropCard o2)
			{
				if (o1.tier() < o2.tier()) return -1;
				if (o1.tier() > o2.tier()) return 1;
				return 0;
			}
		});

		File file = new File(Minecraft.getMinecraft().mcDataDir, "PluginCrops_cropCrossingRatioTable.csv");
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			ModPluginCrops.platform.message2(player, "保存ファイルが開けません！: %s", file);
		}

		{
			{
				StringBuffer sb = new StringBuffer();
				sb.append("＼,");
				sb.append("old,");
				for (int i = 0; i < crops.size(); i++) {
					sb.append("\"" + crops.get(i).owner() + ":" + crops.get(i).name() + "\"");
					sb.append(",");
				}
				extracted(out, player, "%s", sb.toString());
			}

			{
				StringBuffer sb = new StringBuffer();
				sb.append("new,");
				sb.append("＼,");
				for (int i = 0; i < crops.size(); i++) {
					sb.append(crops.get(i).tier());
					sb.append(",");
				}
				extracted(out, player, "%s", sb.toString());
			}

			for (int i = 0; i < crops.size(); i++) {
				StringBuffer sb = new StringBuffer();
				sb.append("\"" + crops.get(i).owner() + ":" + crops.get(i).name() + "\"");
				sb.append(",");
				sb.append(crops.get(i).tier());
				sb.append(",");
				for (int j = 0; j < crops.size(); j++) {

					try {
						sb.append(HCrop.invoke_calculateRatioFor(cropTile, crops.get(i), crops.get(j)));
					} catch (IC2VersionException e) {
						sb.append(-1);
					}
					sb.append(",");

				}
				extracted(out, player, "%s", sb.toString());
			}
		}

		if (out != null) {
			out.close();
			ModPluginCrops.platform.message2(player, "ファイルに保存しました: %s", file);
		}

		return true;
	}

	private void extracted(PrintStream out, EntityPlayer player,
		String message, Object... args)
	{
		String string = String.format(message, args);

		if (out != null) out.println(string);
		ModPluginCrops.platform.messagePlayer(player, string);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List strings, boolean isShift)
	{
		super.addInformation(itemStack, player, strings, isShift);
		strings.add("支柱を対象にクリックしてください。");
	}

}
