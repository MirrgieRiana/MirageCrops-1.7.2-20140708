package miragecrops.api.machines;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesFurnacefamily implements IRecipesFurnacefamily
{

	protected ArrayList<IRecipeFurnacefamily> recipes = new ArrayList<IRecipeFurnacefamily>();

	public void addRecipe(RecipeFurnacefamily recipe)
	{
		recipes.add(recipe);
	}

	@Override
	public ItemStack copyOutputIfMatch(Item item, int meta)
	{
		for (IRecipeFurnacefamily recipe : recipes) {
			ItemStack output = recipe.copyOutputIfMatch(item, meta);
			if (output != null) {
				return output;
			}
		}

		return null;
	}

	@Override
	public void addRecipe(IRecipeProviderFurnaceFamily iRecipeProviderFurnaceFamily)
	{
		for (IRecipeFurnacefamily recipe : recipes) {
			if (recipe != null) {
				recipe.addRecipe(iRecipeProviderFurnaceFamily);
			}
		}
	}

}
