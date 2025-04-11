package mizurin.shieldmod.mixins.world;

import mizurin.shieldmod.WorldFeatureTreeApple;
import mizurin.shieldmod.blocks.RinBlocks;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.biome.BiomeSeasonalForest;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeCherry;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeFancy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

import static mizurin.shieldmod.ShieldMod.appleGenerate;

@Mixin(value = BiomeSeasonalForest.class, remap = false)
public class SeasonalMixin {
	@Inject(method = "getRandomWorldGenForTrees(Ljava/util/Random;)Lnet/minecraft/core/world/generate/feature/WorldFeature;", at = @At("HEAD"), cancellable = true)
	void injectSeasonal(Random random, CallbackInfoReturnable<WorldFeature> cir) {
		if (random.nextInt(3) == 0) {
			cir.setReturnValue ((WorldFeature)(random.nextInt(2) == 0 ? new WorldFeatureTreeFancy(Blocks.LEAVES_OAK.id(), Blocks.LOG_OAK.id()) : new WorldFeatureTree(Blocks.LEAVES_OAK.id(), Blocks.LOG_OAK.id(), 4)));
		} else {
			if (random.nextInt(3) == 0 && appleGenerate) {
				cir.setReturnValue ((WorldFeature)(random.nextInt(3) == 0 ? new WorldFeatureTreeFancy(RinBlocks.leavesApple.id(), RinBlocks.logApple.id()) : new WorldFeatureTreeApple(RinBlocks.leavesApple.id(), RinBlocks.logApple.id(), 4)));
			} else {
			cir.setReturnValue ((WorldFeature)(random.nextInt(3) == 0 ? new WorldFeatureTreeFancy(Blocks.LEAVES_CHERRY.id(), Blocks.LOG_CHERRY.id()) : new WorldFeatureTreeCherry(Blocks.LEAVES_CHERRY.id(), Blocks.LOG_CHERRY.id(), 4)));
			}
		}
		cir.cancel();
	}
}
