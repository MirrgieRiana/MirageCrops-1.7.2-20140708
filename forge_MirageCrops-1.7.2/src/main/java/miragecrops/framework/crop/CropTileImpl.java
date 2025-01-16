package miragecrops.framework.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class CropTileImpl implements ICropTile
{
	private final CropCard cropCard;
	private final byte size;

	public CropTileImpl(CropCard cropCard, byte size)
	{
		this.cropCard = cropCard;
		this.size = size;
	}

	@Override
	public short getID()
	{
		return (short) cropCard.getId();
	}

	@Override
	public void setID(short id)
	{

	}

	@Override
	public byte getSize()
	{
		return size;
	}

	@Override
	public void setSize(byte size)
	{

	}

	@Override
	public byte getGrowth()
	{
		return 0;
	}

	@Override
	public void setGrowth(byte growth)
	{

	}

	@Override
	public byte getGain()
	{
		return 0;
	}

	@Override
	public void setGain(byte gain)
	{

	}

	@Override
	public byte getResistance()
	{
		return 0;
	}

	@Override
	public void setResistance(byte resistance)
	{

	}

	@Override
	public byte getScanLevel()
	{
		return 0;
	}

	@Override
	public void setScanLevel(byte scanLevel)
	{

	}

	@Override
	public NBTTagCompound getCustomData()
	{
		return null;
	}

	@Override
	public int getNutrientStorage()
	{
		return 0;
	}

	@Override
	public void setNutrientStorage(int nutrientStorage)
	{

	}

	@Override
	public int getHydrationStorage()
	{
		return 0;
	}

	@Override
	public void setHydrationStorage(int hydrationStorage)
	{

	}

	@Override
	public int getWeedExStorage()
	{
		return 0;
	}

	@Override
	public void setWeedExStorage(int weedExStorage)
	{

	}

	@Override
	public byte getHumidity()
	{
		return 0;
	}

	@Override
	public byte getNutrients()
	{
		return 0;
	}

	@Override
	public byte getAirQuality()
	{
		return 0;
	}

	@Override
	public World getWorld()
	{
		return null;
	}

	@Override
	public ChunkCoordinates getLocation()
	{
		return null;
	}

	@Override
	public int getLightLevel()
	{
		return 0;
	}

	@Override
	public boolean pick(boolean manual)
	{
		return false;
	}

	@Override
	public boolean harvest(boolean manual)
	{
		return false;
	}

	@Override
	public void reset()
	{

	}

	@Override
	public void updateState()
	{

	}

	@Override
	public boolean isBlockBelow(Block block)
	{
		return false;
	}

	@Override
	public ItemStack generateSeeds(short plant, byte growth, byte gain, byte resis, byte scan)
	{
		return null;
	}

}
