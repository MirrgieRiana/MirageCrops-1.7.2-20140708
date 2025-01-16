package mirrgmods.plugincrops.api;

import ic2.api.crops.CropCard;

public class CropCrossingHandlerDefault implements ICropCrossingHandler
{

	@Override
	public boolean isCalculating(CropCard a, CropCard b)
	{
		return true;
	}

	@Override
	public int calculateRatioFor(CropCard a, CropCard b)
	{
		if (a == b) return 500;
		int value = 0;
		for (int i = 0; i < 5; ++i) {
			int c = a.stat(i) - b.stat(i);
			if (c < 0) c *= -1;
			switch (c)
			{
				default:
					--value;
					break;
				case 0:
					value += 2;
					break;
				case 1:
					++value;
					break;
				case 2:
					break;
			}
		}
		for (int i = 0; i < a.attributes().length; ++i) {
			for (int j = 0; j < b.attributes().length; ++j) {
				if (a.attributes()[i].equalsIgnoreCase(b.attributes()[j])) {
					value += 5;
				}
			}
		}

		if (b.tier() < a.tier() - 1) {
			value -= 2 * (a.tier() - b.tier());
		}
		if (b.tier() - 3 > a.tier()) {
			value -= b.tier() - a.tier();
		}
		if (value < 0) value = 0;
		return value;
	}

}
