package mizurin.shieldmod.compat.aether;

import mizurin.shieldmod.compat.aether.item.RFAetherItems;
import net.minecraft.core.item.ItemStack;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;
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
			.addInput('X', AetherBlocks.PLANKS_SKYROOT)
			.addInput('A', AetherBlocks.LOG_SKYROOT)
			.create("skyroot_shield", new ItemStack(RFAetherItems.skyrootShield, 1));

		RecipeBuilder.Shaped(MOD_ID, " X ", "XAX", " X ")
			.addInput('X', AetherBlocks.COBBLE_HOLYSTONE)
			.addInput('A', AetherItems.AMBROSIUM)
			.create("holystone_shield", new ItemStack(RFAetherItems.holystoneShield, 1));

		RecipeBuilder.Shaped(MOD_ID, " X ", "XAX", " X ")
			.addInput('X', AetherItems.ZANITE)
			.addInput('A', "minecraft:planks")
			.create("zanite_shield", new ItemStack(RFAetherItems.zaniteShield, 1));

		RecipeBuilder.Shaped(MOD_ID, " X ", "XAX", " X ")
			.addInput('X', AetherBlocks.BLOCK_GRAVITITE)
			.addInput('A', "minecraft:planks")
			.create("zanite_shield", new ItemStack(RFAetherItems.gravititeShield, 1));
	}
}
