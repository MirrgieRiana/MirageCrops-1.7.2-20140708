package miragecrops.core.machines.framework;

import miragecrops.api.machines.RecipesFurnacefamily;
import miragecrops.framework.EnumNBTTypes;
import mirrg.mir34.helpers.HelperSide;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * has side and RecipeFurnacefamily
 */
public abstract class TileEntityMetaBlockFurnacefamily extends TileEntityFurnace
{

	public boolean dirty = true;
	private int side;

	public TileEntityMetaBlockFurnacefamily()
	{
		super();
	}

	public boolean isLit()
	{
		return this.furnaceBurnTime > 0;
	}

	public int getSide()
	{
		return side;
	}

	public void setSide(int side)
	{
		this.side = side;
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void updateEntity()
	{
		boolean flag = isLit();
		boolean flag1 = false;

		if (isLit())
		{
			this.furnaceBurnTime -= 1;
		}

		if (!(this.worldObj.isRemote))
		{
			if ((this.furnaceBurnTime == 0) && (canSmelt()))
			{
				this.furnaceBurnTime = getFuelValue(this.getStackInSlot(1));
				this.currentItemBurnTime = this.furnaceBurnTime;

				if (isLit())
				{
					flag1 = true;

					if (this.getStackInSlot(1) != null)
					{
						this.getStackInSlot(1).stackSize -= 1;

						if (this.getStackInSlot(1).stackSize == 0)
						{
							this.setInventorySlotContents(1, this.getStackInSlot(1).getItem().getContainerItem(this.getStackInSlot(1)));
						}
					}
				}
			}

			if ((isBurning()) && (canSmelt()))
			{
				this.furnaceCookTime += 4;

				if (this.furnaceCookTime >= 200)
				{
					this.furnaceCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			}
			else
			{
				this.furnaceCookTime = 0;
			}

		}

		if (flag != isLit()) {
			dirty = true;
		}

		if (dirty) {
			dirty = false;

			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			this.worldObj.updateLightByType(EnumSkyBlock.Block, this.xCoord, this.yCoord, this.zCoord);
			if ((!HelperSide.helper(this).isSimulating()) || HelperSide.helper(this).isRendering()) return;
		}
	}

	protected int getFuelValue(ItemStack itemStack)
	{
		return TileEntityFurnace.getItemBurnTime(itemStack);
	}

	protected boolean canSmelt()
	{
		if (this.getStackInSlot(0) == null)
		{
			return false;

		}

		ItemStack itemstack = getRecipes().copyOutputIfMatch(
			this.getStackInSlot(0).getItem(),
			this.getStackInSlot(0).getItemDamage());
		if (itemstack == null) return false;
		if (this.getStackInSlot(2) == null) return true;
		if (!(this.getStackInSlot(2).isItemEqual(itemstack))) return false;
		int result = this.getStackInSlot(2).stackSize + itemstack.stackSize;
		return ((result <= getInventoryStackLimit()) && (result <= this.getStackInSlot(2).getMaxStackSize()));
	}

	@Override
	public void smeltItem()
	{
		if (!(canSmelt()))
			return;
		ItemStack itemstack = getRecipes().copyOutputIfMatch(
			this.getStackInSlot(0).getItem(),
			this.getStackInSlot(0).getItemDamage());

		if (this.getStackInSlot(2) == null)
		{
			this.setInventorySlotContents(2, itemstack.copy());
		}
		else if (this.getStackInSlot(2).getItem() == itemstack.getItem())
		{
			this.getStackInSlot(2).stackSize += itemstack.stackSize;
		}

		this.getStackInSlot(0).stackSize -= 1;

		if (this.getStackInSlot(0).stackSize > 0)
			return;
		this.setInventorySlotContents(0, null);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);

		if (nbtTagCompound.hasKey("Side", EnumNBTTypes.INT.ordinal())) {
			setSide(nbtTagCompound.getInteger("Side"));
		} else {
			setSide(ForgeDirection.SOUTH.ordinal());
		}

		dirty = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);

		nbtTagCompound.setInteger("Side", getSide());
	}

	protected abstract RecipesFurnacefamily getRecipes();

	protected String customInventoryName;

	@Override
	public boolean hasCustomInventoryName()
	{
		return ((this.customInventoryName != null) && (this.customInventoryName.length() > 0));
	}

	@Override
	public void func_145951_a(String customInventoryName)
	{
		this.customInventoryName = customInventoryName;
	}

	public String getDefaultInventoryName()
	{
		return "container.furnace";
	}

	@Override
	public final String getInventoryName()
	{
		return ((hasCustomInventoryName()) ? this.customInventoryName : getDefaultInventoryName());
	}

}
