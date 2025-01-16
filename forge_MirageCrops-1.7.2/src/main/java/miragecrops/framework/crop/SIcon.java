package miragecrops.framework.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SIcon
{

	public static NormalIC2 normalIC2(String domain)
	{
		return new NormalIC2(domain);
	}

	public static NormalMirage normalMirage(String domain)
	{
		return new NormalMirage(domain);
	}

	public static IconStatus iconStatus(String domain, String iconClassName)
	{
		return new IconStatus(domain, iconClassName);
	}

	public static IconStatus iconStatus(String formatter, String domain, String iconClass)
	{
		return new IconStatus(formatter, domain, iconClass);
	}

	public static Inherit inherit(int inheritFrom, int inheritTo,
		IIconProviderFromSize superIconProviderFromSize, IIconProviderFromSize subIconProviderFromSize)
	{
		return new Inherit(inheritFrom, inheritTo, superIconProviderFromSize, subIconProviderFromSize);
	}

	public static Inherit inherit(int inheritTo,
		IIconProviderFromSize superIconProviderFromSize, IIconProviderFromSize subIconProviderFromSize)
	{
		return new Inherit(inheritTo, superIconProviderFromSize, subIconProviderFromSize);
	}

	// ---------------------------------------------------------------

	private static class NormalIC2 extends IconProviderAbstractNamed
	{

		public NormalIC2(String domain)
		{
			super(domain);
		}

		@Override
		protected String getIconName(CropCard cropCard, int size)
		{
			return "ic2:crop/blockCrop." + cropCard.name() + "." + size;
		}

	}

	private static class NormalMirage implements IIconProviderFromSize
	{

		private static IIcon NULL_ICON;

		@SideOnly(Side.CLIENT)
		protected IIcon textures[];
		protected String domain;

		public NormalMirage(String domain)
		{
			this.domain = domain;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IIcon getSprite(CropCard cropCard, ICropTile crop)
		{
			if (crop.getSize() <= 0 || crop.getSize() > textures.length) return NULL_ICON;

			IIcon texture = textures[crop.getSize() - 1];
			if (texture == null) return NULL_ICON;

			return texture;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void registerSprites(CropCard cropCard, IIconRegister iconRegister)
		{
			//if (textures != null) return;
			registerSpritesReady(cropCard, iconRegister);

			for (int size = 1; size <= textures.length; size++) {
				registerSprites(cropCard, iconRegister, size);
			}

			if (NULL_ICON == null) {
				NULL_ICON = iconRegister.registerIcon(String.format("%s:NULL_ICON", domain));
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void registerSpritesReady(CropCard cropCard, IIconRegister iconRegister)
		{
			textures = new IIcon[cropCard.maxSize()];
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void registerSprites(CropCard cropCard, IIconRegister iconRegister, int size)
		{
			textures[size - 1] = iconRegister.registerIcon(String.format("%s:crops/blockCrop.%s.%d",
				domain, cropCard.name(), size));
		}

	}

	private static abstract class IconProviderAbstractNamed extends NormalMirage
	{

		public IconProviderAbstractNamed(String domain)
		{
			super(domain);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void registerSprites(CropCard cropCard, IIconRegister iconRegister, int size)
		{
			textures[size - 1] = iconRegister.registerIcon(getIconName(cropCard, size));
		}

		protected abstract String getIconName(CropCard cropCard, int size);

	}

	private static abstract class IconProviderAbstractNamedFromSize extends IconProviderAbstractNamed
	{

		public IconProviderAbstractNamedFromSize(String domain)
		{
			super(domain);
		}

		@Override
		protected String getIconName(CropCard cropCard, int size)
		{
			return getIconName(size);
		}

		protected abstract String getIconName(int size);

	}

	private static class IconStatus extends IconProviderAbstractNamedFromSize
	{

		protected final String iconClass;
		protected final String formatter;

		public IconStatus(String domain, String iconClass)
		{
			super(domain);
			this.formatter = "%s:crops/blockCrop.%s.%d";
			this.iconClass = iconClass;
		}

		public IconStatus(String formatter, String domain, String iconClass)
		{
			super(domain);
			this.formatter = formatter;
			this.iconClass = iconClass;
		}

		@Override
		protected String getIconName(int size)
		{
			return String.format(formatter,
				domain,
				iconClass,
				size);
		}

	}

	private static class Inherit implements IIconProvider
	{

		protected final int inheritFrom;
		protected final int inheritTo;
		protected final IIconProviderFromSize superIconProviderFromSize;
		protected final IIconProviderFromSize subIconProviderFromSize;

		public Inherit(int inheritFrom, int inheritTo,
			IIconProviderFromSize superIconProviderFromSize, IIconProviderFromSize subIconProviderFromSize)
		{
			this.inheritFrom = inheritFrom;
			this.inheritTo = inheritTo;
			this.superIconProviderFromSize = superIconProviderFromSize;
			this.subIconProviderFromSize = subIconProviderFromSize;
		}

		public Inherit(int inheritTo,
			IIconProviderFromSize superIconProviderFromSize, IIconProviderFromSize subIconProviderFromSize)
		{
			this(1, inheritTo, superIconProviderFromSize, subIconProviderFromSize);
		}

		protected boolean shouldInherit(int size)
		{
			if (size >= inheritFrom) {
				if (size <= inheritTo) {
					return true;
				}
			}

			return false;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public final IIcon getSprite(CropCard cropCard, ICropTile crop)
		{
			if (shouldInherit(crop.getSize())) return superIconProviderFromSize.getSprite(cropCard, crop);
			return subIconProviderFromSize.getSprite(cropCard, crop);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public final void registerSprites(CropCard cropCard, IIconRegister iconRegister)
		{
			superIconProviderFromSize.registerSpritesReady(cropCard, iconRegister);
			subIconProviderFromSize.registerSpritesReady(cropCard, iconRegister);

			for (int size = 1; size <= cropCard.maxSize(); size++) {
				if (shouldInherit(size)) {
					superIconProviderFromSize.registerSprites(cropCard, iconRegister, size);
				} else {
					subIconProviderFromSize.registerSprites(cropCard, iconRegister, size);
				}
			}
		}

	}

}
