package miragecrops.core.machines;

import miragecrops.api.machines.IRecipeFurnacefamily.IRecipeProviderFurnaceFamily;
import miragecrops.api.machines.IRecipesFurnacefamily;
import miragecrops.framework.RecipeProviderAbstractImpl;
import net.minecraft.item.ItemStack;
import uristqwerty.CraftGuide.api.ItemSlot;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeProviderFurnaceFamily extends RecipeProviderAbstractImpl implements IRecipeProviderFurnaceFamily
{

	protected final IRecipesFurnacefamily recipes;

	public RecipeProviderFurnaceFamily(ItemStack craftingType, IRecipesFurnacefamily recipes)
	{
		super(craftingType);
		this.recipes = recipes;
	}

	@Override
	protected RecipeTemplate createTemplate()
	{
		return generator.createRecipeTemplate(new Slot[] {
			new ItemSlot(9 + 3 + 0 * 18, 0 + 3 + 1 * 18, 16, 16, true).setSlotType(SlotType.INPUT_SLOT).drawOwnBackground(),
			new ItemSlot(9 + 3 + 2 * 18, 0 + 3 + 1 * 18, 16, 16, true).setSlotType(SlotType.OUTPUT_SLOT).drawOwnBackground(),
			new ItemSlot(9 + 3 + 1 * 18, 9 + 3 + 1 * 18, 16, 16, false).setSlotType(SlotType.MACHINE_SLOT),
		}, craftingType);
	}

	@Override
	protected void generateRecipes()
	{
		recipes.addRecipe(this);
	}

	@Override
	public void addRecipe(ItemStack input, ItemStack output)
	{
		Object[] recipeContents = {
			input,
			output,
			craftingType,
		};

		generator.addRecipe(template, recipeContents);
	}

}
