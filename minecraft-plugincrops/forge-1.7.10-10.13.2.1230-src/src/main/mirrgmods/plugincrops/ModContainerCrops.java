package mirrgmods.plugincrops;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class ModContainerCrops extends DummyModContainer
{

	public ModContainerCrops()
	{
		super(new ModMetadata());

		ModMetadata meta = getMetadata();

		meta.modId = "mirrg_PluginCrops";
		meta.name = "PluginCrops";
		meta.version = PluginCrops.VERSION;
		meta.authorList = Arrays.asList("Mirrgie Riana");
		meta.description = "Patch of IC2 Crops";
		meta.url = "";
		meta.credits = "";

		this.setEnabledState(true);
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}

}
