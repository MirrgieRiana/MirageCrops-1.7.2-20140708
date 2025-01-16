package miragecrops.core.machines.machine;

import miragecrops.core.machines.machine.framework.ContainerMirageMachine;
import miragecrops.core.machines.machine.framework.EnergyTank;
import miragecrops.core.machines.machine.framework.FluidSlot;
import miragecrops.core.machines.machine.framework.FluidTank;
import miragecrops.core.machines.machine.framework.Inventory;
import miragecrops.core.machines.machine.framework.TileEntityMirageMachineConnected;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileEntitySqueezer extends TileEntityMirageMachineConnected
{

	@Override
	protected void tick()
	{

	}

	@Override
	public String getDefaultName()
	{
		return "container.squeezer";
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

		container.addFluidSlot(new FluidSlot(fluidTank, 8 + (3 * 2) * 9, 19, 16 * 3 + 2 * 2, 10 * 5 - 1));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				container.addSlot(new Slot(container.getPlayer().inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			container.addSlot(new Slot(container.getPlayer().inventory, i, 8 + i * 18, 142));
		}

	}

	//////////////////////////////////// Contents ////////////////////////////////////

	public final Inventory inventory = new Inventory(this, 1);
	public final FluidTank fluidTank = new FluidTank(this, 16000);
	public final EnergyTank progress = new EnergyTank(this, 1000);

	{
		add(inventory, "Inventory1");
		add(fluidTank, "Fluid1");
		add(progress, "Progress");
	}

	@Override
	protected Inventory[] getInventoryAccessible(int side)
	{
		return new Inventory[] {
			inventory,
		};
	}

	@Override
	protected Inventory[] getInventoryExtract(int side, ItemStack itemStack)
	{
		return null;
	}

	@Override
	protected Inventory[] getInventoryInsert(int side, ItemStack itemStack)
	{
		return new Inventory[] {
			inventory,
		};
	}

	@Override
	protected FluidTank getFluidTankFill(ForgeDirection arg0, Fluid arg1)
	{
		return fluidTank;
	}

	@Override
	protected FluidTank getFluidTankDrain(ForgeDirection arg0, Fluid arg1)
	{
		return fluidTank;
	}

	@Override
	protected FluidTank getFluidTankDrain(ForgeDirection arg0)
	{
		return fluidTank;
	}

	public FluidTank getFluidTank()
	{
		return fluidTank;
	}

	@Override
	protected FluidTank[] getFluidTank(ForgeDirection arg0)
	{
		return new FluidTank[] {
			fluidTank,
		};
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with)
	{
		if (type.equals(PipeType.FLUID)) return ConnectOverride.CONNECT;
		return ConnectOverride.DEFAULT;
	}

}
