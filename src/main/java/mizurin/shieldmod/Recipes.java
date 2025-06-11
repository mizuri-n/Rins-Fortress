package mizurin.shieldmod;

import mizurin.shieldmod.blocks.RinBlocks;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryRepairable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class Recipes implements RecipeEntrypoint {
	public static final String MOD_ID = ShieldMod.MOD_ID;
	public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Blocks.WORKBENCH)));

	//initNamespaces for recipes in servers.
	@Override
	public void initNamespaces() {
		RecipeNamespace BETTERWITHDEFENSE = new RecipeNamespace();
		BETTERWITHDEFENSE.register("workbench", WORKBENCH);
		Registries.RECIPES.register("shieldmod", BETTERWITHDEFENSE);
	}

	@Override
	public void onRecipesReady() {
		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', "minecraft:planks")
			.addInput('L',"minecraft:logs")
			.create("woodenShield", Shields.woodenShield.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', "minecraft:cobblestones")
			.addInput('L',"minecraft:planks")
			.create("stoneShield", Shields.stoneShield.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', Items.INGOT_IRON)
			.addInput('L',"minecraft:planks")
			.create("ironShield", Shields.ironShield.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', Items.INGOT_GOLD)
			.addInput('L',"minecraft:planks")
			.create("goldShield", Shields.goldShield.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', Items.DIAMOND)
			.addInput('L',"minecraft:planks")
			.create("diamondShield", Shields.diamondShield.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', Items.INGOT_STEEL)
			.addInput('L',"minecraft:planks")
			.create("steelShield", Shields.steelShield.getDefaultStack());


		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', Items.LEATHER)
			.addInput('L', "minecraft:wools")
			.create("leatherShield", Shields.leatherShield.getDefaultStack());

		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(Blocks.PUMPKIN)
			.addInput(Items.FOOD_PORKCHOP_COOKED)
			.addInput(Items.BOWL)
			.create("pumpkinstew", Shields.pumpkinStew.getDefaultStack());

		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(Items.DUST_REDSTONE)
			.addInput(Items.DUST_REDSTONE)
			.addInput(Items.BUCKET_WATER)
			.addInput(Blocks.FLOWER_LIGHT_BLUE)
			.addInput(Blocks.FLOWER_LIGHT_BLUE)
			.addInput(Blocks.FLOWER_LIGHT_BLUE)
			.addInput(Items.JAR)
			.addInput(Items.JAR)
			.addInput(Items.JAR)
			.create("poisonbottle", new ItemStack(Shields.poisonBottle, 3));

		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(new ItemStack(RinBlocks.logApple, 1))
			.create("apple_log_to_grey", new ItemStack(Blocks.PLANKS_OAK_PAINTED, 4, 8));


		WORKBENCH.register("tearstoneShield", new RecipeEntryRepairable(Shields.tearShield.getDefaultStack(), new RecipeSymbol(new ItemStack(Items.INGOT_IRON, 1))));

		WORKBENCH.register("amuletRegenChest", new RecipeEntryRepairable(Shields.regenAmulet.getDefaultStack(), new RecipeSymbol(new ItemStack(Items.DUST_REDSTONE))));

		WORKBENCH.register("rockyHelmetHat", new RecipeEntryRepairable(Shields.rockyHelmet.getDefaultStack(), new RecipeSymbol(new ItemStack(Blocks.COBBLE_STONE))));
	}

}


