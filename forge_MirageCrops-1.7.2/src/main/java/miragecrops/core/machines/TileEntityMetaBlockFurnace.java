package miragecrops.core.machines;

import miragecrops.api.machines.RecipesFurnacefamily;
import miragecrops.core.machines.framework.TileEntityMetaBlockFurnacefamily;

public class TileEntityMetaBlockFurnace extends TileEntityMetaBlockFurnacefamily
{

	@Override
	protected RecipesFurnacefamily getRecipes()
	{
		return ModuleMachines.recipesMetaBlockFurnace;
	}

}
