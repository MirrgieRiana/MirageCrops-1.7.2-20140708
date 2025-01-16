package miragecrops.framework.multiicon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MultiIcon implements IMultiIcon
{

	private final IMultiIconShape iMultiIconShape;
	private final int[] colors;

	protected class IniterSuper
	{

		protected void init(int[] colors)
		{

		}

	}

	protected class Initer extends IniterSuper
	{

		@Override
		@SideOnly(Side.CLIENT)
		protected void init(int[] colors)
		{
			if (iMultiIconShape.getChannelsUpperBound() != colors.length - 1) {
				throw new RuntimeException("channel size mismatched: " + iMultiIconShape.getChannelsUpperBound() + " != " + (colors.length - 1));
			}
		}

	}

	public MultiIcon(IMultiIconShape iMultiIconShape, int... colors)
	{
		this.iMultiIconShape = iMultiIconShape;
		this.colors = colors.clone();

		new Initer().init(colors);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getLength()
	{
		return iMultiIconShape.getLength();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getChannelsUpperBound()
	{
		return iMultiIconShape.getChannelsUpperBound();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int pass)
	{
		return iMultiIconShape.getIcon(pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void register(IIconRegister iIconRegister)
	{
		iMultiIconShape.register(iIconRegister);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getDefaultColorRate(int pass)
	{
		return iMultiIconShape.getDefaultColorRate(pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getChannel(int pass)
	{
		return iMultiIconShape.getChannel(pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColor(int pass)
	{
		return getColorRate(colors[getChannel(pass)], getDefaultColorRate(pass));
	}

	private static int trim(int r, int min, int max)
	{
		if (r < min) return min;
		if (r > max) return max;
		return r;
	}

	private static int getColorRate(int color, double rate)
	{
		int r = (color & 0xFF0000) >> 16;
		int g = (color & 0xFF00) >> 8;
		int b = color & 0xFF;

		r = (int) (r * rate);
		g = (int) (g * rate);
		b = (int) (b * rate);

		r = trim(r, 0, 255);
		g = trim(g, 0, 255);
		b = trim(b, 0, 255);

		return (r << 16) | (g << 8) | b;
	}

}
