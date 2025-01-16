package miragecrops.core.ores.material;

import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;

import java.util.Hashtable;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.IMirageMaterial;
import miragecrops.api.ores.ItemsModuleOres;
import miragecrops.framework.item.MetaItem;
import miragecrops.framework.material.EnumMachineTier;
import miragecrops.framework.material.IFactory;
import miragecrops.framework.material.IMirageMaterialMachine;
import miragecrops.framework.material.MirageMaterialShaped;
import miragecrops.framework.material.RegistererShapeRoot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class MirageMaterial extends MirageMaterialShaped
{

	public MirageMaterial(String name, int color, String chemicalFormula)
	{
		super(name, color, chemicalFormula);
	}

	@Override
	public void initTableRegister()
	{

		putRegisterShapeItem(EnumShape.machineHull, new RegistererShapeRoot<MetaItem>(
			IMirageMaterialMachine.Helper.getMachineTier(this) != EnumMachineTier.unusable) {

			@Override
			public MetaItem registerShape(IMirageMaterial iMaterial, EnumShape enumShape, IFactory<MetaItem> iMetaItemFactory)
			{
				MetaItem mi = super.registerShape(iMaterial, enumShape, iMetaItemFactory);

				OreDictionary.registerOre("craftingRawMachine", mi.createItemStack());
				for (EnumMachineTier enumMachineTier : IMirageMaterialMachine.Helper.getMachineTier(iMaterial).getCompatibleTiers()) {
					OreDictionary.registerOre("craftingRawMachine" + enumMachineTier.tierSuffix, mi.createItemStack());
				}

				return mi;
			}

		});
		putRegisterShapeItem(EnumShape.dust, new RegistererShapeRoot<MetaItem>(true));
		putRegisterShapeItem(EnumShape.dustSmall, new RegistererShapeRoot<MetaItem>(true));
		putRegisterShapeItem(EnumShape.dustTiny, new RegistererShapeRoot<MetaItem>(true));

	}

	@Override
	protected Hashtable<String, ItemStack> getHashtable()
	{
		return ItemsModuleOres.materials;
	}

	@Override
	public void registerRecipe()
	{

		// #################### ore -> dust
		try {
			Recipes.macerator.addRecipe(
				new RecipeInputOreDict(getDictionaryName(EnumShape.ore)),
				null,
				copyItemStack(EnumShape.dust));
		} catch (RuntimeException e) {
			// èdï°éûó·äOî≠ê∂
		}

		// #################### dust <-> dustSmall
		GameRegistry.addRecipe(new ShapedOreRecipe(
			copyItemStack(EnumShape.dustSmall, 4),
			"X",
			" ",
			'X', getDictionaryName(EnumShape.dust)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			copyItemStack(EnumShape.dust),
			getDictionaryName(EnumShape.dustSmall),
			getDictionaryName(EnumShape.dustSmall),
			getDictionaryName(EnumShape.dustSmall),
			getDictionaryName(EnumShape.dustSmall)));

		// #################### dust <-> dustTiny
		GameRegistry.addRecipe(new ShapedOreRecipe(
			copyItemStack(EnumShape.dustTiny, 9),
			" ",
			"X",
			'X', getDictionaryName(EnumShape.dust)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			copyItemStack(EnumShape.dust),
			getDictionaryName(EnumShape.dustTiny),
			getDictionaryName(EnumShape.dustTiny),
			getDictionaryName(EnumShape.dustTiny),
			getDictionaryName(EnumShape.dustTiny),
			getDictionaryName(EnumShape.dustTiny),
			getDictionaryName(EnumShape.dustTiny),
			getDictionaryName(EnumShape.dustTiny),
			getDictionaryName(EnumShape.dustTiny),
			getDictionaryName(EnumShape.dustTiny)));

		if (IMirageMaterialMachine.Helper.getMachineTier(this) != EnumMachineTier.unusable) {

			// #################### plate -> machineHull
			GameRegistry.addRecipe(new ShapedOreRecipe(
				copyItemStack(EnumShape.machineHull),
				"XXX",
				"XWX",
				"XXX",
				'X', getDictionaryName(EnumShape.plate),
				'W', "craftingToolWrench"));

			// #################### machineHull -> dust
			try {
				Recipes.macerator.addRecipe(
					new RecipeInputOreDict(getDictionaryName(EnumShape.machineHull)),
					null,
					copyItemStack(EnumShape.dust, 6));
			} catch (RuntimeException e) {
			}

		}

	}

}
