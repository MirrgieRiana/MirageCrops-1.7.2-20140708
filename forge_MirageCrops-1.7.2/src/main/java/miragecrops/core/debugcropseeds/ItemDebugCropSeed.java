package miragecrops.core.debugcropseeds;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;
import ic2.api.item.IC2Items;

import java.util.List;

import miragecrops.framework.crop.CropTileImpl;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDebugCropSeed extends Item
{

	@Override
	public boolean getHasSubtypes()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs par1CreativeTabs, List itemList)
	{
		CropCard[] cropList = Crops.instance.getCropList();

		for (CropCard crop : cropList) {
			if (crop != null) {

				for (int size = 1; size <= crop.maxSize(); size++) {
					itemList.add(createItemStack(crop.getId(), size));
				}

			}
		}

	}

	public ItemStack createItemStack(int id)
	{
		if (id < 0) return null;
		if (id >= Crops.instance.getCropList().length) return null;

		CropCard cropCard = Crops.instance.getCropList()[id];
		if (cropCard == null) return null;

		ItemStack itemStack = new ItemStack(this, 1, id);

		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setInteger("size", cropCard.maxSize());
		itemStack.setTagCompound(nbtTagCompound);

		return itemStack;
	}

	public ItemStack createItemStack(int id, int size)
	{
		ItemStack itemStack = new ItemStack(this, 1, id);

		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setInteger("size", size);
		itemStack.setTagCompound(nbtTagCompound);

		return itemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber()
	{
		return 0;
	}

	// -------------------------------------------------------------------------------

	protected int getSize(ItemStack itemStack)
	{
		if (!itemStack.hasTagCompound()) return 0;
		if (!itemStack.getTagCompound().hasKey("size")) return 0;
		return itemStack.getTagCompound().getInteger("size");
	}

	protected CropCard getCropCard(ItemStack itemStack)
	{
		return Crops.instance.getCropList()[itemStack.getItemDamage()];
	}

	protected boolean isValid(ItemStack itemStack)
	{
		if (getCropCard(itemStack) == null) return false;
		if (getSize(itemStack) == 0) return false;
		return true;
	}

	// -------------------------------------------------------------------------------

	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{
		if (isValid(itemStack)) {
			return String.format("%s: %s %d",
				super.getItemStackDisplayName(itemStack),
				getCropCard(itemStack).name(),
				getSize(itemStack));
		} else {
			return String.format("%s: Inavtive",
				super.getItemStackDisplayName(itemStack));
		}
	}

	public static boolean tryPlantIn(ICropTile iCropTile, int id, int si, int statGr, int statGa, int statRe, int scan)
	{
		if (iCropTile.getID() > -1) return false;

		CropCard cropCard = Crops.instance.getCropList()[id];
		if (cropCard == null) return false;

		if (!cropCard.canGrow(iCropTile)) return false;

		iCropTile.reset();
		iCropTile.setID((short) id);
		iCropTile.setSize((byte) si);
		iCropTile.setGrowth((byte) statGr);
		iCropTile.setGain((byte) statGa);
		iCropTile.setResistance((byte) statRe);
		iCropTile.setScanLevel((byte) scan);

		return true;
	}

	protected int[] placeBlock(ItemStack itemStack, EntityPlayer par2EntityPlayer, World world,
		int x, int y, int z, int side, float x2, float y2, float z2)
	{
		Block block = world.getBlock(x, y, z);

		if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
		{
			side = 1;
		}
		else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z))
		{
			if (side == 0)
			{
				--y;
			}

			if (side == 1)
			{
				++y;
			}

			if (side == 2)
			{
				--z;
			}

			if (side == 3)
			{
				++z;
			}

			if (side == 4)
			{
				--x;
			}

			if (side == 5)
			{
				++x;
			}
		}

		Block block2;
		{
			Item item = IC2Items.getItem("crop").getItem();
			System.out.println(3);
			if (item == null) return null;
			System.out.println(item);
			if (!(item instanceof ItemBlock)) return null;
			System.out.println(5);
			block2 = ((ItemBlock) item).field_150939_a;
		}

		if (itemStack.stackSize == 0)
		{
			return null;
		}
		else if (!par2EntityPlayer.canPlayerEdit(x, y, z, side, itemStack))
		{
			return null;
		}
		else if (y == 255 && block2.getMaterial().isSolid())
		{
			return null;
		}
		else if (world.canPlaceEntityOnSide(block2, x, y, z, false, side, par2EntityPlayer, itemStack))
		{
			int i1 = this.getMetadata(itemStack.getItemDamage());
			int j1 = block2.onBlockPlaced(world, x, y, z, side, x2, y2, z2, i1);

			if (placeBlockAt(itemStack, par2EntityPlayer, world, x, y, z, side, x2, y2, z2, j1, block2))
			{
				world.playSoundEffect(
					x + 0.5F,
					y + 0.5F,
					z + 0.5F,
					block2.stepSound.func_150496_b(),
					(block2.stepSound.getVolume() + 1.0F) / 2.0F,
					block2.stepSound.getPitch() * 0.8F);
				--itemStack.stackSize;
			}

			return new int[] {
				x, y, z,
			};
		}
		else
		{
			return null;
		}
	}

	protected boolean plantIn(ItemStack itemStack, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null) {
			if (tileEntity instanceof ICropTile) {
				ICropTile iCropTile = (ICropTile) tileEntity;

				if (tryPlantIn(
					iCropTile,
					itemStack.getItemDamage(), getSize(itemStack),
					0, 0, 0, 3)) {

					if (!(world.isRemote)) {
						world.playAuxSFX(2005, x, y, z, 0);
						itemStack.stackSize -= 1;
					}

					return true;
				}

			}
		}

		return false;
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World world,
		int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if (!isValid(itemStack)) return false;

		if (plantIn(itemStack, world, x, y, z)) return true;

		int[] placeBlock = placeBlock(itemStack, par2EntityPlayer, world, x, y, z, side, x2, y2, z2);

		if (placeBlock != null) {
			if (plantIn(itemStack, world, placeBlock[0], placeBlock[1], placeBlock[2])) return true;
		}

		return false;
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata, Block block)
	{

		if (!world.setBlock(x, y, z, block, metadata, 3))
		{
			return false;
		}

		if (world.getBlock(x, y, z) == block)
		{
			block.onBlockPlacedBy(world, x, y, z, player, stack);
			block.onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List list,
		boolean par4)
	{
		if (!isValid(itemStack)) {
			list.add(EnumChatFormatting.RED + "Inactive");
			return;
		}

		CropCard cropCard = getCropCard(itemStack);
		list.add("    " + EnumChatFormatting.GOLD + cropCard.name());
		list.add(EnumChatFormatting.AQUA + "Size: " + EnumChatFormatting.RESET + getSize(itemStack));
		list.add("Id: " + cropCard.getId());
		list.add("Tier: " + cropCard.tier());
		list.add("DiscoveredBy: " + cropCard.discoveredBy());
		list.add("MaxSize: " + cropCard.maxSize());
		list.add("Stat: " +
			cropCard.stat(0) + ", " +
			cropCard.stat(1) + ", " +
			cropCard.stat(2) + ", " +
			cropCard.stat(3) + ", " +
			cropCard.stat(4));
		list.add("");
		list.add(cropCard.desc(0));
		list.add(cropCard.desc(1));

	}

	@Override
	public IIcon getIcon(ItemStack itemStack, int pass)
	{
		if (!isValid(itemStack)) return super.getIcon(itemStack, pass);

		CropCard cropCard = getCropCard(itemStack);
		return cropCard.getSprite(new CropTileImpl(cropCard, (byte) getSize(itemStack)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack itemStack)
	{
		if (!isValid(itemStack)) return super.getIconIndex(itemStack);

		CropCard cropCard = getCropCard(itemStack);
		return cropCard.getSprite(new CropTileImpl(cropCard, (byte) getSize(itemStack)));
	}

}
