package miragecrops.core.machines.framework;

import mirrg.mir34.modding.IMod;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMetaBlockFurnacefamily extends GuiContainer
{

	public ResourceLocation guiTexture;

	@SuppressWarnings("unused")
	private final MetaBlockFurnacefamily metaBlockFurnacefamily;
	private final TileEntityMetaBlockFurnacefamily tileFurnace;

	public GuiMetaBlockFurnacefamily(IMod iMod, MetaBlockFurnacefamily metaBlockFurnacefamily, EntityPlayer player, World world, int x, int y, int z, TileEntityMetaBlockFurnacefamily tileFurnace)
	{
		super(new ContainerMetaBlockFurnacefamily(metaBlockFurnacefamily, player, world, x, y, z, tileFurnace));
		guiTexture = new ResourceLocation(iMod.getModId() + ":" + "textures/gui/deoxidizationFurnace.png");
		this.metaBlockFurnacefamily = metaBlockFurnacefamily;
		this.tileFurnace = tileFurnace;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		String s = this.tileFurnace.hasCustomInventoryName() ? this.tileFurnace.getInventoryName() : I18n.format(this.tileFurnace.getInventoryName(), new Object[0]);
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);

		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(guiTexture);
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

		this.drawTexturedModalRect(xStart + 56, yStart + 36, 176 + 0, 0, 14, 14);
		if (this.tileFurnace.isBurning())
		{
			int i1 = this.tileFurnace.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(xStart + 56, yStart + 36 + 12 - i1, 176 + 14, 12 - i1, 14, i1 + 2);
		}

		this.drawTexturedModalRect(xStart + 79, yStart + 34, 176, 14, 24 + 1, 16);
		{
			int i1 = this.tileFurnace.getCookProgressScaled(24);
			this.drawTexturedModalRect(xStart + 79, yStart + 34, 176 + 24, 14, i1 + 1, 16);
		}

	}
}
