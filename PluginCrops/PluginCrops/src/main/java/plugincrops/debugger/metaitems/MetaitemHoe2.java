package plugincrops.debugger.metaitems;

import plugincrops.debugger.MetaitemPluginCrops;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import mirrg.minecraft.item.multi.copper.IMetaitem;
import net.minecraft.world.World;

public class MetaitemHoe2 extends MetaitemHoe
{

	public MetaitemHoe2(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid, arguments);
	}

	@Override
	protected boolean canHoe(World world, int x, int y, int z)
	{
		return !world.getBlock(x, y + 1, z).isOpaqueCube();
	}

}
