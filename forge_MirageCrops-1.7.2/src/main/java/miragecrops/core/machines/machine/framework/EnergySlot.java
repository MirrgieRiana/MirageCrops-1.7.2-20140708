package miragecrops.core.machines.machine.framework;

public class EnergySlot
{

	protected EnergyTank energyTank;
	protected int x;
	protected int y;
	protected int w;
	protected int h;

	public EnergySlot(EnergyTank energyTank, int x, int y, int w, int h)
	{
		this.energyTank = energyTank;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

}
