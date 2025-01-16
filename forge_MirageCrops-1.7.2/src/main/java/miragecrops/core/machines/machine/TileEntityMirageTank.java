package miragecrops.core.machines.machine;

import miragecrops.core.machines.machine.framework.ContainerMirageMachine;
import miragecrops.core.machines.machine.framework.FluidSlot;
import miragecrops.core.machines.machine.framework.FluidTank;
import miragecrops.core.machines.machine.framework.TileEntityMirageMachineConnected;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileEntityMirageTank extends TileEntityMirageMachineConnected
{

	@Override
	protected void tick()
	{

	}

	@Override
	public String getDefaultName()
	{
		return "container.mirageTank";
	}

	//////////////////////////////////// GUI ////////////////////////////////////

	protected ResourceLocation guiTexture =
		new ResourceLocation(LoaderMirageMachine.module.getMod().getModId() + ":" + "textures/gui/mirageMachine.png");

	@Override
	protected boolean hasGui()
	{
		return true;
	}

	@Override
	protected ResourceLocation getGuiTexture(ContainerMirageMachine container)
	{
		return guiTexture;
	}

	@Override
	protected void prepareContainerSlots(ContainerMirageMachine container)
	{

		container.addFluidSlot(new FluidSlot(fluidTank1, 8 + (3 * 2) * 9, 19, 16 * 3 + 2 * 2, 10 * 5 - 1));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				container.addSlot(new Slot(container.getPlayer().inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			container.addSlot(new Slot(container.getPlayer().inventory, i, 8 + i * 18, 142));
		}

	}

	//////////////////////////////////// FluidTank ////////////////////////////////////

	public final FluidTank fluidTank1 = new FluidTank(this, 16000);

	{
		add(fluidTank1, "Fluid1");
	}

	@Override
	protected FluidTank getFluidTankFill(ForgeDirection arg0, Fluid arg1)
	{
		return fluidTank1;
	}

	@Override
	protected FluidTank getFluidTankDrain(ForgeDirection arg0, Fluid arg1)
	{
		return fluidTank1;
	}

	@Override
	protected FluidTank getFluidTankDrain(ForgeDirection arg0)
	{
		return fluidTank1;
	}

	public FluidTank getFluidTank()
	{
		return fluidTank1;
	}

	@Override
	protected FluidTank[] getFluidTank(ForgeDirection arg0)
	{
		return new FluidTank[] {
			fluidTank1,
		};
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with)
	{
		if (type.equals(PipeType.FLUID)) return ConnectOverride.CONNECT;
		return ConnectOverride.DEFAULT;
	}

}
