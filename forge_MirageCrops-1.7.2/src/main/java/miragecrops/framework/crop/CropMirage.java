package miragecrops.framework.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import miragecrops.api.framework.cropgainrecipe.ICropGainRecipe;
import miragecrops.framework.StaticsCrop;
import miragecrops.framework.crop.CropSpecification.ICropCardListener;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CropMirage extends CropCard implements ICropGainRecipe
{

	protected final CropSpecification cropSpecification;

	public CropMirage(CropSpecification cropSpecification)
	{
		this.cropSpecification = cropSpecification;
	}

	// --------------------------------------------------------------------

	@Override
	public final int stat(int n)
	{
		return cropSpecification.stats[n];
	}

	@Override
	public final String name()
	{
		return cropSpecification.name;
	}

	@Override
	public final String discoveredBy()
	{
		return cropSpecification.discoveredBy;
	}

	@Override
	public final String[] attributes()
	{
		return cropSpecification.attributes.clone();
	}

	@Override
	public final int tier()
	{
		return cropSpecification.tier;
	}

	@Override
	public final int maxSize()
	{
		return cropSpecification.maxSize;
	}

	@Override
	public byte getSizeAfterHarvest(ICropTile crop)
	{
		return cropSpecification.sizeAfterHarvest;
	}

	@Override
	public final int weightInfluences(ICropTile crop, float humidity, float nutrients, float air)
	{
		return (int) (cropSpecification.rateHumidity * humidity +
			cropSpecification.rateNutrients * nutrients + cropSpecification.rateAir * air);
	}

	@Override
	public final ItemStack getGain(ICropTile crop)
	{
		return cropSpecification.iGainProducer.getGain(this, crop);
	}

	@Override
	public void addRecipe(ICropGainRecipeAdder iRecipeAdder, CropCard cropCard, ItemStack debugCropSeed)
	{
		cropSpecification.iGainProducer.addRecipe(iRecipeAdder, cropCard, debugCropSeed);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final IIcon getSprite(ICropTile crop)
	{
		return cropSpecification.iIconProducer.getSprite(this, crop);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void registerSprites(IIconRegister iconRegister)
	{
		cropSpecification.iIconProducer.registerSprites(this, iconRegister);
	}

	// --------------------------------------------------------------------

	@Override
	public final boolean canGrow(ICropTile iCropTile)
	{
		if (iCropTile.getID() == -1) {
			if (StaticsCrop.isUpgraded(iCropTile)) {
				return canSpawnAt(iCropTile);
			}
		}

		if (iCropTile.getSize() >= maxSize()) return false;
		return true;
	}

	@Override
	public boolean canBeHarvested(ICropTile crop)
	{
		return crop.getSize() == maxSize();
	}

	// --------------------------------------------------------------------

	@Override
	public final boolean leftclick(ICropTile crop, EntityPlayer player)
	{
		boolean changed = false;

		for (ICropCardListener iCropCardListener : cropSpecification.iCropCardListeners) {
			switch (iCropCardListener.leftclick(this, crop, player)) {
				case CONTINUE_CHANGED:
					changed = true;
					break;
				case CONTINUE_NOT_CHANGED:
					break;
				case INTERRUPT_CHANGED:
					changed = true;
					return changed;
				case INTERRUPT_NOT_CHANGED:
					return changed;
				default:
					break;
			}
		}

		if (super.leftclick(crop, player)) changed = true;
		return changed;
	}

	@Override
	public final boolean rightclick(ICropTile crop, EntityPlayer player)
	{
		boolean changed = false;

		for (ICropCardListener iCropCardListener : cropSpecification.iCropCardListeners) {
			switch (iCropCardListener.rightclick(this, crop, player)) {
				case CONTINUE_CHANGED:
					changed = true;
					break;
				case CONTINUE_NOT_CHANGED:
					break;
				case INTERRUPT_CHANGED:
					changed = true;
					return changed;
				case INTERRUPT_NOT_CHANGED:
					return changed;
				default:
					break;
			}
		}

		if (super.rightclick(crop, player)) changed = true;
		return changed;
	}

	@Override
	public final void onNeighbourChange(ICropTile crop)
	{
		for (ICropCardListener iCropCardListener : cropSpecification.iCropCardListeners) {
			iCropCardListener.onNeighbourChange(this, crop);
		}
		super.onNeighbourChange(crop);
	}

	@Override
	public final void tick(ICropTile crop)
	{
		for (ICropCardListener iCropCardListener : cropSpecification.iCropCardListeners) {
			iCropCardListener.tick(this, crop);
		}
		super.tick(crop);
	}

	@Override
	public final void onBlockDestroyed(ICropTile crop)
	{
		for (ICropCardListener iCropCardListener : cropSpecification.iCropCardListeners) {
			iCropCardListener.onBlockDestroyed(this, crop);
		}
		super.onBlockDestroyed(crop);
	}

	@Override
	public final boolean onEntityCollision(ICropTile crop, Entity entity)
	{
		for (ICropCardListener iCropCardListener : cropSpecification.iCropCardListeners) {
			iCropCardListener.onEntityCollision(this, crop, entity);
		}

		switch (cropSpecification.tramplingResistance) {
			case NOT_HAPPEN:
				return false;
			case HAPPEN_IF_SPRINTING:
				if (entity instanceof EntityLivingBase)
				{
					return ((EntityLivingBase) entity).isSprinting();
				}
				return false;
			case HAPPEN_IF_NOT_SKEAKING:
				if (entity instanceof EntityLivingBase)
				{
					return !((EntityLivingBase) entity).isSneaking();
				}
				return false;
			case HAPPEN_ALWAYS:
				if (entity instanceof EntityLivingBase)
				{
					return true;
				}
				return false;
			case HAPPEN_EVEN_NONLIVING:
				return true;
			default:
				return super.onEntityCollision(crop, entity);
		}

	}

	@Override
	public boolean canCross(ICropTile crop)
	{
		return crop.getSize() >= maxSize() - 1;
	}

	protected boolean canSpawnAt(ICropTile iCropTile)
	{
		return false;
	}

}
