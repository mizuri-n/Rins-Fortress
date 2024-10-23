package mizurin.shieldmod.mixins;

import mizurin.shieldmod.item.Shields;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static mizurin.shieldmod.ShieldMod.hurtSound;

@Mixin(value = EntityLiving.class, remap = false)
public class EntityLivingMixin {
	@Inject(
		method = "onDeath(Lnet/minecraft/core/entity/Entity;)V",
		at = @At(value = "TAIL"))
	public void healthSteal(Entity entityKilledBy, CallbackInfo ci) {

		if(entityKilledBy instanceof EntityPlayer){
			ItemStack helmet_item = ((EntityPlayer)entityKilledBy).inventory.armorItemInSlot(2);
			boolean AR = false;
			if ((helmet_item != null && helmet_item.getItem().equals(Shields.regenAmulet))){
				AR = true;
			}
			if (AR) {
				((EntityPlayer) entityKilledBy).heal(1);
				for (int j = 0; j < 8; ++j) {
					entityKilledBy.world.spawnParticle("largesmoke", entityKilledBy.x, entityKilledBy.y, entityKilledBy.z, 0.0, 0.0, 0.0, 0);

				}
			}
		}
	}

	//needs fixing.
	@Inject(method = "getHurtSound()Ljava/lang/String;", at = @At(value = "HEAD"), cancellable = true)
	public void insertHurt(CallbackInfoReturnable<String> cir){
		if(hurtSound) {
			cir.setReturnValue("damage.hurtflesh");
		}
	}
	@Inject(method = "getDeathSound()Ljava/lang/String;", at = @At(value = "HEAD"), cancellable = true)
	public void insertHurtDeath(CallbackInfoReturnable<String> cir){
		if(hurtSound) {
			cir.setReturnValue("damage.hurtflesh");
		}
	}
}
