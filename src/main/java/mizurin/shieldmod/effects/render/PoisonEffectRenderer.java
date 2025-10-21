package mizurin.shieldmod.effects.render;

import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRenderer;
import sunsetsatellite.catalyst.effects.api.effect.render.TintEffectRender;

public class PoisonEffectRenderer<T extends Effect> extends EffectRenderer<T> implements CustomHeartContainer {
	public final String PATH_HEART;

	public PoisonEffectRenderer(T effect, String heartPath) {
		super(effect);
		PATH_HEART = heartPath;
	}

//	@Override
//	public float calcAlpha(EffectStack effectStack) {
//		float currentAmount = (float) effectStack.getDuration() * (effectStack.getAmount() - 1);
//		float totalTime = (float) effectStack.getDuration() * effectStack.getEffect().getMaxStack();
//		float percent = (currentAmount + effectStack.getTimeLeft()) / totalTime;
//		return 0.35F + percent / 3.0F;
//	}


	@Override
	public HeartContainer getCustomContainer(Player player) {
		return new HeartContainerSimple(player, this.PATH_HEART);
	}
}
