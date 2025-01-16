package miragecrops.framework.block;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import miragecrops.api.framework.block.IBlockMeta;
import miragecrops.api.framework.block.IMetaBlock;
import miragecrops.api.framework.block.IUnionBlock;
import mirrg.mir34.modding.IMod;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;

public class UnionBlock<T extends BlockMeta> implements IUnionBlock<T>
{

	protected final IMod iMod;
	protected final Class<T> classBlockMeta;
	protected final String name;
	protected final CreativeTabs creativeTab;

	protected final ArrayList<T> blockMetas = new ArrayList<T>();
	protected final ArrayList<MetaBlock> metaBlocks = new ArrayList<MetaBlock>();
	protected int size = 0;
	private Class<? extends ItemBlock> classItemBlockMeta = ItemBlockMeta.class;

	/**
	 * @param iMod
	 * @param classBlockMeta
	 * @param name
	 *            大文字で始まるキャメルケース"Name"
	 */
	public UnionBlock(IMod iMod, Class<T> classBlockMeta, String name, CreativeTabs creativeTab)
	{
		this.iMod = iMod;
		this.classBlockMeta = classBlockMeta;
		this.name = name;
		this.creativeTab = creativeTab;
	}

	public Class<? extends ItemBlock> getClassItemBlockMeta()
	{
		return classItemBlockMeta;
	}

	public void setClassItemBlockMeta(Class<? extends ItemBlock> classItemBlockMeta)
	{
		this.classItemBlockMeta = classItemBlockMeta;
	}

	public T getNextBlockMetaThatShouldBeCreatedInto()
	{
		int blockMetaIndex = getBlockMetaIndex(size);
		while (blockMetas.size() <= blockMetaIndex) {
			// 足りない
			blockMetas.add(createBlockMeta(blockMetas.size()));
		}

		return blockMetas.get(blockMetaIndex);
	}

	public int getNextMetaIdThatShouldBeCreatedInto()
	{
		return getIndexInBlockMeta(size) + 1;
	}

	public void add(MetaBlock metaBlock)
	{
		getNextBlockMetaThatShouldBeCreatedInto().setMetaBlock(getNextMetaIdThatShouldBeCreatedInto(), metaBlock);

		metaBlocks.add(metaBlock);

		size++;
	}

	@Override
	public T getBlockMeta(int blockMetaIndex)
	{
		return blockMetas.get(blockMetaIndex);
	}

	@Override
	public int getBlockMetaSize()
	{
		return blockMetas.size();
	}

	@Override
	public MetaBlock getMetaBlock(int metaBlockIndex)
	{
		return metaBlocks.get(metaBlockIndex);
	}

	@Override
	public int getMetaBlockSize()
	{
		return metaBlocks.size();
	}

	protected T createBlockMeta(int blockMetaIndex)
	{
		try {
			T blockMeta = classBlockMeta.getConstructor(IMod.class).newInstance(iMod);

			onBlockMetaCreated(blockMeta, blockMetaIndex);

			return blockMeta;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public void onBlockMetaCreated(T blockMeta, int blockMetaIndex)
	{
		String unlocalizedName = getUnlocalizedName(blockMetaIndex);
		blockMeta.setHardness(1.5F);
		blockMeta.setResistance(10.0F);
		blockMeta.setStepSound(Block.soundTypeStone);
		blockMeta.setBlockName(unlocalizedName);
		blockMeta.setCreativeTab(creativeTab);
		GameRegistry.registerBlock(blockMeta, getClassItemBlockMeta(), unlocalizedName, iMod.getModId(), blockMeta);
	}

	public String getUnlocalizedName(int blockMetaIndex)
	{
		return "unionBlock" + name + blockMetaIndex;
	}

	@Override
	public int getBlockMetaIndex(int metaBlockIndex)
	{
		return metaBlockIndex / 15;
	}

	@Override
	public int getIndexInBlockMeta(int metaBlockIndex)
	{
		return metaBlockIndex % 15;
	}

	public IUnionBlock<IBlockMeta> toAPI()
	{
		return new IUnionBlockBridge();
	}

	private class IUnionBlockBridge implements IUnionBlock<IBlockMeta>
	{

		@Override
		public int getMetaBlockSize()
		{
			return UnionBlock.this.getMetaBlockSize();
		}

		@Override
		public IMetaBlock getMetaBlock(int metaBlockIndex)
		{
			return UnionBlock.this.getMetaBlock(metaBlockIndex);
		}

		@Override
		public int getIndexInBlockMeta(int metaBlockIndex)
		{
			return UnionBlock.this.getIndexInBlockMeta(metaBlockIndex);
		}

		@Override
		public int getBlockMetaSize()
		{
			return UnionBlock.this.getBlockMetaSize();
		}

		@Override
		public int getBlockMetaIndex(int metaBlockIndex)
		{
			return UnionBlock.this.getBlockMetaIndex(metaBlockIndex);
		}

		@Override
		public IBlockMeta getBlockMeta(int blockMetaIndex)
		{
			return UnionBlock.this.getBlockMeta(blockMetaIndex);
		}

	}

}
