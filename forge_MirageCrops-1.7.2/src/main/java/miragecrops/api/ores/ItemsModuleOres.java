package miragecrops.api.ores;

import java.util.Hashtable;

import miragecrops.api.framework.block.IBlockMeta;
import miragecrops.api.framework.block.IUnionBlock;
import miragecrops.api.framework.item.IItemMeta;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemsModuleOres
{

	public static Hashtable<String, ItemStack> materials = new Hashtable<String, ItemStack>();
	public static Hashtable<String, Item> itemToolCraftings = new Hashtable<String, Item>();

	public static IUnionBlock<IBlockMeta> unionBlockBlock;
	public static IUnionBlock<IBlockMeta> unionBlockOre;

	public static IItemMeta multiItemGem;
	public static IItemMeta multiItemIngot;
	public static IItemMeta multiItemNugget;
	public static IItemMeta multiItemPlate;
	public static IItemMeta multiItemRod;
	public static IItemMeta multiItemWire;
	public static IItemMeta multiItemDust;
	public static IItemMeta multiItemDustSmall;
	public static IItemMeta multiItemDustTiny;
	public static IItemMeta multiItemMachineHull;

}
