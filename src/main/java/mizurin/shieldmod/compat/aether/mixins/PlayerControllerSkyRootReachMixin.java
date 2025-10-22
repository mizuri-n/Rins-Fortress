package mizurin.shieldmod.compat.aether.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import mizurin.shieldmod.item.ShieldMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerController;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(value = PlayerController.class, remap = false)
public class PlayerControllerSkyRootReachMixin {

	@Final
	@Shadow
	protected Minecraft mc;

	@ModifyReturnValue(method = "getBlockReachDistance", at = @At("RETURN"))
	public float getBlockReachDistance(float original) {
		return original + (ShieldMaterials.isHoldingSkyRootTool(this.mc.thePlayer) ? 1 : 0);
	}


	@ModifyReturnValue(method = "getEntityReachDistance", at = @At("RETURN"))
	public float getEntityReachDistance(float original) {
		return original + (ShieldMaterials.isHoldingSkyRootTool(this.mc.thePlayer) ? 1 : 0);
	}
}
