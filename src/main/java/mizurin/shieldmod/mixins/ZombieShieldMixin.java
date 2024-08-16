package mizurin.shieldmod.mixins;

import mizurin.shieldmod.item.Shields;
import net.minecraft.core.entity.monster.EntityArmoredZombie;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = EntityArmoredZombie.class, remap = false)
public class ZombieShieldMixin {
	@Unique
	public boolean isHoldingShield;


	@Inject(method = "<init>(Lnet/minecraft/core/world/World;)V", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I",shift = At.Shift.BEFORE))
	private void injectZombie(World world, CallbackInfo ci){
		Random rand = new Random();
		this.isHoldingShield = rand.nextInt(5) == 0;
	}

	@Inject(method = "getHeldItem()Lnet/minecraft/core/item/ItemStack;", at =@At("HEAD"), cancellable = true)
	private void injectShield(CallbackInfoReturnable<ItemStack> cir){
		cir.setReturnValue(this.isHoldingShield ? new ItemStack(Shields.ironShield, 1) : null);
	}
}
