package miragecrops.core.machines.network;

import miragecrops.api.machines.ITransportContent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NetworkAgentsEnumerator
{

	private static int MAX_CAPACITY = 2000000000;

	private final ForgeDirection[] directions = new ForgeDirection[] {
		ForgeDirection.WEST,
		ForgeDirection.EAST,
		ForgeDirection.DOWN,
		ForgeDirection.UP,
		ForgeDirection.SOUTH,
		ForgeDirection.NORTH
	};

	private ITransportContent transportContent;
	private World world;
	private int x;
	private int y;
	private int z;

	public NetworkAgentsEnumerator(ITransportContent transportContent, World world, int x, int y, int z)
	{
		this.transportContent = transportContent;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Points enumerateAgents()
	{
		Points points = new Points();
		walk(MAX_CAPACITY, points, points.new Point(x, y, z));
		return points;
	}

	public void walk(int capacity, Points points, Points.Point center)
	{
		points.put(center, capacity);

		for (ForgeDirection direction : directions) {
			Points.Point point = points.new Point(
				center.x + direction.offsetX,
				center.y + direction.offsetY,
				center.z + direction.offsetZ);

			if (center.canWalk(world, point)) {
				float capacity2 = point.getCapacity(transportContent, world, center);
				if (capacity2 >= 1) {
					int cost = (int) (MAX_CAPACITY / capacity2);
					if (capacity >= cost) {

						if (point.isBetter(capacity - cost)) {
							walk(capacity - cost, points, point);
						}

					}
				}
			}
		}

	}

}
