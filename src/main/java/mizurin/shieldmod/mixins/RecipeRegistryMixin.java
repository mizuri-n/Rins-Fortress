package mizurin.shieldmod.mixins;

import mizurin.shieldmod.RecipeColor;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeRegistry;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RecipeRegistry.class, remap = false)
public class RecipeRegistryMixin {
	@Shadow
	@Final
	public RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH;

	@Inject(method = "addProceduralRecipes", at = @At("TAIL"))
	public void addDyeingRecipe(CallbackInfo ci) {
		this.WORKBENCH.register("leather", new RecipeColor());
	}
}
