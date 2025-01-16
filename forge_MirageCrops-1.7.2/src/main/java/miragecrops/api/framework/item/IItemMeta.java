package miragecrops.api.framework.item;

import net.minecraft.item.Item;

public interface IItemMeta
{

	public Item getItem();

	public IMetaItem getMetaItem(int meta);

}
