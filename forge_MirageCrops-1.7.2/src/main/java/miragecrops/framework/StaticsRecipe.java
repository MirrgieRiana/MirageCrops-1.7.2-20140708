package miragecrops.framework;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class StaticsRecipe
{

	public static void addSmelting(ItemStack input, ItemStack output, float xp)
	{
		if (input == null) throw new NullPointerException("smelting recipe input");
		if (output == null) throw new NullPointerException("smelting recipe output");
		GameRegistry.addSmelting(input, output, xp);
	}
}
