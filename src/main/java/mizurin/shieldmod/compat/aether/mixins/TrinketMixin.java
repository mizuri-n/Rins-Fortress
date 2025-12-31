package mizurin.shieldmod.compat.aether.mixins;


import mizurin.shieldmod.compat.aether.item.RFItemTags;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.item.AetherItemTags;
import teamport.aether.item.accessory.SlotAccessory;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;

@Mixin(value = SlotAccessory.class, remap = false)
public class TrinketMixin {

	@Shadow
	int armorType;

	@Inject(method = "mayPlace(Lnet/minecraft/core/item/ItemStack;)Z", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
	public void fixMayPlace(ItemStack itemstack, CallbackInfoReturnable<Boolean> cir){
		Item item = itemstack.getItem();
		cir.setReturnValue((item.hasTag(AetherItemTags.TRINKET) || item.hasTag(RFItemTags.TRINKETCUSTOM)) && this.armorType >= TRINKET_1_SLOT);
	}
}

