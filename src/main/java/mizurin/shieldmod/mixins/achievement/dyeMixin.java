package mizurin.shieldmod.mixins.achievement;

import mizurin.shieldmod.ShieldAchievements;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
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

	@Inject(method = "onPickupFromSlot", at = @At("TAIL"))
	private void injectPickup(ItemStack itemStack, CallbackInfo ci){
		/*Item item = itemStack.getItem();
		if(item.id == Shields.woodenShield.id){
			thePlayer.triggerAchievement(ShieldAchievements.SHIELD_GOT);
		}
		if(item.id == Shields.steelShield.id){
			thePlayer.addStat(ShieldAchievements.MODERN_AGE, 1);
		}
		if(item.id == Shields.armorLeatherHelmet.id && itemStack.getData().containsKey("dyed_color")){
			thePlayer.addStat(ShieldAchievements.COLORS, 1);
		}
		if(item.id == Shields.armorLeatherChest.id && itemStack.getData().containsKey("dyed_color")){
			thePlayer.addStat(ShieldAchievements.COLORS, 1);
		}
		if(item.id == Shields.armorLeatherLeg.id && itemStack.getData().containsKey("dyed_color")){
			thePlayer.addStat(ShieldAchievements.COLORS, 1);
		}
		if(item.id == Shields.armorLeatherBoot.id && itemStack.getData().containsKey("dyed_color")){
			thePlayer.addStat(ShieldAchievements.COLORS, 1);
		}
		if(item.id == Shields.leatherShield.id && itemStack.getData().containsKey("dyed_color")){
			thePlayer.addStat(ShieldAchievements.COLORS, 1);
		}

	*/}
}
