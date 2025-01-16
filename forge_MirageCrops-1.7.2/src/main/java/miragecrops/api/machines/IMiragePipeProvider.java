package miragecrops.api.machines;

import net.minecraft.world.IBlockAccess;

public interface IMiragePipeProvider
{

	public IMiragePipe getMiragePipe(IBlockAccess iBlockAccess, int x, int y, int z);

}
