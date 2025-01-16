package miragecrops.core.ores.material;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.framework.block.MetaBlock;
import miragecrops.framework.material.IMirageMaterialBlock;
import miragecrops.framework.material.RegistererShapeRoot;

public class MirageMaterialOre extends MirageMaterial implements IMirageMaterialBlock
{

	public final int blockHarvestLevel;

	public MirageMaterialOre(
		String name, int color, String chemicalFormula,
		int blockHarvestLevel)
	{
		super(name, color, chemicalFormula);
		this.blockHarvestLevel = blockHarvestLevel;
	}

	@Override
	public void initTableRegister()
	{
		super.initTableRegister();

		putRegisterShapeBlock(EnumShape.ore, new RegistererShapeRoot<MetaBlock>(true));

	}

	@Override
	public int getBlockHarvestLevel()
	{
		return blockHarvestLevel;
	}

}
