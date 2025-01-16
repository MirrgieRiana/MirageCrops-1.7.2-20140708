package miragecrops.core.ores;

import miragecrops.api.framework.material.EnumShape;
import miragecrops.api.framework.material.MirageMaterialsManager;
import miragecrops.framework.worldgen.FilterBiome;
import miragecrops.framework.worldgen.IWorldGeneratorXZ;
import miragecrops.framework.worldgen.WorldGeneratorXZOre;
import miragecrops.framework.worldgen.WorldGeneratorXZOre.CountPer;
import miragecrops.framework.worldgen.WorldGeneratorXYZOre;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModuleWorldGen
{

	/**
	 * registerBlocks‚ÌŒã
	 */
	static void registerWorldgen()
	{
		IWorldGeneratorXZ iWorldGeneratorXY;

		iWorldGeneratorXY = new WorldGeneratorXZOre(CountPer.CUBE, 2.0, 0, 128,
			new WorldGeneratorXYZOre(8, MirageMaterialsManager.calcite.copyItemStack(EnumShape.ore)));
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		//

		iWorldGeneratorXY = new WorldGeneratorXZOre(CountPer.CUBE, 1.4, 0, 96,
			new WorldGeneratorXYZOre(4, MirageMaterialsManager.magnesite.copyItemStack(EnumShape.ore)));
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		iWorldGeneratorXY = new WorldGeneratorXZOre(CountPer.CUBE, 1.2, 0, 80,
			new WorldGeneratorXYZOre(3.5, MirageMaterialsManager.siderite.copyItemStack(EnumShape.ore)));
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		iWorldGeneratorXY = new WorldGeneratorXZOre(CountPer.CUBE, 1.0, 0, 64,
			new WorldGeneratorXYZOre(3, MirageMaterialsManager.smithsonite.copyItemStack(EnumShape.ore)));
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		//

		iWorldGeneratorXY = new FilterBiome(new WorldGeneratorXZOre(CountPer.CUBE, 0.6, 0, 48,
			new WorldGeneratorXYZOre(2.5, MirageMaterialsManager.rhodochrosite.copyItemStack(EnumShape.ore))),
			"ocean");
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		iWorldGeneratorXY = new FilterBiome(new WorldGeneratorXZOre(CountPer.CUBE, 0.5, 0, 32,
			new WorldGeneratorXYZOre(2, MirageMaterialsManager.sphaerocobaltite.copyItemStack(EnumShape.ore))),
			"forest");
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		iWorldGeneratorXY = new FilterBiome(new WorldGeneratorXZOre(CountPer.CUBE, 0.4, 0, 24,
			new WorldGeneratorXYZOre(2, MirageMaterialsManager.gaspeite.copyItemStack(EnumShape.ore))),
			"desert");
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		//

		iWorldGeneratorXY = new FilterBiome(new WorldGeneratorXZOre(CountPer.CUBE, 0.2, 0, 16,
			new WorldGeneratorXYZOre(1.5, MirageMaterialsManager.otavite.copyItemStack(EnumShape.ore))),
			"extreme");
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		//
		//

		iWorldGeneratorXY = new WorldGeneratorXZOre(CountPer.CUBE, 2.0, 0, 60,
			new WorldGeneratorXYZOre(4, MirageMaterialsManager.bismuth.copyItemStack(EnumShape.ore)));
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		iWorldGeneratorXY = new WorldGeneratorXZOre(CountPer.CUBE, 8.0, 0, 120,
			new WorldGeneratorXYZOre(4, MirageMaterialsManager.spinatite.copyItemStack(EnumShape.ore)));
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		//
		//

		iWorldGeneratorXY = new FilterBiome(new WorldGeneratorXZOre(CountPer.CUBE, 1.0, 64, 128,
			new WorldGeneratorXYZOre(8, MirageMaterialsManager.apatite.copyItemStack(EnumShape.ore))),
			"extreme");
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

		iWorldGeneratorXY = new FilterBiome(new WorldGeneratorXZOre(CountPer.CUBE, 1.0, 64, 128,
			new WorldGeneratorXYZOre(4, MirageMaterialsManager.fluorite.copyItemStack(EnumShape.ore))),
			"extreme");
		GameRegistry.registerWorldGenerator(iWorldGeneratorXY, 801);

	}

}
