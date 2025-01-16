package miragecrops.framework.material;

import miragecrops.api.framework.item.EnumToolMaterialHarvestLevels;
import miragecrops.api.framework.material.IMirageMaterial;

public interface IMirageMaterialBlock extends IMirageMaterial
{

	public int getBlockHarvestLevel();

	public static class Helper
	{

		public static int getBlockHarvestLevel(IMirageMaterial iMaterial)
		{
			if (iMaterial instanceof IMirageMaterialBlock) {
				return ((IMirageMaterialBlock) iMaterial).getBlockHarvestLevel();
			}
			return EnumToolMaterialHarvestLevels.HAND;
		}

	}

}
