package miragecrops.framework.material;

import miragecrops.api.framework.material.IMirageMaterial;

public interface IMirageMaterialMachine extends IMirageMaterial
{

	public EnumMachineTier getMachineTier();

	public static class Helper
	{

		public static EnumMachineTier getMachineTier(IMirageMaterial iMaterial)
		{
			if (iMaterial instanceof IMirageMaterialMachine) {
				return ((IMirageMaterialMachine) iMaterial).getMachineTier();
			}
			return EnumMachineTier.unusable;
		}

	}

}
