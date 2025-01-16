package plugincrops.debugger.metaitems;

import java.util.ArrayList;

import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.ModPluginCrops;

public class MetaitemOreList extends MetaitemPluginCrops
{

	public MetaitemOreList(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (!world.isRemote) return true;

		ModPluginCrops.platform.message2(player, EnumChatFormatting.YELLOW + "[Ores]" + EnumChatFormatting.RESET);

		int i = 0;

		for (String oreName : OreDictionary.getOreNames()) {
			ArrayList<ItemStack> ores = OreDictionary.getOres(oreName);

			if (ores.size() == 0) {
				ModPluginCrops.platform.message2(player, "%s: {}", oreName);
			} else if (ores.size() == 1) {
				ModPluginCrops.platform.message2(player, "%s: %s %s", oreName,
					ores.get(0), ores.get(0).getDisplayName());
			} else {
				ModPluginCrops.platform.message2(player, "%s:", oreName);

				for (ItemStack ore : ores) {
					ModPluginCrops.platform.message2(player, "    %s %s",
						ore, ores.get(0).getDisplayName());
				}
			}

		}

		return true;
	}

}
