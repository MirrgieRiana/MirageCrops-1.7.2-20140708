package miragecrops.api.framework.cropgainrecipe;

import ic2.api.crops.CropCard;

import java.util.Hashtable;

import net.minecraft.item.ItemStack;

public class CropGainRecipe
{

	/**
	 * FMLInitializationEvent
	 */
	public static Hashtable<Class<?>, ICropGainRecipe> cropGainRecipeList = new Hashtable<Class<?>, ICropGainRecipe>();

	public static ICropGainRecipe createCropGainRecipe(ItemStack... itemStacks)
	{
		return new CropGainRecipeImpl(itemStacks);
	}

	private static final class CropGainRecipeImpl implements ICropGainRecipe
	{

		private final ItemStack[] itemStacks;

		public CropGainRecipeImpl(ItemStack... itemStacks)
		{
			this.itemStacks = itemStacks;
		}

		@Override
		public void addRecipe(ICropGainRecipeAdder iRecipeAdder, CropCard cropCard, ItemStack debugCropSeed)
		{
			for (ItemStack itemStack : itemStacks) {
				iRecipeAdder.addRecipe(debugCropSeed, itemStack);
			}
		}

	}

}
