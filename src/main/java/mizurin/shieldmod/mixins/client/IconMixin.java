package mizurin.shieldmod.mixins.client;

import mizurin.shieldmod.interfaces.IDazed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentHealthBar;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HudComponentHealthBar.class, remap = false)
public class IconMixin {
	// TODO This needs to be reworked
//	@Inject(method = "render(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/hud/HudIngame;IIF)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glBindTexture(II)V", shift = At.Shift.AFTER))
//	public void injectIcon(Minecraft mc, HudIngame gui, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci){
//		if (((IDazed) mc.thePlayer).shieldmod$getDazedHurt() > 0) {
//			GL11.glBindTexture(3553, mc.renderEngine.getTexture("/assets/shieldmod/textures/gui/icons.png"));
//		}
//	}
}

