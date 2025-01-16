package miragecrops.core.crops;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import miragecrops.framework.crop.CropSpecification;
import miragecrops.framework.crop.CropSpecification.EnumClickEventProgress;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class CropFireWheat extends CropCrossingRecipe
{

	public CropFireWheat(CropSpecification cropSpecification, ICrossingRecipe iCrossingRecipe)
	{
		super(cropSpecification, iCrossingRecipe);
		cropSpecification.addListener(new CropSpecification.CropCardListenerImpl() {

			@Override
			public EnumClickEventProgress rightclick(CropCard cropCard, ICropTile crop, EntityPlayer player)
			{
				if (crop.getSize() == 8) {
					player.setFire(8);
				}

				return super.rightclick(cropCard, crop, player);
			}

			@Override
			public void onEntityCollision(CropCard cropCard, ICropTile crop, Entity entity)
			{
				if (crop.getSize() == 8) {
					if (entity instanceof EntityLivingBase) {
						entity.setFire(8);
					}
				}
			}

		});
	}

	@Override
	public int getEmittedLight(ICropTile crop)
	{
		if (crop.getSize() == 8) return 14;
		return super.getEmittedLight(crop);
	}

}
