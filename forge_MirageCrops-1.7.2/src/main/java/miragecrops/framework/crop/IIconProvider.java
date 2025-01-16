package miragecrops.framework.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IIconProvider
{

	@SideOnly(Side.CLIENT)
	public IIcon getSprite(CropCard cropCard, ICropTile crop);

	@SideOnly(Side.CLIENT)
	public void registerSprites(CropCard cropCard, IIconRegister iconRegister);

}
