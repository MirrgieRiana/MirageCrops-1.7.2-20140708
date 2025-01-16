package miragecrops.core.crops;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import miragecrops.framework.RecipeProviderAbstractImpl;
import net.minecraft.item.ItemStack;
import uristqwerty.CraftGuide.api.ItemSlot;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeProviderCropCrossing extends RecipeProviderAbstractImpl
{

	public RecipeProviderCropCrossing(ItemStack craftingType)
	{
		super(craftingType);
	}

	@Override
	protected RecipeTemplate createTemplate()
	{
		return generator.createRecipeTemplate(new Slot[] {
			new ItemSlot(9 + 3 + 1 * 18, 3 + 0 * 18, 16, 16, false).setSlotType(SlotType.INPUT_SLOT),
			new ItemSlot(9 + 3 + 0 * 18, 3 + 1 * 18, 16, 16, false).setSlotType(SlotType.INPUT_SLOT),
			new ItemSlot(9 + 3 + 2 * 18, 3 + 1 * 18, 16, 16, false).setSlotType(SlotType.INPUT_SLOT),
			new ItemSlot(9 + 3 + 1 * 18, 3 + 2 * 18, 16, 16, false).setSlotType(SlotType.INPUT_SLOT),
			new ItemSlot(9 + 3 + 1 * 18, 3 + 1 * 18, 16, 16, false).setSlotType(SlotType.OUTPUT_SLOT).drawOwnBackground(),
		}, craftingType);
	}

	@Override
	protected void generateRecipes()
	{
		for (CropCard cropCard : Crops.instance.getCropList()) {
			if (cropCard != null) {
				if (cropCard instanceof CropCrossingRecipe) {
					CropCrossingRecipe cropCrossingRecipe = (CropCrossingRecipe) cropCard;
					ICrossingRecipe iCrossingRecipe = cropCrossingRecipe.crossingRecipe;

					iCrossingRecipe.addRecipe(this, cropCard);
				}
			}
		}
	}

	public void addRecipe(
		ItemStack itemStack1,
		ItemStack itemStack2,
		ItemStack itemStack3,
		ItemStack itemStack4,
		ItemStack itemStackOut)
	{
		Object[] recipeContents = {
			itemStack1,
			itemStack2,
			itemStack3,
			itemStack4,
			itemStackOut,
		};

		generator.addRecipe(template, recipeContents);
	}

}
