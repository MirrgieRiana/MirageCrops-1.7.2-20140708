package miragecrops.core.machines.machine.framework;

import miragecrops.framework.EnumNBTTypes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Inventory implements IInventory, ISetDirty
{

	public ItemStack[] itemStacks;
	protected ISetDirty parent;

	public Inventory(ISetDirty parent, int size)
	{
		this.parent = parent;
		itemStacks = new ItemStack[size];
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
	public ItemStack decrStackSize(int arg0, int arg1)
	{
		ItemStack itemStack = itemStacks[arg0].copy();
		itemStacks[arg0].stackSize -= arg1;
		itemStack.stackSize = arg1;
		if (itemStacks[arg0].stackSize <= 0) {
			itemStacks[arg0] = null;
		}
		return itemStack;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public int getSizeInventory()
	{
		return itemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int arg0)
	{
		return itemStacks[arg0];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int arg0)
	{
		ItemStack itemStack = itemStacks[arg0];
		itemStacks[arg0] = null;
		return itemStack;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public String getInventoryName()
	{
		return "container.inventory";
	}

	@Override
	public boolean isItemValidForSlot(int arg0, ItemStack arg1)
	{
		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer arg0)
	{
		return false;
	}

	@Override
	public void setInventorySlotContents(int arg0, ItemStack arg1)
	{
		itemStacks[arg0] = arg1;
	}

	public void dropAll(World worldObj, float xCoord, float yCoord, float zCoord)
	{
		for (int i = 0; i < itemStacks.length; i++) {
			ItemStack itemStack = itemStacks[i];
			if (itemStack != null) {
				dropItemStack(worldObj, xCoord, yCoord, zCoord, itemStack);
			}
		}
	}

	protected static void dropItemStack(World worldObj, float xCoord, float yCoord, float zCoord, ItemStack itemStack)
	{
		float f1 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
		float f2 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
		float f3 = worldObj.rand.nextFloat() * 0.8F + 0.1F;

		while (itemStack.stackSize > 0) {
			int j = worldObj.rand.nextInt(21) + 10;
			if (j > itemStack.stackSize) j = itemStack.stackSize;
			itemStack.stackSize -= j;

			EntityItem entityItem = new EntityItem(worldObj, xCoord + f1, yCoord + f2, zCoord + f3,
				new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));

			if (itemStack.hasTagCompound()) {
				entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
			}

			float f4 = 0.05F;
			entityItem.motionX = ((float) worldObj.rand.nextGaussian() * f4);
			entityItem.motionY = ((float) worldObj.rand.nextGaussian() * f4 + 0.2F);
			entityItem.motionZ = ((float) worldObj.rand.nextGaussian() * f4);
			worldObj.spawnEntityInWorld(entityItem);
		}

	}

	public void writeToNBT(NBTTagCompound tag)
	{
		for (int i = 0; i < itemStacks.length; i++) {
			if (itemStacks[i] != null) {
				NBTTagCompound tag2 = new NBTTagCompound();
				itemStacks[i].writeToNBT(tag2);
				tag.setTag("Slot_" + i, tag2);
			}
		}
	}

	public void readFromNBT(NBTTagCompound tag)
	{
		for (int i = 0; i < itemStacks.length; i++) {
			if (tag.hasKey("Slot_" + i, EnumNBTTypes.COMPOUND.ordinal())) {
				NBTTagCompound tag2 = tag.getCompoundTag("Slot_" + i);
				itemStacks[i] = ItemStack.loadItemStackFromNBT(tag2);
			}
		}
	}

	@Override
	public void setDirty()
	{
		parent.setDirty();
	}

	@Override
	public void markDirty()
	{
		parent.setDirty();
	}

}
