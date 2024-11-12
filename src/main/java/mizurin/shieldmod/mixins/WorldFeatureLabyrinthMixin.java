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

	private boolean isHot;

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
		} else if(isBoreal){
			 int r = random.nextInt(5);
			 switch (r){
				 case 0:
				 case 1:
					 cir.setReturnValue(new ItemStack(Shields.tearShield));
					 break;
				 case 2:
				 case 3:
					 cir.setReturnValue(new ItemStack(Shields.rockyHelmet));
					 break;
				 case 4:
					 cir.setReturnValue(new ItemStack(Shields.regenAmulet));
					 break;
			 }
		}
		 else if (random.nextInt(3) == 0) {
				cir.setReturnValue(new ItemStack(Shields.regenAmulet));
			}
	}
//	@Inject(method = "generate(Lnet/minecraft/core/world/World;Ljava/util/Random;III)Z", at = @At(value = "FIELD", target = "net/minecraft/core/world/generate/feature/WorldFeatureLabyrinth.slabBlock : I", ordinal = 0))
//	private void addBiome(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir){
//		this.isHot = true;
//	}
}

