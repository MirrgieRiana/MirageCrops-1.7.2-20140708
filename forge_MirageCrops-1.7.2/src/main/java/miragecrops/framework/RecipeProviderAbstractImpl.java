package miragecrops.framework;

import net.minecraft.item.ItemStack;
import uristqwerty.CraftGuide.api.CraftGuideAPIObject;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeProvider;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class RecipeProviderAbstractImpl extends CraftGuideAPIObject implements RecipeProvider
{

	protected RecipeGenerator generator;
	protected RecipeTemplate template;
	protected ItemStack craftingType;

	public RecipeProviderAbstractImpl(ItemStack craftingType)
	{
		super();

		this.craftingType = craftingType;
	}

	public RecipeGenerator getGenerator()
	{
		return generator;
	}

	public RecipeTemplate getTemplate()
	{
		return template;
	}

	protected void ready(RecipeGenerator generator)
	{
		this.generator = generator;
		template = createTemplate();
	}

	protected abstract RecipeTemplate createTemplate();

	@Override
	public void generateRecipes(RecipeGenerator generator)
	{
		ready(generator);

		generateRecipes();
	}

	protected abstract void generateRecipes();

}
