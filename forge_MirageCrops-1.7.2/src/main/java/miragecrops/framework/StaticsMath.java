package miragecrops.framework;

import java.util.Random;

public class StaticsMath
{

	public static int floorRandom(double value)
	{
		int integer = (int) Math.floor(value);
		double addition = value - integer;

		if (Math.random() < addition) {
			integer++;
		}

		return integer;
	}

	public static int floorRandom(double value, Random random)
	{
		int integer = (int) Math.floor(value);
		double addition = value - integer;

		if (random.nextDouble() < addition) {
			integer++;
		}

		return integer;
	}

}
