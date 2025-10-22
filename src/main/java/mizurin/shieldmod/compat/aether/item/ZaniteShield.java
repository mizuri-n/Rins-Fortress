package mizurin.shieldmod.compat.aether.item;

import mizurin.shieldmod.effects.ShieldEffects;
import mizurin.shieldmod.item.ShieldItem;
import mizurin.shieldmod.item.ShieldMaterials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;

import static teamport.aether.AetherMod.ZANITE_MULTIPLIER;

public class ZaniteShield extends ShieldItem {

	public ZaniteShield(String name, String namespaceID, int id, ShieldMaterials shieldMaterials) {
		super(name, namespaceID, id, shieldMaterials);
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, Mob target, Mob player) {
		if ((target.hurtTime == 10 || target instanceof Player)) {

			ShieldEffects.add(target, ShieldEffects.poisonEffect, 1);
			target.fling(target.xd * 0.2, target.yd * 0, target.zd * 0.2, 0.5F);

			itemstack.damageItem(1, player);
		}

		return true;
	}
	@Override
	public int getDamageVsEntity(Entity entity, ItemStack is) {
		// we will 'lerp' between the starting damage and starting damage time ZANITE_MULTIPLIER
		float durability_progress = (float) is.getMetadata() / this.getMaxDamage();
		float starting_damage = (float) super.getDamageVsEntity(entity, is);
		return Math.round(MathHelper.lerp(starting_damage, starting_damage * ZANITE_MULTIPLIER, durability_progress));
	}

	@Override
	public float getGuard(ItemStack is) {
		float durability_progress = (float) is.getMetadata() / this.getMaxDamage();
		float starting_guard = (float) super.getGuard(is);
		return (MathHelper.lerp(starting_guard, starting_guard * 0.5f, durability_progress));
	}
}
