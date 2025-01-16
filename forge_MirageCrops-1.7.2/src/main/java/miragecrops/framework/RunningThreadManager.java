package miragecrops.framework;

import java.util.List;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.RecipeSorter;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class RunningThreadManager
{

	protected int i2;
	protected int j2;
	protected int phaze;
	protected long timestamp;

	protected boolean countup(int iLength, int jLength, int modding)
	{
		j2++;
		if (j2 >= jLength) {
			j2 = 0;
			i2++;

			if (modding == 0 || i2 % modding == 0) {
				FMLLog.log(Level.INFO, "%d / %d", i2, iLength);
			}

			if (i2 >= iLength) {
				i2 = 0;

				phaze++;

				return true;
			}
		}

		return false;
	}

	public Integer[][] run(final List<IRecipe> recipeList)
	{
		final int modding = (recipeList.size() / 10);
		final Integer[][] compare = new Integer[recipeList.size()][recipeList.size()];
		i2 = 0;
		j2 = 0;
		phaze = 0;

		Runnable runnable = new Runnable() {

			@Override
			public void run()
			{
				while (true) {

					timestamp = System.currentTimeMillis();
					compare[i2][j2] = RecipeSorter.INSTANCE.compare(recipeList.get(i2), recipeList.get(j2));

					if (countup(recipeList.size(), recipeList.size(), modding)) {
						break;
					}
				}

			}

		};

		while (phaze == 0) {

			Thread thread = new Thread(runnable);
			thread.start();
			timestamp = System.currentTimeMillis();

			while (true) {
				if (timestamp + 100 < System.currentTimeMillis()) {
					thread.stop();
					FMLLog.log(Level.INFO, "non stop comparation: %d(%s), %d(%s)",
						i2, recipeList.get(i2), j2, recipeList.get(j2));
					countup(recipeList.size(), recipeList.size(), modding);
					break;
				}
				if (phaze > 0) {
					break;
				}
			}

		}

		return compare;
	}

}
