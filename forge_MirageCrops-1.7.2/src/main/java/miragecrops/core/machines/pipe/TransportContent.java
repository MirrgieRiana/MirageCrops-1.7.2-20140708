package miragecrops.core.machines.pipe;

import miragecrops.api.machines.ITransportContent;
import net.minecraft.util.IIcon;

public class TransportContent implements ITransportContent
{

	private final String name;

	public TransportContent(String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public IIcon getIcon()
	{
		return null;
	}

}
