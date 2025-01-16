package miragecrops.core.ores.material;

import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;
import miragecrops.api.framework.material.EnumShape;
import miragecrops.framework.StaticsRecipe;
import miragecrops.framework.item.MetaItem;
import miragecrops.framework.material.EnumMachineTier;
import miragecrops.framework.material.RegistererShapeRoot;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class MirageMaterialMetal extends MirageMaterialIndustrial
{

	public MirageMaterialMetal(
		String name, int color, String chemicalFormula,
		int itemToolHarvestLevel, EnumMachineTier machineTier, boolean allowMakeBlock, boolean allowMakeOre)
	{
		super(name, color, chemicalFormula, itemToolHarvestLevel, machineTier, allowMakeBlock, allowMakeOre);
	}

	@Override
	public void initTableRegister()
	{
		super.initTableRegister();

		putRegisterShapeItem(EnumShape.ingot, new RegistererShapeRoot<MetaItem>(true));
		putRegisterShapeItem(EnumShape.nugget, new RegistererShapeRoot<MetaItem>(true));
		putRegisterShapeItem(EnumShape.rod, new RegistererShapeRoot<MetaItem>(true));
		putRegisterShapeItem(EnumShape.plate, new RegistererShapeRoot<MetaItem>(true));
		putRegisterShapeItem(EnumShape.wire, new RegistererShapeRoot<MetaItem>(true));

	}

	@Override
	public void registerRecipe()
	{
		super.registerRecipe();

		if (isProvidingShapeBlock(EnumShape.block)) {

			// #################### block -> ingot
			StaticsRecipe.addSmelting(
				copyItemStack(EnumShape.block),
				copyItemStack(EnumShape.ingot, 9),
				0);

			// #################### ingot -> block
			try {
				Recipes.compressor.addRecipe(
					new RecipeInputOreDict(getDictionaryName(EnumShape.ingot), 9),
					null,
					copyItemStack(EnumShape.block));
			} catch (RuntimeException e) {
			}

		}

		if (isProvidingShapeBlock(EnumShape.ore)) {

			// #################### ore -> ingot
			StaticsRecipe.addSmelting(
				copyItemStack(EnumShape.ore),
				copyItemStack(EnumShape.ingot),
				0);

		}

		// #################### dust -> ingot
		StaticsRecipe.addSmelting(
			copyItemStack(EnumShape.dust),
			copyItemStack(EnumShape.ingot),
			0);

		// #################### ingot -> dust
		try {
			Recipes.macerator.addRecipe(
				new RecipeInputOreDict(getDictionaryName(EnumShape.ingot)),
				null,
				copyItemStack(EnumShape.dust));
		} catch (RuntimeException e) {
		}

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			copyItemStack(EnumShape.dust),
			"craftingToolMortar",
			getDictionaryName(EnumShape.ingot)));

		// #################### ingot -> plate
		GameRegistry.addRecipe(new ShapedOreRecipe(
			copyItemStack(EnumShape.plate),
			"H",
			"X",
			"X",
			'X', getDictionaryName(EnumShape.ingot),
			'H', "craftingToolHardHammer"));

		// #################### ingot -> rod
		GameRegistry.addRecipe(new ShapedOreRecipe(
			copyItemStack(EnumShape.rod),
			"F",
			"X",
			'X', getDictionaryName(EnumShape.ingot),
			'F', "craftingToolFile"));

		// #################### ingot -> nugget
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			copyItemStack(EnumShape.nugget, 9),
			getDictionaryName(EnumShape.ingot)));

		// #################### nugget -> ingot
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			copyItemStack(EnumShape.ingot),
			getDictionaryName(EnumShape.nugget),
			getDictionaryName(EnumShape.nugget),
			getDictionaryName(EnumShape.nugget),
			getDictionaryName(EnumShape.nugget),
			getDictionaryName(EnumShape.nugget),
			getDictionaryName(EnumShape.nugget),
			getDictionaryName(EnumShape.nugget),
			getDictionaryName(EnumShape.nugget),
			getDictionaryName(EnumShape.nugget)));

		// #################### plate -> wire
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			copyItemStack(EnumShape.wire, 4),
			getDictionaryName(EnumShape.plate),
			"craftingToolCutter"));

	}

}
