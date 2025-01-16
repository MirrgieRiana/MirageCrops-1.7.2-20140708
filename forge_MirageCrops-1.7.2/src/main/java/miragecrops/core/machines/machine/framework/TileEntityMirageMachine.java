package miragecrops.core.machines.machine.framework;

import miragecrops.core.MirageCrops;
import miragecrops.framework.EnumNBTTypes;
import miragecrops.framework.IGuiProvider;
import miragecrops.framework.block.BlockPointer;
import mirrg.h.struct.Tuple1;
import mirrg.mir34.helpers.HelperSide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMirageMachine extends TileEntity implements IGuiProvider, ISetDirty, IInventoryName, ITileEntityMirageMachine
{

	@Override
	public void writeToNBT(NBTTagCompound p_145841_1_)
	{
		super.writeToNBT(p_145841_1_);

		if (customInventoryName != null) {
			p_145841_1_.setString("CustomName", customInventoryName);
		}

	}

	@Override
	public void readFromNBT(NBTTagCompound p_145839_1_)
	{
		super.readFromNBT(p_145839_1_);

		dirty = true;

		if (p_145839_1_.hasKey("CustomName", EnumNBTTypes.STRING.ordinal())) {
			customInventoryName = p_145839_1_.getString("CustomName");
		} else {
			customInventoryName = null;
		}

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

	///////////////////////// IGuiProvider /////////////////////////

	@Override
	@SideOnly(Side.CLIENT)
	public Tuple1<GuiMirageMachine> createGui(EntityPlayer player, World world, int x, int y, int z)
	{
		if (!hasGui()) return null;
		ContainerMirageMachine container = createContainer(player, world, x, y, z);
		return new Tuple1<GuiMirageMachine>(new GuiMirageMachine(container, getGuiTexture(container)));
	}

	@Override
	public ContainerMirageMachine createContainer(EntityPlayer player, World world, int x, int y, int z)
	{
		if (!hasGui()) return null;
		ContainerMirageMachine container = new ContainerMirageMachine(player, this, new BlockPointer(world, x, y, z));
		prepareContainerSlots(container);
		return container;
	}

	protected ResourceLocation getGuiTexture(ContainerMirageMachine container)
	{
		return MirageCrops.NULL_GUI_TEXTURE;
	}

	protected boolean hasGui()
	{
		return false;
	}

	protected void prepareContainerSlots(ContainerMirageMachine container)
	{

	}

	///////////////////////// IMarkDirty /////////////////////////

	public boolean dirty = true;

	@Override
	public void markDirty()
	{
		super.markDirty();
		dirty = true;
	}

	@Override
	public void setDirty()
	{
		markDirty();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		tick();

		if (dirty) {
			dirty = false;

			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			this.worldObj.updateLightByType(EnumSkyBlock.Block, this.xCoord, this.yCoord, this.zCoord);
			if ((!HelperSide.helper(this).isSimulating()) || HelperSide.helper(this).isRendering()) return;
		}
	}

	protected void tick()
	{

	}

	///////////////////////// IInventoryName /////////////////////////

	protected String customInventoryName;

	@Override
	public void setCustomInventoryName(String customInventoryName)
	{
		this.customInventoryName = customInventoryName;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return customInventoryName != null;
	}

	@Override
	public String getDefaultName()
	{
		return "container.mirageMachine";
	}

	@Override
	public String getInventoryName()
	{
		return hasCustomInventoryName() ? customInventoryName : getDefaultName();
	}

	@Override
	public String getLocalizedName()
	{
		return hasCustomInventoryName() ? getInventoryName() : StatCollector.translateToLocal(getInventoryName());
	}

	///////////////////////// ITileEntityMirageMachine /////////////////////////

	@Override
	public TileEntity getTileEntity()
	{
		return this;
	}

	@Override
	public void onBroken()
	{

	}

}
