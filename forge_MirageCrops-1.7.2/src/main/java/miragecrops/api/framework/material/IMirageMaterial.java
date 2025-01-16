package miragecrops.api.framework.material;

import net.minecraft.item.ItemStack;

public interface IMirageMaterial
{

	public String name();

	public int getColor();

	public String getChemicalFormula();

	public ItemStack copyItemStack(EnumShape enumShape, int amount);

	public ItemStack copyItemStack(EnumShape enumShape);

	public String getDictionaryName(EnumShape enumShape);

}
