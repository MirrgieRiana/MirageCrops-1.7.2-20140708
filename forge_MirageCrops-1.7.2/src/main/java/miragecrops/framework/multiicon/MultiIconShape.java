package miragecrops.framework.multiicon;

import mirrg.h.struct.Tuple3;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MultiIconShape implements IMultiIconShape
{

	private final String prefix;
	private final IIcon[] icons;
	private final Entry[] entries;
	private final int channelsUpperBound;

	public MultiIconShape(String prefix, Entry... entries)
	{
		this.prefix = prefix;
		icons = new IIcon[entries.length];
		this.entries = entries;

		int channelsUpperBound = 0;
		for (Entry entry : entries) {
			if (channelsUpperBound < entry.getZ()) {
				channelsUpperBound = entry.getZ();
			}
		}
		this.channelsUpperBound = channelsUpperBound;
	}

	public static Entry entry(String name, double defaultColorRate)
	{
		return entry(name, defaultColorRate, 0);
	}

	public static Entry entry(String name, double defaultColorRate, int channel)
	{
		return new Entry(name, defaultColorRate, channel);
	}

	public static class Entry extends Tuple3<String, Double, Integer>
	{

		private Entry(String name, double defaultColorRate, int channel)
		{
			super(name, defaultColorRate, channel);
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getLength()
	{
		return entries.length;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getChannelsUpperBound()
	{
		return channelsUpperBound;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int pass)
	{
		if (pass >= getLength()) return getIcon(getLength() - 1);
		return icons[pass];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void register(IIconRegister iIconRegister)
	{
		for (int pass = 0; pass < getLength(); pass++) {
			icons[pass] = iIconRegister.registerIcon(getTextureName(pass));
		}
	}

	private String getTextureName(int pass)
	{
		return getPrefix() + "_" + getEntryName(pass);
	}

	private String getEntryName(int pass)
	{
		return entries[pass].getX();
	}

	private String getPrefix()
	{
		return prefix;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getDefaultColorRate(int pass)
	{
		if (pass >= getLength()) return getDefaultColorRate(getLength() - 1);
		return entries[pass].getY();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getChannel(int pass)
	{
		if (pass >= getLength()) return getChannel(getLength() - 1);
		return entries[pass].getZ();
	}

}
