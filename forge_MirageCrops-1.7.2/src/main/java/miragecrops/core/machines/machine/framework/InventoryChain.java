package miragecrops.core.machines.machine.framework;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryChain implements IInventory
{

	protected ArrayList<Inventory> inventories = new ArrayList<Inventory>();
	protected IInventoryName name;
	protected int inventoryStackLimit = 64;
	protected ISetDirty parent;

	public InventoryChain(ISetDirty parent, IInventoryName name)
	{
		this.parent = parent;
		this.name = name;
	}

	public void add(Inventory inventory)
	{
		inventories.add(inventory);
	}

	/**
	 * @return new int[] { inventoryIndex, localSlotIndex in inventory }
	 */
	public int[] getAddress(int globalSlotIndex)
	{
		if (globalSlotIndex < 0) return null;

		for (int i = 0; i < inventories.size(); i++) {
			Inventory inventory = inventories.get(i);

			if (globalSlotIndex < inventory.getSizeInventory()) {
				return new int[] {
					i,
					globalSlotIndex,
				};
			} else {
				globalSlotIndex -= inventory.getSizeInventory();
			}
		}

		return null;
	}

	@Override
	public int getSizeInventory()
	{
		int size = 0;
		for (Inventory inventory : inventories) {
			size += inventory.getSizeInventory();
		}
		return size;
	}

	@Override
	public ItemStack getStackInSlot(int globalSlotIndex)
	{
		int[] address = getAddress(globalSlotIndex);
		return inventories.get(address[0]).getStackInSlot(address[1]);
	}

	@Override
	public ItemStack decrStackSize(int globalSlotIndex, int amount)
	{
		int[] address = getAddress(globalSlotIndex);
		return inventories.get(address[0]).decrStackSize(address[1], amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int globalSlotIndex)
	{
		int[] address = getAddress(globalSlotIndex);
		return inventories.get(address[0]).getStackInSlotOnClosing(address[1]);
	}

	@Override
	public void setInventorySlotContents(int globalSlotIndex, ItemStack var2)
	{
		int[] address = getAddress(globalSlotIndex);
		inventories.get(address[0]).setInventorySlotContents(address[1], var2);
	}

	@Override
	public String getInventoryName()
	{
		return name.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return name.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return inventoryStackLimit;
	}

	@Override
	public void markDirty()
	{
		parent.setDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return true;
	}

	@Override
	public void openInventory()
	{

	}

	@Override
	public void closeInventory()
	{

	}

	@Override
	public boolean isItemValidForSlot(int globalSlotIndex, ItemStack var2)
	{
		int[] address = getAddress(globalSlotIndex);
		return inventories.get(address[0]).isItemValidForSlot(address[1], var2);
	}

	public int[] getSlotsOfInventory(Inventory... inventoryList)
	{
		ArrayList<Integer> slots = new ArrayList<Integer>();

		int size = 0;
		for (Inventory inventory : inventories) {
			for (Inventory inventory2 : inventoryList) {
				if (inventory == inventory2) {
					for (int i = 0; i < inventory.getSizeInventory(); i++) {
						slots.add(size + i);
					}
				}
			}
			size += inventory.getSizeInventory();
		}

		int[] slots2 = new int[slots.size()];

		for (int i = 0; i < slots2.length; i++) {
			slots2[i] = slots.get(i);
		}

		return slots2;
	}

	public int getInventoryIndex(Inventory inventory)
	{
		return inventories.indexOf(inventory);
	}

	public Inventory getInventoryFromGlobalSlotIndex(int globalSlotIndex)
	{
		return inventories.get(getAddress(globalSlotIndex)[0]);
	}

}
