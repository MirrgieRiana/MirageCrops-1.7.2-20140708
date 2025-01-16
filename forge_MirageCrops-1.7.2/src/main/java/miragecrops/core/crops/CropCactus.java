package miragecrops.core.crops;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import miragecrops.framework.crop.CropSpecification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class CropCactus extends CropCrossingRecipe
{

	public CropCactus(CropSpecification cropSpecification, ICrossingRecipe iCrossingRecipe)
	{
		super(cropSpecification, iCrossingRecipe);
		cropSpecification.addListener(new CropSpecification.CropCardListenerImpl() {

			@Override
			public void onEntityCollision(CropCard cropCard, ICropTile crop, Entity entity)
			{
				if (crop.getSize() >= getStickingSize()) {
					if (entity instanceof EntityLivingBase) {
						entity.attackEntityFrom(DamageSource.cactus,
							getCollisionDamage(crop, (EntityLivingBase) entity));
					}
				}
			}

		});
	}

	protected float getCollisionDamage(ICropTile crop, EntityLivingBase entity)
	{
		return 1.0F;
	}

	protected int getStickingSize()
	{
		return maxSize();
	}

}
