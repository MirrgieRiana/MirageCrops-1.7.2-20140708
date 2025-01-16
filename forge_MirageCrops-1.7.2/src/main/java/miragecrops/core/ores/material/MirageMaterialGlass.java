package miragecrops.core.ores.material;

import miragecrops.framework.material.EnumMachineTier;

public class MirageMaterialGlass extends MirageMaterialCrystal
{

	public MirageMaterialGlass(
		String name, int color, String chemicalFormula,
		int toolHarvestLevel, EnumMachineTier machineTier)
	{
		super(name, color, chemicalFormula, toolHarvestLevel, machineTier, false, false);
	}

}
