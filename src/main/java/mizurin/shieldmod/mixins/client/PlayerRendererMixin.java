package mizurin.shieldmod.mixins.client;

import mizurin.shieldmod.ColoredArmorTexture;
import mizurin.shieldmod.ShieldMod;
import mizurin.shieldmod.interfaces.IColoredArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(value = MobRendererPlayer.class, remap = false)
public class PlayerRendererMixin {
	@Unique
	private final Minecraft mc = Minecraft.getMinecraft();
	@Unique
	private ColoredArmorTexture[] armorTextures;
	@Inject(method = "Lnet/minecraft/client/render/entity/MobRendererPlayer;prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At("HEAD"))
	private void colorArmor(Player entity, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> cir){
		float brightness = mc.fullbright ? 1f : entity.getBrightness(0);
		GL11.glColor4f(brightness,brightness,brightness,1f);
		ItemStack itemstack = entity.inventory.armorItemInSlot(3 - renderPass);
		armorTextures = null;
		if (itemstack != null && itemstack.getItem() instanceof IColoredArmor){
			armorTextures =((IColoredArmor) itemstack.getItem()).getArmorTextures(itemstack);
			if (ShieldMod.playerArmorRenderOffset > armorTextures.length) return;
			Color color = armorTextures[ShieldMod.playerArmorRenderOffset].getColor();
			GL11.glColor4f((color.getRed()/255f) * brightness, (color.getGreen()/255f) * brightness, (color.getBlue()/255f) * brightness,color.getAlpha()/255f);
		}
	}
	@Inject(method = "Lnet/minecraft/client/render/entity/MobRendererPlayer;prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At("TAIL"))
	private void colorArmorOff(Player entity, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> cir){
		float brightness = mc.fullbright ? 1f : entity.getBrightness(0);
		GL11.glColor4f(brightness,brightness,brightness,1f);
	}
	@Redirect(method = "Lnet/minecraft/client/render/entity/MobRendererPlayer;prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerRenderer;loadTexture(Ljava/lang/String;)V", ordinal = 3))
	private void customArmorTexture(MobRendererPlayer instance, String string){
		if (armorTextures != null){
			if (ShieldMod.playerArmorRenderOffset > armorTextures.length) return;
			String tmp = string.replace(".png", "");
			int renderPass = Integer.decode(String.valueOf(tmp.charAt(tmp.length()-1)));
			NamespaceID armorTexture = armorTextures[ShieldMod.playerArmorRenderOffset].getArmorTexture();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/assets/" + armorTexture.namespace + "/textures/armor/" + armorTexture.value + "_" + (renderPass != 2 ? 1 : 2) + ".png"));
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture(string));
		}
	}
}
