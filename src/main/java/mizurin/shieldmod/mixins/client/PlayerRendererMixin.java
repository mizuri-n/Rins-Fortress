package mizurin.shieldmod.mixins.client;

import mizurin.shieldmod.ColoredArmorTexture;
import mizurin.shieldmod.ShieldMod;
import mizurin.shieldmod.interfaces.IColoredArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.Color;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MobRendererPlayer.class, remap = false)
public abstract class PlayerRendererMixin extends MobRenderer<Player> {
	@Unique
	private final Minecraft mc = Minecraft.getMinecraft();
	@Unique
	private ColoredArmorTexture[] armorTextures;

	public PlayerRendererMixin(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}

	@Inject(method = "prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At("HEAD"))
	private void colorArmor(Player entity, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> cir){
		if(renderPass > 3){
			return;
		}
		float brightness = mc.fullbright ? 1f : entity.getBrightness(0);
		GL11.glColor4f(brightness,brightness,brightness,1f);
		ItemStack itemstack = entity.inventory.armorItemInSlot(3 - renderPass);
		armorTextures = null;
		if (itemstack != null && itemstack.getItem() instanceof IColoredArmor){
			armorTextures =((IColoredArmor) itemstack.getItem()).getArmorTextures(itemstack);
			if (ShieldMod.playerArmorRenderOffset > armorTextures.length) return;
			int color = armorTextures[ShieldMod.playerArmorRenderOffset].getColor();
			GL11.glColor4f((Color.redFromInt(color) /255f) * brightness, (Color.greenFromInt(color)/255f) * brightness, (Color.blueFromInt(color)/255f) * brightness, Color.alphaFromInt(color)/255f);
		}
	}
	@Inject(method = "prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At("TAIL"))
	private void colorArmorOff(Player entity, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> cir){
		float brightness = mc.fullbright ? 1f : entity.getBrightness(0);
		GL11.glColor4f(brightness,brightness,brightness,1f);
	}
	@Redirect(method = "prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobRendererPlayer;bindTexture(Ljava/lang/String;)V", ordinal = 3))
	private void customArmorTexture(MobRendererPlayer instance, String string){
		if (armorTextures != null){
			if (ShieldMod.playerArmorRenderOffset > armorTextures.length) return;
			String tmp = string.replace(".png", "");
			int renderPass = Integer.decode(String.valueOf(tmp.charAt(tmp.length()-1)));
			NamespaceID armorTexture = armorTextures[ShieldMod.playerArmorRenderOffset].getArmorTexture();
			this.bindTexture("/assets/" + armorTexture.namespace() + "/textures/armor/" + armorTexture.value() + "_" + (renderPass != 2 ? 1 : 2) + ".png");
		} else {
			this.bindTexture(string);
		}
	}
}
