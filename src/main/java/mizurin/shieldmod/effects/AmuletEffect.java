package mizurin.shieldmod.effects;
import mizurin.shieldmod.ShieldMod;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import sunsetsatellite.catalyst.effects.helper.HealthHelper;

import java.util.List;
import java.util.Random;

public class AmuletEffect extends Effect {
	public final Random random = new Random();

	public AmuletEffect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
		super(nameKey, id, modifiers, effectTimeType, maxStack);
	}

	@Override
	public <T> void activated(EffectStack effectStack, EffectContainer<T> effectContainer) {
		if (!canApplyTo((Entity) effectContainer.getParent())) {
			return;
		}
		if (ShieldEffects.isLocked(effectStack, effectContainer)) {
			return;
		}
		if (effectStack.getAmount() == 1) {
			HealthHelper.addExtraHealth(((Player) effectContainer.getParent()), 6);
		}
	}

	@Override
	public <T> void expired(EffectStack effectStack, EffectContainer<T> effectContainer) {
		effectContainer.remove(ShieldEffects.extraHealthEffect);
		EffectStack newStack = new EffectStack((IHasEffects) effectContainer.getParent(), ShieldEffects.extraHealthEffect, effectStack.getAmount() - 1);
		newStack.start(effectContainer);
		effectContainer.add(newStack);
	}

	@Override
	public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
		if (!(effectContainer.getParent() instanceof Mob)) return;
		Mob mob = (Mob) effectContainer.getParent();
		if (mob.world == null) {
			ShieldMod.LOGGER.warn("AmuletEffect is not applied cause the world is null");
			return;
		}
		if(mob.tickCount % 600 == 0){
			assert effectContainer.getParent() instanceof Mob;
			mob.tickCount = 0;
			mob.heal(1);

			float width = 1.0f;
			double dx = mob.world.rand.nextGaussian() * 0.002;
			double dy = mob.world.rand.nextGaussian() * 0.002;
			double dz = mob.world.rand.nextGaussian() * 0.002;
			mob.world.spawnParticle(
				"heart",
				mob.x + (double) (mob.world.rand.nextFloat() * width * 2.0F) - (double) width,
				mob.y + mob.getHeadHeight() - 1 + (double) (mob.world.rand.nextFloat() * width),
				mob.z + (double) (mob.world.rand.nextFloat() * width * 2.0F) - (double) width,
				dx, dy, dz, 0
			);
		}
	}
	@Override
	public boolean canApplyTo(Entity target) {
		return target instanceof Mob && super.canApplyTo(target);
	}
	@Override
	public <T> void stackAdded(EffectStack effectStack, EffectContainer<T> effectContainer) {
		super.stackAdded(effectStack, effectContainer);
	}
}
