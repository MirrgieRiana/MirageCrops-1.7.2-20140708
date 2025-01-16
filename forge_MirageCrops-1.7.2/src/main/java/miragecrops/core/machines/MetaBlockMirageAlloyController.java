package miragecrops.core.machines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;

import miragecrops.api.machines.IMiragePipe;
import miragecrops.api.machines.ITransportContent;
import miragecrops.core.machines.ModuleMachines.TileEntityMetaBlockMirageAlloyController;
import miragecrops.core.machines.network.NetworkAgentsEnumerator;
import miragecrops.core.machines.network.Points;
import miragecrops.core.machines.network.Points.Point;
import miragecrops.core.machines.pipe.HelperMiragePipe;
import miragecrops.framework.block.BlockMeta;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MetaBlockMirageAlloyController extends MetaBlockFurnace
{
	public MetaBlockMirageAlloyController(BlockMeta blockMeta, int metaId)
	{
		super(blockMeta, metaId);
	}

	@Override
	public TileEntity createNewTileEntity(World paramWorld, int paramInt)
	{
		return new TileEntityMetaBlockMirageAlloyController();
	}

	@Override
	public boolean onBlockActivated(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
	{
		if (!paramWorld.isRemote) {
			return true;
		}

		IMiragePipe iMiragePipe = HelperMiragePipe.getMiragePipe(paramWorld, paramInt1, paramInt2, paramInt3);
		if (iMiragePipe != null) {
			ITransportContent[] transportContents = iMiragePipe.getTransportContents(paramWorld, paramInt1, paramInt2, paramInt3);

			System.out.println("------------- miragemachine controller -----------");

			for (ITransportContent transportContent : transportContents) {
				NetworkAgentsEnumerator networkAgentsEnumerator =
					new NetworkAgentsEnumerator(transportContent, paramWorld, paramInt1, paramInt2, paramInt3);
				Points enumerateAgents = networkAgentsEnumerator.enumerateAgents();

				{
					ArrayList<ItemStack> itemStacks = new ArrayList<ItemStack>();

					Enumeration<Point> enumeration = enumerateAgents.keys();
					while (enumeration.hasMoreElements()) {
						Points.Point point = enumeration.nextElement();
						Block block = paramWorld.getBlock(point.x, point.y, point.z);
						Item item = Item.getItemById(Block.getIdFromBlock(block));
						int meta = paramWorld.getBlockMetadata(point.x, point.y, point.z);

						boolean flag = false;
						for (ItemStack itemStack : itemStacks) {
							if (item == itemStack.getItem()) {
								if (meta == itemStack.getItemDamage()) {
									itemStack.stackSize++;
									flag = true;
									break;
								}
							}
						}
						if (!flag) {
							itemStacks.add(new ItemStack(item, 1, meta));
						}

					}

					Collections.sort(itemStacks, new Comparator<ItemStack>() {

						@Override
						public int compare(ItemStack a, ItemStack b)
						{
							if (a.stackSize == b.stackSize) return 0;
							return a.stackSize > b.stackSize ? -1 : 1;
						}

					});

					System.out.println("transportContent: " + transportContent.getName());
					for (ItemStack itemStack : itemStacks) {
						System.out.println(String.format("%3d * %s", itemStack.stackSize, itemStack.getDisplayName()));
					}
				}

			}

		}

		return true;
	}

}
