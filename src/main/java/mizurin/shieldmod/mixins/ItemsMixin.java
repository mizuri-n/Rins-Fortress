package mizurin.shieldmod.mixins;

import mizurin.shieldmod.item.RFItems;
import net.minecraft.core.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Items.class, remap = false)
public class ItemsMixin {
	@Inject(method = "setupItems", at = @At("TAIL"))
	private static void replaceLeatherArmor(CallbackInfo ci) {
		RFItems.replaceVanillaItems();
	}
}
