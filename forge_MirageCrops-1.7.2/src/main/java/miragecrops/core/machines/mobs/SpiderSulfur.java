package miragecrops.core.machines.mobs;

import java.util.ArrayList;
import java.util.HashMap;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.MirageMaterialsManager;
import miragecrops.api.machines.ItemsModuleMachines;
import mirrg.mir34.modding.IMod;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SpiderSulfur
{

	public static class EntitySpiderSulfur extends EntitySpider
	{

		private ItemStack dropItem = MirageMaterialsManager.sulfur.copyItemStack(EnumShape.dust);
		private ItemStack dropItemRare = ItemsModuleMachines.spikeIron.createItemStack();

		public EntitySpiderSulfur(World p_i1732_1_)
		{
			super(p_i1732_1_);
			setSize(1.1F, 0.6F);
		}

		@Override
		public void onLivingUpdate()
		{
			super.onLivingUpdate();

			if (this.rand.nextInt(2) == 0) {
				this.worldObj.spawnParticle("smoke",
					this.posX + 0.7 * MathHelper.sin((float) (renderYawOffset / 180 * Math.PI)) * this.width,
					this.posY + 0.7 * this.height,
					this.posZ - 0.7 * MathHelper.cos((float) (renderYawOffset / 180 * Math.PI)) * this.width,
					+this.rand.nextFloat() * 0.07 * MathHelper.sin((float) (renderYawOffset / 180 * Math.PI)) * this.width,
					+this.rand.nextFloat() * 0.07 * this.height,
					-this.rand.nextFloat() * 0.07 * MathHelper.cos((float) (renderYawOffset / 180 * Math.PI)) * this.width);
			}
		}

		@Override
		protected void applyEntityAttributes()
		{
			super.applyEntityAttributes();

			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.5);
			getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(20);
		}

		@Override
		protected void dropFewItems(boolean paramBoolean, int paramInt)
		{
			{
				int j = this.rand.nextInt(3);

				if (paramInt > 0)
				{
					j += this.rand.nextInt(paramInt + 1);
				}

				for (int k = 0; k < j; ++k)
				{
					entityDropItem(dropItem, 0);
				}
			}

			if (paramBoolean) {
				if (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + paramInt) > 0) {
					entityDropItem(dropItemRare, 0);
				}
			}
		}

		@Override
		public IEntityLivingData onSpawnWithEgg(IEntityLivingData paramIEntityLivingData)
		{
			return paramIEntityLivingData;
		}

	}

	@SideOnly(Side.CLIENT)
	public static void initClient(IMod mod)
	{
		RenderSpiderSulfur renderSpiderSulfur = new RenderSpiderSulfur();
		renderSpiderSulfur.setRenderManager(RenderManager.instance);
		RenderManager.instance.entityRenderMap.put(EntitySpiderSulfur.class, renderSpiderSulfur);
	}

	public static void init(IMod mod)
	{

		int globalId;
		{
			HashMap<Integer, EntityEggInfo> entityEggs = EntityList.entityEggs;

			int i = 120;
			while (true) {

				if (!entityEggs.containsKey(Integer.valueOf(i))) {
					break;
				}

				i++;
			}

			globalId = i;
		}

		EntityRegistry.registerModEntity(EntitySpiderSulfur.class, "SpiderSulfur", globalId, mod, 128, 1, true);
		EntityList.addMapping(EntitySpiderSulfur.class, "SpiderSulfur", globalId, 0xF0ECB6, 0xE8D050);

		{
			BiomeGenBase[] biomeGenArray = BiomeGenBase.getBiomeGenArray();
			ArrayList<BiomeGenBase> biomeGenList = new ArrayList<BiomeGenBase>();

			for (BiomeGenBase biomeGen : biomeGenArray) {
				if (biomeGen != null) {
					if (biomeGen.rainfall < 0.1) {
						biomeGenList.add(biomeGen);
					}
				}
			}

			EntityRegistry.addSpawn(
				EntitySpiderSulfur.class,
				100,
				1,
				8,
				EnumCreatureType.monster,
				biomeGenList.toArray(new BiomeGenBase[biomeGenList.size()]));

		}

	}

}
