package mizurin.shieldmod.mixins;

import mizurin.shieldmod.ShieldAchievements;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.SlotCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SlotCrafting.class, remap = false)
public class dyeMixin {

	@Shadow
	private EntityPlayer thePlayer;

	@Inject(method = "onPickupFromSlot(Lnet/minecraft/core/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;onCrafting(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/EntityPlayer;)V", shift = At.Shift.AFTER))
	public void injectPickup(ItemStack itemStack, CallbackInfo ci){
		if(itemStack.itemID == Shields.woodenShield.id){
			thePlayer.addStat(ShieldAchievements.SHIELD_GOT, 1);
		}
		if(itemStack.itemID == Shields.steelShield.id){
			thePlayer.addStat(ShieldAchievements.MODERN_AGE, 1);
		}
		if(itemStack.itemID == Shields.armorLeatherHelmet.id && itemStack.getData().containsKey("dyed_color")){
			thePlayer.addStat(ShieldAchievements.COLORS, 1);
		}
		if(itemStack.itemID == Shields.armorLeatherChest.id && itemStack.getData().containsKey("dyed_color")){
			thePlayer.addStat(ShieldAchievements.COLORS, 1);
		}
		if(itemStack.itemID == Shields.armorLeatherLeg.id && itemStack.getData().containsKey("dyed_color")){
			thePlayer.addStat(ShieldAchievements.COLORS, 1);
		}
		if(itemStack.itemID == Shields.armorLeatherBoot.id && itemStack.getData().containsKey("dyed_color")){
			thePlayer.addStat(ShieldAchievements.COLORS, 1);
		}
		if(itemStack.itemID == Shields.leatherShield.id && itemStack.getData().containsKey("dyed_color")){
			thePlayer.addStat(ShieldAchievements.COLORS, 1);
		}

	}
}
