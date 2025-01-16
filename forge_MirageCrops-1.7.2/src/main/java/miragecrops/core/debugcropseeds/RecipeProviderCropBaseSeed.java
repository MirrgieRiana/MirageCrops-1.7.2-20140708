package miragecrops.core.debugcropseeds;

import ic2.api.crops.BaseSeed;
import ic2.api.crops.Crops;

import java.util.ArrayList;
import java.util.Iterator;

import miragecrops.api.framework.cropgainrecipe.ICropGainRecipe.ICropGainRecipeAdder;
import miragecrops.framework.RecipeProviderAbstractImpl;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import uristqwerty.CraftGuide.api.ItemSlot;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeProviderCropBaseSeed extends RecipeProviderAbstractImpl implements ICropGainRecipeAdder
{

	public RecipeProviderCropBaseSeed(ItemStack craftingType)
	{
		super(craftingType);
	}

	@Override
	protected RecipeTemplate createTemplate()
	{
		return generator.createRecipeTemplate(new Slot[] {
			new ItemSlot(9 + 3 + 0 * 18, 0 + 3 + 1 * 18, 16, 16, true).setSlotType(SlotType.INPUT_SLOT).drawOwnBackground(),
			new ItemSlot(9 + 3 + 2 * 18, 0 + 3 + 1 * 18, 16, 16, false).setSlotType(SlotType.OUTPUT_SLOT),
			new ItemSlot(9 + 3 + 1 * 18, 9 + 3 + 1 * 18, 16, 16, false).setSlotType(SlotType.MACHINE_SLOT),
		}, craftingType);
	}

	@Override
	protected void generateRecipes()
	{
		Iterator<Item> iterator = Item.itemRegistry.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();

			if (item != null) {
				ArrayList<ItemStack> list = new ArrayList<ItemStack>();
				try {
					item.getSubItems(item, null, list);
				} catch (NullPointerException e) {
				}

				for (ItemStack itemStack : list) {
					BaseSeed baseSeed = Crops.instance.getBaseSeed(itemStack);
					if (baseSeed != null) {
						addRecipe(
							itemStack,
							ModuleDebugCropSeeds.debugCropSeed.createItemStack(baseSeed.id));
					}
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
