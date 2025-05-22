package mizurin.shieldmod.mixins.client;

import com.llamalad7.mixinextras.sugar.Local;
import mizurin.shieldmod.ColoredArmorTexture;
import mizurin.shieldmod.ShieldMod;
import mizurin.shieldmod.interfaces.IColoredArmor;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//Mixin for colored armor renderer
@Mixin(value = MobRenderer.class, remap = false)
public abstract class LivingRendererMixin<T extends Mob> {
	@Shadow
	protected abstract boolean prepareArmor(T entity, int layer, float partialTick);

	@Shadow
	protected ModelBase armorModel;
	@Unique
	float limbSwing;
	@Unique
	float limbYaw;
	@Unique
	float ticksExisted;
	@Unique
	float headYaw;
	@Unique
	float headYawOffset;
	@Unique
	float headPitch;
	@Unique
	float scale;
	@Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobRenderer;prepareArmor(Lnet/minecraft/core/entity/Mob;IF)Z"))
	private void captureLocals(
		Tessellator tessellator, T entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci,
		@Local(name = "walkProgress") float limbSwing,
		@Local(name = "walkSpeed") float limbYaw,
		@Local(name = "limbSway") float ticksExisted,
		@Local(name = "headYaw") float headYaw,
		@Local(name = "bodyYaw") float headYawOffset,
		@Local(name = "headPitch") float headPitch,
		@Local(name = "scale") float scale
	){
		this.limbSwing = limbSwing;
		this.limbYaw = limbYaw;
		this.ticksExisted = ticksExisted;
		this.headYaw = headYaw;
		this.headYawOffset = headYawOffset;
		this.headPitch = headPitch;
		this.scale = scale;
	}
	@Redirect(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobRenderer;prepareArmor(Lnet/minecraft/core/entity/Mob;IF)Z"))
	private boolean hijackRenderPass(MobRenderer instance, T entity, int renderPass, float partialTick){
		ShieldMod.playerArmorRenderOffset = 0;
		if (entity instanceof Player){
			ItemStack itemstack = ((Player) entity).inventory.armorItemInSlot(3 - renderPass);
			if (itemstack != null && itemstack.getItem() instanceof IColoredArmor){
				// do stuff
				ColoredArmorTexture[] cTex = ((IColoredArmor) itemstack.getItem()).getArmorTextures(itemstack);
				for (ColoredArmorTexture ignored : cTex) {
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(770, 771);
					prepareArmor(entity, renderPass, partialTick);
					this.armorModel.render(limbSwing, limbYaw, ticksExisted, headYaw - headYawOffset, headPitch, scale);
					GL11.glDisable(3042);
					GL11.glEnable(3008);
					ShieldMod.playerArmorRenderOffset++;
				}
				ShieldMod.playerArmorRenderOffset = 0;
				return false;
			}
		}
		return this.prepareArmor(entity, renderPass, partialTick);
	}
}
