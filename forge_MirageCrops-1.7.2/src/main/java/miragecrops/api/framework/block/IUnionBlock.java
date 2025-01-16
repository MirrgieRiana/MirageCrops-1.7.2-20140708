package miragecrops.api.framework.block;

/**
 * 1つのIBlockMetaに15個ずつ、IMetaBlockを格納していく。
 * IMetaBlockの登録番号はmetaBlockIndexで示され、0から14までの15個はblockMetaIndexが0であるblockMeta、
 * それより先は1つのblockMetaに15個ずつmetaBlockが入る。
 * blockMetaの登録番号はblockMetaIndexで示され、これも0から始まる。
 * indexInBlockMetaは1つのblockMetaのどのスロットにmetaBlockが入っているかをきめ、
 * これが0である場合、blockMeta内でのmetaBlockのmetaIdは1となる。
 * 
 * <pre>
 * IMetaBlockの登録方法：
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
