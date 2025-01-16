package miragecrops.core.ores.materials;

import miragecrops.framework.block.BlockMeta;
import miragecrops.framework.block.MetaBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class MetaBlockMirageMaterial extends MetaBlock
{

	protected String materialName = null;
	protected String shapeName = null;

	public MetaBlockMirageMaterial(BlockMeta blockMeta, int metaId)
	{
		super(blockMeta, metaId);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		if (StatCollector.canTranslate(unlocalizedName)) {
			return StatCollector.translateToLocal(unlocalizedName);
		}

		if (materialName != null && shapeName != null) {
			String materialLocalizedName = StatCollector.translateToLocal("material." + materialName + ".name");
			String shapeLocalizedFormat = StatCollector.translateToLocal("shape." + shapeName + ".format");

			return String.format(shapeLocalizedFormat, materialLocalizedName).trim();
		}

		return super.getItemStackDisplayName(par1ItemStack);
	}

	public void setMaterialNameAndShapeName(String materialName, String shapeName)
	{
		this.materialName = materialName;
		this.shapeName = shapeName;
	}

}
