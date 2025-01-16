package miragecrops.framework.material;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.IMirageMaterial;

public class RegistererShapeRoot<T> implements IRegistererShape<T>
{

	private boolean isProvidingShape;

	public RegistererShapeRoot(boolean isProvidingShape)
	{
		this.isProvidingShape = isProvidingShape;
	}

	@Override
	public T registerShape(IMirageMaterial iMirageMaterial, EnumShape enumShape, IFactory<T> iFactory)
	{
		return iFactory.create();
	}

	@Override
	public final boolean isProvidingShape()
	{
		return isProvidingShape;
	}

}
