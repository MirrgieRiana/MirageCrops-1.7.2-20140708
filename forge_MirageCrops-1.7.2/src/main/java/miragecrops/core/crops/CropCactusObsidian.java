package miragecrops.core.crops;

import ic2.api.crops.ICropTile;
import miragecrops.framework.crop.CropSpecification;
import net.minecraft.entity.EntityLivingBase;

public class CropCactusObsidian extends CropCactus
{

	public CropCactusObsidian(CropSpecification cropSpecification, ICrossingRecipe iCrossingRecipe)
	{
		super(cropSpecification, iCrossingRecipe);
	}

	@Override
	protected float getCollisionDamage(ICropTile crop, EntityLivingBase entity)
	{
		return 2.0F;
	}

}
