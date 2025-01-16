package miragecrops.framework.material;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.framework.block.MetaBlock;
import miragecrops.framework.item.MetaItem;

public interface IMirageMaterialShaped
{

	public boolean isProvidingShapeItem(EnumShape enumShape);

	public boolean isProvidingShapeBlock(EnumShape enumShape);

	public MetaItem registerShapeItem(IFactory<MetaItem> iMetaItemFactory, EnumShape enumShape);

	public MetaBlock registerShapeBlock(IFactory<MetaBlock> iMetaBlockFactory, EnumShape enumShape);

	public void registerRecipe();

}
