package miragecrops.framework.material;

import java.util.Hashtable;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.IMirageMaterial;
import miragecrops.framework.StaticsString;
import net.minecraft.item.ItemStack;

abstract class MirageMaterialImpl implements IMirageMaterial
{

	protected final String name;
	protected final int color;
	protected final String chemicalFormula;

	public MirageMaterialImpl(String name, int color, String chemicalFormula)
	{
		this.name = name;
		this.color = color;
		this.chemicalFormula = chemicalFormula;
	}

	@Override
	public final String name()
	{
		return name;
	}

	@Override
	public final int getColor()
	{
		return color;
	}

	@Override
	public final String getChemicalFormula()
	{
		return chemicalFormula;
	}

	@Override
	public ItemStack copyItemStack(EnumShape enumShape, int amount)
	{
		if (getHashtable() == null) return null;
		String dictionaryName = getDictionaryName(enumShape);
		if (!getHashtable().containsKey(dictionaryName)) return null;
		ItemStack itemStack = getHashtable().get(dictionaryName);
		if (itemStack == null) return null;
		itemStack = itemStack.copy();
		itemStack.stackSize = amount;
		return itemStack;
	}

	protected abstract Hashtable<String, ItemStack> getHashtable();

	@Override
	public ItemStack copyItemStack(EnumShape enumShape)
	{
		return copyItemStack(enumShape, 1);
	}

	@Override
	public String getDictionaryName(EnumShape enumShape)
	{
		return enumShape + StaticsString.toUpperCaseHead(name());
	}

}
