package mizurin.shieldmod.effects;

import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;

public interface ILockInteractable {
	default void lockTriggered(IHasEffects entity) {
	}
}
