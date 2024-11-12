package mizurin.shieldmod.mixins;

import mizurin.shieldmod.interfaces.IDazed;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = EntityLiving.class, remap = false)
public abstract class DazedMixin extends Entity implements IDazed {


	@Unique
	private static final int DATA_DAZE = 24;

	@Unique
	private static final int DATA_FREEZE = 25;

	public DazedMixin(World world) {
		super(world);
	}


	@Shadow
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		return false;
	}

	@Shadow
	public abstract int getMaxHealth();

	@Shadow
	public int bonusHealth;

	@Shadow
	public abstract void setHealthRaw(int health);

	@Inject(method = "<init>", at = @At("TAIL"))
	public void defineSyncStatus(CallbackInfo ci){
		entityData.define(DATA_DAZE, 0);
		entityData.define(DATA_FREEZE, 0);
	}


	//Mirrors the fire status effect but cannot be put out and slows the entity.
	@Inject(method = "baseTick()V", at = @At("HEAD"))
	public void inject(CallbackInfo callbackInfo) {
		if (this.shieldmod$getDazedHurt() > 0) {

			this.xd *= 0.85D;
			this.zd *= 0.85D;

			if (this.shieldmod$getDazedHurt() % 16 == 0) {
				this.hurt( null, 1, DamageType.COMBAT);
			}

			this.entityData.set(DATA_DAZE, this.entityData.getInt(DATA_DAZE) - 3);
			//the ticks are put in a way to deal small damage and slow while also being offset by fire status
			//having it mimic the fire status countdown causes them to overlap and only deal damage once due to invulnerability frames.
		}
		if (this.shieldmod$getFreezeHurt() > 0) {
			this.xd *= 0.65D;
			this.zd *= 0.65D;

			this.entityData.set(DATA_FREEZE, this.entityData.getInt(DATA_FREEZE) - 1);

		}

	}
	@Override
	public void shieldmod$dazedHurt(int dazedTicks){
		this.entityData.set(DATA_DAZE, dazedTicks);
	}

	@Override
	public int shieldmod$getDazedHurt() {
		return entityData.getInt(DATA_DAZE);
	}


	@Override
	public void shieldmod$freezeHurt(int freezeTicks) {
		this.entityData.set(DATA_FREEZE, freezeTicks);
	}

	@Override
	public int shieldmod$getFreezeHurt() {
		return entityData.getInt(DATA_FREEZE);
	}

}
