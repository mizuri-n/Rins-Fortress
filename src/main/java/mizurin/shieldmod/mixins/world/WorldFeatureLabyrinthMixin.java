package mizurin.shieldmod.mixins.world;

import mizurin.shieldmod.item.Shields;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
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

	@Unique
	private boolean isBoreal;

	public WorldFeatureLabyrinthMixin(boolean isHot, boolean isBoreal) {
		this.isHot = isHot;
		this.isBoreal = isBoreal;
	}


	@Inject(method = "pickCheckLootItem(Ljava/util/Random;)Lnet/minecraft/core/item/ItemStack;", at = @At(value = "FIELD", target = "Lnet/minecraft/core/world/generate/feature/WorldFeatureLabyrinth;treasureGenerated:Z", ordinal = 1, shift = At.Shift.AFTER), cancellable = true)
	private void addTreasure(Random random, CallbackInfoReturnable<ItemStack> cir) {
		if (isCold) {
			if(random.nextInt(2) == 0) {
				cir.setReturnValue(new ItemStack(Shields.tearShield));
			}
		}
		 else if(isHot){
				 cir.setReturnValue(new ItemStack(Shields.rockyHelmet));
		}
		 else if (random.nextInt(3) == 0) {
				cir.setReturnValue(new ItemStack(Shields.regenAmulet));
			}
	}
	@Inject(method = "generate", at = @At("HEAD"))
	public void generate(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		Biome biome = world.getBlockBiome(x, y, z);
		if (biome == Biomes.OVERWORLD_BOREAL_FOREST || biome == Biomes.OVERWORLD_MEADOW){
			isBoreal = true;
		}

		if(biome == Biomes.OVERWORLD_CAATINGA || biome == Biomes.OVERWORLD_DESERT || biome == Biomes.OVERWORLD_OUTBACK || biome == Biomes.OVERWORLD_OUTBACK_GRASSY){
			isHot = true;
		}
	}
}

