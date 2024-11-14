package mizurin.shieldmod.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import mizurin.shieldmod.SpiderLabyrinth;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkDecoratorOverworld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = ChunkDecoratorOverworld.class, remap = false)
public class SpiderLabyrinthMixin {
	@Final
	@Shadow
	private World world;

	@Unique
	int treeDensity;

	@Unique
	int i11;




	@Inject(method = "decorate(Lnet/minecraft/core/world/chunk/Chunk;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/chunk/Chunk;getChunkRandom(J)Ljava/util/Random;", shift = At.Shift.AFTER), cancellable = true)
	public void injectLabyrinth(Chunk chunk, CallbackInfo ci, @Local(name = "x") int x, @Local(name = "z") int z){
//		Random rand = new Random(this.world.getRandomSeed());
//		treeDensity = x + rand.nextInt(16) + 8;
//		i11 = z + rand.nextInt(16) + 8;
//		i11 = this.world.getHeightValue(treeDensity, i11) - (rand.nextInt(2) + 2);
//
//		Random lRand = chunk.getChunkRandom(75644760L);
		if(isBoreal){
			(new SpiderLabyrinth()).generate(this.world, lRand, treeDensity, i11, i11);
		} else {
			ci.cancel();
		}

	}
}
