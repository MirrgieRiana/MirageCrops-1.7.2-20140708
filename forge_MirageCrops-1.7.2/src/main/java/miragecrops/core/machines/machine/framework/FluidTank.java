package miragecrops.core.machines.machine.framework;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class FluidTank implements IFluidTank
{

	public FluidStack fluidStack;
	protected ISetDirty parent;
	private int capacity;

	public FluidTank(ISetDirty parent, int capacity)
	{
		this.parent = parent;
		this.capacity = capacity;
	}

	@Override
	public int getCapacity()
	{
		return capacity;
	}

	public void markDirty()
	{
		parent.setDirty();
	}

	public void writeToNBT(NBTTagCompound tag)
	{
		if (fluidStack != null) {
			fluidStack.writeToNBT(tag);
		}
	}

	public void readFromNBT(NBTTagCompound tag)
	{
		fluidStack = FluidStack.loadFluidStackFromNBT(tag);
	}

	public boolean isEmpty()
	{
		if (fluidStack == null) return true;
		if (fluidStack.getFluid() == null) return true;
		if (fluidStack.amount == 0) return true;
		return false;
	}

	public FluidStack getValidatedFluidStack()
	{
		return isEmpty() ? null : fluidStack;
	}

	public boolean canFill(Fluid fluid)
	{
		if (fluid == null) return false;
		if (isEmpty()) return true;
		return fluid.equals(this.fluidStack.getFluid());
	}

	public boolean canDrain(Fluid fluid)
	{
		if (fluid == null) return false;
		if (isEmpty()) return false;
		return fluid.equals(this.fluidStack.getFluid());
	}

	public boolean canDrain(FluidStack fluidStack)
	{
		if (fluidStack == null) return false;
		if (!canDrain(fluidStack.getFluid())) return false;
		return true;
	}

	public boolean canFill(FluidStack fluidStack)
	{
		if (fluidStack == null) return false;
		if (!canFill(fluidStack.getFluid())) return false;
		return true;
	}

	public int getSpace()
	{
		if (isEmpty()) return getCapacity();
		return getCapacity() - fluidStack.amount;
	}

	@Override
	public int fill(FluidStack paramFluidStack, boolean paramBoolean)
	{
		if (!canFill(paramFluidStack)) return 0;
		int moveAmount = Math.min(getSpace(), paramFluidStack.amount);

		if (paramBoolean) {
			if (isEmpty()) {
				fluidStack = new FluidStack(paramFluidStack.getFluid(), 0);
			}
			fluidStack.amount += moveAmount;
			parent.setDirty();
		}

		return moveAmount;
	}

	@Override
	public FluidStack drain(int paramInt, boolean paramBoolean)
	{
		if (isEmpty()) return null;
		int moveAmount = Math.min(fluidStack.amount, paramInt);

		if (paramBoolean) {
			fluidStack.amount -= moveAmount;
			parent.setDirty();
		}

		return new FluidStack(fluidStack.getFluid(), moveAmount);
	}

	public FluidStack drain(FluidStack arg1, boolean arg2)
	{
		if (isEmpty()) return null;
		if (arg1 == null) return null;
		if (!fluidStack.getFluid().equals(arg1.getFluid())) return null;
		return drain(arg1.amount, arg2);
	}

	@Override
	public FluidStack getFluid()
	{
		return fluidStack;
	}

	@Override
	public int getFluidAmount()
	{
		return fluidStack == null ? 0 : fluidStack.amount;
	}

	@Override
	public FluidTankInfo getInfo()
	{
		return new FluidTankInfo(fluidStack, capacity);
	}

}
