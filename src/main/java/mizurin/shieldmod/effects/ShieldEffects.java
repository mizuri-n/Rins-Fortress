package mizurin.shieldmod.effects;

import mizurin.shieldmod.effects.render.CustomHeartContainer;
import mizurin.shieldmod.effects.render.ExtraHealthEffectRenderer;
import mizurin.shieldmod.effects.render.PoisonEffectRenderer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.attribute.Attributes;
import sunsetsatellite.catalyst.effects.api.attribute.type.IntAttribute;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRenderer;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRendererDispatcher;
import sunsetsatellite.catalyst.effects.api.modifier.ModifierType;
import sunsetsatellite.catalyst.effects.api.modifier.type.IntModifier;
import turniplabs.halplibe.helper.EnvironmentHelper;

import javax.annotation.Nullable;
import java.util.*;

import static mizurin.shieldmod.ShieldMod.MOD_ID;

public class ShieldEffects {
	public static class LookupLooks {
		public static final LookupLooks instance = new LookupLooks();
		public final Map<Effect, Effect> locker = new HashMap<>();
		public final Map<Effect, HashSet<Effect>> lockedEffects = new HashMap<>();

		public void addEntry(Effect getLocked, Effect lock) {
			this.locker.put(getLocked, lock);
			if (this.lockedEffects.containsKey(lock)) {
				HashSet<Effect> effects = this.lockedEffects.get(lock);
				effects.add(getLocked);
				return;
			}
			HashSet<Effect> effects = new HashSet<>();
			effects.add(getLocked);
			this.lockedEffects.put(lock, effects);
		}

		public @Nullable Effect getLocker(Effect id) {
			return this.locker.getOrDefault(id, null);
		}

		public @Nullable HashSet<Effect> getLockedEffects(Effect id) {
			return this.lockedEffects.getOrDefault(id, null);
		}

		public Map<Effect, Effect> getLockerMap() {
			return this.locker;
		}

		public Map<Effect, HashSet<Effect>> getLockedEffectsMap() {
			return this.lockedEffects;
		}
	}

	private static boolean hasInit = false;

	public static void init() {
		if (hasInit) {
			return;
		}
		hasInit = true;
		registerAttributes();
		assignEffects();
		registerEffects();
		if (!EnvironmentHelper.isServerEnvironment()) assignEffectRenderers();
	}


	public static IntAttribute EXTRA_HEALTH = (IntAttribute) new IntAttribute("attribute.shieldmod.extraHealth", 0).setAsDefault();

	private static void registerAttributes() {
		Attributes catalystAttributes = Attributes.getInstance();

		catalystAttributes.register("shieldmod:extra_health", EXTRA_HEALTH);
	}

	public static Effect poisonEffect;
	public static Effect webEffect;
	public static Effect slowEffect;
	public static Effect weaknessEffect;
	public static Effect extraHealthEffect;

	/**
	 * @implNote The path for the assets that effects uses is: assets/ + MOD_ID +/effects/icon/ + imagePath
	 */
	private static void assignEffects() {
		extraHealthEffect = new AmuletEffect(
			"effect.shieldmod.extra_health",
			MOD_ID + ":extra_health",
			Collections.singletonList(new IntModifier(EXTRA_HEALTH, ModifierType.ADD, 1)),
			EffectTimeType.KEEP,
			1
		).setDefaultDuration(600);

		poisonEffect = new PoisonEffect(
			"effect.shieldmod.poison",
			MOD_ID + ":poison",
			new ArrayList<>(),
			EffectTimeType.KEEP,
			1
		).setDefaultDuration(200);

		webEffect = new WebEffect(
			"effect.shieldmod.web",
			MOD_ID + ":web",
			new ArrayList<>(),
			EffectTimeType.KEEP,
			1
		).setDefaultDuration(60);

		slowEffect = new SlowEffect(
			"effect.shieldmod.slow",
			MOD_ID + ":slow",
			new ArrayList<>(),
			EffectTimeType.KEEP,
			1
		).setDefaultDuration(100);

		weaknessEffect = new WeakEffect(
			"effect.shieldmod.weak",
			MOD_ID + ":weak",
			new ArrayList<>(),
			EffectTimeType.KEEP,
			1
		).setDefaultDuration(100);

		ShieldEffects.registerLock(poisonEffect, webEffect, slowEffect, weaknessEffect);
	}

	private static void registerEffects() {
		Effects effects = Effects.getInstance();
		effects.register(extraHealthEffect.id, extraHealthEffect);
		effects.register(poisonEffect.id, poisonEffect);
		effects.register(webEffect.id, webEffect);
		effects.register(slowEffect.id, slowEffect);
		effects.register(weaknessEffect.id, weaknessEffect);
	}

