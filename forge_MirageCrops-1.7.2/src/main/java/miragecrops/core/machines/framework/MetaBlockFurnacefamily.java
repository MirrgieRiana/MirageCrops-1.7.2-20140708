package miragecrops.core.machines.framework;

import java.util.Random;

import miragecrops.api.machines.IMiragePipe;
import miragecrops.api.machines.IMiragePipeProvider;
import miragecrops.api.machines.MiragePipes;
import miragecrops.core.MirageCrops;
import miragecrops.framework.IGuiProvider;
import miragecrops.framework.block.BlockMeta;
import miragecrops.framework.block.container.MetaBlockContainer;
import mirrg.h.struct.Tuple1;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * has side, lit
 */
public abstract class MetaBlockFurnacefamily extends MetaBlockContainer implements IGuiProvider, IMiragePipeProvider
{

	private static boolean blockBreakEventLocked;

	public MetaBlockFurnacefamily(BlockMeta blockMeta, int metaId)
	{
		super(blockMeta, metaId);
	}

	protected boolean isLit(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		TileEntityMetaBlockFurnacefamily localTileEntityFurnace = (TileEntityMetaBlockFurnacefamily) iBlockAccess.getTileEntity(x, y, z);

		if (localTileEntityFurnace != null) {
			return localTileEntityFurnace.isLit();
		}

		return false;
	}

	protected void setSide(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		TileEntityMetaBlockFurnacefamily localTileEntityFurnace = (TileEntityMetaBlockFurnacefamily) iBlockAccess.getTileEntity(x, y, z);

		if (localTileEntityFurnace != null) {
			localTileEntityFurnace.setSide(side);
		}
	}

	protected int getSide(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		TileEntityMetaBlockFurnacefamily localTileEntityFurnace = (TileEntityMetaBlockFurnacefamily) iBlockAccess.getTileEntity(x, y, z);

		if (localTileEntityFurnace != null) {
			return localTileEntityFurnace.getSide();
		}

		return ForgeDirection.SOUTH.ordinal();
	}

	public String iconNameLit;
	public String iconNameSide;
	public String iconNameTop;
	public String iconNameBottom;
	protected IIcon iconLit;
	protected IIcon iconSide;
	protected IIcon iconTop;
	protected IIcon iconBottom;

	public int getSideInInventory()
	{
		return ForgeDirection.SOUTH.ordinal();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == 1) return this.iconTop;
		if (side == 0) return this.iconBottom;

		if (side == getSideInInventory()) return this.blockIcon;
		return this.iconSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		if (side == 1) return this.iconTop;
		if (side == 0) return this.iconBottom;

		if (side == getSide(iBlockAccess, x, y, z)) {
			if (isLit(iBlockAccess, x, y, z)) {
				return this.iconLit;
			} else {
				return this.blockIcon;
			}
		}

