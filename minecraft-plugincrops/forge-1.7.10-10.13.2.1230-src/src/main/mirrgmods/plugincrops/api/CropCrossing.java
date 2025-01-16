package mirrgmods.plugincrops.api;

import ic2.api.crops.CropCard;

import java.util.ArrayList;

public class CropCrossing
{

	/**
	 * from the last handler
	 */
	public static ArrayList<ICropCrossingHandler> handlers = new ArrayList<ICropCrossingHandler>();

	static {
		handlers.add(new CropCrossingHandlerDefault());
	}

	public static int calculateRatioFor(CropCard a, CropCard b)
	{

		for (int i = handlers.size() - 1; i >= 0; i--) {
			if (handlers.get(i).isCalculating(a, b)) {
				return handlers.get(i).calculateRatioFor(a, b);
			}
		}

		return 0;
	}

}
