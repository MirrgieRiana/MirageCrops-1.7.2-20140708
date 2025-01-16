package miragecrops.framework.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class MetaBlock extends MetaBlockImpl
{

	protected IDrop dropsOverride;
	protected String description;

	public MetaBlock(BlockMeta blockMeta, int metaId)
	{
		super(blockMeta, metaId);
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean isAdvancedItemTooltips)
	{
		if (description != null) info.add(description);
		super.addInformation(itemStack, player, info, isAdvancedItemTooltips);
		if (isAdvancedItemTooltips) info.add("UnlocalizedName: " + getUnlocalizedNamePlain());

		if (isAdvancedItemTooltips) {
			String[] oreNames = OreDictionary.getOreNames();

			for (String oreName : oreNames) {
				ArrayList<ItemStack> ores = OreDictionary.getOres(oreName);
				for (ItemStack ore : ores) {
					if (OreDictionary.itemMatches(ore, itemStack, false)) {
						info.add("OreName: " + oreName);
						break;
					}
				}
			}

		}
	}

	public void setBlockHardness(float blockHardness)
	{
		this.blockHardness = blockHardness;
	}

	public void setHarvestLevel(String harvestTool, int harvestLevel)
	{
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
	}

	public void setDrops()
	{
		this.dropsOverride = null;
	}

	public void setDrops(ItemStack itemStack)
	{
		this.dropsOverride = new DropItemStack(itemStack.copy());
	}

	/**
	 * itemStackの個数のアイテムを落とし、フォーチュンも有効。
	 */
	public void setDropsFortune(ItemStack itemStack)
	{
		this.dropsOverride = new DropItemStackFortune(itemStack.copy());
	}

	public void setDrops(IDrop dropsOverride)
	{
		this.dropsOverride = dropsOverride;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		if (dropsOverride != null) {
			return dropsOverride.getDrops(world, x, y, z, meta, fortune);
		} else {
			return super.getDrops(world, x, y, z, meta, fortune);
		}
	}

	public static int quantityDroppedWithBonus(int quantity, int bonus, Random random)
	{
		if (bonus > 0) {
			int j = random.nextInt(bonus + 2) - 1;

			if (j < 0) {
				j = 0;
			}

			return (quantity * (j + 1));
		}

		return quantity;
	}

	public static interface IDrop
	{

		public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune);

	}

	public static class DropItemStack implements IDrop
	{

		private final ItemStack itemStack;

		public DropItemStack(ItemStack itemStack)
		{
			this.itemStack = itemStack;
		}

		@Override
		public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
		{
			ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			list.add(itemStack.copy());
			return list;
		}

	}

	public static class DropItemStackFortune implements IDrop
	{

		private final ItemStack itemStack;

		public DropItemStackFortune(ItemStack itemStack)
		{
			this.itemStack = itemStack;
		}

		@Override
		public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
		{
			ArrayList ret = new ArrayList();

			int count = quantityDroppedWithBonus(itemStack.stackSize, fortune, world.rand);
			for (int i = 0; i < count; ++i)
			{
				Item item = itemStack.getItem();
				if (item == null)
					continue;
				ret.add(new ItemStack(item, 1, itemStack.getItemDamage()));
			}

			return ret;
		}

	}

}
