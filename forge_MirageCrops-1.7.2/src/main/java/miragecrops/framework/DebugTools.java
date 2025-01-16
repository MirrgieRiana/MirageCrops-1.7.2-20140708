package miragecrops.framework;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;

public class DebugTools
{

	public static void printOreDictionaryContents()
	{
		String[] names = OreDictionary.getOreNames();
		FMLLog.log(Level.INFO, "##############################################");
		for (String name : names) {
			ArrayList<ItemStack> ores = OreDictionary.getOres(name);
			FMLLog.log(Level.INFO, "" + name + "," + ores.size());
		}
		FMLLog.log(Level.INFO, "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		for (String name : names) {
			ArrayList<ItemStack> ores = OreDictionary.getOres(name);
			for (ItemStack ore : ores) {
				UniqueIdentifier ui = GameRegistry.findUniqueIdentifierFor(ore.getItem());
				FMLLog.log(Level.INFO, "" + ui.modId + "," + ui.name + "," + name);
			}
		}
		FMLLog.log(Level.INFO, "##############################################");
	}

	public static ArrayList<Class<?>> getAllCraftingRecipeClasses()
	{
		ArrayList<Class<?>> list = new ArrayList<Class<?>>();

		for (Object recipe : CraftingManager.getInstance().getRecipeList()) {
			Class<?> clazz = recipe.getClass();
			if (!list.contains(clazz)) {
				list.add(clazz);
			}
		}

		return list;
	}

	public static void validateRecipeSorter()
	{
		FMLLog.log(Level.INFO, "[validateRecipeSorter] begin");
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		int modding = (recipeList.size() / 10);

		FMLLog.log(Level.INFO, "checking: size = %d", recipeList.size());
		Integer[][] compare = new RunningThreadManager().run(recipeList);

		for (int i = 0; i < recipeList.size(); i++) {
			for (int j = 0; j < recipeList.size(); j++) {
				if (i < j) {
					if (compare[i][j] == null) {
						continue;
					}
					if (compare[j][i] == null) {
						continue;
					}
					if (compare[i][j] != -compare[j][i]) {
						FMLLog.log(Level.INFO, "illegal comparation: %d(%s), %d(%s) -> (%d, %d)",
							i, recipeList.get(i), j, recipeList.get(j), compare[i][j], compare[j][i]);
					}
				}
			}
			if (modding == 0 || i % modding == 0) {
				FMLLog.log(Level.INFO, "%d / %d", i, recipeList.size());
			}
		}

		FMLLog.log(Level.INFO, "[validateRecipeSorter] end");
	}

}
