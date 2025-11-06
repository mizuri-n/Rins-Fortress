package mizurin.shieldmod.compat.aether.mixins;

import mizurin.shieldmod.compat.aether.item.RFAetherItems;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.world.feature.dungeon.silver.WorldFeatureAetherSilverDungeon;

@Mixin(value = WorldFeatureAetherSilverDungeon.class, remap = false)
public class SilverTreasureMixin {
	@Shadow
	@Final
	public static WeightedRandomBag<WeightedRandomLootObject> TREASURE;

	static {
		TREASURE.addEntry(new WeightedRandomLootObject(RFAetherItems.valkyrieShield.getDefaultStack()), 200.0);
	}
}
