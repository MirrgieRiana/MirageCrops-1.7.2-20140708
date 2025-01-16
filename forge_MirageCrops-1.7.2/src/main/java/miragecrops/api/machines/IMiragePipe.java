package miragecrops.api.machines;

import net.minecraft.world.IBlockAccess;

public interface IMiragePipe
{

	/**
	 * 近隣ブロックに繋げるかどうか。 無限ループになるので、これの判定に相手の同メソッドを呼び出してはならない。
	 * このインターフェースのヘルパー以外から呼び出してはならない。
	 */
	public boolean canConnectMiragePipe(IBlockAccess iBlockAccess, int selfX, int selfY, int selfZ, int toX, int toY, int toZ);

	/**
	 * 単位はメートル
	 */
	public float getTransportCapacity(ITransportContent iTransportContent, IBlockAccess iBlockAccess, int selfX, int selfY, int selfZ, int fromX, int fromY, int fromZ);

	public ITransportContent[] getTransportContents(IBlockAccess iBlockAccess, int x, int y, int z);

}
