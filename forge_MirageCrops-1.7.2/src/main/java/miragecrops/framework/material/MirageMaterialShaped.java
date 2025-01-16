package miragecrops.framework.material;

import java.util.Hashtable;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.framework.block.MetaBlock;
import miragecrops.framework.item.MetaItem;

public abstract class MirageMaterialShaped extends MirageMaterialImpl implements IMirageMaterialShaped
{

	private final Hashtable<EnumShape, IRegistererShape<MetaItem>> tableRegisterShapeItem = new Hashtable<EnumShape, IRegistererShape<MetaItem>>();
	private final Hashtable<EnumShape, IRegistererShape<MetaBlock>> tableRegisterShapeBlock = new Hashtable<EnumShape, IRegistererShape<MetaBlock>>();

	public MirageMaterialShaped(String name, int color, String chemicalFormula)
	{
		super(name, color, chemicalFormula);
	}

	protected abstract void initTableRegister();

	@Override
	public final boolean isProvidingShapeItem(EnumShape enumShape)
	{
		IRegistererShape<MetaItem> iRegisterShapeItem = getRegisterShapeItem(enumShape);
		return iRegisterShapeItem.isProvidingShape();
	}

	@Override
	public final boolean isProvidingShapeBlock(EnumShape enumShape)
	{
		IRegistererShape iRegisterShapeBlock = getRegisterShapeBlock(enumShape);
		return iRegisterShapeBlock.isProvidingShape();
	}

	@Override
	public final MetaItem registerShapeItem(IFactory<MetaItem> iMetaItemFactory, EnumShape enumShape)
	{
		IRegistererShape<MetaItem> iRegisterShapeItem = getRegisterShapeItem(enumShape);
		if (!iRegisterShapeItem.isProvidingShape()) return null;
		return iRegisterShapeItem.registerShape(this, enumShape, iMetaItemFactory);
	}

	@Override
	public final MetaBlock registerShapeBlock(IFactory<MetaBlock> iMetaBlockFactory, EnumShape enumShape)
	{
		IRegistererShape<MetaBlock> iRegisterShapeBlock = getRegisterShapeBlock(enumShape);
		if (!iRegisterShapeBlock.isProvidingShape()) return null;
		return iRegisterShapeBlock.registerShape(this, enumShape, iMetaBlockFactory);
	}

	protected IRegistererShape<MetaItem> getRegisterShapeItem(EnumShape enumShape)
	{
		if (!tableRegisterShapeItem.containsKey(enumShape)) {
			tableRegisterShapeItem.put(enumShape, new RegistererShapeRoot<MetaItem>(false));
		}

		return tableRegisterShapeItem.get(enumShape);
	}

	protected IRegistererShape<MetaBlock> getRegisterShapeBlock(EnumShape enumShape)
	{
		if (!tableRegisterShapeBlock.containsKey(enumShape)) {
			tableRegisterShapeBlock.put(enumShape, new RegistererShapeRoot<MetaBlock>(false));
		}

		return tableRegisterShapeBlock.get(enumShape);
	}

	protected void putRegisterShapeItem(EnumShape enumShape, IRegistererShape<MetaItem> iRegisterShape)
	{
		tableRegisterShapeItem.put(enumShape, iRegisterShape);
	}

	protected void putRegisterShapeBlock(EnumShape enumShape, IRegistererShape<MetaBlock> iRegisterShape)
	{
		tableRegisterShapeBlock.put(enumShape, iRegisterShape);
	}

}
