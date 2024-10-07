package mizurin.shieldmod.mixins;

import mizurin.shieldmod.item.IDazed;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, remap = false)
public class DazedMixin implements IDazed {

	@Shadow
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		return false;
	}
	@Shadow
	public double xd;

	@Shadow
	public double zd;


	@Shadow
	public int heartsFlashTime;
	@Unique
	public int remainingDazedTicks;

	@Inject(method = "Lnet/minecraft/core/entity/Entity;baseTick()V", at = @At("HEAD"))
	public void inject(CallbackInfo callbackInfo) {
		if (this.remainingDazedTicks > 0) {
			this.xd *= 0.85D;
			this.zd *= 0.85D;
			if (this.remainingDazedTicks % 16 == 0) {
				this.hurt((Entity) null, 1, DamageType.COMBAT);
				this.heartsFlashTime = 0;
			}

			--this.remainingDazedTicks;
			--this.remainingDazedTicks;
			--this.remainingDazedTicks;
		}
	}
	@Override
	public void better_with_defense$dazedHurt(){
		this.hurt((Entity) null, 1, DamageType.COMBAT);
		this.remainingDazedTicks = 300;
	}
}
