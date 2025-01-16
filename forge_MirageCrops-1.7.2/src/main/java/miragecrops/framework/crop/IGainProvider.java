package miragecrops.framework.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import miragecrops.api.framework.cropgainrecipe.ICropGainRecipe.ICropGainRecipeAdder;
import net.minecraft.item.ItemStack;

public interface IGainProvider
{

	public ItemStack getGain(CropCard cropCard, ICropTile crop);

	public void addRecipe(ICropGainRecipeAdder iRecipeAdder, CropCard cropCard, ItemStack debugCropSeed);

}
