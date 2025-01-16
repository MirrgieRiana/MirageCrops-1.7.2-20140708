package miragecrops.framework.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import miragecrops.api.framework.block.IBlockMeta;
import mirrg.mir34.modding.IMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMeta extends Block implements IBlockMeta
{

	public MetaBlock[] metaBlocks = new MetaBlock[512];
	public MetaBlock metaBlockDefault;

	public BlockMeta(IMod iMod)
	{
		super(Material.rock);

		metaBlockDefault = new MetaBlock(this, 0);
		metaBlockDefault.setTextureName(iMod.getModId() + ":NULL_ICON");
		setMetaBlock(metaBlockDefault.getMetaId(), metaBlockDefault);
	}

	@Override
	public Block getBlock()
	{
		return this;
	}

	public Integer setMetaBlock(MetaBlock metaBlock)
	{
		for (int i = 0; i < metaBlocks.length; i++) {
			if (metaBlocks[i] == null) {
				metaBlocks[i] = metaBlock;
				return i;
			}
		}
		return null;
	}

	public void setMetaBlock(int id, MetaBlock metaBlock)
	{
		if (metaBlocks[id] == null) {
			metaBlocks[id] = metaBlock;
		} else {
			throw new DuplicateMetaBlockRegisterException(
				metaBlock.getUnlocalizedName() + " -> " + metaBlock.getBlock());
		}
	}

	@Override
	public MetaBlock getMetaBlock(int meta)
	{
		MetaBlock metaBlock = metaBlocks[meta];
		if (metaBlock == null) return metaBlockDefault;
		return metaBlock;
	}

	public MetaBlock getMetaBlock(ItemStack itemStack)
	{
		return getMetaBlock(itemStack.getItemDamage());
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean isAdvancedItemTooltips)
	{
		getMetaBlock(itemStack.getItemDamage()).addInformation(itemStack, player, info, isAdvancedItemTooltips);
	}

	// --------------------------------------------------------------------------

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs p_149666_2_, List list)
	{
		for (int meta = 1; meta < metaBlocks.length; meta++) {
			if (metaBlocks[meta] != null) {
				metaBlocks[meta].getSubBlocks(item, p_149666_2_, list);
			}
		}
	}

	public String getUnlocalizedName(ItemStack paramItemStack)
	{
		int i = paramItemStack.getItemDamage();
		return getMetaBlock(i).getUnlocalizedName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iIconRegister)
	{
		for (int i = 0; i < metaBlocks.length; i++) {
			if (metaBlocks[i] != null) {
				metaBlocks[i].registerBlockIcons(iIconRegister);
			}
		}
	}

	// --------------------------------------------------------------------------

	protected ThreadLocal<Integer> silk_check_meta = new ThreadLocal();

	@Override
	public MapColor getMapColor(int meta)
	{
		return getMetaBlock(meta).getMapColor(meta);
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).getBlocksMovement(iBlockAccess, x, y, z);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getBlockHardness(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMixedBrightnessForBlock(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).getMixedBrightnessForBlock(iBlockAccess, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).shouldSideBeRendered(iBlockAccess, x, y, z, side);
	}

	public double getMinX()
	{
		return minX;
	}

	public double getMaxX()
	{
		return maxX;
	}

	public double getMinY()
	{
		return minY;
	}

	public double getMaxY()
	{
		return maxY;
	}

	public double getMinZ()
	{
		return minZ;
	}

	public double getMaxZ()
	{
		return maxZ;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isBlockSolid(iBlockAccess, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return getMetaBlock(meta).getIcon(side, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).getIcon(iBlockAccess, x, y, z, side);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity)
	{
		getMetaBlock(world.getBlockMetadata(x, y, z)).addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getSelectedBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public boolean canCollideCheck(int meta, boolean p_149678_2_)
	{
		return getMetaBlock(meta).canCollideCheck(meta, p_149678_2_);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		getMetaBlock(meta).breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public Item getItemDropped(int meta, Random random, int fortune)
	{
		return getMetaBlock(meta).getItemDropped(meta, random, fortune);
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getPlayerRelativeBlockHardness(player, world, x, y, z);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float explosionDropRate, int fortune)
	{
		getMetaBlock(meta).dropBlockAsItemWithChance(world, x, y, z, meta, explosionDropRate, fortune);
	}

	@Override
	protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack itemStack)
	{
		getMetaBlock(world.getBlockMetadata(x, y, z)).dropBlockAsItem(world, x, y, z, itemStack);
	}

	@Override
	public void dropXpOnBlockBreak(World world, int x, int y, int z, int expToDrop)
	{
		getMetaBlock(world.getBlockMetadata(x, y, z)).dropXpOnBlockBreak(world, x, y, z, expToDrop);
	}

	@Override
	public int damageDropped(int meta)
	{
		return getMetaBlock(meta).damageDropped(meta);
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).collisionRayTrace(world, x, y, z, par5Vec3, par6Vec3);
	}

	@Override
	public boolean canReplace(World world, int x, int y, int z, int side, ItemStack itemStack)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).canReplace(world, x, y, z, side, itemStack);
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).canPlaceBlockOnSide(world, x, y, z, side);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).canPlaceBlockAt(world, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float x2, float y2, float z2)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).onBlockActivated(world, x, y, z, player, side, x2, y2, z2);
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float x2, float y2, float z2, int meta)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).onBlockPlaced(world, x, y, z, side, x2, y2, z2, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta)
	{
		return getMetaBlock(meta).getRenderColor(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).colorMultiplier(iBlockAccess, x, y, z);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isProvidingWeakPower(iBlockAccess, x, y, z, side);
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isProvidingStrongPower(iBlockAccess, x, y, z, side);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
	{
		getMetaBlock(meta).harvestBlock(world, player, x, y, z, meta);
	}

	@Override
	protected boolean canSilkHarvest()
	{
		Integer meta = this.silk_check_meta.get();
		//if (renderAsNormalBlock());
		return (!(hasTileEntity((meta == null) ? 0 : meta.intValue())));
	}

	@Override
	protected ItemStack createStackedBlock(int meta)
	{
		return getMetaBlock(meta).createStackedBlock(meta);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).canBlockStay(world, x, y, z);
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventParameter)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).onBlockEventReceived(world, x, y, z, eventId, eventParameter);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getItem(world, x, y, z);
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getDamageValue(world, x, y, z);
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getComparatorInputOverride(world, x, y, z, side);
	}

	@Override
	public int getLightValue(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).getLightValue(iBlockAccess, x, y, z);
	}

	@Override
	public boolean isLadder(IBlockAccess iBlockAccess, int x, int y, int z, EntityLivingBase living)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isLadder(iBlockAccess, x, y, z, living);
	}

	@Override
	public boolean isNormalCube(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isNormalCube(iBlockAccess, x, y, z);
	}

	@Override
	public boolean isSideSolid(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isSideSolid(iBlockAccess, x, y, z, side);
	}

	@Override
	public boolean isReplaceable(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isReplaceable(iBlockAccess, x, y, z);
	}

	@Override
	public boolean isBurning(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isBurning(iBlockAccess, x, y, z);
	}

	@Override
	public boolean isAir(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isAir(iBlockAccess, x, y, z);
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta)
	{
		return getMetaBlock(meta).canHarvestBlock(player, meta);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).removedByPlayer(world, player, x, y, z);
	}

	@Override
	public int getFlammability(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).getFlammability(iBlockAccess, x, y, z, side);
	}

	@Override
	public boolean isFlammable(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isFlammable(iBlockAccess, x, y, z, side);
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).getFireSpreadSpeed(iBlockAccess, x, y, z, side);
	}

	@Override
	public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).isFireSource(world, x, y, z, side);
	}

	@Override
	public boolean hasTileEntity(int meta)
	{
		return getMetaBlock(meta).hasTileEntity(meta);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return getMetaBlock(meta).createTileEntity(world, meta);
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		return getMetaBlock(meta).quantityDropped(meta, fortune, random);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune)
	{
		return getMetaBlock(meta).getDrops(world, x, y, z, meta, fortune);
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int meta)
	{
		return getMetaBlock(meta).canSilkHarvest(world, player, x, y, z, meta);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType creatureType, IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).canCreatureSpawn(creatureType, iBlockAccess, x, y, z);
	}

	@Override
	public boolean isBed(IBlockAccess iBlockAccess, int x, int y, int z, EntityLivingBase living)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isBed(iBlockAccess, x, y, z, living);
	}

	@Override
	public ChunkCoordinates getBedSpawnPosition(IBlockAccess iBlockAccess, int x, int y, int z, EntityPlayer player)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).getBedSpawnPosition(iBlockAccess, x, y, z, player);
	}

	@Override
	public void setBedOccupied(IBlockAccess iBlockAccess, int x, int y, int z, EntityPlayer player, boolean occupied)
	{
		getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).setBedOccupied(iBlockAccess, x, y, z, player, occupied);
	}

	@Override
	public int getBedDirection(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).getBedDirection(iBlockAccess, x, y, z);
	}

	@Override
	public boolean isBedFoot(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isBedFoot(iBlockAccess, x, y, z);
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).canSustainLeaves(iBlockAccess, x, y, z);
	}

	@Override
	public boolean isLeaves(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isLeaves(iBlockAccess, x, y, z);
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).canBeReplacedByLeaves(iBlockAccess, x, y, z);
	}

	@Override
	public boolean isWood(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isWood(iBlockAccess, x, y, z);
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).isReplaceableOreGen(world, x, y, z, target);
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		getMetaBlock(world.getBlockMetadata(x, y, z)).onBlockExploded(world, x, y, z, explosion);
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).canConnectRedstone(iBlockAccess, x, y, z, side);
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).canPlaceTorchOnTop(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getPickBlock(target, world, x, y, z);
	}

	@Override
	public boolean isFoliage(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isFoliage(iBlockAccess, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).addDestroyEffects(world, x, y, z, meta, effectRenderer);
	}

	@Override
	public boolean canSustainPlant(IBlockAccess iBlockAccess, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).canSustainPlant(iBlockAccess, x, y, z, direction, plantable);
	}

	@Override
	public void onPlantGrow(World world, int x, int y, int z, int sourceX, int sourceY, int sourceZ)
	{
		getMetaBlock(world.getBlockMetadata(x, y, z)).onPlantGrow(world, x, y, z, sourceX, sourceY, sourceZ);
	}

	@Override
	public boolean isFertile(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).isFertile(world, x, y, z);
	}

	@Override
	public int getLightOpacity(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).getLightOpacity(iBlockAccess, x, y, z);
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess iBlockAccess, int x, int y, int z, Entity entity)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).canEntityDestroy(iBlockAccess, x, y, z, entity);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess iBlockAccess, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).isBeaconBase(iBlockAccess, x, y, z, beaconX, beaconY, beaconZ);
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection side)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).rotateBlock(world, x, y, z, side);
	}

	@Override
	public ForgeDirection[] getValidRotations(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getValidRotations(world, x, y, z);
	}

	@Override
	public float getEnchantPowerBonus(World world, int x, int y, int z)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).getEnchantPowerBonus(world, x, y, z);
	}

	@Override
	public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int color)
	{
		return getMetaBlock(world.getBlockMetadata(x, y, z)).recolourBlock(world, x, y, z, side, color);
	}

	@Override
	public int getExpDrop(IBlockAccess iBlockAccess, int meta, int fortune)
	{
		return getMetaBlock(meta).getExpDrop(iBlockAccess, meta, fortune);
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockAccess iBlockAccess, int x, int y, int z, int side)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).shouldCheckWeakPower(iBlockAccess, x, y, z, side);
	}

	@Override
	public boolean getWeakChanges(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).getWeakChanges(iBlockAccess, x, y, z);
	}

	@Override
	public int getHarvestLevel(int meta)
	{
		return getMetaBlock(meta).getHarvestLevel(meta);
	}

	@Override
	public String getHarvestTool(int meta)
	{
		return getMetaBlock(meta).getHarvestTool(meta);
	}

	@Override
	public boolean isToolEffective(String type, int meta)
	{
		return getMetaBlock(meta).isToolEffective(type, meta);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		getMetaBlock(world.getBlockMetadata(x, y, z)).onBlockAdded(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		getMetaBlock(world.getBlockMetadata(x, y, z)).randomDisplayTick(world, x, y, z, random);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack itemStack)
	{
		getMetaBlock(world.getBlockMetadata(x, y, z)).onBlockPlacedBy(world, x, y, z, living, itemStack);
	}

	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		return getMetaBlock(par1ItemStack.getItemDamage()).getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		getMetaBlock(iBlockAccess.getBlockMetadata(x, y, z)).setBlockBoundsBasedOnState(iBlockAccess, x, y, z);
	}

}