	private static void assignEffectRenderers() {
		EffectRendererDispatcher dispatcher = EffectRendererDispatcher.getInstance();
		dispatcher.addDispatch(extraHealthEffect,
			new ExtraHealthEffectRenderer<>(extraHealthEffect)
				.setIcon("regen_amulet.png")
		);

		dispatcher.addDispatch(webEffect, new EffectRenderer<Effect>(webEffect).setIcon("icon_web.png"));

		dispatcher.addDispatch(slowEffect, new EffectRenderer<Effect>(slowEffect).setIcon("icon_slow.png"));

		dispatcher.addDispatch(weaknessEffect, new EffectRenderer<Effect>(weaknessEffect).setIcon("icon_weakness.png"));

		dispatcher.addDispatch(poisonEffect, new PoisonEffectRenderer<>(
				poisonEffect,
				"shieldmod:gui/hud/poison/"
			)
				.setIcon("icon_poison.png")
		);
	}

	/**
	 * @param affected       effect that lock will act on
	 * @param lock           affected effect won't apply if this effect is present
	 * @param weaknessEffect
	 * @param effect
	 */
	public static void registerLock(Effect affected, Effect lock, Effect weaknessEffect, Effect effect) {
		LookupLooks.instance.addEntry(affected, lock);
	}

	/**
	 * @param player affected Player
	 * @return most potent EffectStack affecting the player
	 */
	public static EffectStack resolveDominantEffect(Player player) {
		EffectStack dominant = null;
		EffectRendererDispatcher dispatcher = EffectRendererDispatcher.getInstance();

		for (EffectStack effectStack : ((IHasEffects) player).getContainer().getEffects()) {
			if (dispatcher.getDispatch(effectStack.getEffect()) instanceof CustomHeartContainer) {
				if (dominant == null) dominant = effectStack;
				int effectStackPotency = effectStack.getAmount() * effectStack.getDuration();
				int dominantPotency = dominant.getAmount() * dominant.getDuration();
				if (effectStackPotency > dominantPotency) dominant = effectStack;
			}
		}
		return dominant;
	}


	/**
	 * @param entity    affected Mob
	 * @param newEffect Effect affecting the entity
	 * @param amount    stack size of the effect
	 * @return true if the effect was applied false otherwise
	 * @apiNote If you want aether style effect use this function to add your effects.
	 * @implNote Effect can only affect entity if the effect is not locked.
	 * Each effect defined what effect lock it out from being reapplied.
	 * Returns always false if a given effect is locked.
	 * @see ILockInteractable
	 */
	public static boolean add(Entity entity, Effect newEffect, int amount) {
		if (!(entity instanceof IHasEffects)) return false;
		EffectStack stack = new EffectStack((IHasEffects) entity, newEffect, amount);
		return ShieldEffects.add(entity, stack);
	}


	/**
	 * @param entity     affected Mob
	 * @param stackToAdd Effect stack affecting the entity
	 * @return true if the effect was applied false otherwise
	 * @apiNote If you want aether style effect use this function to add your effects.
	 * @implNote Effect can only affect entity if the effect is not locked.
	 * Each effect defined what effect lock it out from being reapplied.
	 * Returns always false if a given effect is locked.
	 * @see ILockInteractable
	 */
	public static boolean add(Entity entity, EffectStack stackToAdd) {
		if (!(entity instanceof IHasEffects)) return false;
		IHasEffects hasEffects = (IHasEffects) entity;

		for (EffectStack currStack : hasEffects.getContainer().getEffects()) {
			Effect currEffect = currStack.getEffect();
			int currMax = currEffect.getMaxStack();

			if (currEffect == stackToAdd.getEffect()) {
				if (currStack.getAmount() + stackToAdd.getAmount() >= currMax) {
					int amountToAdd = currMax - currStack.getAmount();

					currStack.add(amountToAdd, hasEffects.getContainer());
					return true;
				}
			}
		}

		if (isLocked(stackToAdd, ((IHasEffects) entity).getContainer())) return false;

		stackToAdd.start(hasEffects.getContainer());
		hasEffects.getContainer().add(stackToAdd);
		return true;
	}


	public static <T> boolean isLocked(EffectStack effectStack, EffectContainer<T> effectContainer) {
		Effect effectBlocked = effectStack.getEffect();
		Effect effectBlocker = ShieldEffects.LookupLooks.instance.getLocker(effectBlocked);

		if (effectBlocker == null) return false;

		T parent = effectContainer.getParent();
		if (parent instanceof IHasEffects && parent instanceof Mob) {
			if (effectBlocker instanceof ILockInteractable && effectContainer.hasEffect(effectBlocker)) {
				((ILockInteractable) effectBlocker).lockTriggered((IHasEffects) parent);

				effectContainer.remove(effectBlocked);
				return true;
			}
		}

		return false;
	}
}
