package plugincrops.debugger;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class PlatformClient extends Platform
{

	@Override
	public void messagePlayer(EntityPlayer player, String message, Object... args)
	{
		message = escape(message);
		if (args.length > 0) {
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation(message, (Object[]) getMessageComponents(args)));
		} else {
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
		}
	}

}
