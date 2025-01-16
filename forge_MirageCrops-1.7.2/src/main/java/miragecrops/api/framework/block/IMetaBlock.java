package miragecrops.api.framework.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IMetaBlock
{

	public Block getBlock();

	public int getMetaId();

	public ItemStack createItemStack();

	public ItemStack createItemStack(int amount);

	public String getUnlocalizedNamePlain();

}
