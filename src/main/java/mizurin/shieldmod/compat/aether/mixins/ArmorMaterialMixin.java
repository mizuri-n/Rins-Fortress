package mizurin.shieldmod.compat.aether.mixins;

import mizurin.shieldmod.item.ArmorMaterials;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.AetherMod;

@Mixin(value = ArmorMaterials.class, remap = false)
public class ArmorMaterialMixin {
	static {
		ArmorMaterials.rockyArmor.withProtectionPercentage(AetherMod.HOLY, 20f).withProtectionPercentage(AetherMod.LIGHTNING, 10f);
		ArmorMaterials.heartAmulet.withProtectionPercentage(AetherMod.HOLY, 30f).withProtectionPercentage(AetherMod.LIGHTNING, 30f);
	}
}
