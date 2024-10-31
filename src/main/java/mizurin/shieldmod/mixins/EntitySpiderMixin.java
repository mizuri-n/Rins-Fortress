package mizurin.shieldmod.mixins;

import mizurin.shieldmod.entities.EntityWeb;
import mizurin.shieldmod.interfaces.IDazed;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.monster.EntitySkeleton;
import net.minecraft.core.entity.monster.EntitySpider;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static mizurin.shieldmod.ShieldMod.expertMode;

@Mixin(value = EntitySpider.class, remap = false)
public class EntitySpiderMixin extends EntityMonster {
	public EntitySpiderMixin(World world) {
		super(world);
	}

	public void spawnInit() {
		super.init();
		if (this.world.difficultySetting != 0 && this.random.nextInt(70 / this.world.difficultySetting) == 0) {
			EntitySkeleton entityskeleton = new EntitySkeleton(this.world);
			entityskeleton.moveTo(this.x, this.y, this.z, this.yRot, 0.0F);
			this.world.entityJoinedWorld(entityskeleton);
			entityskeleton.startRiding(this);
		}

	}

	/**
	 * @author Rin
	 * @reason Painini
	 */
	@Overwrite
	public void attackEntity(Entity entity, float distance) {
		if (expertMode) {
			float brightness = this.getBrightness(1.0F);
			if (brightness > 0.5F && this.random.nextInt(100) == 0) {
				this.entityToAttack = null;
			} else {
				if (distance < 10.0F && distance > 6.0F) {
					double dX = entity.x - this.x;
					double dZ = entity.z - this.z;
					if (this.attackTime == 0) {
						if (!this.world.isClientSide) {
							EntityWeb web = new EntityWeb(this.world, this);
							web.y += 2;
							double d2 = entity.y + (double)entity.getHeadHeight() - 0.2 - web.y;
							float f1 = MathHelper.sqrt_double(dX * dX + dZ * dZ) * 0.2F;
							this.world.playSoundAtEntity((Entity)null, this, "random.bow", 0.5F, 0.4F / (this.random.nextFloat() * 0.4F + 0.8F));
							web.setHeading(dX, d2 + (double) f1, dZ, 1F, 1.0F);
							this.world.entityJoinedWorld(web);

						}

						this.attackTime = 60;
					}

					this.yRot = (float)(Math.atan2(dZ, dX) * 180.0 / Math.PI) - 90.0F;
					this.hasAttacked = true;
				}
				 else if ((distance > 2.0F && distance < 6.0F && this.random.nextInt(10) == 0)) {
					if (this.onGround) {
						double d = entity.x - this.x;
						double d1 = entity.z - this.z;
						float f2 = MathHelper.sqrt_double(d * d + d1 * d1);
						this.xd = d / (double) f2 * 0.5 * 0.800000011920929 + this.xd * 0.20000000298023224;
						this.zd = d1 / (double) f2 * 0.5 * 0.800000011920929 + this.zd * 0.20000000298023224;
						this.yd = 0.4000000059604645;
					}
				} else {
					if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
						this.attackTime = 20;
						entity.hurt(this, 1, DamageType.COMBAT);
						((IDazed) entity).shieldmod$dazedHurt(200);
					}
				}

			}
		} else {
			float brightness = this.getBrightness(1.0F);
			if (brightness > 0.5F && this.random.nextInt(100) == 0) {
				this.entityToAttack = null;
			} else {
				if (distance > 2.0F && distance < 6.0F && this.random.nextInt(10) == 0) {
					if (this.onGround) {
						double d = entity.x - this.x;
						double d1 = entity.z - this.z;
						float f2 = MathHelper.sqrt_double(d * d + d1 * d1);
						this.xd = d / (double)f2 * 0.5 * 0.800000011920929 + this.xd * 0.20000000298023224;
						this.zd = d1 / (double)f2 * 0.5 * 0.800000011920929 + this.zd * 0.20000000298023224;
						this.yd = 0.4000000059604645;
					}
				} else {
					super.attackEntity(entity, distance);
				}

			}
		}
	}
}
