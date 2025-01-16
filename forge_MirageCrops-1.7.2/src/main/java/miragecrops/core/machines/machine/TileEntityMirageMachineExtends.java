package miragecrops.core.machines.machine;

import miragecrops.core.machines.machine.framework.ContainerMirageMachine;
import miragecrops.core.machines.machine.framework.EnergySlot;
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

public class TileEntityMirageMachineExtends extends TileEntityMirageMachineConnected
{

	@Override
	protected void tick()
	{

	}

	@Override
	public String getDefaultName()
	{
		return "container.mirageMachineExtends";
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

		container.addSlot(new Slot(inventory1, 0, 8 + (2 * 2 + 1) * 9, 4 + (2 * 2 + 1) * 9));
		container.addSlot(new Slot(inventory2, 0, 8 + (4 * 2) * 9, 4 + (2 * 2 - 2) * 9));
		container.addSlot(new Slot(inventory3, 0, 8 + (6 * 2 - 1) * 9, 4 + (2 * 2 + 1) * 9));

		container.addFluidSlot(new FluidSlot(fluidTank1, 8 + (1 * 2) * 9, 19, 16, 10 * 5 - 1));
		container.addFluidSlot(new FluidSlot(fluidTank2, 8 + (7 * 2) * 9, 19, 16, 10 * 5 - 1));

		container.addEnergySlot(new EnergySlot(progress, 8 + (4 * 2 - 1) * 9 + 5, 4 + (2 * 2 + 1) * 9 - 1, 24, 17));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				container.addSlot(new Slot(container.getPlayer().inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			container.addSlot(new Slot(container.getPlayer().inventory, i, 8 + i * 18, 142));
		}

	}

	//////////////////////////////////// Inventory ////////////////////////////////////

	public final Inventory inventory1 = new Inventory(this, 1);
	public final Inventory inventory2 = new Inventory(this, 1);
	public final Inventory inventory3 = new Inventory(this, 1);

	{
		inventoryChain.add(inventory1);
		inventoryChain.add(inventory2);
		inventoryChain.add(inventory3);
	}

	@Override
	protected Inventory[] getInventoryInsert(int side, ItemStack itemStack)
	{
		return new Inventory[] {
			inventory1,
		};
	}

	@Override
	protected Inventory[] getInventoryExtract(int side, ItemStack itemStack)
	{
		return new Inventory[] {
			inventory3,
		};
	}

	@Override
	protected Inventory[] getInventoryAccessible(int side)
	{
		return new Inventory[] {
			inventory1,
			inventory2,
			inventory3,
		};
	}

	@Override
	public void onBroken()
	{
		inventory1.dropAll(worldObj, xCoord, yCoord, zCoord);
		inventory2.dropAll(worldObj, xCoord, yCoord, zCoord);
		inventory3.dropAll(worldObj, xCoord, yCoord, zCoord);
	}

	//////////////////////////////////// FluidTank ////////////////////////////////////

	public final FluidTank fluidTank1 = new FluidTank(this, 16000);
	public final FluidTank fluidTank2 = new FluidTank(this, 16000);

	{
		add(fluidTank1, "Fluid1");
		add(fluidTank2, "Fluid2");
	}

	@Override
	protected FluidTank getFluidTankFill(ForgeDirection arg0, Fluid arg1)
	{
		return fluidTank1;
	}

	@Override
	protected FluidTank getFluidTankDrain(ForgeDirection arg0, Fluid arg1)
	{
		return fluidTank2;
	}

	@Override
	protected FluidTank getFluidTankDrain(ForgeDirection arg0)
	{
		return fluidTank2;
	}

	@Override
	protected FluidTank[] getFluidTank(ForgeDirection arg0)
	{
		return new FluidTank[] {
			fluidTank1,
			fluidTank2,
		};
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with)
	{
		if (type.equals(PipeType.FLUID)) return ConnectOverride.CONNECT;
		return ConnectOverride.DEFAULT;
	}

	//////////////////////////////////// EnergyTank ////////////////////////////////////

	public final EnergyTank progress = new EnergyTank(this, 1000);

}
