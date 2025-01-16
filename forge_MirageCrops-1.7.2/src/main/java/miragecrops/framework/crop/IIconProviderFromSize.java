package miragecrops.framework.crop;

import ic2.api.crops.CropCard;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IIconProviderFromSize extends IIconProvider
{

	@SideOnly(Side.CLIENT)
	public void registerSpritesReady(CropCard cropCard, IIconRegister iconRegister);

	@SideOnly(Side.CLIENT)
	public void registerSprites(CropCard cropCard, IIconRegister iconRegister, int size);

}
