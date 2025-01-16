package miragecrops.core.ores.material;

import static miragecrops.api.framework.item.EnumToolMaterialHarvestLevels.*;
import miragecrops.api.framework.material.EnumShape;
import miragecrops.framework.block.MetaBlock;
import miragecrops.framework.material.EnumMachineTier;
import miragecrops.framework.material.IMirageMaterialBlock;
import miragecrops.framework.material.IMirageMaterialItemTool;
import miragecrops.framework.material.IMirageMaterialMachine;
import miragecrops.framework.material.RegistererShapeRoot;

public class MirageMaterialIndustrial extends MirageMaterial implements IMirageMaterialItemTool, IMirageMaterialMachine, IMirageMaterialBlock
{

	public final int itemToolHarvestLevel;
	private final EnumMachineTier machineTier;
	public final boolean allowMakeBlock;
	public final boolean allowMakeOre;

	public MirageMaterialIndustrial(
		String name, int color, String chemicalFormula,
		int itemToolHarvestLevel, EnumMachineTier machineTier, boolean allowMakeBlock, boolean allowMakeOre)
	{
		super(name, color, chemicalFormula);
		this.itemToolHarvestLevel = itemToolHarvestLevel;
		this.machineTier = machineTier;
		this.allowMakeBlock = allowMakeBlock;
		this.allowMakeOre = allowMakeOre;
	}

	@Override
	public void initTableRegister()
	{
		super.initTableRegister();

		putRegisterShapeBlock(EnumShape.block, new RegistererShapeRoot<MetaBlock>(allowMakeBlock));
		putRegisterShapeBlock(EnumShape.ore, new RegistererShapeRoot<MetaBlock>(allowMakeOre));

	}

	@Override
	public int getItemToolHarvestLevel()
	{
		return itemToolHarvestLevel;
	}

	@Override
	public EnumMachineTier getMachineTier()
	{
		return machineTier;
	}

	@Override
	public int getBlockHarvestLevel()
	{
		return STONE;
	}

}
