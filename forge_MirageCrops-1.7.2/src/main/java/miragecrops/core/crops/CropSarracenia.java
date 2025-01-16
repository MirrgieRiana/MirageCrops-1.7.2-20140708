package miragecrops.core.crops;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import miragecrops.framework.crop.CropSpecification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.DamageSource;

public class CropSarracenia extends CropCrossingRecipe
{

	protected static final DamageSource DAMAGESOURCE = new DamageSource("Sarracenia");
	protected final int lightLevel;

	public CropSarracenia(CropSpecification cropSpecification, ICrossingRecipe iCrossingRecipe, int lightLevel)
	{
		super(cropSpecification, iCrossingRecipe);
		cropSpecification.addListener(new CropSpecification.CropCardListenerImpl() {

			@Override
			public void onEntityCollision(CropCard cropCard, ICropTile crop, Entity entity)
			{
				if (crop.getSize() >= maxSize() - 1) {
					if (crop.getNutrientStorage() < 20) {

						if (entity instanceof EntityLivingBase) {
							EntityLivingBase entityLivingBase = (EntityLivingBase) entity;

							float damage = getDamageForEntity(crop, entityLivingBase);
							if (damage != 0) {
								entityLivingBase.attackEntityFrom(DAMAGESOURCE, damage);
								if (entityLivingBase.isDead) {
									crop.setNutrientStorage(Math.min(100, crop.getNutrientStorage() +
										(int) (10 * entityLivingBase.getMaxHealth())));
									crop.getWorld().playAuxSFX(2005,
										crop.getLocation().posX,
										crop.getLocation().posY,
										crop.getLocation().posZ, 0);
								}
							}

						}

					}
				}
			}

		});
		this.lightLevel = lightLevel;
	}

	protected float getDamageForEntity(ICropTile iCropTile, EntityLivingBase entityLivingBase)
	{
		if (entityLivingBase.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) return 1.0f;
		return 0;
	}

	@Override
	public int getEmittedLight(ICropTile iCropTile)
	{
		if (iCropTile.getSize() < maxSize()) {
			return super.getEmittedLight(iCropTile);
		}

		return lightLevel;
	}

}
