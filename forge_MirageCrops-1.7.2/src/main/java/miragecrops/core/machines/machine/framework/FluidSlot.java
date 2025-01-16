package miragecrops.core.machines.machine.framework;

public class FluidSlot
{

	protected FluidTank fluidTank;
	protected int x;
	protected int y;
	protected int w;
	protected int h;

	public FluidSlot(FluidTank fluidTank, int x, int y, int w, int h)
	{
		this.fluidTank = fluidTank;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

}
