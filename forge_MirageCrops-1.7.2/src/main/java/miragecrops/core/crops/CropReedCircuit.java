package miragecrops.core.crops;

import ic2.api.crops.ICropTile;
import miragecrops.framework.crop.CropSpecification;

public class CropReedCircuit extends CropCrossingRecipe
{

	public CropReedCircuit(CropSpecification cropSpecification, ICrossingRecipe crossingRecipe)
	{
		super(cropSpecification, crossingRecipe);
	}

	@Override
	public byte getSizeAfterHarvest(ICropTile crop)
	{
		if (crop.getSize() == maxSize()) return (byte) (Math.random() * 3 + 1);
		return super.getSizeAfterHarvest(crop);
	}

	@Override
	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() >= 2;
	}

}