		return this.iconSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iIconRegister)
	{
		super.registerBlockIcons(iIconRegister);
		this.iconLit = iIconRegister.registerIcon(iconNameLit);
		this.iconSide = iIconRegister.registerIcon(iconNameSide);
		this.iconTop = iIconRegister.registerIcon(iconNameTop);
		this.iconBottom = iIconRegister.registerIcon(iconNameBottom);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom)
	{
		if (isLit(paramWorld, paramInt1, paramInt2, paramInt3)) {

			int i = getSide(paramWorld, paramInt1, paramInt2, paramInt3);

			float f1 = paramInt1 + 0.5F;
			float f2 = paramInt2 + 0.0F + paramRandom.nextFloat() * 6.0F / 16.0F;
			float f3 = paramInt3 + 0.5F;
			float f4 = 0.52F;
			float f5 = paramRandom.nextFloat() * 0.6F - 0.3F;

			if (i == 4) {
				paramWorld.spawnParticle("smoke", f1 - f4, f2, f3 + f5, 0.0D, 0.0D, 0.0D);
				paramWorld.spawnParticle("flame", f1 - f4, f2, f3 + f5, 0.0D, 0.0D, 0.0D);
			} else if (i == 5) {
				paramWorld.spawnParticle("smoke", f1 + f4, f2, f3 + f5, 0.0D, 0.0D, 0.0D);
				paramWorld.spawnParticle("flame", f1 + f4, f2, f3 + f5, 0.0D, 0.0D, 0.0D);
			} else if (i == 2) {
				paramWorld.spawnParticle("smoke", f1 + f5, f2, f3 - f4, 0.0D, 0.0D, 0.0D);
				paramWorld.spawnParticle("flame", f1 + f5, f2, f3 - f4, 0.0D, 0.0D, 0.0D);
			} else if (i == 3) {
				paramWorld.spawnParticle("smoke", f1 + f5, f2, f3 + f4, 0.0D, 0.0D, 0.0D);
				paramWorld.spawnParticle("flame", f1 + f5, f2, f3 + f4, 0.0D, 0.0D, 0.0D);
			}

		}
	}

	@Override
	public void onBlockPlacedBy(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack)
	{
		int i = MathHelper.floor_double(paramEntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;

		TileEntityMetaBlockFurnacefamily tileEntity = (TileEntityMetaBlockFurnacefamily) paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);

		if (i == 0) setSide(paramWorld, paramInt1, paramInt2, paramInt3, 2);
		if (i == 1) setSide(paramWorld, paramInt1, paramInt2, paramInt3, 5);
		if (i == 2) setSide(paramWorld, paramInt1, paramInt2, paramInt3, 3);
		if (i == 3) setSide(paramWorld, paramInt1, paramInt2, paramInt3, 4);

		if (paramItemStack.hasDisplayName()) {
			tileEntity.func_145951_a(paramItemStack.getDisplayName());
		}
	}

	@Override
	public void onBlockAdded(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
	{
		super.onBlockAdded(paramWorld, paramInt1, paramInt2, paramInt3);
		initSide(paramWorld, paramInt1, paramInt2, paramInt3);
	}

	private void initSide(World p_149930_1_, int p_149930_2_, int p_149930_3_, int p_149930_4_)
	{
		if (p_149930_1_.isRemote) {
			return;
		}

		Block localBlock1 = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ - 1);
		Block localBlock2 = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ + 1);
		Block localBlock3 = p_149930_1_.getBlock(p_149930_2_ - 1, p_149930_3_, p_149930_4_);
		Block localBlock4 = p_149930_1_.getBlock(p_149930_2_ + 1, p_149930_3_, p_149930_4_);

		int i = 3;
		if ((localBlock1.func_149730_j()) && (!(localBlock2.func_149730_j()))) i = 3;
		if ((localBlock2.func_149730_j()) && (!(localBlock1.func_149730_j()))) i = 2;
		if ((localBlock3.func_149730_j()) && (!(localBlock4.func_149730_j()))) i = 5;
		if ((localBlock4.func_149730_j()) && (!(localBlock3.func_149730_j()))) i = 4;
		setSide(p_149930_1_, p_149930_2_, p_149930_3_, p_149930_4_, i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Tuple1<GuiContainer> createGui(EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null) return null;
		if (!(tileEntity instanceof TileEntityMetaBlockFurnacefamily)) return null;

		return new Tuple1<GuiContainer>(new GuiMetaBlockFurnacefamily(MirageCrops.guiHandler.iMod, this, player, world, x, y, z, (TileEntityMetaBlockFurnacefamily) tileEntity));
	}

	@Override
	public Container createContainer(EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null) return null;
		if (!(tileEntity instanceof TileEntityMetaBlockFurnacefamily)) return null;

		return new ContainerMetaBlockFurnacefamily(this, player, world, x, y, z, (TileEntityMetaBlockFurnacefamily) tileEntity);
	}

	@Override
	public boolean onBlockActivated(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
	{
		if (paramWorld.isRemote) {
			return true;
		}

		paramEntityPlayer.openGui(MirageCrops.guiHandler.iMod, MirageCrops.guiHandler.guiId, paramWorld, paramInt1, paramInt2, paramInt3);

		return true;
	}

	@Override
	public void breakBlock(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4)
	{
		if (!(blockBreakEventLocked)) {
			TileEntityMetaBlockFurnacefamily localTileEntityFurnace = (TileEntityMetaBlockFurnacefamily) paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
			if (localTileEntityFurnace != null) {
				for (int i = 0; i < localTileEntityFurnace.getSizeInventory(); ++i) {
					ItemStack localItemStack = localTileEntityFurnace.getStackInSlot(i);
					if (localItemStack != null) {
						dropItemStack(paramWorld, paramInt1, paramInt2, paramInt3, localItemStack);
					}
				}
				paramWorld.func_147453_f(paramInt1, paramInt2, paramInt3, paramBlock);
			}
		}
		super.breakBlock(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
	}

	protected void dropItemStack(World paramWorld, int paramInt1, int paramInt2, int paramInt3, ItemStack localItemStack)
	{
		float f1 = paramWorld.rand.nextFloat() * 0.8F + 0.1F;
		float f2 = paramWorld.rand.nextFloat() * 0.8F + 0.1F;
		float f3 = paramWorld.rand.nextFloat() * 0.8F + 0.1F;

		while (localItemStack.stackSize > 0) {
			int j = paramWorld.rand.nextInt(21) + 10;
			if (j > localItemStack.stackSize) j = localItemStack.stackSize;
			localItemStack.stackSize -= j;

			EntityItem localEntityItem = new EntityItem(paramWorld, paramInt1 + f1, paramInt2 + f2, paramInt3 + f3, new ItemStack(localItemStack.getItem(), j, localItemStack.getItemDamage()));

			if (localItemStack.hasTagCompound()) {
				localEntityItem.getEntityItem().setTagCompound((NBTTagCompound) localItemStack.getTagCompound().copy());
			}

			float f4 = 0.05F;
			localEntityItem.motionX = ((float) paramWorld.rand.nextGaussian() * f4);
			localEntityItem.motionY = ((float) paramWorld.rand.nextGaussian() * f4 + 0.2F);
			localEntityItem.motionZ = ((float) paramWorld.rand.nextGaussian() * f4);
			paramWorld.spawnEntityInWorld(localEntityItem);
		}
	}

	@Override
	public int getComparatorInputOverride(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
	{
		return Container.calcRedstoneFromInventory((IInventory) paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3));
	}

	@Override
	public abstract TileEntity createNewTileEntity(World paramWorld, int paramInt);

	@Override
	public IMiragePipe getMiragePipe(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return MiragePipes.mirageAlloy;
	}

}
