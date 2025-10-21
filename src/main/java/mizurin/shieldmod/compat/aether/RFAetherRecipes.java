package mizurin.shieldmod.compat.aether;

import mizurin.shieldmod.compat.aether.item.RFAetherItems;
import net.minecraft.core.item.ItemStack;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static teamport.aether.AetherMod.MOD_ID;

public class RFAetherRecipes implements RecipeEntrypoint {
	@Override
	public void onRecipesReady() {
		RFAetherRecipes.workbenchRecipes();
	}

	@Override
	public void initNamespaces() {

	}

	public static void workbenchRecipes() {
		RecipeBuilder.Shaped(MOD_ID, " X ", "XAX", " X ")
			.addInput('X', AetherBlocks.COBBLE_HOLYSTONE)
			.addInput('A', AetherItems.AMBROSIUM)
			.create("holystone_shield", new ItemStack(RFAetherItems.holystoneShield, 1));
	}
}
