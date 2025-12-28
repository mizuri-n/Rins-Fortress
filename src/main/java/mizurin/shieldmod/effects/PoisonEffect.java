package mizurin.shieldmod.effects;

import mizurin.shieldmod.ShieldMod;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.DamageType;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import java.util.List;
import java.util.Random;

public class PoisonEffect extends Effect {
	public final Random random = new Random();

	public PoisonEffect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
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
	}

	@Override
	public <T> void expired(EffectStack effectStack, EffectContainer<T> effectContainer) {
		effectContainer.remove(ShieldEffects.poisonEffect);
		EffectStack newStack = new EffectStack((IHasEffects) effectContainer.getParent(), ShieldEffects.poisonEffect, effectStack.getAmount() - 1);
		newStack.start(effectContainer);
		effectContainer.add(newStack);
	}

	@Override
	public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
		if (!(effectContainer.getParent() instanceof Mob)) return;
		Mob mob = (Mob) effectContainer.getParent();
		if (mob.world == null) {
			ShieldMod.LOGGER.warn("PoisonEffect is not applied cause the world is null");
			return;
		}
		if(mob.tickCount % 60 == 0){
			assert effectContainer.getParent() instanceof Mob;
			((Mob) effectContainer.getParent()).hurt(null, 2, DamageType.GENERIC);
		}
		if (mob.tickCount % 10 == 0) {
			float width = 1.0f;
			double dx = mob.world.rand.nextGaussian() * 0.002;
			double dy = mob.world.rand.nextGaussian() * 0.002;
			double dz = mob.world.rand.nextGaussian() * 0.002;
			mob.world.spawnParticle(
				"purpleflame",
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
