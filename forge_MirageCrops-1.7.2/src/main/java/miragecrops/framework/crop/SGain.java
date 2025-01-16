package miragecrops.framework.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import miragecrops.api.framework.cropgainrecipe.ICropGainRecipe.ICropGainRecipeAdder;
import miragecrops.api.framework.item.IMetaItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SGain
{
	public static final Nothing nothing = new Nothing();

	public static Normal normal(Block param)
	{
		return new Normal(new ItemStack(param));
	}

	public static Normal normal(Item param)
	{
		return new Normal(new ItemStack(param));
	}

	public static Normal normal(IMetaItem param)
	{
		return new Normal(param.createItemStack());
	}

	public static Normal normal(ItemStack param)
	{
		return new Normal(param);
	}

	public static Random random(IGainProvider... iGains)
	{
		return new Random(iGains);
	}

	public static Random random(Item... items)
	{
		IGain[] iGains = new IGain[items.length];

		for (int i = 0; i < items.length; i++) {
			iGains[i] = SGain.normal(items[i]);
		}

		return new Random(iGains);
	}

	public static Random random(ItemStack... itemStacks)
	{
		IGain[] iGains = new IGain[itemStacks.length];

		for (int i = 0; i < itemStacks.length; i++) {
			iGains[i] = SGain.normal(itemStacks[i]);
		}

		return new Random(iGains);
	}

	// ---------------------------------------------------------------

	private static class Nothing implements IGain
	{

		@Override
		public ItemStack getGain(CropCard cropCard, ICropTile crop)
		{
			return null;
		}

		@Override
		public ItemStack getGain()
		{
			return null;
		}

		@Override
		public void addRecipe(ICropGainRecipeAdder iRecipeAdder, CropCard cropCard, ItemStack debugCropSeed)
		{

		}

	}

	private static class Normal implements IGain
	{

		protected final ItemStack itemStack;

		public Normal(ItemStack param)
		{
			this.itemStack = param;
		}

		@Override
		public ItemStack getGain(CropCard cropCard, ICropTile crop)
		{
			return itemStack.copy();
		}

		@Override
		public ItemStack getGain()
		{
			return itemStack.copy();
		}

		@Override
		public void addRecipe(ICropGainRecipeAdder iRecipeAdder, CropCard cropCard, ItemStack debugCropSeed)
		{
			iRecipeAdder.addRecipe(debugCropSeed, itemStack);
		}

	}

	private static class Random implements IGainProvider
	{

		protected final IGainProvider[] iGainProviders;

		public Random(IGainProvider... iGainProviders)
		{
			this.iGainProviders = iGainProviders;
		}

		@Override
		public ItemStack getGain(CropCard cropCard, ICropTile crop)
		{
			return iGainProviders[crop.getWorld().rand.nextInt(iGainProviders.length)].getGain(cropCard, crop);
		}

		@Override
		public void addRecipe(ICropGainRecipeAdder iRecipeAdder, CropCard cropCard, ItemStack debugCropSeed)
		{
			for (IGainProvider iGainProvider : iGainProviders) {
				iGainProvider.addRecipe(iRecipeAdder, cropCard, debugCropSeed);
			}
		}

	}

}
