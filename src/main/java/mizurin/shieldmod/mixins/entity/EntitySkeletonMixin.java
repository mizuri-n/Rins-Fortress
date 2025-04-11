package mizurin.shieldmod.mixins.entity;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.monster.MobSkeleton;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static mizurin.shieldmod.ShieldMod.expertMode;

@Mixin(value = MobSkeleton.class, remap = false)
public class EntitySkeletonMixin extends MobMonster {
	public EntitySkeletonMixin(World world) {
		super(world);
	}
	public void spawnInit() {
		if(expertMode){
			this.mobDrops.add(new WeightedRandomLootObject(Items.FLINT.getDefaultStack(), 0, 1));
		}
	}

	protected void dropDeathItems() {
		if (this.random.nextInt(1000) == 0) {
			this.dropItem(Items.BUCKET_MILK.id, 1);
		}

		super.dropDeathItems();
	}

	/**
	 * @author Rin
	 * @reason I hate myself
	 */
	@Overwrite
	public void attackEntity(Entity entity, float distance) {
		if (expertMode) {
			if (distance < 12.0F) {
				double d = entity.x - this.x;
				double d1 = entity.z - this.z;
				if (this.attackTime == 0) {
					if (!this.world.isClientSide) {
						ProjectileArrow entityarrow = new ProjectileArrow(this.world, this, false, 0);
						//++entityarrow.y;
						double d2 = entity.y + (double) entity.getHeadHeight() - 0.20000000298023224 - entityarrow.y;
						float f1 = MathHelper.sqrt(d * d + d1 * d1) * 0.2F;
						this.world.playSoundAtEntity((Entity) null, this, "random.bow", 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
						entityarrow.setHeading(d, d2 + (double) f1, d1, 0.8F, 1.0F);
						this.world.entityJoinedWorld(entityarrow);
					}

					this.attackTime = 40;
				}

				this.yRot = (float) (Math.atan2(d1, d) * 180.0 / 3.1415927410125732) - 90.0F;
				this.hasAttacked = true;
			}
		} else {
			if (distance < 10.0F) {
				double d = entity.x - this.x;
				double d1 = entity.z - this.z;
				if (this.attackTime == 0) {
					if (!this.world.isClientSide) {
						ProjectileArrow entityarrow = new ProjectileArrow(this.world, this, false, 0);
						//++entityarrow.y;
						double d2 = entity.y + (double)entity.getHeadHeight() - 0.20000000298023224 - entityarrow.y;
						float f1 = MathHelper.sqrt(d * d + d1 * d1) * 0.2F;
						this.world.playSoundAtEntity((Entity)null, this, "random.bow", 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
						entityarrow.setHeading(d, d2 + (double)f1, d1, 0.6F, 12.0F);
						this.world.entityJoinedWorld(entityarrow);
					}

					this.attackTime = 30;
				}

				this.yRot = (float)(Math.atan2(d1, d) * 180.0 / 3.1415927410125732) - 90.0F;
				this.hasAttacked = true;
			}
		}
	}
}

