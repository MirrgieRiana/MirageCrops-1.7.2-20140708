package plugincrops.debugger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class Platform
{

	public void message2(EntityPlayer player, String format, Object... args)
	{
		messagePlayer(player, String.format(format, args));
	}

	public void messagePlayer(EntityPlayer player, String message, Object... args)
	{
		message = escape(message);
		if (player instanceof EntityPlayerMP)
		{
			IChatComponent msg;
			if (args.length > 0) {
				msg = new ChatComponentTranslation(message, (Object[]) getMessageComponents(args));
			} else {
				msg = new ChatComponentTranslation(message, new Object[0]);
			}
			((EntityPlayerMP) player).addChatMessage(msg);
		}
	}

	protected String escape(String message)
	{
		return message.replaceAll("%", "%%");
	}

	protected IChatComponent[] getMessageComponents(Object args[])
	{
		IChatComponent encodedArgs[] = new IChatComponent[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof String && ((String) args[i]).startsWith("ic2.")) {
				encodedArgs[i] = new ChatComponentTranslation((String) args[i], new Object[0]);
			} else {
				encodedArgs[i] = new ChatComponentText(args[i].toString());
			}
		}

		return encodedArgs;
	}

}
