package miragecrops.core.machines.machine.framework;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMirageMachine extends GuiContainer
{

	protected final ResourceLocation guiTexture;
	protected final ContainerMirageMachine container;
	public final GuiRenderHelperImpl handler = new GuiRenderHelperImpl();

	public GuiMirageMachine(ContainerMirageMachine container, ResourceLocation guiTexture)
	{
		super(container);
		this.container = container;
		this.guiTexture = guiTexture;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{

		String s = container.getTileEntity().getLocalizedName();
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 0x404040);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);

		for (Object slot : inventorySlots.inventorySlots) {
			if (slot instanceof IRenderer<?>) {
				((IRendererProvider<Slot>) slot).getRenderer().drawForegroundLayer(handler, (Slot) slot);
			} else {
				RenderSlot.instance.drawForegroundLayer(handler, (Slot) slot);
			}
		}

		for (FluidSlot fluidSlot : container.fluidSlots) {
			if (fluidSlot instanceof IRenderer<?>) {
				((IRendererProvider<FluidSlot>) fluidSlot).getRenderer().drawForegroundLayer(handler, fluidSlot);
			} else {
				RenderFluidSlot.instance.drawForegroundLayer(handler, fluidSlot);
			}
		}

		for (EnergySlot energySlot : container.energySlots) {
			if (energySlot instanceof IRenderer<?>) {
				((IRendererProvider<EnergySlot>) energySlot).getRenderer().drawForegroundLayer(handler, energySlot);
			} else {
				RenderEnergySlotProgress.instance.drawForegroundLayer(handler, energySlot);
			}
		}

		drawGuiContainerToolTip(mouseX, mouseY);

	}

	protected void drawGuiContainerToolTip(int mouseX, int mouseY)
	{

		for (Object slot : inventorySlots.inventorySlots) {
			if (slot instanceof IRenderer<?>) {
				if (((IRendererProvider<Slot>) slot).getRenderer().drawToolTip(handler, (Slot) slot, mouseX, mouseY)) return;
			} else {
				if (RenderSlot.instance.drawToolTip(handler, (Slot) slot, mouseX, mouseY)) return;
			}
		}

		for (FluidSlot fluidSlot : container.fluidSlots) {
			if (fluidSlot instanceof IRenderer<?>) {
				if (((IRendererProvider<FluidSlot>) fluidSlot).getRenderer().drawToolTip(handler, fluidSlot, mouseX, mouseY)) return;
			} else {
				if (RenderFluidSlot.instance.drawToolTip(handler, fluidSlot, mouseX, mouseY)) return;
			}
		}

		for (EnergySlot energySlot : container.energySlots) {
			if (energySlot instanceof IRenderer<?>) {
				if (((IRendererProvider<EnergySlot>) energySlot).getRenderer().drawToolTip(handler, energySlot, mouseX, mouseY)) return;
			} else {
				if (RenderEnergySlotProgress.instance.drawToolTip(handler, energySlot, mouseX, mouseY)) return;
			}
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float arg0, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(guiTexture);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

		for (Object slot : inventorySlots.inventorySlots) {
			if (slot instanceof IRenderer<?>) {
				((IRendererProvider<Slot>) slot).getRenderer().drawBackgroundLayer(handler, (Slot) slot);
			} else {
				RenderSlot.instance.drawBackgroundLayer(handler, (Slot) slot);
			}
		}

		for (FluidSlot fluidSlot : container.fluidSlots) {
			if (fluidSlot instanceof IRenderer<?>) {
				((IRendererProvider<FluidSlot>) fluidSlot).getRenderer().drawBackgroundLayer(handler, fluidSlot);
			} else {
				RenderFluidSlot.instance.drawBackgroundLayer(handler, fluidSlot);
			}
		}

		for (EnergySlot energySlot : container.energySlots) {
			if (energySlot instanceof IRenderer<?>) {
				((IRendererProvider<EnergySlot>) energySlot).getRenderer().drawBackgroundLayer(handler, energySlot);
			} else {
				RenderEnergySlotProgress.instance.drawBackgroundLayer(handler, energySlot);
			}
		}

	}

	protected static void glColor4f(int color)
	{
		int b = color & 0xff;
		color >>= 8;
		int g = color & 0xff;
		color >>= 8;
		int r = color & 0xff;
		color >>= 8;
		int a = color & 0xff;

		GL11.glColor4f(r * 0.00390625F, g * 0.00390625F, b * 0.00390625F, a * 0.00390625F);
	}

	public class GuiRenderHelperImpl implements IGuiRenderHelper
	{

		@Override
		public Minecraft getMinecraft()
		{
			return mc;
		}

		@Override
		public FontRenderer getFontRenderer()
		{
			return fontRendererObj;
		}

		@Override
		public void drawGradientRect(int x1, int y1, int x2, int y2, int color1, int color2)
		{
			GuiMirageMachine.this.drawGradientRect(x1, y1, x2, y2, color1, color2);
		}

		@Override
		public void drawTexturedModelRect(int x, int y, int w, int h, float sx1, float sy1, float sx2, float sy2)
		{
			drawTexturedModelRect(x, y, w, h, sx1, sy1, sx2, sy2, EnumRotate.RIGHT0);
		}

		@Override
		public void drawTexturedModelRect(int x, int y, int w, int h, float sx1, float sy1, float sx2, float sy2, EnumRotate rotate)
		{
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();

			switch (rotate) {
				case RIGHT0:
					tessellator.addVertexWithUV(x + 0, y + h, zLevel, sx1, sy2);
					tessellator.addVertexWithUV(x + w, y + h, zLevel, sx2, sy2);
					tessellator.addVertexWithUV(x + w, y + 0, zLevel, sx2, sy1);
					tessellator.addVertexWithUV(x + 0, y + 0, zLevel, sx1, sy1);
					break;
				case RIGHT90:
					tessellator.addVertexWithUV(x + 0, y + h, zLevel, sx2, sy2);
					tessellator.addVertexWithUV(x + w, y + h, zLevel, sx2, sy1);
					tessellator.addVertexWithUV(x + w, y + 0, zLevel, sx1, sy1);
					tessellator.addVertexWithUV(x + 0, y + 0, zLevel, sx1, sy2);
					break;
				case RIGHT180:
					tessellator.addVertexWithUV(x + 0, y + h, zLevel, sx2, sy1);
					tessellator.addVertexWithUV(x + w, y + h, zLevel, sx1, sy1);
					tessellator.addVertexWithUV(x + w, y + 0, zLevel, sx1, sy2);
					tessellator.addVertexWithUV(x + 0, y + 0, zLevel, sx2, sy2);
					break;
				case RIGHT270:
					tessellator.addVertexWithUV(x + 0, y + h, zLevel, sx1, sy1);
					tessellator.addVertexWithUV(x + w, y + h, zLevel, sx1, sy2);
					tessellator.addVertexWithUV(x + w, y + 0, zLevel, sx2, sy2);
					tessellator.addVertexWithUV(x + 0, y + 0, zLevel, sx2, sy1);
					break;
				default:
					break;
			}

			tessellator.draw();
		}

		@Override
		public void drawTexturedModelRectFromIcon(int x, int y, int w, int h, IIcon icon, float sx1, float sy1, float sx2, float sy2)
		{
			float su1 = icon.getMinU() + (icon.getMaxU() - icon.getMinU()) * sx1;
			float sv1 = icon.getMinV() + (icon.getMaxV() - icon.getMinV()) * sy1;
			float su2 = icon.getMinU() + (icon.getMaxU() - icon.getMinU()) * sx2;
			float sv2 = icon.getMinV() + (icon.getMaxV() - icon.getMinV()) * sy2;

			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(x + 0, y + h, zLevel, su1, sv2);
			tessellator.addVertexWithUV(x + w, y + h, zLevel, su2, sv2);
			tessellator.addVertexWithUV(x + w, y + 0, zLevel, su2, sv1);
			tessellator.addVertexWithUV(x + 0, y + 0, zLevel, su1, sv1);
			tessellator.draw();
		}

		@Override
		public void drawRectAdd(int x1, int y1, int x2, int y2, int color)
		{
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);

			glColor4f(color);

			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			drawRect(x1, y1, x2, y2);

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}

		@Override
		public void drawRectMultiply(int x1, int y1, int x2, int y2, int color)
		{
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);

			glColor4f(color);

			GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_SRC_COLOR);
			drawRect(x1, y1, x2, y2);

			GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_SRC_ALPHA);
			drawRect(x1, y1, x2, y2);

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}

		@Override
		public void drawRect(int x1, int y1, int x2, int y2)
		{
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertex(x1, y2, zLevel);
			tessellator.addVertex(x2, y2, zLevel);
			tessellator.addVertex(x2, y1, zLevel);
			tessellator.addVertex(x1, y1, zLevel);
			tessellator.draw();
		}

		@Override
		public void drawRectFrame(int x, int y, int w, int h, int padding, int borderSize)
		{

			x -= padding;
			y -= padding;
			w += 2 * padding;
			h += 2 * padding;

			drawRectMultiply(x, y, x + w, y + h, 0xffb3b3b3);
			drawRectMultiply(x - borderSize, y - borderSize, x + w, y, 0xff474747);
			drawRectMultiply(x - borderSize, y, x, y + h, 0xff474747);
			drawRectMultiply(x - borderSize, y + h, x, y + h + borderSize, 0xffb3b3b3);
			drawRectMultiply(x + w, y - borderSize, x + w + borderSize, y, 0xffb3b3b3);
			drawRectAdd(x + w, y, x + w + borderSize, y + h, 0xff3a3a3a);
			drawRectAdd(x, y + h, x + w + borderSize, y + h + borderSize, 0xff3a3a3a);

		}

		@Override
		public void drawHoveringText(List list, int mouseX, int mouseY, FontRenderer font)
		{
			GuiMirageMachine.this.drawHoveringText(list, mouseX, mouseY, font);
		}

		@Override
		public int getScreenWidth()
		{
			return width;
		}

		@Override
		public int getScreenHeight()
		{
			return height;
		}

		@Override
		public int getGuiWidth()
		{
			return xSize;
		}

		@Override
		public int getGuiHeight()
		{
			return ySize;
		}

	}

}
