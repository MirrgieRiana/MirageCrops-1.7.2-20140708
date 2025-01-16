package miragecrops.core.machines.machine;

import static miragecrops.api.framework.item.EnumToolMaterialHarvestLevels.*;

import java.util.Hashtable;

import miragecrops.api.machines.ItemsModuleMachines;
import miragecrops.core.MirageCrops;
import miragecrops.core.machines.ModuleMachines;
import miragecrops.core.machines.machine.framework.BlockMetaMirageMachine;
import miragecrops.core.machines.machine.framework.ItemBlockMetaMirageMachine;
import miragecrops.core.machines.machine.framework.MetaBlockMirageMachine;
import miragecrops.core.machines.machine.framework.TileEntityProvider;
import miragecrops.core.machines.machine.tank.BlockMetaWithRenderer;
import miragecrops.core.machines.machine.tank.ItemBlockMetaWithRenderer;
import miragecrops.core.machines.machine.tank.MetaBlockMirageTank;
import miragecrops.core.machines.machine.tank.RenderBlockMirageTank;
import miragecrops.framework.block.MetaBlock;
import miragecrops.framework.block.UnionBlock;
import mirrg.h.struct.Struct1;
import mirrg.mir34.modding.IModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LoaderMirageMachine
{

	@SideOnly(Side.CLIENT)
	public static RenderBlockMirageTank renderBlockTank;

	public static Struct1<Integer> renderTypeBlockTank = new Struct1<Integer>(0);

	public static IModule module;

	public static void preInit(IModule module)
	{
		LoaderMirageMachine.module = module;
	}

	public static void registerBlocks(ModuleMachines module)
	{

		{
			UnionBlock<BlockMetaWithRenderer> ub = new UnionBlock<BlockMetaWithRenderer>(
				module.getMod(),
				BlockMetaWithRenderer.class,
				"MirageTank",
				MirageCrops.creativeTab) {

				@Override
				public void onBlockMetaCreated(BlockMetaWithRenderer blockMeta, int blockMetaIndex)
				{
					super.onBlockMetaCreated(blockMeta, blockMetaIndex);
					blockMeta.setRenderType(renderTypeBlockTank);
				}

			};
			ub.setClassItemBlockMeta(ItemBlockMetaWithRenderer.class);
			ItemsModuleMachines.unionBlockMirageTank = ub;

			Class<?> c = ItemsModuleMachines.class;
			Hashtable<String, ItemStack> h = ItemsModuleMachines.materials;
			Class<MetaBlock> mc = MetaBlock.class;
			MetaBlock mb;

			GameRegistry.registerTileEntity(TileEntityMirageTank.class, "MirageTank");

			{
				MetaBlockMirageTank mb2 = (MetaBlockMirageTank) module.registerMetaBlock(module, c, h, ub, "mirageTankMirageAlloy",
					new MetaBlockMirageTank(
						ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto(),
						new TileEntityProvider(TileEntityMirageTank.class)));
				mb2.setBlockHardness(3.0F);
				mb2.setHarvestLevel("wrench", IRON);
				mb2.setTextureName(module.getMod().getModId() + ":" + module.getModuleName() + "/" + "machineMirageAlloyTank");
				mb2.iconNameEdge = module.getMod().getModId() + ":" + module.getModuleName() + "/" + "machineMirageAlloyFlat";
				mb2.iconNameTop = module.getMod().getModId() + ":" + module.getModuleName() + "/" + "machineMirageAlloyWild";
				mb2.iconNameBottom = module.getMod().getModId() + ":" + module.getModuleName() + "/" + "machineMirageAlloyFlat";
			}

		}

		{
			UnionBlock<BlockMetaMirageMachine> ub = new UnionBlock<BlockMetaMirageMachine>(
				module.getMod(),
				BlockMetaMirageMachine.class,
				"MirageMachine",
				MirageCrops.creativeTab);
			ub.setClassItemBlockMeta(ItemBlockMetaMirageMachine.class);
			ItemsModuleMachines.unionBlockMirageMachine = ub;

			Class<?> c = ItemsModuleMachines.class;
			Hashtable<String, ItemStack> h = ItemsModuleMachines.materials;
			Class<MetaBlock> mc = MetaBlock.class;
			MetaBlock mb;

			GameRegistry.registerTileEntity(TileEntityMirageMachineExtends.class, "MirageMachine");

			{
				MetaBlockMirageMachine mb2 = (MetaBlockMirageMachine) module.registerMetaBlock(module, c, h, ub, "mirageMachineMirageAlloy",
					new MetaBlockMirageMachine(
						ub.getNextBlockMetaThatShouldBeCreatedInto(),
						ub.getNextMetaIdThatShouldBeCreatedInto(),
						new TileEntityProvider(TileEntityMirageMachineExtends.class)));
				mb2.setBlockHardness(1.0F);
				mb2.setHarvestLevel("wrench", IRON);
				mb2.setTextureName(module.getMod().getModId() + ":" + module.getModuleName() + "/" + "machineMirageAlloy");
			}

		}

	}

	@SideOnly(Side.CLIENT)
	public static void registerRenderer(ModuleMachines moduleMachines)
	{

		{
			int renderId = RenderingRegistry.getNextAvailableRenderId();
			renderBlockTank = new RenderBlockMirageTank(renderId);
			RenderingRegistry.registerBlockHandler(renderId, renderBlockTank);
			renderTypeBlockTank.setX(renderBlockTank.getRenderId());
		}

	}

	public static void registerRecipes()
	{

		GameRegistry.addRecipe(new ShapedOreRecipe(
			ItemsModuleMachines.mirageTankMirageAlloy.createItemStack(),
			"P P",
			"PWP",
			"PMP",
			'P', "plateMirageAlloy",
			'M', "craftingRawMachineTier01",
			'W', "craftingToolWrench"));

	}

}
