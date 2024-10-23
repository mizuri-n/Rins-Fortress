package mizurin.shieldmod.mixins;

import mizurin.shieldmod.interfaces.IDazed;
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

	//Mirrors the fire status effect but cannot be put out and slows the entity.
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
			//the ticks are put in a way to deal small damage and slow while also being offset by fire status
			//having it mimic the fire status countdown causes them to overlap and only deal damage once due to invulnerability frames.
		}

	}
	@Override
	public void shieldmod$dazedHurt(){
		this.hurt((Entity) null, 1, DamageType.COMBAT);
		this.remainingDazedTicks = 300;
	}
}
