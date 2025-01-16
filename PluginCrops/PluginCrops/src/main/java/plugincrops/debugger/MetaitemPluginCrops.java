package plugincrops.debugger;

import java.util.List;

import mirrg.minecraft.item.multi.copper.IMetaitem;
import mirrg.minecraft.item.multi.copper.Metaitem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaitemPluginCrops extends Metaitem
{

	public static class Arguments
	{

		private final String textureName;
		private final int color;
		private final String name;

		public Arguments(String textureName, int color, String name)
		{
			this.textureName = textureName;
			this.color = color;
			this.name = name;
		}

	}

	private Arguments arguments;

	public MetaitemPluginCrops(IMetaitem _super, int metaid, Arguments arguments)
	{
		super(_super, metaid);
		this.arguments = arguments;
	}

	private IIcon icon;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		icon = iconRegister.registerIcon(arguments.textureName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemStack, int pass)
	{
		return arguments.color;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List strings, boolean isShift)
	{
		strings.add(StatCollector.translateToLocal("item." + arguments.name + ".information"));
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return "item." + arguments.name;
	}

	protected int getNBTInteger(ItemStack itemStack, String path, int _default)
	{
		Integer integer = HNbt.getInteger(itemStack.getTagCompound(), path);
		if (integer == null) return _default;
		return integer;
	}

	protected String getNBTString(ItemStack itemStack, String path, String _default)
	{
		String string = HNbt.getString(itemStack.getTagCompound(), path);
		if (string == null) return _default;
		return string;
	}

}
