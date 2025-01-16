package plugincrops.debugger;

import java.util.ArrayList;

import mirrg.minecraft.item.multi.copper.IMetaitem;
import mirrg.minecraft.item.multi.copper.ItemMulti;
import mirrg.minecraft.item.multi.copper.Metaitem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import plugincrops.debugger.MetaitemPluginCrops.Arguments;
import plugincrops.debugger.metaitems.MetaitemAxe;
import plugincrops.debugger.metaitems.MetaitemBomb;
import plugincrops.debugger.metaitems.MetaitemBucket;
import plugincrops.debugger.metaitems.MetaitemCropList;
import plugincrops.debugger.metaitems.MetaitemCrosser;
import plugincrops.debugger.metaitems.MetaitemCrosser2;
import plugincrops.debugger.metaitems.MetaitemCrossingRateTable;
import plugincrops.debugger.metaitems.MetaitemHarvester;
import plugincrops.debugger.metaitems.MetaitemHoe;
import plugincrops.debugger.metaitems.MetaitemHoe2;
import plugincrops.debugger.metaitems.MetaitemHourglass;
import plugincrops.debugger.metaitems.MetaitemOreList;
import plugincrops.debugger.metaitems.MetaitemPicker;
import plugincrops.debugger.metaitems.MetaitemPlacer;
import plugincrops.debugger.metaitems.MetaitemScanner;
import plugincrops.debugger.metaitems.MetaitemScanner2;
import plugincrops.debugger.metaitems.MetaitemSeedPicker;
import plugincrops.debugger.metaitems.MetaitemSetter;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = ModPluginCrops.MODID, version = ModPluginCrops.VERSION)
public class ModPluginCrops
{

	public static final String MODID = "PluginCrops_Debugger";
	public static final String VERSION = "1.3";

	@SidedProxy(clientSide = "plugincrops.debugger.PlatformClient", serverSide = "plugincrops.debugger.Platform")
	public static Platform platform;

	public static CreativeTabs creativeTab = new CreativeTabs("pluginCrops") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return itemMulti;
		}
	};
	public static ItemMulti itemMulti;

	@EventHandler
	public void handle(FMLPreInitializationEvent event)
	{

		itemMulti = new ItemMulti();
		itemMulti.setTextureName("minecraft:apple");
		itemMulti.setCreativeTab(creativeTab);

		{
			IMetaitem s = itemMulti.getSuper();
			String m = "minecraft:";
			ArrayList<Metaitem> a = itemMulti.metaitems;
			boolean t = true;
			boolean f = false;

			int cB = 0x000000;
			int cg = 0x008800;
			int cy = 0x888800;
			int cyH = 0xffff00;
			int cr = 0x880000;

			a(new MetaitemScanner(s, a.size(), a(m + "book_enchanted", cyH, "scanner")));
			a(new MetaitemHoe(s, a.size(), a(m + "iron_hoe", cg, "hoe")));
			a(new MetaitemHoe2(s, a.size(), a(m + "iron_hoe", cr, "hoe2")));
			a(new MetaitemAxe(s, a.size(), a(m + "iron_axe", cg, "axe")));
			a(new MetaitemBucket(s, a.size(), a(m + "bucket_milk", cg, "bucket")));
			a(new MetaitemBomb(s, a.size(), a(m + "iron_sword", cB, "sword"), f, f));
			a(new MetaitemBomb(s, a.size(), a(m + "fireball", cB, "fireball"), t, f));
			a(new MetaitemBomb(s, a.size(), a(m + "minecart_tnt", cB, "tnt"), f, t));
			a(new MetaitemPlacer(s, a.size(), a(m + "brick", cg, "placer")));
			a(new MetaitemCrosser(s, a.size(), a(m + "nether_star", cB, "crosser")));
			a(new MetaitemCrosser2(s, a.size(), a(m + "nether_star", cr, "crosser2")));
			a(new MetaitemHourglass(s, a.size(), a(m + "experience_bottle", cB, "hourglass")));
			a(new MetaitemPicker(s, a.size(), a(m + "quiver", cg, "picker")));
			a(new MetaitemCropList(s, a.size(), a(m + "sign", cg, "cropList")));
			a(new MetaitemCrossingRateTable(s, a.size(), a(m + "item_frame", cg, "crossingRateTable")));
			a(new MetaitemHarvester(s, a.size(), a(m + "shears", cg, "harvester")));
			a(new MetaitemSeedPicker(s, a.size(), a(m + "seeds_wheat", cg, "seedPicker")));
			a(new MetaitemSetter(s, a.size(), a(m + "sugar", cB, "setter")));
			a(new MetaitemScanner2(s, a.size(), a(m + "book_normal", cr, "scanner2")));
			a(new MetaitemOreList(s, a.size(), a(m + "sign", 0x0000ff, "oreList")));

		}

		// TileのNBT分析機
		// 種箱
		// バイオーム改変機
		// 足場製造機

		GameRegistry.registerItem(itemMulti, "debugger");

	}

	private Arguments a(String textureName, int color, String name)
	{
		return new Arguments(textureName, color, "plugincrops_debugger.debugger." + name);
	}

	private void a(Metaitem metaitem)
	{
		itemMulti.metaitems.add(metaitem);
	}

}
