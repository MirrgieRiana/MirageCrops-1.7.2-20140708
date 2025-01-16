package plugincrops.coremodinitializer;

import net.minecraft.util.IIcon;
import plugincrops.api.client.ApiCoreModRendererBlockCrop;
import plugincrops.api.client.IHandlerRendering;
import plugincrops.coremod.client.Renderer;

public class ProxyClient extends Proxy
{

	@Override
	public void init()
	{
		ApiCoreModRendererBlockCrop.handler = new IHandlerRendering() {
			@Override
			public Boolean renderBlock(IIcon icon, int x, int y, int z)
			{
				return Renderer.renderBlockCropsImpl(icon, x, y, z);
			}
		};
	}

}
