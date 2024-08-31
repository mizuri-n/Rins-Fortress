package mizurin.shieldmod.mixins;

import mizurin.shieldmod.item.Shields;
import net.minecraft.core.entity.monster.EntityArmoredZombie;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.SpawnerMobs;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/*@Mixin(value = SpawnerMobs.class, remap = false)
public class ZombieShieldMixin {

	@Shadow
	@Final
	private static Class<?>[] nightSpawnEntities;

	@Inject(method = "Lnet/minecraft/core/world/SpawnerMobs;<init>()V", at = @At(value = "HEAD"))
	private static void injectEntity(CallbackInfo callbackInfo){
		nightSpawnEntities
	}


} */
