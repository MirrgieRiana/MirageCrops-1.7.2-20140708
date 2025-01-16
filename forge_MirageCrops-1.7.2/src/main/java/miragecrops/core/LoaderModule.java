package miragecrops.core;

import java.lang.reflect.InvocationTargetException;

import miragecrops.core.biome.ModuleBiome;
import miragecrops.core.crops.ModuleCrops;
import miragecrops.core.debugcropseeds.ModuleDebugCropSeeds;
import miragecrops.core.machines.ModuleMachines;
import mirrg.mir34.modding.ILoaderModule;
import mirrg.mir34.modding.IMod;
import mirrg.mir34.modding.ModuleAbstract;

public class LoaderModule implements ILoaderModule
{

	@Override
	public void loadModule(IMod iMod)
	{
		try {
			iMod.addModule((ModuleAbstract) Class.forName("miragecrops.core.ores.ModuleOres").getConstructor(IMod.class).newInstance(iMod));
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		iMod.addModule(new ModuleDebugCropSeeds(iMod));
		iMod.addModule(new ModuleCrops(iMod));
		iMod.addModule(new ModuleMachines(iMod));
		iMod.addModule(new ModuleBiome(iMod));
	}

}
