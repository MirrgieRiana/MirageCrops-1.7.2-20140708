package plugincrops.coremod.client;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;

import java.util.Hashtable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import plugincrops.api.client.ApiCoreModRendererBlockCrop;
import plugincrops.api.client.IHandlerRendering;
import plugincrops.coremod.ConfigurationPluginCrops.RenderBlockCropsImpl.CrossingDefault;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Renderer
{

	private static CropCard getCropCard(IIcon icon, int x, int y, int z)
	{
		TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(x, y, z);
		if (tileEntity instanceof ICropTile) {
			return ((ICropTile) tileEntity).getCrop();
		}

		return null;
	}

	static
	{
		ApiCoreModRendererBlockCrop.handlerRenderings.add(new HandlerRenderingSwitcher());
		ApiCoreModRendererBlockCrop.handlerRenderings.add(new HandlerRenderingCrossingDefault());

		ApiCoreModRendererBlockCrop.handlerRenderingDefault = new HandlerRenderingDefault();

		ApiCoreModRendererBlockCrop.registerHandlerRendering.put("default",
			ApiCoreModRendererBlockCrop.handlerRenderingDefault);
		ApiCoreModRendererBlockCrop.registerHandlerRendering.put("crossing",
			new HandlerRenderingCrossing());
	}

	public static boolean renderBlockCropsImpl(IIcon icon, int x, int y, int z)
	{
		for (IHandlerRendering handlerRenderingBlockCrop : ApiCoreModRendererBlockCrop.handlerRenderings) {
			Boolean res = handlerRenderingBlockCrop.renderBlock(icon, x, y, z);
			if (res != null) return res;
		}

		return false;
	}

	private static class HandlerRenderingSwitcher implements IHandlerRendering
	{

		@Override
		public Boolean renderBlock(IIcon icon, int x, int y, int z)
		{
			CropCard cropCard = getCropCard(icon, x, y, z);
			if (cropCard != null) {

				if (ApiCoreModRendererBlockCrop.tableCropCardToHandlerRendering.containsKey(cropCard)) {
					return ApiCoreModRendererBlockCrop.tableCropCardToHandlerRendering.get(cropCard).renderBlock(icon, x, y, z);
				}

			}

			return null;
		}

	}

	private static class HandlerRenderingCrossingDefault implements IHandlerRendering
	{

		private Hashtable<CropCard, Boolean> cache = new Hashtable<CropCard, Boolean>();

		@Override
		public Boolean renderBlock(IIcon icon, int x, int y, int z)
		{
			CropCard cropCard = getCropCard(icon, x, y, z);
			if (cropCard != null) {
				if (isCrossingDefault(cropCard)) {
					ApiCoreModRendererBlockCrop.registerHandlerRendering.get("crossing").renderBlock(icon, x, y, z);
					return true;
				}
			}

			return null;
		}

		private boolean isCrossingDefault(CropCard cropCard)
		{
			if (!cache.containsKey(cropCard)) {
				String displayName = StatCollector.translateToFallback(cropCard.displayName());
				String[] attributes = cropCard.attributes();
				boolean res = CrossingDefault.isCrossingDefault(displayName, attributes);
				cache.put(cropCard, res);
			}
			return cache.get(cropCard);
		}

	}

	private static class HandlerRenderingDefault implements IHandlerRendering
	{

		@Override
		public Boolean renderBlock(IIcon icon, int x, int y, int z)
		{
			return false;
		}

	}

	private static class HandlerRenderingCrossing implements IHandlerRendering
	{

		@Override
		public Boolean renderBlock(IIcon icon, int x, int y, int z)
		{
			Tessellator tessellator = Tessellator.instance;
			double yBase = y - 0.0625D;
			double uStart = icon.getInterpolatedU(0.0D);
			double uEnd = icon.getInterpolatedU(16.0D);
			double vStart = icon.getInterpolatedV(0.0D);
			double vEnd = icon.getInterpolatedV(16.0D);
			double x1 = x + 0.5D - 0.5D; // x + 0.5D - 0.25D;
			double x2 = x + 0.5D + 0.5D; // x + 0.5D + 0.25D;
			double z1 = z + 0.5D - 0.5D;
			double z2 = z + 0.5D + 0.5D;

			tessellator.addVertexWithUV(x1, yBase + 1.0D, z1, uStart, vStart);
			tessellator.addVertexWithUV(x1, yBase + 0.0D, z1, uStart, vEnd);
			tessellator.addVertexWithUV(x2, yBase + 0.0D, z2, uEnd, vEnd);
			tessellator.addVertexWithUV(x2, yBase + 1.0D, z2, uEnd, vStart);

			tessellator.addVertexWithUV(x1, yBase + 1.0D, z2, uStart, vStart);
			tessellator.addVertexWithUV(x1, yBase + 0.0D, z2, uStart, vEnd);
			tessellator.addVertexWithUV(x2, yBase + 0.0D, z1, uEnd, vEnd);
			tessellator.addVertexWithUV(x2, yBase + 1.0D, z1, uEnd, vStart);

			tessellator.addVertexWithUV(x2, yBase + 1.0D, z2, uStart, vStart);
			tessellator.addVertexWithUV(x2, yBase + 0.0D, z2, uStart, vEnd);
			tessellator.addVertexWithUV(x1, yBase + 0.0D, z1, uEnd, vEnd);
			tessellator.addVertexWithUV(x1, yBase + 1.0D, z1, uEnd, vStart);

			tessellator.addVertexWithUV(x2, yBase + 1.0D, z1, uStart, vStart);
			tessellator.addVertexWithUV(x2, yBase + 0.0D, z1, uStart, vEnd);
			tessellator.addVertexWithUV(x1, yBase + 0.0D, z2, uEnd, vEnd);
			tessellator.addVertexWithUV(x1, yBase + 1.0D, z2, uEnd, vStart);

			return true;
		}

	}

}
