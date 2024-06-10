package mizurin.shieldmod;

import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryRepairable;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.util.RecipeEntrypoint;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.RecipeBuilder;
import net.minecraft.core.data.DataLoader;
import java.awt.*;

import mizurin.shieldmod.item.Shields;

public class recipes implements RecipeEntrypoint {
	public static final String MOD_ID = ShieldMod.MOD_ID;
	public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));

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
			.addInput('P', Block.cobbleStone)
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


		WORKBENCH.register("tearstoneShield", new RecipeEntryRepairable(Shields.tearShield, Item.ingotIron));
	}
	public void initializeRecipe(){
		Registries.RECIPE_TYPES.register("colored/shield", recipeColor.class);
		WORKBENCH.register("leather", new recipeColor());
	}
}


