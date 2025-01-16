package miragecrops.api.machines;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeFurnacefamily implements IRecipeFurnacefamily
{

	protected final ItemStack output;
	protected final Item item;
	protected final int meta;

	public RecipeFurnacefamily(ItemStack outout, Item item, int meta)
	{
		this.output = outout;
		this.item = item;
		this.meta = meta;
	}

	@Override
	public ItemStack copyOutputIfMatch(Item item, int meta)
	{
		if (this.item == item) {
			if (this.meta == meta) {
				return output.copy();
			}
		}

		return null;
	}

	@Override
	public void addRecipe(IRecipeProviderFurnaceFamily iRecipeProviderFurnaceFamily)
	{
		iRecipeProviderFurnaceFamily.addRecipe(new ItemStack(item, 1, meta), output);
	}

}
