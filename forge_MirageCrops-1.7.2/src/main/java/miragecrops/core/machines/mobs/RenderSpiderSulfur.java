package miragecrops.core.machines.mobs;

import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpiderSulfur extends RenderSpider
{

	static final ResourceLocation spiderSulfurTextures =
		new ResourceLocation("miragecrops:textures/entity/SpiderSulfur.png");

	public RenderSpiderSulfur()
	{
		this.shadowSize *= 0.8F;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase paramEntityCaveSpider, float paramFloat)
	{
		GL11.glScalef(0.8F, 0.8F, 0.8F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySpider paramEntityCaveSpider)
	{
		return spiderSulfurTextures;
	}

}
