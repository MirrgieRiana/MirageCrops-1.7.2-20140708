package mirrgmods.plugincrops.api;

import ic2.api.crops.CropCard;

public interface ICropCrossingHandler
{

	public boolean isCalculating(CropCard a, CropCard b);

	public int calculateRatioFor(CropCard a, CropCard b);

}
