package miragecrops.core.machines.pipe;

import miragecrops.api.machines.IMiragePipe;
import miragecrops.api.machines.ITransportContent;
import miragecrops.api.machines.TransportContents;
import net.minecraft.world.IBlockAccess;

public class MiragePipeMirageAlloy implements IMiragePipe
{

	@Override
	public boolean canConnectMiragePipe(IBlockAccess iBlockAccess, int selfX, int selfY, int selfZ, int toX, int toY, int toZ)
	{
		return true;
	}

	@Override
	public float getTransportCapacity(ITransportContent iTransportContent, IBlockAccess iBlockAccess, int selfX, int selfY, int selfZ, int fromX, int fromY, int fromZ)
	{
		if (iTransportContent == TransportContents.item) return 10.0F;
		return 0;
	}

	@Override
	public ITransportContent[] getTransportContents(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return new ITransportContent[] {
			TransportContents.item,
		};
	}

}
