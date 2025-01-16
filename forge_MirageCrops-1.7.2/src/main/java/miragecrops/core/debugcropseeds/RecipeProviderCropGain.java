package miragecrops.core.debugcropseeds;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import miragecrops.api.framework.cropgainrecipe.CropGainRecipe;
import miragecrops.api.framework.cropgainrecipe.ICropGainRecipe;
import miragecrops.api.framework.cropgainrecipe.ICropGainRecipe.ICropGainRecipeAdder;
import miragecrops.framework.RecipeProviderAbstractImpl;
import miragecrops.framework.crop.CropTileImpl;
import miragecrops.framework.item.SItemStack;
import net.minecraft.item.ItemStack;
import uristqwerty.CraftGuide.api.ItemSlot;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeProviderCropGain extends RecipeProviderAbstractImpl implements ICropGainRecipeAdder
{

	public RecipeProviderCropGain(ItemStack craftingType)
	{
		super(craftingType);
	}

	@Override
	protected RecipeTemplate createTemplate()
	{
		return generator.createRecipeTemplate(new Slot[] {
			new ItemSlot(9 + 3 + 0 * 18, 0 + 3 + 1 * 18, 16, 16, false).setSlotType(SlotType.INPUT_SLOT),
			new ItemSlot(9 + 3 + 2 * 18, 0 + 3 + 1 * 18, 16, 16, true).setSlotType(SlotType.OUTPUT_SLOT).drawOwnBackground(),
			new ItemSlot(9 + 3 + 1 * 18, 9 + 3 + 1 * 18, 16, 16, false).setSlotType(SlotType.MACHINE_SLOT),
		}, craftingType);
	}

	@Override
	protected void generateRecipes()
	{
		for (int id = 0; id < Crops.instance.getCropList().length; id++) {
			CropCard cropCard = Crops.instance.getCropList()[id];
			if (cropCard != null) {

				addRecipeOfCrop(generator, id, cropCard);

			}
		}
	}

	protected void addRecipeOfCrop(RecipeGenerator generator, int id, CropCard cropCard)
	{
		ICropGainRecipe iCropGainRecipe = CropGainRecipe.cropGainRecipeList.get(cropCard.getClass());
		if (iCropGainRecipe != null) {

			iCropGainRecipe.addRecipe(this, cropCard, ModuleDebugCropSeeds.debugCropSeed.createItemStack(id));

			return;
		}

		if (cropCard instanceof ICropGainRecipe) {
			((ICropGainRecipe) cropCard).addRecipe(
				this, cropCard, ModuleDebugCropSeeds.debugCropSeed.createItemStack(id));
			return;
		}

		ItemStack[] itemStacks = new ItemStack[cropCard.maxSize()];
		for (int size = 1; size <= cropCard.maxSize(); size++) {
			CropTileImpl cropTile = new CropTileImpl(cropCard, (byte) size);

			try {
				if (cropCard.canBeHarvested(cropTile)) {
					itemStacks[size - 1] = cropCard.getGain(cropTile);
				}
			} catch (Exception e) {
			}

		}

		ItemStack itemStackMaxSize = itemStacks[cropCard.maxSize() - 1];

		for (int size = 1; size <= cropCard.maxSize(); size++) {
			ItemStack itemStack = itemStacks[size - 1];
			if (itemStack != null) {
				if (size == cropCard.maxSize() ||
					!SItemStack.isItemStackEqual_ItemDamageNBTSize(itemStack, itemStackMaxSize)) {

					addRecipe(ModuleDebugCropSeeds.debugCropSeed.createItemStack(id, size), itemStack);

				}
			}
		}
	}

	@Override
	public void addRecipe(ItemStack itemStack, ItemStack itemStack2)
	{
		Object[] recipeContents = {
			itemStack,
			itemStack2,
			craftingType,
		};

		generator.addRecipe(template, recipeContents);
	}

}
