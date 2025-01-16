package miragecrops.api.machines;

import net.minecraft.world.IBlockAccess;

public interface IMiragePipe
{

	/**
	 * �ߗ׃u���b�N�Ɍq���邩�ǂ����B �������[�v�ɂȂ�̂ŁA����̔���ɑ���̓����\�b�h���Ăяo���Ă͂Ȃ�Ȃ��B
	 * ���̃C���^�[�t�F�[�X�̃w���p�[�ȊO����Ăяo���Ă͂Ȃ�Ȃ��B
	 */
	public boolean canConnectMiragePipe(IBlockAccess iBlockAccess, int selfX, int selfY, int selfZ, int toX, int toY, int toZ);

	/**
	 * �P�ʂ̓��[�g��
	 */
	public float getTransportCapacity(ITransportContent iTransportContent, IBlockAccess iBlockAccess, int selfX, int selfY, int selfZ, int fromX, int fromY, int fromZ);

	public ITransportContent[] getTransportContents(IBlockAccess iBlockAccess, int x, int y, int z);

}
