package miragecrops.core.machines.machine.framework;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityProvider<T extends TileEntity> implements ITileEntityProvider
{

	private final Class<T> clazz;

	public TileEntityProvider(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public T createNewTileEntity(World arg0, int arg1)
	{
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

}
