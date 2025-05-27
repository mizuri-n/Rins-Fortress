package mizurin.shieldmod.mixins.client;

import mizurin.shieldmod.interfaces.IDazed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentHealthBar;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.item.ItemBucketIceCream;
import net.minecraft.core.item.ItemFood;
import org.checkerframework.checker.signature.qual.SignatureUnknown;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = HudComponentHealthBar.class, remap = false)
public class IconMixin {
	// TODO This needs to be reworked
//
//
//	@Inject(method = "render(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/hud/HudIngame;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/PlayerLocal;getGamemode()Lnet/minecraft/core/player/gamemode/Gamemode;", shift = At.Shift.AFTER))
//	public void injectIcon(Minecraft mc, HudIngame gui, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci) {
//	}
}

