package miragecrops.api.framework.material;

public interface IMirageMaterialsManager
{

	public int getMirageMaterialsCount();

	public IMirageMaterial getMirageMaterial(int ordinal);

	public int getOrdinalFromMirageMaterial(IMirageMaterial iMirageMaterial);

}
