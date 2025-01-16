package miragecrops.core.crops;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;
import ic2.api.item.IC2Items;

import java.lang.reflect.Constructor;
import java.util.Map;

import miragecrops.api.crops.ItemsModuleCrops;
import miragecrops.api.framework.cropgainrecipe.ICropGainRecipe.ICropGainRecipeAdder;
import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.MirageMaterialsManager;
import miragecrops.core.MirageCrops;
import miragecrops.framework.StaticsCrop;
import miragecrops.framework.StaticsReflection;
import miragecrops.framework.crop.CropSpecification;
import miragecrops.framework.crop.CropTileImpl;
import miragecrops.framework.crop.IGainProvider;
import miragecrops.framework.crop.SGain;
import miragecrops.framework.crop.SIcon;
import miragecrops.framework.item.ItemMeta;
import miragecrops.framework.item.MetaItem;
import miragecrops.framework.item.SItemStack;
import miragecrops.framework.multiicon.IMultiIconShape;
import miragecrops.framework.multiicon.MultiIcon;
import miragecrops.framework.multiicon.MultiIconShape;
import mirrg.mir34.modding.IMod;
import mirrg.mir34.modding.IModule;
import mirrg.mir34.modding.ModuleAbstract;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Ores‚ÌŒã
 */
public class ModuleCrops extends ModuleAbstract
{

	public ModuleCrops(IMod mod)
	{
		super(mod);
	}

	@Override
	public String getModuleName()
	{
		return "crops";
	}

	@Override
	public void handle(FMLPreInitializationEvent event)
	{

		if (getMod().isClient()) {
			SEED_SPINACH = new MultiIconShape(getMod().getModId() + ":" + "multi/" + "seedSpinach",
				MultiIconShape.entry("", 1.0));
		}

		registerItems();

		registerCrops();
	}

