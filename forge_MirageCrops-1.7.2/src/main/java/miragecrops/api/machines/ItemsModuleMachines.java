package miragecrops.api.machines;

import java.util.Hashtable;

import miragecrops.api.framework.block.IBlockMeta;
import miragecrops.api.framework.block.IMetaBlock;
import miragecrops.api.framework.block.IUnionBlock;
import miragecrops.api.framework.item.IItemMeta;
import miragecrops.api.framework.item.IMetaItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class ItemsModuleMachines
{

	public static final Hashtable<String, ItemStack> materials = new Hashtable<String, ItemStack>();

	public static Block superdirt;

	public static IUnionBlock<? extends IBlockMeta> unionBlockBlockMachine;
	public static IMetaBlock blockMachineMirageAlloy;
	public static IMetaBlock blockMachineMirageAlloyFlat;
	public static IMetaBlock blockMachineMirageAlloyWild;
	public static IMetaBlock blockMachineApatite;
	public static IMetaBlock blockMachineApatiteFlat;
	public static IMetaBlock blockMachineMirageAlloyHighly;

	public static IUnionBlock<? extends IBlockMeta> unionBlockMachine;
	public static IMetaBlock machineMirageAlloyFurnace;
	public static IMetaBlock machineMirageAlloyCropHarvester;
	public static IMetaBlock machineMirageAlloySmallBlastFurnace;
	public static IMetaBlock machineMirageAlloyController;

	public static IUnionBlock<? extends IBlockMeta> unionBlockMiragePipe;
	public static IMetaBlock miragePipeMirageAlloy;
	public static IMetaBlock miragePipeApatite;
	public static IMetaBlock miragePipeTopaz;
	public static IMetaBlock miragePipeObsidian;
	public static IMetaBlock miragePipeStainlessSteel;

	public static IUnionBlock<? extends IBlockMeta> unionBlockMirageTank;
	public static IMetaBlock mirageTankMirageAlloy;

	public static IUnionBlock<? extends IBlockMeta> unionBlockMirageMachine;
	public static IMetaBlock mirageMachineMirageAlloy;

	public static IItemMeta multiItemMachines;
	public static IMetaItem sawbladeIron;
	public static IMetaItem spikeIron;
	public static IMetaItem bucketSpinachjuice;

	public static Fluid spinachjuice;

}
