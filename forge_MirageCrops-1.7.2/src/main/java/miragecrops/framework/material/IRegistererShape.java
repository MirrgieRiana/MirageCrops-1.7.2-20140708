package miragecrops.framework.material;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.IMirageMaterial;

public interface IRegistererShape<T>
{

	/**
	 * @return non null
	 */
	public T registerShape(IMirageMaterial iMirageMaterial, EnumShape enumShape, IFactory<T> iFactory);

	public boolean isProvidingShape();

}
