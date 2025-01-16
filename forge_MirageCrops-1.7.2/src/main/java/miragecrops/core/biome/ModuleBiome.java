package miragecrops.core.biome;

import miragecrops.core.MirageCrops;
import mirrg.mir34.modding.IMod;
import mirrg.mir34.modding.ModuleAbstract;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ModuleBiome extends ModuleAbstract
{

	public ModuleBiome(IMod mod)
	{
		super(mod);
	}

	@Override
	public String getModuleName()
	{
		return "biome";
	}

	public static class Configurations
	{

		public static boolean enableTaintedWorld;

	}

	@Override
	public void handle(FMLPreInitializationEvent event)
	{
		Configurations.enableTaintedWorld =
			MirageCrops.configuration.get(getModuleName(), "enableTaintedWorld", false).getBoolean();
	}

	@Override
	public void handle(FMLInitializationEvent event)
	{
		if (Configurations.enableTaintedWorld) {
			FMLLog.info("[tainted world]: ON");

			ReflectionsThaumcraft.init();
			FMLLog.info("[tainted world]->?enabled = %s", String.valueOf(ReflectionsThaumcraft.thaumcraftOk));

			if (ReflectionsThaumcraft.thaumcraftOk) {
				MinecraftForge.EVENT_BUS.register(new Handler());
			}

		} else {
			FMLLog.info("[tainted world]: OFF");
		}
	}

	public static class Handler
	{

		@SubscribeEvent
		public void handle(DecorateBiomeEvent.Post event)
		{
			Chunk chunk = event.world.getChunkFromBlockCoords(event.chunkX, event.chunkZ);
			byte[] array = chunk.getBiomeArray();
			for (int i = 0; i < 256; i++) {
				array[i] = (byte) (ReflectionsThaumcraft.biomeTaint.biomeID & 0xFF);
			}
			chunk.setBiomeArray(array);

			ReflectionsThaumcraft.decorateSpecial(event.world, event.rand, event.chunkX, event.chunkZ);
			ReflectionsThaumcraft.decorateSpecial(event.world, event.rand, event.chunkX, event.chunkZ);

		}
	}

}
