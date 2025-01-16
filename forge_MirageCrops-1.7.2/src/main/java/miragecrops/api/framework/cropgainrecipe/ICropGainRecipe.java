package miragecrops.api.framework.cropgainrecipe;

import ic2.api.crops.CropCard;
import net.minecraft.item.ItemStack;

public interface ICropGainRecipe
{

	public static interface ICropGainRecipeAdder
	{

		public void addRecipe(ItemStack debugCropSeed, ItemStack gain);

	}

	public void addRecipe(ICropGainRecipeAdder iRecipeAdder, CropCard cropCard, ItemStack debugCropSeed);

}
