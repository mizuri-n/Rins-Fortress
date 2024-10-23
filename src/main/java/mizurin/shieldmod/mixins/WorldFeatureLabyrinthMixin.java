package mizurin.shieldmod.mixins;

import mizurin.shieldmod.item.Shields;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureLabyrinth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

//Mixin to add different treasure items to biomes based off temperature.
@Mixin(value = WorldFeatureLabyrinth.class, remap = false)
public class WorldFeatureLabyrinthMixin {
	@Shadow
	boolean isCold;

	@Unique
	private boolean isHot;


	@Inject(method = "pickCheckLootItem(Ljava/util/Random;)Lnet/minecraft/core/item/ItemStack;", at = @At(value = "FIELD", target = "Lnet/minecraft/core/world/generate/feature/WorldFeatureLabyrinth;treasureGenerated:Z", ordinal = 1, shift = At.Shift.AFTER), cancellable = true)
	private void addTreasure(Random random, CallbackInfoReturnable<ItemStack> cir) {
		if (isCold && random.nextInt(2) == 0) {
			cir.setReturnValue(new ItemStack(Shields.tearShield));
		}
		if(isHot && random.nextInt(2)== 0){
			cir.setReturnValue(new ItemStack(Shields.rockyHelmet));
		}
		if(!isHot && !isCold && random.nextInt(2)== 0){
			cir.setReturnValue(new ItemStack(Shields.regenAmulet));
		}
	}
	@Inject(method = "generate(Lnet/minecraft/core/world/World;Ljava/util/Random;III)Z", at = @At(value = "FIELD", target = "net/minecraft/core/world/generate/feature/WorldFeatureLabyrinth.slabBlock : I", ordinal = 0))
	private void addBiome(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir){
		this.isHot = true;
	}
}

