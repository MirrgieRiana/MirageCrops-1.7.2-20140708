package miragecrops.core.ores.toolcrafting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemToolCrafting extends ItemToolMultiIcon
{

	protected int maxDamage;
	protected int damageOnCrafting;
	protected ItemStack destroyed;
	protected String materialName = null;
	protected String shapeName = null;

	public ItemToolCrafting(
		float damageVsEntity,
		ToolMaterial toolMaterial,
		Set<Block> efficiencyBlocks,
		int maxDamage,
		int damageOnCrafting,
		ItemStack destroyed)
	{
		super(damageVsEntity, toolMaterial, efficiencyBlocks == null ? new HashSet<Block>() : efficiencyBlocks);
		this.maxDamage = maxDamage;
		this.damageOnCrafting = damageOnCrafting;
		this.destroyed = destroyed;
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
	{
		return false;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		ItemStack tmp = itemStack.copy();
		tmp.setItemDamage(tmp.getItemDamage() + damageOnCrafting);
		if (tmp.getItemDamage() >= tmp.getMaxDamage()) return destroyed.copy();
		return tmp;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return stack.getItemDamage() < getMaxDamage(stack);
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return maxDamage;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		if (par4) par3List.add("MultiIcon: " + (iMultiIcon != null));
		if (par4) par3List.add("UnlocalizedName: " + getUnlocalizedName());
		par3List.add("Durability: " + (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage()) + " / " + par1ItemStack.getMaxDamage());

		if (par4) {
			ItemStack itemStack = par1ItemStack;
			String[] oreNames = OreDictionary.getOreNames();

			for (String oreName : oreNames) {
				ArrayList<ItemStack> ores = OreDictionary.getOres(oreName);
				for (ItemStack ore : ores) {
					if (OreDictionary.itemMatches(ore, itemStack, false)) {
						par3List.add("OreName: " + oreName);
						break;
					}
				}
			}

		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		String unlocalizedName = getUnlocalizedNameInefficiently(par1ItemStack) + ".name";
		if (StatCollector.canTranslate(unlocalizedName)) {
			return StatCollector.translateToLocal(unlocalizedName);
		}

		if (materialName != null && shapeName != null) {
			String materialLocalizedName = StatCollector.translateToLocal("material." + materialName + ".name");
			String shapeLocalizedFormat = StatCollector.translateToLocal("shape." + shapeName + ".format");

			return String.format(shapeLocalizedFormat, materialLocalizedName).trim();
		}

		return super.getItemStackDisplayName(par1ItemStack);
	}

	public void setMaterialNameAndShapeName(String materialName, String shapeName)
	{
		this.materialName = materialName;
		this.shapeName = shapeName;
	}

}
