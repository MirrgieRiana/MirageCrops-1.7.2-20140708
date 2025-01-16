package miragecrops.core.machines.machine.framework;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IRenderer<T>
{

	@SideOnly(Side.CLIENT)
	public void drawBackgroundLayer(IGuiRenderHelper gui, T t);

	@SideOnly(Side.CLIENT)
	public void drawForegroundLayer(IGuiRenderHelper gui, T t);

	/**
	 * @return trueのとき、後続のレンダラーのToolTipの描画をキャンセルする。
	 */
	@SideOnly(Side.CLIENT)
	public boolean drawToolTip(IGuiRenderHelper gui, T t, int mouseX, int mouseY);

}
