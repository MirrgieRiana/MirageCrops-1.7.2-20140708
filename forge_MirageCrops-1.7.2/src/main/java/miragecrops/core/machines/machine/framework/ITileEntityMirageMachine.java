package miragecrops.core.machines.machine.framework;

import net.minecraft.tileentity.TileEntity;

public interface ITileEntityMirageMachine extends IInventoryName
{

	public TileEntity getTileEntity();

	public void onBroken();

}
