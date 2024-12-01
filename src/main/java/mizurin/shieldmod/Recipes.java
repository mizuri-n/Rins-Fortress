package mizurin.shieldmod;

import mizurin.shieldmod.blocks.RinBlocks;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryRepairable;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class Recipes implements RecipeEntrypoint {
	public static final String MOD_ID = ShieldMod.MOD_ID;
	public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));

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
			.addInput('P', Item.ingotIron)
			.addInput('L',"minecraft:planks")
			.create("ironShield", Shields.ironShield.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', Item.ingotGold)
			.addInput('L',"minecraft:planks")
			.create("goldShield", Shields.goldShield.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', Item.diamond)
			.addInput('L',"minecraft:planks")
			.create("diamondShield", Shields.diamondShield.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', Item.ingotSteel)
			.addInput('L',"minecraft:planks")
			.create("steelShield", Shields.steelShield.getDefaultStack());


		RecipeBuilder.Shaped(MOD_ID)
			.setShape(" P ","PLP"," P ")
			.addInput('P', Item.leather)
			.addInput('L', "minecraft:wools")
			.create("leatherShield", Shields.leatherShield.getDefaultStack());

		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(Block.pumpkin)
			.addInput(Item.foodPorkchopCooked)
			.addInput(Item.bowl)
			.create("pumpkinstew", Shields.pumpkinStew.getDefaultStack());

		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(Item.dustRedstone)
			.addInput(Item.dustRedstone)
			.addInput(Item.bucketWater)
			.addInput(Block.flowerLightBlue)
			.addInput(Block.flowerLightBlue)
			.addInput(Block.flowerLightBlue)
			.addInput(Item.jar)
			.addInput(Item.jar)
			.addInput(Item.jar)
			.create("poisonbottle", new ItemStack(Shields.poisonBottle, 3));

		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(new ItemStack(RinBlocks.logApple, 1))
			.create("apple_log_to_light_grey", new ItemStack(Block.planksOakPainted, 4, 8));


		WORKBENCH.register("tearstoneShield", new RecipeEntryRepairable(Shields.tearShield, Item.ingotIron));

		WORKBENCH.register("amuletRegenChest", new RecipeEntryRepairable(Shields.regenAmulet, Item.dustRedstone));

		WORKBENCH.register("rockyHelmetHat", new RecipeEntryRepairable(Shields.rockyHelmet, Block.cobbleStone.asItem()));
	}
	public void initializeRecipe(){
		Registries.RECIPE_TYPES.register("colored/shield", RecipeColor.class);
		WORKBENCH.register("leather", new RecipeColor());
	}
}


