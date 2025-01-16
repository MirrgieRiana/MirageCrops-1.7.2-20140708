package miragecrops.framework.multiicon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IMultiIconShape
{

	@SideOnly(Side.CLIENT)
	public int getLength();

	@SideOnly(Side.CLIENT)
	public int getChannelsUpperBound();

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int pass);

	@SideOnly(Side.CLIENT)
	public void register(IIconRegister iIconRegister);

	@SideOnly(Side.CLIENT)
	public double getDefaultColorRate(int pass);

	@SideOnly(Side.CLIENT)
	public int getChannel(int pass);

}
