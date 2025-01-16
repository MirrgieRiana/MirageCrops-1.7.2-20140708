package miragecrops.core.machines.machine.framework;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IGuiRenderHelper
{

	public Minecraft getMinecraft();

	public FontRenderer getFontRenderer();

	public void drawGradientRect(int x1, int y1, int x2, int y2, int color1, int color2);

	public void drawTexturedModelRect(int x, int y, int w, int h, float sx1, float sy1, float sx2, float sy2);

	public void drawTexturedModelRect(int x, int y, int w, int h, float sx1, float sy1, float sx2, float sy2, EnumRotate rotate);

	public void drawTexturedModelRectFromIcon(int x, int y, int w, int h, IIcon icon, float sx1, float sy1, float sx2, float sy2);

	public void drawRectAdd(int x1, int y1, int x2, int y2, int color);

	public void drawRectMultiply(int x1, int y1, int x2, int y2, int color);

	public void drawRect(int x1, int y1, int x2, int y2);

	public void drawRectFrame(int x, int y, int w, int h, int padding, int borderSize);

	public void drawHoveringText(List list, int mouseX, int mouseY, FontRenderer font);

	public int getScreenWidth();

	public int getScreenHeight();

	public int getGuiWidth();

	public int getGuiHeight();

	public static enum EnumRotate
	{
		RIGHT0,
		RIGHT90,
		RIGHT180,
		RIGHT270,
	}

}
