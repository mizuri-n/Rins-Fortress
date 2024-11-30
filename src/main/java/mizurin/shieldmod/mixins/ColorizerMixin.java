package mizurin.shieldmod.mixins;

import mizurin.shieldmod.blocks.RinBlocks;
import net.minecraft.client.render.colorizer.Colorizer;
import net.minecraft.client.render.colorizer.Colorizers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Colorizers.class, remap = false)
public abstract class ColorizerMixin {

	@Shadow
	public static Colorizer add(Colorizer colorizer) {
		return null;
	}

	@Inject(method = "registerColorizers()V", at = @At("TAIL"))
	private static void injectColor(CallbackInfo ci){
		RinBlocks.apple = add(new Colorizer("apple"));
	}
}
