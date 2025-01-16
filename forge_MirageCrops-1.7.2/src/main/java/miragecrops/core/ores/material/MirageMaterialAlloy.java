package miragecrops.core.ores.material;

import miragecrops.framework.material.EnumMachineTier;

public class MirageMaterialAlloy extends MirageMaterialMetal
{

	public MirageMaterialAlloy(
		String name, int color, String chemicalFormula,
		int toolHarvestLevel, EnumMachineTier machineTier, boolean allowMakeBlock)
	{
		super(name, color, chemicalFormula, toolHarvestLevel, machineTier, allowMakeBlock, false);
	}

}
