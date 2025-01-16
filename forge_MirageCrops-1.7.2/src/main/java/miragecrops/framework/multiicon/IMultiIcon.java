package miragecrops.framework.multiicon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IMultiIcon extends IMultiIconShape
{

	@SideOnly(Side.CLIENT)
	public int getColor(int pass);

}
