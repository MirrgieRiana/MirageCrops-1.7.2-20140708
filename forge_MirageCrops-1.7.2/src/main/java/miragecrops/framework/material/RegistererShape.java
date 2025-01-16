package miragecrops.framework.material;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.IMirageMaterial;

public class RegistererShape<T> implements IRegistererShape<T>
{

	protected IRegistererShape<T> superRegister;

	public RegistererShape(IRegistererShape<T> superRegister)
	{
		this.superRegister = superRegister;
	}

	@Override
	public T registerShape(IMirageMaterial iMirageMaterial, EnumShape enumShape, IFactory<T> iFactory)
	{
		return superRegister.registerShape(iMirageMaterial, enumShape, iFactory);
	}

	@Override
	public final boolean isProvidingShape()
	{
		return superRegister.isProvidingShape();
	}

}
