package mizurin.shieldmod.compat.aether.item;

import mizurin.shieldmod.effects.ShieldEffects;
import mizurin.shieldmod.interfaces.IHasHealthSteal;
import mizurin.shieldmod.item.ShieldItem;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;

public class HolystoneShield extends ShieldItem implements IHasHealthSteal {

	public HolystoneShield(String name, String namespaceID, int id, ToolMaterial toolMaterial) {
		super(name, namespaceID, id, toolMaterial);
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, Mob target, Mob player) {
		if ((target.hurtTime == 10 || target instanceof Player)) {

			ShieldEffects.add(target, ShieldEffects.weaknessEffect, 1);
			target.fling(target.xd * 0.2, target.yd * 0, target.zd * 0.2, 0.5F);

			itemstack.damageItem(1, player);
		}

		return true;
	}
}
