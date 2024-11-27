package mizurin.shieldmod.mixins.entity;

import mizurin.shieldmod.interfaces.IDazed;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;


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
					entityKilledBy.world.spawnParticle("explode", entityKilledBy.x, entityKilledBy.y + entityKilledBy.getHeadHeight(), entityKilledBy.z, 0.0, 0.0, 0.0, 0);

			}
		}
	}
}
