package mizurin.shieldmod.mixins.world;

import com.llamalad7.mixinextras.sugar.Local;
import mizurin.shieldmod.SpiderLabyrinth;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkDecoratorOverworld;
import net.minecraft.core.world.generate.feature.WorldFeatureLabyrinth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(value = ChunkDecoratorOverworld.class, remap = false)
public class SpiderLabyrinthMixin {




	@Redirect(method = "decorate", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/generate/feature/WorldFeatureLabyrinth;generate(Lnet/minecraft/core/world/World;Ljava/util/Random;III)Z"))
	public boolean redirect(WorldFeatureLabyrinth instance, World world, Random rand, int x, int y, int z,
							@Local(name = "biome") Biome biome) {
		boolean isBoreal = (biome == Biomes.OVERWORLD_BOREAL_FOREST || biome == Biomes.OVERWORLD_MEADOW);
		if (isBoreal) {
			return new SpiderLabyrinth().generate(world, rand, x, y, z);
		}
		return instance.generate(world, rand, x, y, z);
	}
}
