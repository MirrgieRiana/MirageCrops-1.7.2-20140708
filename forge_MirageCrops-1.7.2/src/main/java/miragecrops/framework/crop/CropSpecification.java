package miragecrops.framework.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class CropSpecification
{

	protected final int[] stats;
	protected final String name;
	protected final String discoveredBy;
	protected final String[] attributes;
	protected final int tier;
	protected final int maxSize;
	protected final byte sizeAfterHarvest;
	protected final float rateHumidity;
	protected final float rateNutrients;
	protected final float rateAir;
	protected final IGainProvider iGainProducer;
	protected final IIconProvider iIconProducer;
	protected final ArrayList<ICropCardListener> iCropCardListeners;
	protected final EnumTramplingResistance tramplingResistance;

	public CropSpecification(int statChemical, int statFood, int statDefensive, int statColor, int statWeed,
		String name, String discoveredBy, String[] attributes, int tier, int maxSize, int sizeAfterHarvest,
		float rateHumidity, float rateNutrients, float rateAir,
		IGainProvider iGainProducer, IIconProvider iIconProducer, EnumTramplingResistance tramplingResistance)
	{
		stats = new int[] {
			statChemical,
			statFood,
			statDefensive,
			statColor,
			statWeed,
		};
		this.name = name;
		this.discoveredBy = discoveredBy;
		this.attributes = attributes.clone();
		this.tier = tier;
		this.maxSize = maxSize;
		this.sizeAfterHarvest = (byte) sizeAfterHarvest;
		this.rateHumidity = rateHumidity;
		this.rateNutrients = rateNutrients;
		this.rateAir = rateAir;
		this.iGainProducer = iGainProducer;
		this.iIconProducer = iIconProducer;
		iCropCardListeners = new ArrayList<ICropCardListener>();
		this.tramplingResistance = tramplingResistance;
	}

	public void addListener(ICropCardListener listener)
	{
		iCropCardListeners.add(listener);
	}

	public static interface ICropCardListener
	{

		public EnumClickEventProgress leftclick(CropCard cropCard, ICropTile crop, EntityPlayer player);

		public EnumClickEventProgress rightclick(CropCard cropCard, ICropTile crop, EntityPlayer player);

		public void onNeighbourChange(CropCard cropCard, ICropTile crop);

		public void tick(CropCard cropCard, ICropTile crop);

		public void onBlockDestroyed(CropCard cropCard, ICropTile crop);

		public void onEntityCollision(CropCard cropCard, ICropTile crop, Entity entity);

	}

	public static class CropCardListenerImpl implements ICropCardListener
	{

		@Override
		public EnumClickEventProgress leftclick(CropCard cropCard, ICropTile crop, EntityPlayer player)
		{
			return EnumClickEventProgress.CONTINUE_NOT_CHANGED;
		}

		@Override
		public EnumClickEventProgress rightclick(CropCard cropCard, ICropTile crop, EntityPlayer player)
		{
			return EnumClickEventProgress.CONTINUE_NOT_CHANGED;
		}

		@Override
		public void onNeighbourChange(CropCard cropCard, ICropTile crop)
		{

		}

		@Override
		public void tick(CropCard cropCard, ICropTile crop)
		{

		}

		@Override
		public void onBlockDestroyed(CropCard cropCard, ICropTile crop)
		{

		}

		@Override
		public void onEntityCollision(CropCard cropCard, ICropTile crop, Entity entity)
		{

		}

	}

	public static enum EnumTramplingResistance
	{
		NOT_HAPPEN,
		HAPPEN_IF_SPRINTING,
		HAPPEN_IF_NOT_SKEAKING,
		HAPPEN_ALWAYS,
		HAPPEN_EVEN_NONLIVING,
	}

	public static enum EnumClickEventProgress
	{
		CONTINUE_CHANGED,
		CONTINUE_NOT_CHANGED,
		INTERRUPT_CHANGED,
		INTERRUPT_NOT_CHANGED,
	}

}
