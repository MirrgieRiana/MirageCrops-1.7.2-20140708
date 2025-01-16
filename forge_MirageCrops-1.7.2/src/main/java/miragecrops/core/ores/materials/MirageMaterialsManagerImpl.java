package miragecrops.core.ores.materials;

import java.util.ArrayList;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.IMirageMaterial;
import miragecrops.api.framework.material.IMirageMaterialsManager;
import miragecrops.api.framework.material.MirageMaterialsManager;
import miragecrops.api.ores.ItemsModuleOres;
import miragecrops.core.ores.ModuleOres;
import miragecrops.core.ores.material.MirageMaterial;
import miragecrops.framework.StaticsReflection;
import miragecrops.framework.StaticsString;
import miragecrops.framework.block.BlockMeta;
import miragecrops.framework.block.MetaBlock;
import miragecrops.framework.block.UnionBlock;
import miragecrops.framework.item.ItemMeta;
import miragecrops.framework.item.MetaItem;
import miragecrops.framework.item.SItemStack;
import miragecrops.framework.material.IFactory;
import miragecrops.framework.material.IMirageMaterialBlock;
import miragecrops.framework.multiicon.IMultiIconShape;
import miragecrops.framework.multiicon.MultiIcon;
import mirrg.mir34.modding.IModule;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class MirageMaterialsManagerImpl implements IMirageMaterialsManager
{

	public ArrayList<MirageMaterial> values = new ArrayList<MirageMaterial>();
	private IModule module;

	public MirageMaterialsManagerImpl(IModule module)
	{
		this.module = module;
	}

	@Override
	public int getMirageMaterialsCount()
	{
		return values.size();
	}

	@Override
	public IMirageMaterial getMirageMaterial(int ordinal)
	{
		return values.get(ordinal);
	}

	@Override
	public int getOrdinalFromMirageMaterial(IMirageMaterial iMirageMaterial)
	{
		return values.indexOf(iMirageMaterial);
	}

	public IModule getModule()
	{
		return module;
	}

	// ------------------------------------------------------------------

	public void addMirageMaterial(MirageMaterial mirageMaterial)
	{
		mirageMaterial.initTableRegister();
		values.add(mirageMaterial);

		StaticsReflection.setStaticFieldValue(MirageMaterialsManager.class, mirageMaterial.name(), mirageMaterial);
	}

	public ItemMeta registerItemsOfShape(final EnumShape enumShape, final IMultiIconShape iMultiIconShape)
	{
		final ItemMeta itemMeta = new ItemMeta(module.getMod());
		String unlocalizedName = "multiItem" + StaticsString.toUpperCaseHead(enumShape.name());
		itemMeta.setUnlocalizedName(unlocalizedName);
		itemMeta.setCreativeTab(ModuleOres.creativeTabMaterials);
		GameRegistry.registerItem(itemMeta, unlocalizedName, getModule().getMod().getModId());

		int id = 1;
		for (final MirageMaterial enumMaterial : values) {
			if (enumMaterial.isProvidingShapeItem(enumShape)) {
				final int idFinal = id;
				enumMaterial.registerShapeItem(new IFactory<MetaItem>() {

					@Override
					public MetaItem create()
					{
						MetaItemMirageMaterial metaItem = new MetaItemMirageMaterial(itemMeta, idFinal);
						itemMeta.setMetaItem(idFinal, metaItem);
						String dictionaryNameMeta = enumMaterial.getDictionaryName(enumShape);
						ItemsModuleOres.materials.put(dictionaryNameMeta, metaItem.createItemStack());
						OreDictionary.registerOre(dictionaryNameMeta, metaItem.createItemStack());

						metaItem.setDescription(enumMaterial.getChemicalFormula());
						metaItem.setUnlocalizedName(dictionaryNameMeta);
						metaItem.setMaterialNameAndShapeName(enumMaterial.name(), enumShape.name());

						metaItem.setTextureName(getModule().getMod().getModId() + ":" + getModule().getModuleName() + "/" + dictionaryNameMeta);
						if (!SItemStack.isExistingTextureResource(metaItem)) {
							metaItem.setMultiIcon(new MultiIcon(iMultiIconShape, enumMaterial.getColor()));
						}

						return metaItem;
					}

				}, enumShape);
			}
			id++;
		}

		return itemMeta;
	}

	public <T extends BlockMeta> UnionBlock<T> registerBlocksOfShepe(Class<T> classBlockMeta, final EnumShape enumShape)
	{

		final UnionBlock<T> unionBlock = new UnionBlock<T>(
			getModule().getMod(),
			classBlockMeta,
			StaticsString.toUpperCaseHead(enumShape.name()),
			ModuleOres.creativeTabMaterials);

		for (final MirageMaterial enumMaterial : values) {
			if (enumMaterial.isProvidingShapeBlock(enumShape)) {
				enumMaterial.registerShapeBlock(new IFactory<MetaBlock>() {

					@Override
					public MetaBlock create()
					{
						MetaBlockMirageMaterial metaBlock = new MetaBlockMirageMaterial(
							unionBlock.getNextBlockMetaThatShouldBeCreatedInto(),
							unionBlock.getNextMetaIdThatShouldBeCreatedInto());
						unionBlock.add(metaBlock);
						String dictionaryNameMeta = enumMaterial.getDictionaryName(enumShape);
						ItemsModuleOres.materials.put(dictionaryNameMeta, metaBlock.createItemStack());
						OreDictionary.registerOre(dictionaryNameMeta, metaBlock.createItemStack());

						metaBlock.setDescription(enumMaterial.getChemicalFormula());
						metaBlock.setUnlocalizedName(dictionaryNameMeta);
						metaBlock.setMaterialNameAndShapeName(enumMaterial.name(), enumShape.name());

						metaBlock.setTextureName(getModule().getMod().getModId() + ":" + getModule().getModuleName() + "/" + dictionaryNameMeta);

						metaBlock.setBlockHardness(1.5F);
						metaBlock.setHarvestLevel("pickaxe", IMirageMaterialBlock.Helper.getBlockHarvestLevel(enumMaterial));

						return metaBlock;
					}

				}, enumShape);
			}
		}

		return unionBlock;
	}

	public void registerRecipe()
	{

		for (MirageMaterial enumMaterial : values) {
			enumMaterial.registerRecipe();
		}

	}

}
