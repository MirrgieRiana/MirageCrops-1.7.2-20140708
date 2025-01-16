package plugincrops.api.client;

import ic2.api.crops.CropCard;

import java.util.ArrayList;
import java.util.Hashtable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ApiCoreModRendererBlockCrop
{

	/**
	 * register
	 */
	public static Hashtable<CropCard, IHandlerRendering> tableCropCardToHandlerRendering = new Hashtable<CropCard, IHandlerRendering>();

	/**
	 * register
	 */
	public static ArrayList<IHandlerRendering> handlerRenderings = new ArrayList<IHandlerRendering>();

	/**
	 * getter
	 */
	public static IHandlerRendering handlerRenderingDefault;

	/**
	 * getter
	 */
	public static Hashtable<String, IHandlerRendering> registerHandlerRendering = new Hashtable<String, IHandlerRendering>();

	/**
	 * for ic2 core
	 */
	public static IHandlerRendering handler;

}
