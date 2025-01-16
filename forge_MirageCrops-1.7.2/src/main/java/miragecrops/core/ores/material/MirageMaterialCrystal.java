package miragecrops.core.ores.material;

import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;
import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.IMirageMaterial;
import miragecrops.framework.block.MetaBlock;
import miragecrops.framework.item.MetaItem;
import miragecrops.framework.material.EnumMachineTier;
import miragecrops.framework.material.IFactory;
import miragecrops.framework.material.RegistererShape;
import miragecrops.framework.material.RegistererShapeRoot;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class MirageMaterialCrystal extends MirageMaterialIndustrial
{

	public MirageMaterialCrystal(
		String name, int color, String chemicalFormula,
		int itemToolHarvestLevel, EnumMachineTier machineTier, boolean allowMakeBlock, boolean allowMakeOre)
	{
		super(name, color, chemicalFormula, itemToolHarvestLevel, machineTier, allowMakeBlock, allowMakeOre);
	}

	@Override
	public void initTableRegister()
	{
		super.initTableRegister();

		putRegisterShapeItem(EnumShape.gem, new RegistererShapeRoot<MetaItem>(true));
		putRegisterShapeBlock(EnumShape.ore, new RegistererShape<MetaBlock>(getRegisterShapeBlock(EnumShape.ore)) {

			@Override
			public MetaBlock registerShape(IMirageMaterial iMirageMaterial, EnumShape enumShape, IFactory<MetaBlock> iFactory)
			{
				MetaBlock mb = superRegister.registerShape(iMirageMaterial, enumShape, iFactory);

				mb.setDropsFortune(copyItemStack(EnumShape.gem));

				return mb;
			}

		});

	}

	@Override
	public void registerRecipe()
	{
		super.registerRecipe();

		// #################### block -> gem
		GameRegistry.addRecipe(new ShapelessOreRecipe(
			copyItemStack(EnumShape.gem, 9),
			getDictionaryName(EnumShape.block)));

		// #################### gem -> block
		try {
			Recipes.compressor.addRecipe(
				new RecipeInputOreDict(getDictionaryName(EnumShape.gem), 9),
				null,
				copyItemStack(EnumShape.block));
		} catch (RuntimeException e) {
		}

		// #################### ingot -> dust
		try {
			Recipes.macerator.addRecipe(
				new RecipeInputOreDict(getDictionaryName(EnumShape.gem)),
				null,
				copyItemStack(EnumShape.dust));
		} catch (RuntimeException e) {
		}

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			copyItemStack(EnumShape.dust),
			"craftingToolMortar",
			getDictionaryName(EnumShape.gem)));

	}

}
