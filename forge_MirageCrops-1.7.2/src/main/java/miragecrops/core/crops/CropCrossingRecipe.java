package miragecrops.core.crops;

import ic2.api.crops.ICropTile;
import miragecrops.framework.crop.CropMirage;
import miragecrops.framework.crop.CropSpecification;

public class CropCrossingRecipe extends CropMirage
{

	protected final ICrossingRecipe crossingRecipe;

	public CropCrossingRecipe(CropSpecification cropSpecification, ICrossingRecipe crossingRecipe)
	{
		super(cropSpecification);
		this.crossingRecipe = crossingRecipe;
	}

	@Override
	protected boolean canSpawnAt(ICropTile iCropTile)
	{
		return crossingRecipe.canSpawnAt(this, iCropTile);
	}

}
