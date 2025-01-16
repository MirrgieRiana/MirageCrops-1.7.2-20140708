package miragecrops.core.crops;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface ICrossingRecipe
{

	public boolean canSpawnAt(CropCard cropCard, ICropTile iCropTile);

	@SideOnly(Side.CLIENT)
	public void addRecipe(RecipeProviderCropCrossing recipeProviderCropCrossing, CropCard self);

}
