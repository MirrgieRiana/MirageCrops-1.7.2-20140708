package miragecrops.core.machines.network;

import java.util.Enumeration;
import java.util.Hashtable;

import miragecrops.api.machines.IMiragePipe;
import miragecrops.api.machines.ITransportContent;
import miragecrops.core.machines.pipe.HelperMiragePipe;
import net.minecraft.world.World;

public class Points
{

	private Hashtable<Point, Integer> points = new Hashtable<Point, Integer>();

	public void put(int x, int y, int z, int capacity)
	{
		put(new Point(x, y, z), capacity);
	}

	public void put(Point p, int capacity)
	{
		points.put(p, capacity);
	}

	public Enumeration<Point> keys()
	{
		return points.keys();
	}

	public class Point
	{

		public final int x;
		public final int y;
		public final int z;

		public Point(int x, int y, int z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public final boolean equals(Object obj)
		{
			if (obj == null) return false;
			if (!(obj instanceof Point)) return false;
			Point other = (Point) obj;
			if (x != other.x) return false;
			if (y != other.y) return false;
			if (z != other.z) return false;
			return true;
		}

		@Override
		public final int hashCode()
		{
			return x + y + z;
		}

		public boolean isBetter(int capacity)
		{
			Integer lastCapacity = points.get(this);
			if (lastCapacity == null) return true;
			return capacity > lastCapacity;
		}

		public boolean canWalk(World world, Point p)
		{
			return HelperMiragePipe.canConnectMiragePipe(world, x, y, z, p.x, p.y, p.z);
		}

		public float getCapacity(ITransportContent iTransportContent, World world, Point from)
		{
			IMiragePipe iMiragePipe = HelperMiragePipe.getMiragePipe(world, x, y, z);
			if (iMiragePipe == null) return 0;
			return iMiragePipe.getTransportCapacity(iTransportContent, world, x, y, z, from.x, from.y, from.z);
		}

	}

}
