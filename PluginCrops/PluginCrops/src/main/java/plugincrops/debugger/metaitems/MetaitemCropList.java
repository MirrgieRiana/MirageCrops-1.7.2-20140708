package plugincrops.debugger.metaitems;

import static net.minecraft.util.EnumChatFormatting.*;
import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.ModPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MetaitemCropList extends MetaitemPluginCrops
{

	public MetaitemCropList(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (!world.isRemote) return true;

		ModPluginCrops.platform.message2(player, EnumChatFormatting.YELLOW + "[Crops]" + EnumChatFormatting.RESET);

		int i = 0;

		for (CropCard cropCard : Crops.instance.getCrops()) {
			ModPluginCrops.platform.message2(player,
				"[%s]: %s:" + RED + "%s" + RESET
					+ " (" + DARK_GREEN + "%s" + RESET
					+ ") Tier:" + YELLOW + "%s" + RESET
					+ " By:" + BLUE + "%s" + RESET
					+ " OldId:" + LIGHT_PURPLE + "%s" + RESET,
				i,
				cropCard.owner(),
				cropCard.name(),
				StatCollector.translateToLocal(cropCard.displayName()),
				cropCard.tier(),
				cropCard.discoveredBy(),
				cropCard.getId());
			i++;
		}

		return true;
	}

}
