package miragecrops.core.crops;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import miragecrops.framework.crop.CropSpecification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class CropCactusSnow extends CropCactus
{

	public CropCactusSnow(CropSpecification cropSpecification, ICrossingRecipe iCrossingRecipe)
	{
		super(cropSpecification, iCrossingRecipe);
		cropSpecification.addListener(new CropSpecification.CropCardListenerImpl() {

			@Override
			public void onEntityCollision(CropCard cropCard, ICropTile crop, Entity entity)
			{
				if (crop.getSize() >= getStickingSize()) {
					if (entity instanceof EntityLivingBase) {
						((EntityLivingBase) entity).addPotionEffect(new PotionEffect(2, 6, 3, true));
					}
				}
			}

		});
	}

	@Override
	public byte getSizeAfterHarvest(ICropTile crop)
	{
		if (crop.getSize() == maxSize()) return (byte) (maxSize() - 1);
		return super.getSizeAfterHarvest(crop);
	}

	@Override
	protected int getStickingSize()
	{
		return 4;
	}

	@Override
	public boolean canBeHarvested(ICropTile crop)
	{
		if (crop.getSize() == 4) return true;
		return super.canBeHarvested(crop);
	}

}
