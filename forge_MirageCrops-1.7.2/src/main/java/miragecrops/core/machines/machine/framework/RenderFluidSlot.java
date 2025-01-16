package miragecrops.core.machines.machine.framework;

import java.util.ArrayList;

import miragecrops.core.machines.machine.framework.IGuiRenderHelper.EnumRotate;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFluidSlot implements IRenderer<FluidSlot>
{

	public static final RenderFluidSlot instance = new RenderFluidSlot();

	@Override
	@SideOnly(Side.CLIENT)
	public void drawForegroundLayer(IGuiRenderHelper gui, FluidSlot t)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColorMask(true, true, true, false);

		if (t.fluidTank.fluidStack != null) {
			if (t.fluidTank.fluidStack.getFluid() != null) {

				ResourceLocation resourcelocation = gui.getMinecraft().renderEngine.getResourceLocation(
					t.fluidTank.fluidStack.getFluid().getSpriteNumber());
				gui.getMinecraft().renderEngine.bindTexture(resourcelocation);

				{
					float rate = (float) t.fluidTank.fluidStack.amount / t.fluidTank.getCapacity();
					int height = (int) (t.h * rate);
					int begin = t.y + t.h - height;
					int end = t.y + t.h;

					while (height >= t.w) {
						gui.drawTexturedModelRectFromIcon(t.x, end - t.w, t.w, t.w,
							t.fluidTank.fluidStack.getFluid().getIcon(t.fluidTank.fluidStack),
							0, 0, 1, 1);
						end -= t.w;
						height -= t.w;
					}

					gui.drawTexturedModelRectFromIcon(t.x, begin, t.w, height,
						t.fluidTank.fluidStack.getFluid().getIcon(t.fluidTank.fluidStack),
						0, 1.0F * (t.w - height) / t.w, 1, 1);
				}
			}
		}

		{
			ResourceLocation texture = new ResourceLocation("miragecrops" + ":" + "textures/gui/miragefont.png");
			gui.getMinecraft().renderEngine.bindTexture(texture);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			{
				String amount = "" + t.fluidTank.getFluidAmount();

				if (amount.length() >= 4) {
					drawFluidString(gui, amount.substring(0, amount.length() - 3),
						t.x + t.w - 6 - 1,
						t.y + t.h / 2);
					drawFluidString(gui, amount.substring(amount.length() - 3),
						t.x + t.w - 6 - 1,
						t.y + t.h / 2 + 3 * 4 + 2);
				} else {
					drawFluidString(gui, amount,
						t.x + t.w - 6 - 1,
						t.y + t.h / 2 + 3 * 4 + 2);
				}
			}
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glColorMask(true, true, true, true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

	}

	public void drawFluidString(IGuiRenderHelper gui, String text, int oX, int oY)
	{
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);

			if (' ' <= ch && ch <= '~') {
				int r = (ch >> 4) - 2;
				int c = ch & 0xf;
				gui.drawTexturedModelRect(
					oX - 1,
					oY - text.length() * 4 + i * 4 - 1,
					6 + 2, 4 + 2,
					(float) (c) / 16,
					(float) (r) / 6,
					(float) (c + 1) / 16,
					(float) (r + 1) / 6, EnumRotate.RIGHT90);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawBackgroundLayer(IGuiRenderHelper gui, FluidSlot t)
	{
		int xStart = (gui.getScreenWidth() - gui.getGuiWidth()) / 2;
		int yStart = (gui.getScreenHeight() - gui.getGuiHeight()) / 2;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glColorMask(true, true, true, false);

		gui.drawRectFrame(xStart + t.x, yStart + t.y, t.w, t.h, 0, 1);

		for (int i = 1; i <= 9; i++) {
			int y = yStart + t.y + (t.h * i / 10);
			int w = i == 5 ? t.w : t.w / 3;

			gui.drawGradientRect(
				xStart + t.x,
				y,
				xStart + t.x + w,
				y + 1,
				0xffa00000, 0xffa00000);
		}

		GL11.glColorMask(true, true, true, true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean drawToolTip(IGuiRenderHelper gui, FluidSlot t, int mouseX, int mouseY)
	{
		int xStart = (gui.getScreenWidth() - gui.getGuiWidth()) / 2;
		int yStart = (gui.getScreenHeight() - gui.getGuiHeight()) / 2;

		if (hit(gui, t, mouseX, mouseY)) {
			ArrayList<String> list = new ArrayList<String>();
			if (t.fluidTank.getFluid() == null) {
				list.add("Empty");
			} else {
				list.add(t.fluidTank.getFluid().getFluid().getLocalizedName());
			}
			list.add("" + t.fluidTank.getFluidAmount() + " / " + t.fluidTank.getCapacity());
			gui.drawHoveringText(list, mouseX - xStart, mouseY - yStart, gui.getFontRenderer());
			return true;
		}
		return false;
	}

	public boolean hit(IGuiRenderHelper gui, FluidSlot t, int mouseX, int mouseY)
	{
		int xStart = (gui.getScreenWidth() - gui.getGuiWidth()) / 2;
		int yStart = (gui.getScreenHeight() - gui.getGuiHeight()) / 2;

		return isHit(xStart + t.x, yStart + t.y, xStart + t.x + t.w, yStart + t.y + t.h, mouseX, mouseY);
	}

	public static boolean isHit(int x1, int y1, int x2, int y2, int mouseX, int mouseY)
	{
		if (mouseX < x1) return false;
		if (mouseX > x2) return false;
		if (mouseY < y1) return false;
		if (mouseY > y2) return false;
		return true;
	}

}
