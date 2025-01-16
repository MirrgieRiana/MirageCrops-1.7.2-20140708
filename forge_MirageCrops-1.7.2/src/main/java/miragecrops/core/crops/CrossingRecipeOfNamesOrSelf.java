package miragecrops.core.crops;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;

import java.util.ArrayList;
import java.util.Iterator;

import miragecrops.core.debugcropseeds.ModuleDebugCropSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

class CrossingRecipeOfNamesOrSelf implements ICrossingRecipe
{

	protected String[] recipe;

	public CrossingRecipeOfNamesOrSelf(String... recipe)
	{
		this.recipe = recipe.clone();
	}

	@Override
	public boolean canSpawnAt(CropCard cropCard, ICropTile iCropTile)
	{

		if (iCropTile instanceof TileEntity) {
			TileEntity tec = (TileEntity) iCropTile;
			ArrayList<ICropTile> crossableCropTilesOfNeighbor = getCrossableCropTilesOfNeighbor(
				tec.getWorldObj(), tec.xCoord, tec.yCoord, tec.zCoord);

			if (isSelf(crossableCropTilesOfNeighbor, cropCard)) {
				return true;
			}

			return match(crossableCropTilesOfNeighbor);
		} else {
			return false;
		}
	}

	protected boolean isSelf(ArrayList<ICropTile> crossableCropTiles, CropCard cropCard)
	{

		for (ICropTile ict : crossableCropTiles) {

			if (Crops.instance.getCropList()[ict.getID()] == cropCard) {
				return true;
			}

		}

		return false;
	}

	protected boolean match(ArrayList<ICropTile> crossableCropTiles)
	{
		ArrayList<ICropTile> crossableCropTiles2 = (ArrayList<ICropTile>) crossableCropTiles.clone();

		for (String recipeItem : recipe) {
			boolean isMatch = false;

			Iterator<ICropTile> iterator = crossableCropTiles2.iterator();
			while (iterator.hasNext()) {
				ICropTile ict = iterator.next();

				if (match(Crops.instance.getCropList()[ict.getID()], recipeItem)) {
					iterator.remove();
					isMatch = true;
					break;
				}

			}

			if (!isMatch) {
				return false;
			}
		}

		return true;
	}

	protected static final int[][] xizis = {
		{
			0, 1
		}, {
			0, -1
		}, {
			1, 0
		}, {
			-1, 0
		},
	};

	protected static ArrayList<ICropTile> getCrossableCropTilesOfNeighbor(World world, int x, int y, int z)
	{
		ArrayList<ICropTile> crossableCropTiles = new ArrayList<ICropTile>();

		for (int[] xizi : xizis) {
			TileEntity tileEntity = world.getTileEntity(x + xizi[0], y, z + xizi[1]);
			if (tileEntity != null) {
				if (tileEntity instanceof ICropTile) {
					ICropTile ict = (ICropTile) tileEntity;

					int id = ict.getID();
					if (id >= 0) {
						CropCard cropCard = Crops.instance.getCropList()[id];

						if (cropCard.canCross(ict)) {
							crossableCropTiles.add(ict);
						}
					}
				}
			}
		}

		return crossableCropTiles;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addRecipe(RecipeProviderCropCrossing recipeProviderCropCrossing, CropCard self)
	{
		ItemStack[] itemStacks = new ItemStack[4];

		int index = 0;
		for (String recipeItem : recipe) {

			CropCard[] cropCards = Crops.instance.getCropList();
			for (CropCard cropCard : cropCards) {
				if (cropCard != null) {
					if (match(cropCard, recipeItem)) {
						itemStacks[index] = ModuleDebugCropSeeds.debugCropSeed.createItemStack(cropCard.getId());
						break;
					}
				}
			}

			index++;
		}

		recipeProviderCropCrossing.addRecipe(
			itemStacks[0],
			itemStacks[1],
			itemStacks[2],
			itemStacks[3],
			ModuleDebugCropSeeds.debugCropSeed.createItemStack(self.getId()));
	}

	protected boolean match(CropCard cropCard, String name)
	{
		return cropCard.name().equals(name);
	}

}
