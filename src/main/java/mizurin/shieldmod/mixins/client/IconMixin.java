package mizurin.shieldmod.mixins.client;

import mizurin.shieldmod.interfaces.IDazed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.HealthBarComponent;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HealthBarComponent.class, remap = false)
public class IconMixin {
	@Inject(method = "render(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/GuiIngame;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderEngine;getTexture(Ljava/lang/String;)I"))
	public void injectIcon(Minecraft mc, GuiIngame gui, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci){
		if (((IDazed) mc.thePlayer).shieldmod$getDazedHurt() > 0) {
			GL11.glBindTexture(3553, mc.renderEngine.getTexture("assets/shieldmod/textures/gui/icons.png"));
		}
	}
}

