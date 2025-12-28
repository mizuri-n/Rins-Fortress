package mizurin.shieldmod.compat.aether.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import mizurin.shieldmod.compat.aether.item.GravititeShield;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.accessory.pendant.ItemPendant;

@Mixin(value = ContainerInventory.class, remap = false, priority = 1200)
abstract public class PendantMixin {
	@WrapOperation(method = "getTotalProtectionAmount", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/core/item/IArmorItem;getArmorPiece()I")
	)
	public int ignoreSlotEqualityForTrickets(IArmorItem instance, Operation<Integer> original, @Local int i) {
		if (instance instanceof ItemPendant || instance instanceof GravititeShield) {
			return i;
		}
		return original.call(instance);
	}
}
