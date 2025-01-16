package plugincrops.coremod;

import java.net.URL;
import java.util.ArrayList;

import net.minecraft.launchwrapper.IClassTransformer;
import plugincrops.coremod.ConfigurationPluginCrops.EnumFix;
import plugincrops.coremod.transformer.ClassTransformerReplaceMethodFromJar;
import plugincrops.coremod.util.HDeobf;
import cpw.mods.fml.common.FMLLog;

public class TransformerPluginCropsCoreMod implements IClassTransformer
{

	public static ArrayList<IClassTransformer> entries;

	private boolean initialized;

	@Override
	public byte[] transform(String name, String paramString2, byte[] bytes)
	{
		if (entries == null) {
			if (ConfigurationPluginCrops.initialized) {
				initialize();
			} else {
				return bytes;
			}
		}

		for (IClassTransformer entry : entries) {
			bytes = entry.transform(name, paramString2, bytes);
		}
		return bytes;
	}

	private void initialize()
	{
		String suffix = HDeobf.isDeobfuscated() ? "-dev" : "";
		urlJar = getClass().getResource("/assets/plugincrops_coremod/replacements/PluginCrops_Replacement-1.0" + suffix + ".jar");

		entries = new ArrayList<IClassTransformer>();

		new A(entries, EnumFix.growthBoostOfWartsIsDisabled)
			.entry(create("ic2.core.crop.CropNetherWart", "getrootslength", "(Lic2/api/crops/ICropTile;)I").setAddition(true))
			.entry(create("ic2.core.crop.CropTerraWart", "getrootslength", "(Lic2/api/crops/ICropTile;)I").setAddition(true));
		new A(entries, EnumFix.bugsOfAttemptCrossing)
			.entry(create("ic2.core.crop.TileEntityCrop", "attemptCrossing", "()Z"));
		new A(entries, EnumFix.growthDurationOfStickreed)
			.entry(create("ic2.core.crop.CropStickreed", "growthDuration", "(Lic2/api/crops/ICropTile;)I"));
		new A(entries, EnumFix.directivityOfWeed)
			.entry(create("ic2.core.crop.TileEntityCrop", "generateWeed", "()V"));
		new A(entries, EnumFix.under24GrowthOfWeed)
			.entry(create("ic2.api.crops.CropCard", "isWeed", "(Lic2/api/crops/ICropTile;)Z"));
		new A(entries, EnumFix.calculationOfPicking)
			.entry(create("ic2.core.crop.TileEntityCrop", "pick", "(Z)Z"));
		new A(entries, EnumFix.weedExIsNotSaved)
			.entry(create("ic2.core.crop.TileEntityCrop", "readFromNBT", "(Lnet/minecraft/nbt/NBTTagCompound;)V"))
			.entry(create("ic2.core.crop.TileEntityCrop", "writeToNBT", "(Lnet/minecraft/nbt/NBTTagCompound;)V"));
		new A(entries, EnumFix.directivityOfAirQuality)
			.entry(create("ic2.core.crop.TileEntityCrop", "updateAirQuality", "()B"));
		new A(entries, EnumFix.renderingApis)
			.entry(create("ic2.core.block.RenderBlockCrop", "renderBlockCropsImpl", "(Lnet/minecraft/util/IIcon;III)V").setServer(false));

	}

	private URL urlJar;

	private ClassTransformerReplaceMethodFromJar create(
		String targetClassName,
		String methodNameDeobfuscated,
		String signatureDeobfuscated)
	{
		return new ClassTransformerReplaceMethodFromJar(
			targetClassName,
			urlJar,
			methodNameDeobfuscated,
			signatureDeobfuscated);
	}

	private static class A
	{

		private ArrayList<IClassTransformer> entries;
		private EnumFix fix;

		public A(ArrayList<IClassTransformer> entries, EnumFix fix)
		{
			this.entries = entries;
			this.fix = fix;

			if (!fix.enabled) FMLLog.info("Class transformer entry `%s` was canseled", fix.name());
		}

		public A entry(IClassTransformer transformer)
		{
			if (fix.enabled) entries.add(transformer);
			return this;
		}

	}

}
