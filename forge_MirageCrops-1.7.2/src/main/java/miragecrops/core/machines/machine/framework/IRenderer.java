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
	 * @return true�̂Ƃ��A�㑱�̃����_���[��ToolTip�̕`����L�����Z������B
	 */
	@SideOnly(Side.CLIENT)
	public boolean drawToolTip(IGuiRenderHelper gui, T t, int mouseX, int mouseY);

}
