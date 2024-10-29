package mizurin.shieldmod.mixins;

import mizurin.shieldmod.interfaces.IDazed;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
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


	@Unique
	public int remainingDazedTicks;

	@Unique
	public int remainingFreezeTicks;

	//Mirrors the fire status effect but cannot be put out and slows the entity.
	@Inject(method = "baseTick()V", at = @At("HEAD"))
	public void inject(CallbackInfo callbackInfo) {
		if (this.remainingDazedTicks > 0) {
			this.xd *= 0.85D;
			this.zd *= 0.85D;
			if (this.remainingDazedTicks % 16 == 0) {
				this.hurt( null, 1, DamageType.COMBAT);
			}

			--this.remainingDazedTicks;
			--this.remainingDazedTicks;
			--this.remainingDazedTicks;
			//the ticks are put in a way to deal small damage and slow while also being offset by fire status
			//having it mimic the fire status countdown causes them to overlap and only deal damage once due to invulnerability frames.
		}
		if (this.remainingFreezeTicks > 0) {
			this.xd *= 0.65D;
			this.zd *= 0.65D;

			--this.remainingFreezeTicks;

		}

	}
	@Override
	public void shieldmod$dazedHurt(int dazedTicks){
		this.hurt(null, 1, DamageType.COMBAT);
		this.remainingDazedTicks = dazedTicks;
	}

	@Override
	public int shieldmod$getDazedHurt() {
		return remainingDazedTicks;
	}


	@Override
	public void shieldmod$freezeHurt(int freezeTicks) {
	this.remainingFreezeTicks = freezeTicks;
	}

	@Override
	public int shieldmod$getFreezeHurt() {
		return remainingFreezeTicks;
	}

}
