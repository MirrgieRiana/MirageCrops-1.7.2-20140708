package miragecrops.framework.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import miragecrops.api.framework.block.IMetaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.util.RotationHelper;
import net.minecraftforge.event.ForgeEventFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaBlockImpl implements IMetaBlock
{

	private final BlockMeta blockMeta;
	private final int metaId;
	protected String unlocalizedName;
	protected String textureName;
	protected ItemStack containerItem;
	protected IIcon blockIcon;
	protected float blockHardness;
	protected boolean isTileProvider = this instanceof ITileEntityProvider;
	protected String harvestTool;
	protected int harvestLevel;

	public MetaBlockImpl(BlockMeta blockMeta, int metaId)
	{
		this.blockMeta = blockMeta;
		this.metaId = metaId;
	}

	public int getId()
	{
		return Block.getIdFromBlock(blockMeta);
	}

	@Override
	public BlockMeta getBlock()
	{
		return blockMeta;
	}

	@Override
	public ItemStack createItemStack()
	{
		return createItemStack(1);
	}

	@Override
	public ItemStack createItemStack(int amount)
	{
		return new ItemStack(getBlock(), amount, metaId);
	}

	@Override
	public String getUnlocalizedNamePlain()
	{
		return this.unlocalizedName;
	}

	public Item getItem()
	{
		return createItemStack().getItem();
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iIconRegister)
	{
		this.blockIcon = iIconRegister.registerIcon(getTextureName());
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs p_149666_2_, List list)
	{
		list.add(new ItemStack(item, 1, getMetaId()));
	}

	@Override
	public int getMetaId()
	{
		return metaId;
	}

	public String getUnlocalizedName()
	{
		return "tile." + unlocalizedName;
	}

	public void setUnlocalizedName(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
	}

	public String getTextureName()
	{
		return ((this.textureName == null) ? "MISSING_ICON_BLOCK_" + getId() + "_" + getUnlocalizedName() : this.textureName);
	}

	public void setTextureName(String textureName)
	{
		this.textureName = textureName;
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean isAdvancedItemTooltips)
	{

	}

	// -----------------------------------------------------------------

	protected ThreadLocal<EntityPlayer> harvesters = new ThreadLocal();

	public MapColor getMapColor(int meta)
	{
		return blockMeta.getMaterial().getMaterialMapColor();
	}

	public boolean getBlocksMovement(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return (!(blockMeta.getMaterial().blocksMovement()));
	}

	public float getBlockHardness(World world, int x, int y, int z)
	{
		return this.blockHardness;
	}

	@SideOnly(Side.CLIENT)
	public int getMixedBrightnessForBlock(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		Block block = iBlockAccess.getBlock(x, y, z);
		int l = iBlockAccess.getLightBrightnessForSkyBlocks(x, y, z, block.getLightValue(iBlockAccess, x, y, z));

		if ((l == 0) && (block instanceof BlockSlab))
		{
			--y;
			block = iBlockAccess.getBlock(x, y, z);
			return iBlockAccess.getLightBrightnessForSkyBlocks(x, y, z, block.getLightValue(iBlockAccess, x, y, z));

		}

		return l;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return true;
	}

	public boolean isBlockSolid(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return iBlockAccess.getBlock(x, y, z).getMaterial().isSolid();
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return blockIcon;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return getIcon(side, getMetaId());
	}

	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity)
	{
		AxisAlignedBB axisalignedbb1 = getCollisionBoundingBoxFromPool(world, x, y, z);

		if ((axisalignedbb1 == null) || (!(axisAlignedBB.intersectsWith(axisalignedbb1))))
			return;
		list.add(axisalignedbb1);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getAABBPool().getAABB(
			x + blockMeta.getMinX(), y + blockMeta.getMinY(), z + blockMeta.getMinZ(),
			x + blockMeta.getMaxX(), y + blockMeta.getMaxY(), z + blockMeta.getMaxZ());
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getAABBPool().getAABB(
			x + blockMeta.getMinX(), y + blockMeta.getMinY(), z + blockMeta.getMinZ(),
			x + blockMeta.getMaxX(), y + blockMeta.getMaxY(), z + blockMeta.getMaxZ());
	}

	public boolean canCollideCheck(int meta, boolean p_149678_2_)
	{
		return blockMeta.isCollidable();
	}

	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		if (hasTileEntity(meta)) {
			world.removeTileEntity(x, y, z);
		}
	}

	public Item getItemDropped(int meta, Random random, int fortune)
	{
		return Item.getItemFromBlock(blockMeta);
	}

	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z)
	{
		return ForgeHooks.blockStrength(blockMeta, player, world, x, y, z);
	}

	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float explosionDropRate, int fortune)
	{
		if (world.isRemote)
			return;
		ArrayList<ItemStack> items = getDrops(world, x, y, z, meta, fortune);
		explosionDropRate = ForgeEventFactory.fireBlockHarvesting(items, world, blockMeta, x, y, z, meta, fortune, explosionDropRate, false, harvesters.get());

		for (ItemStack item : items)
		{
			if (world.rand.nextFloat() <= explosionDropRate)
			{
				dropBlockAsItem(world, x, y, z, item);
			}
		}
	}

	protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack itemStack)
	{
		if ((world.isRemote) || (!(world.getGameRules().getGameRuleBooleanValue("doTileDrops"))))
			return;
		float f = 0.7F;
		double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
		double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
		double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
		EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, itemStack);
		entityitem.delayBeforeCanPickup = 10;
		world.spawnEntityInWorld(entityitem);
	}

	public void dropXpOnBlockBreak(World world, int x, int y, int z, int expToDrop)
	{
		while ((!(world.isRemote)) &&

			(expToDrop > 0))
		{
			int i1 = EntityXPOrb.getXPSplit(expToDrop);
			expToDrop -= i1;
			world.spawnEntityInWorld(new EntityXPOrb(world, x + 0.5D, y + 0.5D, z + 0.5D, i1));
		}
	}

	public int damageDropped(int meta)
	{
		return meta;
	}

	/**
	 * Checks if a vector is within the Y and Z bounds of the block.
	 */
	private boolean isVecInsideYZBounds(Vec3 p_149654_1_)
	{
		return p_149654_1_ == null ? false : p_149654_1_.yCoord >= blockMeta.getMinY() && p_149654_1_.yCoord <= blockMeta.getMaxY() && p_149654_1_.zCoord >= blockMeta.getMinZ() && p_149654_1_.zCoord <= blockMeta.getMaxZ();
	}

	/**
	 * Checks if a vector is within the X and Z bounds of the block.
	 */
	private boolean isVecInsideXZBounds(Vec3 p_149687_1_)
	{
		return p_149687_1_ == null ? false : p_149687_1_.xCoord >= blockMeta.getMinX() && p_149687_1_.xCoord <= blockMeta.getMaxX() && p_149687_1_.zCoord >= blockMeta.getMinZ() && p_149687_1_.zCoord <= blockMeta.getMaxZ();
	}

	/**
	 * Checks if a vector is within the X and Y bounds of the block.
	 */
	private boolean isVecInsideXYBounds(Vec3 p_149661_1_)
	{
		return p_149661_1_ == null ? false : p_149661_1_.xCoord >= blockMeta.getMinX() && p_149661_1_.xCoord <= blockMeta.getMaxX() && p_149661_1_.yCoord >= blockMeta.getMinY() && p_149661_1_.yCoord <= blockMeta.getMaxY();
	}

	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		setBlockBoundsBasedOnState(world, x, y, z);
		par5Vec3 = par5Vec3.addVector(-x, -y, -z);
		par6Vec3 = par6Vec3.addVector(-x, -y, -z);
		Vec3 vec32 = par5Vec3.getIntermediateWithXValue(par6Vec3, blockMeta.getMinX());
		Vec3 vec33 = par5Vec3.getIntermediateWithXValue(par6Vec3, blockMeta.getMaxX());
		Vec3 vec34 = par5Vec3.getIntermediateWithYValue(par6Vec3, blockMeta.getMinY());
		Vec3 vec35 = par5Vec3.getIntermediateWithYValue(par6Vec3, blockMeta.getMaxY());
		Vec3 vec36 = par5Vec3.getIntermediateWithZValue(par6Vec3, blockMeta.getMinZ());
		Vec3 vec37 = par5Vec3.getIntermediateWithZValue(par6Vec3, blockMeta.getMaxZ());

		if (!this.isVecInsideYZBounds(vec32))
		{
			vec32 = null;
		}

		if (!this.isVecInsideYZBounds(vec33))
		{
			vec33 = null;
		}

		if (!this.isVecInsideXZBounds(vec34))
		{
			vec34 = null;
		}

		if (!this.isVecInsideXZBounds(vec35))
		{
			vec35 = null;
		}

		if (!this.isVecInsideXYBounds(vec36))
		{
			vec36 = null;
		}

		if (!this.isVecInsideXYBounds(vec37))
		{
			vec37 = null;
		}

		Vec3 vec38 = null;

		if ((vec32 != null) && (((vec38 == null) || (par5Vec3.squareDistanceTo(vec32) < par5Vec3.squareDistanceTo(vec38)))))
		{
			vec38 = vec32;
		}

		if ((vec33 != null) && (((vec38 == null) || (par5Vec3.squareDistanceTo(vec33) < par5Vec3.squareDistanceTo(vec38)))))
		{
			vec38 = vec33;
		}

		if ((vec34 != null) && (((vec38 == null) || (par5Vec3.squareDistanceTo(vec34) < par5Vec3.squareDistanceTo(vec38)))))
		{
			vec38 = vec34;
		}

		if ((vec35 != null) && (((vec38 == null) || (par5Vec3.squareDistanceTo(vec35) < par5Vec3.squareDistanceTo(vec38)))))
		{
			vec38 = vec35;
		}

		if ((vec36 != null) && (((vec38 == null) || (par5Vec3.squareDistanceTo(vec36) < par5Vec3.squareDistanceTo(vec38)))))
		{
			vec38 = vec36;
		}

		if ((vec37 != null) && (((vec38 == null) || (par5Vec3.squareDistanceTo(vec37) < par5Vec3.squareDistanceTo(vec38)))))
		{
			vec38 = vec37;
		}

		if (vec38 == null)
		{
			return null;

		}

		byte b0 = -1;

		if (vec38 == vec32)
		{
			b0 = 4;
		}

		if (vec38 == vec33)
		{
			b0 = 5;
		}

		if (vec38 == vec34)
		{
			b0 = 0;
		}

		if (vec38 == vec35)
		{
			b0 = 1;
		}

		if (vec38 == vec36)
		{
			b0 = 2;
		}

		if (vec38 == vec37)
		{
			b0 = 3;
		}

		return new MovingObjectPosition(x, y, z, b0, vec38.addVector(x, y, z));
	}

	public boolean canReplace(World world, int x, int y, int z, int side, ItemStack itemStack)
	{
		return canPlaceBlockOnSide(world, x, y, z, side);
	}

	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
	{
		return canPlaceBlockAt(world, x, y, z);
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return world.getBlock(x, y, z).isReplaceable(world, x, y, z);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float x2, float y2, float z2)
	{
		return false;
	}

	public int onBlockPlaced(World world, int x, int y, int z, int side, float x2, float y2, float z2, int meta)
	{
		return meta;
	}

	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta)
	{
		return 16777215;
	}

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return 16777215;
	}

	public int isProvidingWeakPower(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return 0;
	}

	public int isProvidingStrongPower(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return 0;
	}

	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
	{
		player.addStat(net.minecraft.stats.StatList.mineBlockStatArray[getId()], 1);
		player.addExhaustion(0.025F);

		if ((canSilkHarvest(world, player, x, y, z, meta)) && (EnchantmentHelper.getSilkTouchModifier(player)))
		{
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			ItemStack itemstack = createStackedBlock(meta);

			if (itemstack != null)
			{
				items.add(itemstack);
			}

			ForgeEventFactory.fireBlockHarvesting(items, world, blockMeta, x, y, z, meta, 0, 1.0F, true, player);
			for (ItemStack is : items)
			{
				dropBlockAsItem(world, x, y, z, is);
			}
		}
		else
		{
			harvesters.set(player);
			int i1 = EnchantmentHelper.getFortuneModifier(player);
			blockMeta.dropBlockAsItem(world, x, y, z, meta, i1);
			harvesters.set(null);
		}
	}

	protected ItemStack createStackedBlock(int meta)
	{
		return createItemStack();
	}

	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return true;
	}

	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventParameter)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return getItem();
	}

	public int getDamageValue(World world, int x, int y, int z)
	{
		return damageDropped(world.getBlockMetadata(x, y, z));
	}

	public int getComparatorInputOverride(World world, int x, int y, int z, int side)
	{
		return 0;
	}

	public int getLightValue(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		Block block = iBlockAccess.getBlock(x, y, z);
		if (block != blockMeta)
		{
			return block.getLightValue(iBlockAccess, x, y, z);
		}
		return blockMeta.getLightValue();
	}

	public boolean isLadder(IBlockAccess iBlockAccess, int x, int y, int z, EntityLivingBase living)
	{
		return false;
	}

	public boolean isNormalCube(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return ((blockMeta.getMaterial().isOpaque()) && (blockMeta.renderAsNormalBlock()) && (!(blockMeta.canProvidePower())));
	}

	public boolean isSideSolid(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection side)
	{
		return isNormalCube(iBlockAccess, x, y, z);
	}

	public boolean isReplaceable(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return blockMeta.getMaterial().isReplaceable();
	}

	public boolean isBurning(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return false;
	}

	public boolean isAir(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return (blockMeta.getMaterial() == Material.air);
	}

	public boolean canHarvestBlock(EntityPlayer player, int meta)
	{
		return ForgeHooks.canHarvestBlock(blockMeta, player, meta);
	}

	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		return world.setBlockToAir(x, y, z);
	}

	public int getFlammability(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection side)
	{
		return Blocks.fire.getFlammability(blockMeta);
	}

	public boolean isFlammable(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection side)
	{
		return (getFlammability(iBlockAccess, x, y, z, side) > 0);
	}

	public int getFireSpreadSpeed(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection side)
	{
		return Blocks.fire.getEncouragement(blockMeta);
	}

	public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}

	public boolean hasTileEntity(int meta)
	{
		return this.isTileProvider;
	}

	public TileEntity createTileEntity(World world, int meta)
	{
		if (this.isTileProvider)
		{
			return ((ITileEntityProvider) this).createNewTileEntity(world, meta);
		}
		return null;
	}

	public int quantityDropped(int meta, int fortune, Random random)
	{
		return blockMeta.quantityDroppedWithBonus(fortune, random);
	}

	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		ArrayList ret = new ArrayList();

		int count = quantityDropped(meta, fortune, world.rand);
		for (int i = 0; i < count; ++i)
		{
			Item item = getItemDropped(meta, world.rand, fortune);
			if (item == null)
				continue;
			ret.add(new ItemStack(item, 1, damageDropped(meta)));
		}

		return ret;
	}

	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int meta)
	{
		blockMeta.silk_check_meta.set(Integer.valueOf(meta));
		boolean ret = blockMeta.canSilkHarvest();
		blockMeta.silk_check_meta.set(null);
		return ret;
	}

	public boolean canCreatureSpawn(EnumCreatureType creatureType, IBlockAccess iBlockAccess, int x, int y, int z)
	{
		int meta = iBlockAccess.getBlockMetadata(x, y, z);
		return isSideSolid(iBlockAccess, x, y, z, ForgeDirection.UP);
	}

	public boolean isBed(IBlockAccess iBlockAccess, int x, int y, int z, EntityLivingBase living)
	{
		return false;
	}

	public ChunkCoordinates getBedSpawnPosition(IBlockAccess iBlockAccess, int x, int y, int z, EntityPlayer player)
	{
		if (iBlockAccess instanceof World)
			return BlockBed.func_149977_a((World) iBlockAccess, x, y, z, 0);
		return null;
	}

	public void setBedOccupied(IBlockAccess iBlockAccess, int x, int y, int z, EntityPlayer player, boolean occupied)
	{
		if (iBlockAccess instanceof World)
			BlockBed.func_149979_a((World) iBlockAccess, x, y, z, occupied);
	}

	public int getBedDirection(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return BlockDirectional.getDirection(iBlockAccess.getBlockMetadata(x, y, z));
	}

	public boolean isBedFoot(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return BlockBed.isBlockHeadOfBed(iBlockAccess.getBlockMetadata(x, y, z));
	}

	public boolean canSustainLeaves(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return false;
	}

	public boolean isLeaves(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return (blockMeta.getMaterial() == Material.leaves);
	}

	public boolean canBeReplacedByLeaves(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return (!(blockMeta.func_149730_j()));
	}

	public boolean isWood(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return false;
	}

	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target)
	{
		return (blockMeta == target);
	}

	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
	{
		return blockMeta.getExplosionResistance(entity);
	}

	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		world.setBlockToAir(x, y, z);
		blockMeta.onBlockDestroyedByExplosion(world, x, y, z, explosion);
	}

	public boolean canConnectRedstone(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return ((blockMeta.canProvidePower()) && (side != -1));
	}

	public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
	{
		if (isSideSolid(world, x, y, z, ForgeDirection.UP))
		{
			return true;
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		Item item = getItem(world, x, y, z);

		if (item == null)
		{
			return null;
		}

		Block block = ((item instanceof ItemBlock) && (!(blockMeta.isFlowerPot()))) ? Block.getBlockFromItem(item) : blockMeta;
		return new ItemStack(item, 1, block.getDamageValue(world, x, y, z));
	}

	public boolean isFoliage(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return false;
	}

	public boolean canSustainPlant(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
	{
		Block plant = plantable.getPlant(iBlockAccess, x, y + 1, z);
		EnumPlantType plantType = plantable.getPlantType(iBlockAccess, x, y + 1, z);

		switch (plantType)
		{
			case Plains:
				return false;
			case Desert:
				return false;
			case Beach:
				return false;
			case Cave:
				return isSideSolid(iBlockAccess, x, y, z, ForgeDirection.UP);
			case Water:
				return false;
			case Nether:
				return false;
			case Crop:
				return false;
		}

		return false;
	}

	public void onPlantGrow(World world, int x, int y, int z, int sourceX, int sourceY, int sourceZ)
	{

	}

	public boolean isFertile(World world, int x, int y, int z)
	{
		return false;
	}

	public int getLightOpacity(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return blockMeta.getLightOpacity();
	}

	public boolean canEntityDestroy(IBlockAccess iBlockAccess, int x, int y, int z, Entity entity)
	{
		return true;
	}

	public boolean isBeaconBase(IBlockAccess iBlockAccess, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
	{
		return false;
	}

	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection side)
	{
		return RotationHelper.rotateVanillaBlock(blockMeta, world, x, y, z, side);
	}

	public ForgeDirection[] getValidRotations(World world, int x, int y, int z)
	{
		return RotationHelper.getValidVanillaBlockRotations(blockMeta);
	}

	public float getEnchantPowerBonus(World world, int x, int y, int z)
	{
		return 0.0F;
	}

	public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int color)
	{
		return false;
	}

	public int getExpDrop(IBlockAccess iBlockAccess, int meta, int fortune)
	{
		return 0;
	}

	public boolean shouldCheckWeakPower(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return blockMeta.isNormalCube();
	}

	public boolean getWeakChanges(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return false;
	}

	public int getHarvestLevel(int meta)
	{
		return harvestLevel;
	}

	public String getHarvestTool(int meta)
	{
		return harvestTool;
	}

	public boolean isToolEffective(String type, int meta)
	{
		if (harvestTool == null) return false;
		return harvestTool.equals(type);
	}

	public void onBlockAdded(World world, int x, int y, int z)
	{

	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{

	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack itemStack)
	{

	}

	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		return "" + StatCollector.translateToLocal(new StringBuilder().append(getUnlocalizedName()).append(".name").toString()).trim();
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z)
	{

	}

}
