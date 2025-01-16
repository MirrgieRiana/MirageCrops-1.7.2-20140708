package miragecrops.api.framework.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IMetaItem
{

	public Item getItem();

	public int getMetaId();

	public ItemStack createItemStack();

	public ItemStack createItemStack(int amount);

	public String getUnlocalizedNamePlain();

}
