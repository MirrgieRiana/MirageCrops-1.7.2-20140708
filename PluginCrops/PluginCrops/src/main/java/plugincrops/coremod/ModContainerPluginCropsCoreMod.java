package plugincrops.coremod;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class ModContainerPluginCropsCoreMod extends DummyModContainer
{

	public ModContainerPluginCropsCoreMod()
	{
		super(new ModMetadata());

		ModMetadata meta = getMetadata();

		meta.modId = "PluginCrops_CoreMod";
		meta.name = "PluginCrops CoreMod";
		meta.authorList = Arrays.asList("Mirrgie Riana");
		meta.description = "Patch of IC2 Crops";
		meta.url = "";
		meta.credits = "IC2Exp creators";

		this.setEnabledState(true);
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}

}
