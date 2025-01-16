package miragecrops.api.framework.block;

/**
 * 1��IBlockMeta��15���AIMetaBlock���i�[���Ă����B
 * IMetaBlock�̓o�^�ԍ���metaBlockIndex�Ŏ�����A0����14�܂ł�15��blockMetaIndex��0�ł���blockMeta�A
 * ��������1��blockMeta��15����metaBlock������B
 * blockMeta�̓o�^�ԍ���blockMetaIndex�Ŏ�����A�����0����n�܂�B
 * indexInBlockMeta��1��blockMeta�̂ǂ̃X���b�g��metaBlock�������Ă��邩�����߁A
 * ���ꂪ0�ł���ꍇ�AblockMeta���ł�metaBlock��metaId��1�ƂȂ�B
 * 
 * <pre>
 * IMetaBlock�̓o�^���@�F
 *   IBlockMeta blockMeta = unionBlock.getNextBlockMetaThatShouldBeCreatedInto();
 *   int metaId = unionBlock.getNextMetaIdThatShouldBeCreatedInto();
 *   IMetaBlock metaBlock = new MetaBlock(blockMeta, metaId);
 *   unionBlock.add(metaBlock);
 * </pre>
 */
public interface IUnionBlock<T extends IBlockMeta>
{

	public T getBlockMeta(int blockMetaIndex);

	public int getBlockMetaSize();

	public IMetaBlock getMetaBlock(int metaBlockIndex);

	public int getMetaBlockSize();

	public int getBlockMetaIndex(int metaBlockIndex);

	public int getIndexInBlockMeta(int metaBlockIndex);

}
