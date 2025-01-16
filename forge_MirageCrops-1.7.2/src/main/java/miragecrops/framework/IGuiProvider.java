package miragecrops.framework;

import mirrg.h.struct.Tuple1;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IGuiProvider
{

	@SideOnly(Side.CLIENT)
	public Tuple1<? extends GuiContainer> createGui(EntityPlayer player, World world, int x, int y, int z);

	public Container createContainer(EntityPlayer player, World world, int x, int y, int z);

}
