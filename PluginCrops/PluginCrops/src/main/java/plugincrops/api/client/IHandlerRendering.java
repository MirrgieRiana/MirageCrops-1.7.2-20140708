package plugincrops.api.client;

import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IHandlerRendering
{

	/**
	 * @return
	 *         true: rendering is processed<br>
	 *         false: rendering should be default<br>
	 *         null: this handler does not touch this rendering
	 */
	public Boolean renderBlock(IIcon icon, int x, int y, int z);

}