	@Override
	public void handle(FMLInitializationEvent event)
	{
		registerRecipe();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClient(FMLPostInitializationEvent event)
	{

		new RecipeProviderCropCrossing(ItemsModuleCrops.leafSarraceniaNagae.createItemStack());

	}

	public void registerItems()
	{

		{
			ItemMeta item = new ItemMeta(getMod());
			String unlocalizedName = "multiItemCropGain";
			item.setUnlocalizedName(unlocalizedName);
			item.setCreativeTab(MirageCrops.creativeTab);
			GameRegistry.registerItem(item, unlocalizedName, getMod().getModId());
			ItemsModuleCrops.multiItemCropGain = item;

			int id = 1;
			Class<?> c = ItemsModuleCrops.class;
			registerMetaItem(this, c, null, item, id++, "leafSarracenia", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSarraceniaImmature", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSarraceniaLightning", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSarraceniaNagae", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSarraceniaDevil", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSarraceniaParn", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "fluoroberries", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSpinach", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSpinachRed", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSpinachBlue", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSpinachPurple", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSpinachFire", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafSpinachIce", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafMandrake", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafRose", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafRoseQuartz", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafCircuitReed", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafCactus", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafCactusObsidian", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "leafCactusSnow", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "wartGlass", MetaItem.class);
			registerMetaItem(this, c, null, item, id++, "pasteHoneydew", MetaItem.class);

		}

		((MetaItem) ItemsModuleCrops.multiItemCropGain.getMetaItem(0)).setTextureName(getMod().getModId() + ":" + getModuleName() + "/leafSpinach");

		{
			ItemMeta item = new ItemMeta(getMod());
			String unlocalizedName = "multiItemCrafting";
			item.setUnlocalizedName(unlocalizedName);
			item.setCreativeTab(MirageCrops.creativeTab);
			GameRegistry.registerItem(item, unlocalizedName, getMod().getModId());
			ItemsModuleCrops.multiItemCrafting = item;

			int id = 1;
			MetaItem mi;
			Class<?> c = ItemsModuleCrops.class;

			id++;
			mi = registerMetaItem(this, c, null, item, id, "seedSpinach", new MetaItem(item, id));
			if (getMod().isClient()) {
				if (!isExistingTextureResource(mi)) {
					mi.setMultiIcon(new MultiIcon(SEED_SPINACH, MirageMaterialsManager.spinachium.getColor()));
				}
			}

			id++;
			mi = registerMetaItem(this, c, null, item, id, "seedSpinachRed", new MetaItem(item, id));
			if (getMod().isClient()) {
				if (!isExistingTextureResource(mi)) {
					mi.setMultiIcon(new MultiIcon(SEED_SPINACH, 0xFF0000));
				}
			}

			id++;
			mi = registerMetaItem(this, c, null, item, id, "seedSpinachBlue", new MetaItem(item, id));
			if (getMod().isClient()) {
				if (!isExistingTextureResource(mi)) {
					mi.setMultiIcon(new MultiIcon(SEED_SPINACH, 0x0000FF));
				}
			}

			id++;
			mi = registerMetaItem(this, c, null, item, id, "seedSpinachPurple", new MetaItem(item, id));
			if (getMod().isClient()) {
				if (!isExistingTextureResource(mi)) {
					mi.setMultiIcon(new MultiIcon(SEED_SPINACH, 0xB200FF));
				}
			}

			id++;
			mi = registerMetaItem(this, c, null, item, id, "seedSpinachFire", new MetaItem(item, id));
			if (getMod().isClient()) {
				if (!isExistingTextureResource(mi)) {
					mi.setMultiIcon(new MultiIcon(SEED_SPINACH, 0xFF7130));
				}
			}

			id++;
			mi = registerMetaItem(this, c, null, item, id, "seedSpinachIce", new MetaItem(item, id));
			if (getMod().isClient()) {
				if (!isExistingTextureResource(mi)) {
					mi.setMultiIcon(new MultiIcon(SEED_SPINACH, 0xB5F1FF));
				}
			}

		}

	}

	private static IMultiIconShape SEED_SPINACH;

	private static boolean isExistingTextureResource(MetaItem metaItem)
	{
		return SItemStack.getURLFromTextureName(metaItem.getTextureName(), SItemStack.TYPE_ITEMS) != null;
	}

	private static MetaItem registerMetaItem(
		IModule module,
		Class<?> containerClazz,
		Map<String, ItemStack> containerOthers,
		ItemMeta itemMeta,
		int id,
		String dictionaryNameMeta,
		Class<? extends MetaItem> clazz)
	{
		MetaItem metaItem;
		try {
			Constructor<? extends MetaItem> constructor =
				clazz.getConstructor(ItemMeta.class, int.class);
			metaItem = constructor.newInstance(itemMeta, id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return registerMetaItem(module, containerClazz, containerOthers, itemMeta, id, dictionaryNameMeta, metaItem);
	}

	private static MetaItem registerMetaItem(
		IModule module,
		Class<?> containerClazz,
		Map<String, ItemStack> containerOthers,
		ItemMeta itemMeta,
		int id,
		String dictionaryNameMeta,
		MetaItem metaItem)
	{
		metaItem.setUnlocalizedName(dictionaryNameMeta);
		metaItem.setTextureName(module.getMod().getModId() + ":" + module.getModuleName() + "/" + dictionaryNameMeta);
		itemMeta.setMetaItem(id, metaItem);

		try {
			StaticsReflection.setStaticFieldValue(containerClazz, dictionaryNameMeta, metaItem);
		} catch (Exception e) {
			containerOthers.put(dictionaryNameMeta, metaItem.createItemStack());
		}

		OreDictionary.registerOre(dictionaryNameMeta, metaItem.createItemStack());

		return metaItem;
	}

	private void registerRecipe()
	{

		// #################### seedSpinachium

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleCrops.seedSpinach.createItemStack(2),
			"X",
			"X",
			'X', MirageMaterialsManager.spinachium.getDictionaryName(EnumShape.nugget)));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			ItemsModuleCrops.seedSpinachRed.createItemStack(),
			ItemsModuleCrops.seedSpinach.createItemStack(),
			MirageMaterialsManager.iron.getDictionaryName(EnumShape.nugget)));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			ItemsModuleCrops.seedSpinachBlue.createItemStack(),
			ItemsModuleCrops.seedSpinach.createItemStack(),
			MirageMaterialsManager.cobalt.getDictionaryName(EnumShape.nugget)));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			ItemsModuleCrops.seedSpinachPurple.createItemStack(),
			ItemsModuleCrops.seedSpinach.createItemStack(),
			MirageMaterialsManager.cadmium.getDictionaryName(EnumShape.nugget)));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			ItemsModuleCrops.seedSpinachFire.createItemStack(),
			ItemsModuleCrops.seedSpinach.createItemStack(),
			MirageMaterialsManager.magnesium.getDictionaryName(EnumShape.nugget)));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			ItemsModuleCrops.seedSpinachIce.createItemStack(),
			ItemsModuleCrops.seedSpinach.createItemStack(),
			MirageMaterialsManager.nickel.getDictionaryName(EnumShape.nugget)));

	}

	/**
	 * after item registration
	 */
	public void registerCrops()
	{
		int id;

		id = 60;
		StaticsCrop.putCrop(id, new CropSarracenia(new CropSpecification(2, 0, 3, 0, 1,
			"Lightning Sarracenia", "Mirrgie Riana", new String[] {
				"Sarracenia", "Light", "Magic",
			}, 4, 5, 3, 1.2f, 0.8f, 1.0f,
			SGain.normal(ItemsModuleCrops.leafSarracenia),
			SIcon.inherit(4, SIcon.iconStatus(getMod().getModId(), "Sarracenia"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Sarracenia", "Blue Spinach"), 15) {

			@Override
			protected float getDamageForEntity(ICropTile iCropTile, EntityLivingBase entityLivingBase)
			{
				if (entityLivingBase.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) return 3.0f;
				return 0;
			}

		});

		id = 61;
		StaticsCrop.putCrop(id, new CropCactus(new CropSpecification(0, 0, 2, 3, 2,
			"Cactus", "Mirrgie Riana", new String[] {
				"Cactus", "Green", "Desert",
			}, 1, 4, 2, 0.2f, 1.2f, 1.6f,
			SGain.normal(ItemsModuleCrops.leafCactus),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Cactus")));
		Crops.instance.registerBaseSeed(new ItemStack(Blocks.cactus, 1), id, 1, 1, 1, 1);

		id = 62;
		StaticsCrop.putCrop(id, new CropCactusObsidian(new CropSpecification(1, 0, 4, 1, 1,
			"Obsidian Cactus", "Mirrgie Riana", new String[] {
				"Cactus", "Purple", "Lava", "Fire", "Black", "Obsidian", "Magic",
			}, 4, 4, 2, 0.0f, 2.0f, 1.0f,
			SGain.normal(ItemsModuleCrops.leafCactusObsidian),
			SIcon.inherit(3, SIcon.iconStatus(getMod().getModId(), "Cactus"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Cactus", "Fire Spinach")));

		id = 63;
		StaticsCrop.putCrop(id, new CropCactusSnow(new CropSpecification(1, 0, 2, 1, 0,
			"Snow Cactus", "Mirrgie Riana", new String[] {
				"Cactus", "White", "Cyan", "Snow", "Ice", "Magic",
			}, 6, 5, 2, 1.0f, 0.4f, 1.6f, new IGainProvider() {

				@Override
				public ItemStack getGain(CropCard cropCard, ICropTile crop)
				{
					if (crop.getSize() == cropCard.maxSize()) {
						if (Math.random() < 0.2) return new ItemStack(Blocks.ice);
						if (Math.random() < 0.2) return new ItemStack(Blocks.snow);
						return new ItemStack(Items.snowball);
					} else {
						return ItemsModuleCrops.leafCactusSnow.createItemStack();
					}
				}

				@Override
				public void addRecipe(ICropGainRecipeAdder iRecipeAdder, CropCard cropCard, ItemStack debugCropSeed)
				{
					if (debugCropSeed.getItemDamage() == cropCard.maxSize()) {
						iRecipeAdder.addRecipe(debugCropSeed, new ItemStack(Blocks.ice));
						iRecipeAdder.addRecipe(debugCropSeed, new ItemStack(Blocks.snow));
						iRecipeAdder.addRecipe(debugCropSeed, new ItemStack(Items.snowball));
					} else {
						iRecipeAdder.addRecipe(debugCropSeed, ItemsModuleCrops.leafCactusSnow.createItemStack());
					}
				}

			},
			SIcon.inherit(3, SIcon.iconStatus(getMod().getModId(), "Cactus"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Cactus", "Ice Spinach")));

		id = 64;
		StaticsCrop.putCrop(id, new CropReedCircuit(new CropSpecification(10, 0, 0, 0, 0,
			"Circuit Reed", "Mirrgie Riana", new String[] {
				"Machine", "Circuit", "Industrial",
			}, 8, 4, 1, 1.0f, 1.0f, 1.0f,
			new IGainProvider() {

				@Override
				public ItemStack getGain(CropCard cropCard, ICropTile crop)
				{
					if (crop.getSize() == cropCard.maxSize()) return IC2Items.getItem("electronicCircuit");
					return new ItemStack(
						IC2Items.getItem("insulatedCopperCableItem").getItem(),
						(crop.getSize() - 1) * 2);
				}

				@Override
				public void addRecipe(ICropGainRecipeAdder iRecipeAdder, CropCard cropCard, ItemStack debugCropSeed)
				{
					iRecipeAdder.addRecipe(debugCropSeed,
						getGain(cropCard, new CropTileImpl(cropCard, (byte) debugCropSeed.getItemDamage())));
				}

			},
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Reed", "Blue Spinach", "Red Spinach", "Fire Spinach")));

		id = 65;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(10, 0, 0, 0, 0,
			"Matter Berries", "Mirrgie Riana", new String[] {
				"Matter", "Industrial",
			}, 14, 4, 3, 1.0f, 1.0f, 1.0f,
			SGain.normal(Items.slime_ball),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Ferru", "Blue Spinach", "Red Spinach", "Fire Spinach")));

		id = 66;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Rose Quartz", "Mirrgie Riana", new String[] {
				"Rose", "Cyan", "Pink", "Quartz", "White", "Flower", "Crystal",
			}, 7, 4, 2, 0.8f, 1.2f, 1.0f,
			SGain.normal(ItemsModuleCrops.leafRoseQuartz),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Rose", "Ice Spinach")));

		id = 67;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Matter Wart", "Mirrgie Riana", new String[] {
				"Matter", "Industrial",
			}, 15, 7, 1, 1.0f, 1.0f, 1.0f,
			SGain.normal(Items.slime_ball),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Nether Wart", "Matter Berries")));

		id = 68;
		StaticsCrop.putCrop(id, new CropFireWheat(new CropSpecification(0, 0, 0, 0, 0,
			"Fire Wheat", "Mirrgie Riana", new String[] {
				"Wheat", "Ashes", "White", "Blaze", "Fire", "Orange",
			}, 3, 9, 1, 1.0f, 1.0f, 1.0f,
			SGain.normal(Items.sugar),
			SIcon.inherit(4, SIcon.iconStatus("%s:crop/blockCrop.%s.%d", "ic2", "Wheat"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Wheat", "Fire Spinach")));

		{
			id = 69;
			CropSpecification cropSpecification = new CropSpecification(2, 0, 3, 0, 1,
				"Nagae Sarracenia", "Mirrgie Riana", new String[] {
					"Sarracenia", "Red", "Scarlet", "Fish", "Thunder",
				}, 5, 5, 3, 1.6f, 0.2f, 1.2f,
				SGain.random(
					SGain.normal(ItemsModuleCrops.leafSarraceniaNagae.createItemStack()),
					SGain.normal(ItemsModuleCrops.leafSarraceniaNagae.createItemStack()),
					SGain.normal(ItemsModuleCrops.leafSarraceniaNagae.createItemStack()),
					SGain.normal(new ItemStack(Items.fish, 1, 0)),
					SGain.random(
						new ItemStack(Items.fish, 1, 1),
						new ItemStack(Items.fish, 1, 2),
						new ItemStack(Items.fish, 1, 3))),
				SIcon.inherit(4, SIcon.iconStatus(getMod().getModId(), "Sarracenia"), SIcon.normalMirage(getMod().getModId())),
				CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING);
			cropSpecification.addListener(new CropSpecification.CropCardListenerImpl() {

				@Override
				public void tick(CropCard cropCard, ICropTile crop)
				{
					if (crop.getSize() >= cropCard.maxSize() - 1) {
						if (crop.getNutrientStorage() < 20 || crop.getSize() == cropCard.maxSize() - 1) {

							if (crop instanceof TileEntity) {
								TileEntity te = (TileEntity) crop;
								World world = crop.getWorld();

								//if (!world.isRemote) {
								if (world.isRaining() || world.isThundering()) {
									if (world.canBlockSeeTheSky(te.xCoord, te.yCoord + 1, te.zCoord)) {
										EntityLightningBolt entity = new EntityLightningBolt(
											world, te.xCoord, te.yCoord + 1, te.zCoord);

										world.addWeatherEffect(entity);

										crop.setNutrientStorage(Math.min(100, crop.getNutrientStorage() + 100));
										if (crop.getSize() == cropCard.maxSize() - 1) {
											crop.setSize((byte) cropCard.maxSize());
										}
										crop.getWorld().playAuxSFX(2005,
											crop.getLocation().posX,
											crop.getLocation().posY,
											crop.getLocation().posZ, 0);
									}
								}
								//}
							}
						}
					}
				}

			});

			StaticsCrop.putCrop(id, new CropSarracenia(cropSpecification,
				new CrossingRecipeOfNamesOrSelf("Lightning Sarracenia", "Blue Spinach"), 15) {

				@Override
				protected float getDamageForEntity(ICropTile iCropTile, EntityLivingBase entityLivingBase)
				{
					return 0;
				}

			});
		}

		id = 70;
		StaticsCrop.putCrop(
			id,
			new CropSarracenia(new CropSpecification(2, 0, 3, 0, 1,
				"Man Eating Sarracenia", "Mirrgie Riana", new String[] {
					"Sarracenia", "Pink", "Redstone", "Air", "Human",
				}, 8, 5, 3, 1.0f, 1.0f, 1.0f,
				SGain.normal(ItemsModuleCrops.leafSarraceniaParn),
				SIcon.inherit(4, SIcon.iconStatus(getMod().getModId(), "Sarracenia"), SIcon.normalMirage(getMod().getModId())),
				CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
				new CrossingRecipeOfNamesOrSelf("Sarracenia", "Red Spinach"), 4) {

				@Override
				protected float getDamageForEntity(ICropTile iCropTile, EntityLivingBase entityLivingBase)
				{
					if (entityLivingBase instanceof EntityZombie) return 3.0f;
					if (entityLivingBase instanceof EntityPlayerMP) return 2.0f;
					if (entityLivingBase instanceof EntityVillager) return 3.0f;
					return 0;
				}

			});

		id = 71;
		StaticsCrop.putCrop(id, new CropSarracenia(new CropSpecification(2, 0, 3, 0, 1,
			"Devil Sarracenia", "Mirrgie Riana", new String[] {
				"Hyme", "Red", "Scarlet", "Redstone",
			}, 17, 5, 3, 1.8f, 1.7f, 2.0f,
			SGain.normal(ItemsModuleCrops.leafSarraceniaDevil),
			SIcon.inherit(4, SIcon.iconStatus(getMod().getModId(), "Sarracenia"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Man Eating Sarracenia", "Red Spinach"), 7) {

			@Override
			protected float getDamageForEntity(ICropTile iCropTile, EntityLivingBase entityLivingBase)
			{
				if (entityLivingBase instanceof EntityAnimal) return 3.0f;
				if (entityLivingBase instanceof EntityPlayerMP) return 2.0f;
				return 0;
			}

		});

		id = 72;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Vine", "Mirrgie Riana", new String[] {
				"Vine", "Green",
			}, 1, 4, 2, 1.4f, 0.6f, 1.2f,
			SGain.normal(Blocks.cactus),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Vine")));

		id = 73;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Enochranks", "Mirrgie Riana", new String[] {
				"Vine", "Green", "Blue", "Apatite",
			}, 5, 4, 2, 1.4f, 0.6f, 1.2f,
			SGain.normal(Blocks.cactus),
			SIcon.inherit(3, SIcon.iconStatus(getMod().getModId(), "Vine"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Vine", "Blue Spinach")));

		id = 74;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Fern", "Mirrgie Riana", new String[] {
				"Fern", "Grass", "Green",
			}, 1, 4, 1, 1.0f, 1.0f, 1.0f,
			SGain.normal(Blocks.cactus),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Fern")));

		id = 75;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Honey Fern", "Mirrgie Riana", new String[] {
				"Fern", "Grass", "Green", "Honeydew",
			}, 3, 5, 1, 1.0f, 1.0f, 1.0f,
			SGain.normal(ItemsModuleCrops.pasteHoneydew),
			SIcon.inherit(4, SIcon.iconStatus(getMod().getModId(), "Fern"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Fern", "Red Spinach")));

		id = 76;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Spinach", "Mirrgie Riana", new String[] {
				"Green", "Herb", "Magic",
			}, 3, 4, 2, 1.0f, 1.0f, 1.0f,
			SGain.normal(ItemsModuleCrops.leafSpinach),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Spinach")));
		Crops.instance.registerBaseSeed(ItemsModuleCrops.seedSpinach.createItemStack(), id, 1, 1, 1, 1);

		id = 77;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Red Spinach", "Mirrgie Riana", new String[] {
				"Red", "Undead", "Spinach", "Magic",
			}, 4, 4, 2, 1.0f, 1.0f, 1.0f,
			SGain.normal(ItemsModuleCrops.leafSpinachRed),
			SIcon.inherit(3, SIcon.iconStatus(getMod().getModId(), "Spinach"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Red Spinach")));
		Crops.instance.registerBaseSeed(ItemsModuleCrops.seedSpinachRed.createItemStack(), id, 1, 1, 1, 1);

		id = 78;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Blue Spinach", "Mirrgie Riana", new String[] {
				"Blue", "Spinach", "Magic",
			}, 5, 4, 2, 1.0f, 1.0f, 1.0f,
			SGain.normal(ItemsModuleCrops.leafSpinachBlue),
			SIcon.inherit(3, SIcon.iconStatus(getMod().getModId(), "Spinach"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Blue Spinach")));
		Crops.instance.registerBaseSeed(ItemsModuleCrops.seedSpinachBlue.createItemStack(), id, 1, 1, 1, 1);

		id = 79;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Purple Spinach", "Mirrgie Riana", new String[] {
				"Purple", "Toxic", "Spinach", "Magic",
			}, 6, 4, 2, 1.0f, 1.0f, 1.0f,
			SGain.normal(ItemsModuleCrops.leafSpinachPurple),
			SIcon.inherit(3, SIcon.iconStatus(getMod().getModId(), "Spinach"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Purple Spinach")));
		Crops.instance.registerBaseSeed(ItemsModuleCrops.seedSpinachPurple.createItemStack(), id, 1, 1, 1, 1);

		id = 80;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Mandrake", "Mirrgie Riana", new String[] {
				"Mandrake", "Green", "Magic",
			}, 7, 4, 2, 1.2f, 0.6f, 1.2f,
			SGain.normal(ItemsModuleCrops.leafMandrake),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Spinach", "Sarracenia")));

		id = 81;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Fluoroberries", "Mirrgie Riana", new String[] {
				"Fluorine", "Fluorite", "Sepia", "Toxic", "Berry", "Apatite",
			}, 11, 4, 3, 1.4f, 1.4f, 1.4f,
			SGain.normal(ItemsModuleCrops.fluoroberries),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Enochranks", "Purple Spinach")));

		id = 82;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Glass Wart", "Mirrgie Riana", new String[] {
				"Glass", "Industrial",
			}, 6, 3, 1, 1.0f, 1.0f, 1.0f,
			SGain.random(
				SGain.normal(ItemsModuleCrops.wartGlass),
				SGain.normal(ItemsModuleCrops.wartGlass),
				SGain.normal(ItemsModuleCrops.wartGlass),
				SGain.normal(Items.glass_bottle)),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Nether Wart", "Blue Spinach", "Red Spinach", "Fire Spinach")));

		id = 83;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Java Coffee", "Mirrgie Riana", new String[] {
				"Coffee",
			}, 4, 4, 3, 1.0f, 1.0f, 1.0f,
			SGain.normal(Blocks.cactus),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Coffee", "Spinach")));

		id = 84;
		StaticsCrop.putCrop(id, new CropSarracenia(new CropSpecification(2, 0, 3, 0, 1,
			"Sarracenia", "Mirrgie Riana", new String[] {
				"Sarracenia", "Light", "Magic",
			}, 4, 5, 3, 1.2f, 0.8f, 1.0f,
			SGain.normal(ItemsModuleCrops.leafSarracenia),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Spinach"), 0));

		id = 85;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(2, 0, 3, 0, 1,
			"Rice", "Mirrgie Riana", new String[] {
				"Rice",
			}, 2, 7, 3, 0.8f, 1.8f, 0.8f,
			SGain.normal(Items.wheat),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Wheat", "Spinach")));

		id = 86;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(2, 0, 3, 0, 1,
			"Chrysanthum", "Mirrgie Riana", new String[] {
				"Chrysanthum",
			}, 2, 4, 2, 1.0f, 1.0f, 1.0f,
			SGain.normal(Items.wheat),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Spinach")));

		id = 87;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(2, 0, 3, 0, 1,
			"Iridium Chrysanthum", "Mirrgie Riana", new String[] {
				"Chrysanthum", "Iridium Chrysanthum", "Metal",
			}, 16, 4, 2, 1.0f, 1.0f, 1.0f,
			SGain.normal(Items.wheat),
			SIcon.normalMirage(getMod().getModId()),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Chrysanthum", "Purple Spinach")));

		id = 88;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Fire Spinach", "Mirrgie Riana", new String[] {
				"Fire", "Spinach", "Magic",
			}, 7, 4, 2, 0.0f, 1.0f, 2.0f,
			SGain.normal(ItemsModuleCrops.leafSpinachFire),
			SIcon.inherit(3, SIcon.iconStatus(getMod().getModId(), "Spinach"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Fire Spinach")));
		Crops.instance.registerBaseSeed(ItemsModuleCrops.seedSpinachFire.createItemStack(), id, 1, 1, 1, 1);

		id = 89;
		StaticsCrop.putCrop(id, new CropCrossingRecipe(new CropSpecification(0, 0, 0, 0, 0,
			"Ice Spinach", "Mirrgie Riana", new String[] {
				"Ice", "Spinach", "Magic",
			}, 6, 4, 2, 2.0f, 0.5f, 0.5f,
			SGain.normal(ItemsModuleCrops.leafSpinachIce),
			SIcon.inherit(3, SIcon.iconStatus(getMod().getModId(), "Spinach"), SIcon.normalMirage(getMod().getModId())),
			CropSpecification.EnumTramplingResistance.HAPPEN_IF_SPRINTING),
			new CrossingRecipeOfNamesOrSelf("Ice Spinach")));
		Crops.instance.registerBaseSeed(ItemsModuleCrops.seedSpinachIce.createItemStack(), id, 1, 1, 1, 1);

	}
}
