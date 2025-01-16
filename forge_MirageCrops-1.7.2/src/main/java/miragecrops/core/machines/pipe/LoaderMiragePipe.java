package miragecrops.core.machines.pipe;

import static miragecrops.api.framework.item.EnumToolMaterialHarvestLevels.*;

import java.util.Hashtable;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.MirageMaterialsManager;
import miragecrops.api.machines.ItemsModuleMachines;
import miragecrops.api.machines.MiragePipes;
import miragecrops.api.machines.TransportContents;
import miragecrops.core.MirageCrops;
import miragecrops.core.machines.ModuleMachines;
import miragecrops.framework.block.MetaBlock;
import miragecrops.framework.block.UnionBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LoaderMiragePipe
{

	@SideOnly(Side.CLIENT)
	public static RenderBlockMiragePipe renderBlockMiragePipe;

	public static void preInit(ModuleMachines moduleMachines)
	{

		TransportContents.electricalEnergy = new TransportContent("electricalEnergy");
		TransportContents.thermalEnergy = new TransportContent("thermalEnergy");
		TransportContents.refrigerationEnergy = new TransportContent("refrigerationEnergy");

		TransportContents.water = new TransportContent("water");
		TransportContents.lava = new TransportContent("lava");
		TransportContents.steam = new TransportContent("steam");
		TransportContents.fluorine = new TransportContent("fluorine");
		TransportContents.spinachExtract = new TransportContent("spinachExtract");

		TransportContents.item = new TransportContent("item");
		TransportContents.fluid = new TransportContent("fluid");

		MiragePipes.mirageAlloy = new MiragePipeMirageAlloy();

	}

	@SideOnly(Side.CLIENT)
	public static void registerRenderer(ModuleMachines moduleMachines)
	{

		{
			int renderId = RenderingRegistry.getNextAvailableRenderId();
			renderBlockMiragePipe = new RenderBlockMiragePipe(renderId);
			RenderingRegistry.registerBlockHandler(renderId, renderBlockMiragePipe);
		}

	}

	public static void registerBlocks(ModuleMachines module)
	{

		{
			UnionBlock<BlockMirageMiragePipe> ub = new UnionBlock<BlockMirageMiragePipe>(
				module.getMod(),
				BlockMirageMiragePipe.class,
				"MiragePipe",
				MirageCrops.creativeTab);
			ub.setClassItemBlockMeta(ItemBlockMetaMiragePipe.class);
			ItemsModuleMachines.unionBlockMiragePipe = ub;

			Class<?> c = ItemsModuleMachines.class;
			Hashtable<String, ItemStack> h = ItemsModuleMachines.materials;
			Class<MetaBlock> mc = MetaBlock.class;
			MetaBlock mb;

			{
				MetaBlockMiragePipe mb2 = (MetaBlockMiragePipe) module.registerMetaBlock(module, c, h, ub, "miragePipeMirageAlloy",
					new MetaBlockMiragePipe(ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto()));
				mb2.setBlockHardness(1.0F);
				mb2.setHarvestLevel("wrench", IRON);
				mb2.setTextureName(module.getMod().getModId() + ":" + module.getModuleName() + "/" + "machineMirageAlloyWild");
			}

			{
				MetaBlockMiragePipe mb2 = (MetaBlockMiragePipe) module.registerMetaBlock(module, c, h, ub, "miragePipeApatite",
					new MetaBlockMiragePipe(ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto()));
				mb2.setBlockHardness(1.0F);
				mb2.setHarvestLevel("wrench", IRON);
			}

			{
				MetaBlockMiragePipe mb2 = (MetaBlockMiragePipe) module.registerMetaBlock(module, c, h, ub, "miragePipeTopaz",
					new MetaBlockMiragePipe(ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto()));
				mb2.setBlockHardness(1.0F);
				mb2.setHarvestLevel("wrench", IRON);
			}

			{
				MetaBlockMiragePipe mb2 = (MetaBlockMiragePipe) module.registerMetaBlock(module, c, h, ub, "miragePipeObsidian",
					new MetaBlockMiragePipe(ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto()));
				mb2.setBlockHardness(1.0F);
				mb2.setHarvestLevel("wrench", IRON);
			}

			{
				MetaBlockMiragePipe mb2 = (MetaBlockMiragePipe) module.registerMetaBlock(module, c, h, ub, "miragePipeStainlessSteel",
					new MetaBlockMiragePipe(ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto()));
				mb2.setBlockHardness(1.0F);
				mb2.setHarvestLevel("wrench", IRON);
			}

		}

	}

	public static void registerRecipes()
	{

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.miragePipeMirageAlloy.createItemStack(3),
			"P P",
			"PWP",
			"P P",
			'P', MirageMaterialsManager.mirageAlloy.getDictionaryName(EnumShape.plate),
			'W', "craftingToolWrench"));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.miragePipeApatite.createItemStack(3),
			"PMP",
			"PMP",
			"PMP",
			'P', MirageMaterialsManager.apatite.getDictionaryName(EnumShape.gem),
			'M', "miragePipeMirageAlloy"));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.miragePipeTopaz.createItemStack(3),
			"PMP",
			"PMP",
			"PMP",
			'P', MirageMaterialsManager.topaz.getDictionaryName(EnumShape.gem),
			'M', "miragePipeMirageAlloy"));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.miragePipeObsidian.createItemStack(3),
			"PMP",
			"PMP",
			"PMP",
			'P', Blocks.obsidian,
			'M', "miragePipeMirageAlloy"));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.miragePipeStainlessSteel.createItemStack(3),
			"PMP",
			"PMP",
			"PMP",
			'P', MirageMaterialsManager.stainlessSteel.getDictionaryName(EnumShape.plate),
			'M', "miragePipeMirageAlloy"));

	}

}
