package miragecrops.core.debugcropseeds;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import miragecrops.framework.EnumNBTTypes;
import miragecrops.framework.StaticsCrop;
import miragecrops.framework.item.ItemMeta;
import miragecrops.framework.item.MetaItem;
import mirrg.mir34.modding.IMod;
import mirrgmods.plugincrops.api.CropCrossing;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMetaDebugCrop extends ItemMeta
{

	public ItemMetaDebugCrop(IMod iMod)
	{
		super(iMod);
	}

	public static class Analyzer extends MetaItem
	{

		public Analyzer(ItemMeta itemMeta, int metaId)
		{
			super(itemMeta, metaId);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List info,
			boolean par4)
		{
			info.add("" + EnumChatFormatting.RESET + "rightclick crop block");
		}

		@Override
		public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world,
			int x, int y, int z, int side, float x2, float y2, float z2)
		{
			if (world.isRemote) return false;

			if (world != null) {
				TileEntity te = world.getTileEntity(x, y, z);
				Block block = world.getBlock(x, y, z);
				int blockId = Block.getIdFromBlock(block);
				int metadata = world.getBlockMetadata(x, y, z);
				Item item = Item.getItemFromBlock(block);

				StaticsCrop.sendChatToPlayer(player, "-- Analyzing ---------");

				StaticsCrop.sendChatToPlayer(
					player,
					String.format("[CropDebugger][Block] xyz: (%d, %d, %d) id: (%d:%d)",
						x,
						y,
						z,
						blockId,
						metadata));

				StaticsCrop.sendChatToPlayer(
					player,
					String.format("Light: %d",
						world.getBlockLightValue(x, y, z)));

				if (item == null) {
					StaticsCrop.sendChatToPlayer(player, "Item: null");
				} else {
					String uName = new ItemStack(item, 1, metadata).getUnlocalizedName();
					String dName = new ItemStack(item, 1, metadata).getDisplayName();
					String cName = "unknown class";
					try {
						cName = block.getClass().getName();
					} catch (Exception e) {

					}

					StaticsCrop.sendChatToPlayer(
						player,
						String.format("name: %s(%s) class:%s",
							uName,
							dName,
							cName));

					if (te == null) {

						StaticsCrop.sendChatToPlayer(
							player,
							String.format("[TileEntity] None"));

					} else {

						{
							NBTTagCompound nbt = new NBTTagCompound();
							te.writeToNBT(nbt);

							Iterator<String> iterator = nbt.func_150296_c().iterator();

							StaticsCrop.sendChatToPlayer(player, "[TileEntity NBT]");

							while (iterator.hasNext())
							{
								String s = iterator.next();
								NBTBase nbtbase = nbt.getTag(s);
								StaticsCrop.sendChatToPlayer(player,
									String.format("%s : %s = %s",
										s,
										nbtbase.getClass().getSimpleName().substring(3),
										nbtbase));
							}

						}

						if (te instanceof ICropTile) {
							ICropTile tec = (ICropTile) te;

							StaticsCrop.sendChatToPlayer(
								player,
								String.format("[TileEntityCrop] id: %d upgraded: %s ticker: %d(%d) dirty: %s",
									(int) tec.getID(),
									"" + StaticsCrop.isUpgraded(tec),
									(int) StaticsCrop.getTicker(tec),
									255 - (StaticsCrop.getTicker(tec) - 1) % 256,
									"" + StaticsCrop.isDirty(tec)));

							StaticsCrop.sendChatToPlayer(
								player,
								String.format("HumNutAir: (%d, %d, %d) WatNutWEx: (%d, %d, %d)",
									(int) tec.getHumidity(),
									(int) tec.getNutrients(),
									(int) tec.getAirQuality(),
									tec.getHydrationStorage(),
									tec.getNutrientStorage(),
									tec.getWeedExStorage()));

							if (tec.getID() >= 0) {
								CropCard card = Crops.instance.getCropList()[tec.getID()];

								StaticsCrop.sendChatToPlayer(
									player,
									String.format("[CropCard] id: %d(%s:%d) size: %d/%d growthPoints: %d/%d",
										(int) tec.getID(),
										card.name(),
										card.tier(),
										(int) tec.getSize(),
										card.maxSize(),
										StaticsCrop.getGrowthPoints(tec),
										card.growthDuration(tec)));

								StaticsCrop.sendChatToPlayer(
									player,
									String.format("CropCard ClassName: %s",
										card.getClass().getName()));

								StaticsCrop.sendChatToPlayer(
									player,
									String.format("GGR: (%d, %d, %d) scanLebel: %d",
										(int) tec.getGrowth(),
										(int) tec.getGain(),
										(int) tec.getResistance(),
										(int) tec.getScanLevel()));

								StaticsCrop.sendChatToPlayer(
									player,
									String.format("canBeHarvested: %s canCross: %s canGrow: %s",
										"" + card.canBeHarvested(tec),
										"" + card.canCross(tec),
										"" + card.canGrow(tec)));

								StaticsCrop.sendChatToPlayer(
									player,
									String.format("dropGain: %f dropSeed: %f duration: %d weightInflu: %d",
										(double) card.dropGainChance(),
										(double) card.dropSeedChance(tec),
										card.growthDuration(tec),
										card.weightInfluences(
											tec,
											tec.getHumidity(),
											tec.getNutrients(),
											tec.getAirQuality())));

								StaticsCrop.sendChatToPlayer(
									player,
									String.format("isWeed: %s sizeAfterHarvest: %d",
										"" + card.isWeed(tec),
										(int) card.getSizeAfterHarvest(tec)));

								StaticsCrop.sendChatToPlayer(
									player,
									String.format("growBase: [%d -> %d] need: %d have: %d",
										3 + 0 + tec.getGrowth(),
										3 + 6 + tec.getGrowth(),
										(card.tier() - 1) * 4 +
											tec.getGrowth() + tec.getGain() + tec.getResistance(),
										card.weightInfluences(
											tec,
											tec.getHumidity(),
											tec.getNutrients(),
											tec.getAirQuality()) * 5));

								{
									String multiCulture;

									try {
										byte mc = (Byte) tec.getClass().getMethod("updateMultiCulture").invoke(tec);
										multiCulture = "" + mc;
									} catch (NullPointerException e) {
										multiCulture = e.toString();
									} catch (ArrayIndexOutOfBoundsException e) {
										multiCulture = e.toString();
									} catch (IllegalArgumentException e) {
										multiCulture = e.toString();
									} catch (SecurityException e) {
										multiCulture = e.toString();
									} catch (IllegalAccessException e) {
										multiCulture = e.toString();
									} catch (InvocationTargetException e) {
										multiCulture = e.toString();
									} catch (NoSuchMethodException e) {
										multiCulture = e.toString();
									}

									StaticsCrop.sendChatToPlayer(
										player,
										String.format("multiCulture: %s",
											multiCulture));
								}

							}

						} else {

							StaticsCrop.sendChatToPlayer(
								player,
								String.format("[TileEntity] " + te.getClass().getName()));

						}
					}
				}
			}

			return false;
		}

	}

	public static class Picker extends MetaItem
	{

		public Picker(ItemMeta itemMeta, int metaId)
		{
			super(itemMeta, metaId);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List info,
			boolean par4)
		{
			if (itemStack.stackTagCompound != null) {
				if (itemStack.stackTagCompound.hasKey("id")) {

					try {
						int id = itemStack.stackTagCompound.getInteger("id");

						info.add("" + EnumChatFormatting.RESET + "Id: " + id);
						if (id >= 0) {
							info.add("" + EnumChatFormatting.RESET + "Name: " + Crops.instance.getCropList()[id].name());
						}
						info.add("" + EnumChatFormatting.GREEN + "Gr: " + itemStack.stackTagCompound.getInteger("growth"));
						info.add("" + EnumChatFormatting.GOLD + "Ga: " + itemStack.stackTagCompound.getInteger("gain"));
						info.add("" + EnumChatFormatting.AQUA + "Re: " + itemStack.stackTagCompound.getInteger("resistance"));
						info.add("" + EnumChatFormatting.GRAY + "ScanLevel: " + itemStack.stackTagCompound.getInteger("scan"));
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					info.add("" + EnumChatFormatting.RESET + "rightclick crop block");
				}
			} else {
				info.add("" + EnumChatFormatting.RESET + "rightclick crop block");
			}
		}

		@Override
		public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world,
			int x, int y, int z, int side, float x2, float y2, float z2)
		{
			if (world.isRemote) return false;

			if (world != null) {
				TileEntity te = world.getTileEntity(x, y, z);
				if (te != null) {

					if (te instanceof ICropTile) {
						ICropTile tec = (ICropTile) te;

						StaticsCrop.sendChatToPlayer(player, "-- Picker ---------");

						if (tec.getID() >= 0) {

							if (itemStack.stackTagCompound == null) {
								itemStack.stackTagCompound = new NBTTagCompound();
							}

							try {
								itemStack.stackTagCompound.setInteger("id", tec.getID());
								itemStack.stackTagCompound.setInteger("growth", tec.getGrowth());
								itemStack.stackTagCompound.setInteger("gain", tec.getGain());
								itemStack.stackTagCompound.setInteger("resistance", tec.getResistance());
								itemStack.stackTagCompound.setInteger("scan", tec.getScanLevel());

								StaticsCrop.sendChatToPlayer(player,
									String.format("[Picker] id: %d(%s) GGR: (%d, %d, %d) scan: %d",
										(int) tec.getID(),
										Crops.instance.getCropList()[tec.getID()].name(),
										(int) tec.getGrowth(),
										(int) tec.getGain(),
										(int) tec.getResistance(),
										(int) tec.getScanLevel()));

							} catch (Exception e) {

							}

						} else {

							if (itemStack.stackTagCompound != null) {
								if (itemStack.stackTagCompound.hasKey("id")) {

									try {
										tec.reset();

										tec.setID((short) itemStack.stackTagCompound.getInteger("id"));
										tec.setGrowth((byte) itemStack.stackTagCompound.getInteger("growth"));
										tec.setGain((byte) itemStack.stackTagCompound.getInteger("gain"));
										tec.setResistance((byte) itemStack.stackTagCompound.getInteger("resistance"));
										tec.setScanLevel((byte) itemStack.stackTagCompound.getInteger("scan"));

										tec.setSize((byte) 1);

									} catch (Exception e) {

									}

								}
							}

						}

					}
				}
			}

			return false;
		}

	}

	public static class Harvester extends MetaItem
	{

		public Harvester(ItemMeta itemMeta, int metaId)
		{
			super(itemMeta, metaId);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List info,
			boolean par4)
		{
			info.add("" + EnumChatFormatting.RESET + "rightclick crop block");
		}

		@Override
		public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world,
			int x, int y, int z, int side, float x2, float y2, float z2)
		{
			if (world.isRemote) return false;

			if (world != null) {
				TileEntity te = world.getTileEntity(x, y, z);
				if (te != null) {

					if (te instanceof ICropTile) {
						ICropTile tec = (ICropTile) te;

						if (tec.getID() >= 0) {
							CropCard card = Crops.instance.getCropList()[tec.getID()];

							{
								ItemStack is = card.getSeeds(tec);

								StaticsCrop.dropAsEntity(world, x, y, z, is);
							}

							{
								ItemStack is = card.getGain(tec);

								StaticsCrop.dropAsEntity(world, x, y, z, is);
							}

						}

					}
				}
			}

			return false;
		}

	}

	public static class Sandglass extends MetaItem
	{

		public Sandglass(ItemMeta itemMeta, int metaId)
		{
			super(itemMeta, metaId);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List info,
			boolean par4)
		{
			info.add("" + EnumChatFormatting.RESET + "rightclick crop block");
		}

		public static boolean grow(World world, EntityPlayer player, ICropTile tec, int x, int y, int z,
			boolean showinfo)
		{

			if (tec.getID() >= 0) {
				CropCard card = Crops.instance.getCropList()[tec.getID()];

				if (card.canGrow(tec)) {

					if (!world.isRemote) {

						int size = tec.getSize();
						int gp = StaticsCrop.getGrowthPoints(tec);
						int maxgp = card.growthDuration(tec);
						int add = StaticsCrop.calcGrowthRate(tec);
						StaticsCrop.setGrowthPoints(tec, StaticsCrop.getGrowthPoints(tec) + add);

						if ((tec.getID() > -1) && (StaticsCrop.getGrowthPoints(tec) >= card.growthDuration(tec)))
						{
							StaticsCrop.setGrowthPoints(tec, 0);
							tec.setSize((byte) (tec.getSize() + 1));
							StaticsCrop.setDirty(tec, true);
						}

						if (showinfo) {
							StaticsCrop.sendChatToPlayer(
								player,
								String.format("[SandGlass] size: %d -> %d gp: %d / %d -> %d / %d(+%d)",
									size,
									(int) tec.getSize(),
									gp,
									maxgp,
									StaticsCrop.getGrowthPoints(tec),
									card.growthDuration(tec),
									add));
						}

					}

					return true;
				} else {

					if (!world.isRemote) {

						if (showinfo) {
							StaticsCrop.sendChatToPlayer(
								player,
								String.format("[SandGlass] can not grow"));
						}

					}

				}

			}

			return false;
		}

		@Override
		public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world,
			int x, int y, int z, int side, float x2, float y2, float z2)
		{
			//if (world.isRemote) return false;

			if (world != null) {

				{
					TileEntity te = world.getTileEntity(x, y, z);
					if (te != null) {
						if (te instanceof ICropTile) {

							if (grow(world, player, (ICropTile) te, x, y, z, true)) {
								te.getWorldObj().playAuxSFX(2005,
									x, y, z, 0);
								return true;
							}

							return false;
						}
					}
				}

				{
					boolean used = false;

					for (int xi = -5; xi <= 5; xi++) {
						for (int yi = -5; yi <= 5; yi++) {
							for (int zi = -5; zi <= 5; zi++) {
								TileEntity te = world.getTileEntity(x + xi, y + yi, z + zi);

								if (te != null) {
									if (te instanceof ICropTile) {

										if (grow(world, player, (ICropTile) te, x + xi, y + yi, z + zi, false)) {
											te.getWorldObj().playAuxSFX(2005,
												x + xi, y + yi, z + zi, 0);
											used = true;
										}

									}
								}
							}
						}
					}

					if (used) return true;
				}

			}

			return false;
		}

	}

	public static class Crosser extends MetaItem
	{

		public Crosser(ItemMeta itemMeta, int metaId)
		{
			super(itemMeta, metaId);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
		{
			int[] timeses = {
				1,
				10,
				100,
				500,
				1000,
				5000,
				10000,
				50000,
				100000,
				500000,
			};

			for (int times : timeses) {
				ItemStack itemStack = new ItemStack(p_150895_1_, 1, getMetaId());
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("Times", times);
				itemStack.setTagCompound(nbt);
				p_150895_3_.add(itemStack);
			}
		}

		public int getTimes(ItemStack itemStack)
		{
			if (!itemStack.hasTagCompound()) return 5000;
			if (!itemStack.getTagCompound().hasKey("Times", EnumNBTTypes.INT.ordinal())) return 5000;
			return itemStack.getTagCompound().getInteger("Times");
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List info,
			boolean par4)
		{
			info.add("try: " + getTimes(itemStack) + " times");
		}

		@Override
		public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world,
			int x, int y, int z, int side, float x2, float y2, float z2)
		{
			//if (!world.isRemote) return false;

			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity != null) {
				if (tileEntity instanceof ICropTile) {
					ICropTile iCropTile = (ICropTile) tileEntity;

					iCropTile.setID((short) -1);
					StaticsCrop.setUpgraded(iCropTile, true);

					Hashtable<String, Integer> hash = new Hashtable<String, Integer>();

					for (int i = 0; i < getTimes(par1ItemStack); i++) {

						if (iCropTile.getID() >= 0) {
							String name = Crops.instance.getCropList()[iCropTile.getID()].name();

							if (hash.containsKey(name)) {
								hash.put(name, hash.get(name) + 1);
							} else {
								hash.put(name, 1);
							}

						}

						iCropTile.setID((short) -1);
						StaticsCrop.setUpgraded(iCropTile, true);

						try {
							iCropTile.getClass().getMethod("attemptCrossing").invoke(iCropTile);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					Set<Entry<String, Integer>> entrySet = hash.entrySet();
					Entry[] array = entrySet.toArray(new Entry[entrySet.size()]);
					Arrays.sort(array, 0, array.length, new Comparator<Entry>() {

						@Override
						public int compare(Entry o1, Entry o2)
						{
							if (o1.getValue() == o2.getValue()) return 0;
							if ((Integer) o1.getValue() > (Integer) o2.getValue()) return 1;
							return -1;
						}

					});

					StaticsCrop.sendChatToPlayer(par2EntityPlayer, "-- AttempCrossing ---------");

					for (Entry entry : array) {
						StaticsCrop.sendChatToPlayer(par2EntityPlayer, "" + entry.getValue() + "  " + entry.getKey());
					}

				}
			}

			return false;
		}

	}

	public static class CrossingDumper extends MetaItem
	{

		public CrossingDumper(ItemMeta itemMeta, int metaId)
		{
			super(itemMeta, metaId);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List info,
			boolean par4)
		{
			info.add("see the std out");
		}

		@Override
		public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world,
			int x, int y, int z, int side, float x2, float y2, float z2)
		{
			if (!world.isRemote) return false;

			ArrayList<CropCard> cropCards = new ArrayList<CropCard>();

			{
				CropCard[] cropList = Crops.instance.getCropList();

				for (int i = 0; i < cropList.length; i++) {
					if (cropList[i] != null) {
						if (!cropList[i].discoveredBy().equals("Mirrgie Riana")) {
							cropCards.add(cropList[i]);
						}
					}
				}

				Collections.sort(cropCards, new Comparator<CropCard>() {

					@Override
					public int compare(CropCard a, CropCard b)
					{
						if (a.tier() == b.tier()) return 0;
						return a.tier() > b.tier() ? 1 : -1;
					}

				});

			}

			{
				System.out.print("Name, Id, Tier, DiscoveredBy, ");

				for (int j = 0; j < cropCards.size(); j++) {
					System.out.print("\"" + cropCards.get(j).name() + "\"");
					System.out.print(", ");
				}

				System.out.println();
			}

			for (int i = 0; i < cropCards.size(); i++) {

				System.out.print("\"" + cropCards.get(i).name() + "\"");
				System.out.print(", ");

				System.out.print(cropCards.get(i).getId());
				System.out.print(", ");

				System.out.print(cropCards.get(i).tier());
				System.out.print(", ");

				System.out.print("\"" + cropCards.get(i).discoveredBy() + "\"");
				System.out.print(", ");

				for (int j = 0; j < cropCards.size(); j++) {

					System.out.print(CropCrossing.calculateRatioFor(cropCards.get(i), cropCards.get(j)));
					System.out.print(", ");

				}

				System.out.println();
			}

			return true;
		}
	}

}
