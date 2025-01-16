package mirrg.minecraft.item.multi.copper;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IMetaitem
{

	public int getMetaId();

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> strings, boolean isShift);

	public String getUnlocalizedName(ItemStack itemStack);

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> itemStacks);

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister);

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage);

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemStack, int pass);

	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2);

}
