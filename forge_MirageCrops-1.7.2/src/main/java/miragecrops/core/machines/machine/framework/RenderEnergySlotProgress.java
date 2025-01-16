package miragecrops.core.machines.machine.framework;

import java.util.ArrayList;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEnergySlotProgress implements IRenderer<EnergySlot>
{

	public static final RenderEnergySlotProgress instance = new RenderEnergySlotProgress();

	@Override
	@SideOnly(Side.CLIENT)
	public void drawForegroundLayer(IGuiRenderHelper gui, EnergySlot t)
	{
		ResourceLocation texture = new ResourceLocation("miragecrops" + ":" + "textures/gui/progress_foreground.png");
		gui.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glColorMask(true, true, true, false);

		float rate = t.energyTank.capacity == 0 ? 1 : (float) t.energyTank.amount / t.energyTank.capacity;
		int w = (int) (t.w * rate);
		rate = (float) w / t.w;
		gui.drawTexturedModelRect(t.x, t.y, w, t.h, 0, 0, rate, 1);

		GL11.glColorMask(true, true, true, true);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawBackgroundLayer(IGuiRenderHelper gui, EnergySlot t)
	{
		int xStart = (gui.getScreenWidth() - gui.getGuiWidth()) / 2;
		int yStart = (gui.getScreenHeight() - gui.getGuiHeight()) / 2;

		ResourceLocation texture = new ResourceLocation("miragecrops" + ":" + "textures/gui/progress_background.png");
		gui.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glColorMask(true, true, true, false);

		gui.drawTexturedModelRect(xStart + t.x, yStart + t.y, t.w, t.h, 0, 0, 1, 1);

		GL11.glColorMask(true, true, true, true);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean drawToolTip(IGuiRenderHelper gui, EnergySlot t, int mouseX, int mouseY)
	{
		int xStart = (gui.getScreenWidth() - gui.getGuiWidth()) / 2;
		int yStart = (gui.getScreenHeight() - gui.getGuiHeight()) / 2;

		if (hit(gui, t, mouseX, mouseY)) {
			ArrayList<String> list = new ArrayList<String>();
			list.add("" + t.energyTank.amount + " / " + t.energyTank.capacity);
			gui.drawHoveringText(list, mouseX - xStart, mouseY - yStart, gui.getFontRenderer());
			return true;
		}
		return false;
	}

	public boolean hit(IGuiRenderHelper gui, EnergySlot t, int mouseX, int mouseY)
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
