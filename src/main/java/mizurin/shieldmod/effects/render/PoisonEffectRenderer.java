package mizurin.shieldmod.effects.render;

import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRenderer;

public class PoisonEffectRenderer<T extends Effect> extends EffectRenderer<T> implements CustomHeartContainer {
	public final String PATH_HEART;

	public PoisonEffectRenderer(T effect, String heartPath) {
		super(effect);
		PATH_HEART = heartPath;
	}

	@Override
	public HeartContainer getCustomContainer(Player player) {
		return new HeartContainerSimple(player, this.PATH_HEART);
	}
}
