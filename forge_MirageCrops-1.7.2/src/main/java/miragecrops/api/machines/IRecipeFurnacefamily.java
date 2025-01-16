package miragecrops.api.machines;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * from Item and MetaID to ItemStack
 */
public interface IRecipeFurnacefamily
{

	public ItemStack copyOutputIfMatch(Item item, int meta);

	public void addRecipe(IRecipeProviderFurnaceFamily iRecipeProviderFurnaceFamily);

	public interface IRecipeProviderFurnaceFamily
	{

		public void addRecipe(ItemStack input, ItemStack output);

	}

}
