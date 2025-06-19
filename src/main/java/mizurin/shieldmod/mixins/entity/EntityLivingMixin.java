package mizurin.shieldmod.mixins.entity;

import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(value = Mob.class, remap = false)
public class EntityLivingMixin {
//	@Inject(
//		method = "onDeath(Lnet/minecraft/core/entity/Entity;)V",
//		at = @At(value = "TAIL"))
//	public void healthSteal(Entity entityKilledBy, CallbackInfo ci) {
//
//		if(entityKilledBy instanceof Player){
//			ItemStack helmet_item = ((Player)entityKilledBy).inventory.armorItemInSlot(2);
//			boolean AR = false;
//			if ((helmet_item != null && helmet_item.getItem().equals(Shields.regenAmulet))){
//				AR = true;
//			}
//			if (AR) {
//				((Player) entityKilledBy).heal(1);
//					entityKilledBy.world.spawnParticle("explode", entityKilledBy.x, entityKilledBy.y + entityKilledBy.getHeadHeight(), entityKilledBy.z, 0.0, 0.0, 0.0, 0);
//
//			}
//		}
//	}
}
