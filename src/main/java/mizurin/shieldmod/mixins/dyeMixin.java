package mizurin.shieldmod.mixins;

import mizurin.shieldmod.ShieldAchievements;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SlotCrafting.class, remap = false)
public abstract class dyeMixin extends Slot {
	@Shadow
	private EntityPlayer thePlayer;

	public dyeMixin(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}
	@Inject(method = "onPickupFromSlot", at = @At("HEAD"), cancellable = false)
	public void injectPickup(ItemStack itemStack, CallbackInfo ci){
		Item item = itemStack.getItem();
		if(item.id == Shields.armorLeatherHelmet.id && itemStack.getData().containsKey("dyed_color")) thePlayer.addStat(ShieldAchievements.COLORS, 1);
		if(item.id == Shields.armorLeatherChest.id && itemStack.getData().containsKey("dyed_color")) thePlayer.addStat(ShieldAchievements.COLORS, 1);
		if(item.id == Shields.armorLeatherLeg.id && itemStack.getData().containsKey("dyed_color")) thePlayer.addStat(ShieldAchievements.COLORS, 1);
		if(item.id == Shields.armorLeatherBoot.id && itemStack.getData().containsKey("dyed_color")) thePlayer.addStat(ShieldAchievements.COLORS, 1);
		if(item.id == Shields.leatherShield.id && itemStack.getData().containsKey("dyed_color")) thePlayer.addStat(ShieldAchievements.COLORS, 1);
	}
}
