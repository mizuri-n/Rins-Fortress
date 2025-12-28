package mizurin.shieldmod.mixins.entity;

import com.mojang.nbt.tags.CompoundTag;
import mizurin.shieldmod.effects.ShieldEffects;
import mizurin.shieldmod.entities.EntityIceBall;
import mizurin.shieldmod.interfaces.IShieldZombie;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.monster.MobSnowman;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.ProjectileSnowball;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

import static mizurin.shieldmod.ShieldMod.expertMode;


@Mixin(value = MobSnowman.class, remap = false)
public abstract class EntitySnowManMixin extends MobMonster implements IShieldZombie {
	public EntitySnowManMixin(World world) {
		super(world);
	}

	@Override
	public void dropDeathItems() {
		if (this.random.nextInt(900) == 0 && expertMode) {
			this.dropItem(Items.BUCKET_ICECREAM.id, 1);
		}
		if(expertMode && random.nextInt(10) == 0){
			this.dropItem(Blocks.ICE.id(), 1);
		}

		super.dropDeathItems();
	}

	public void defineSynchedData() {
		super.spawnInit();
		entityData.define(22, (byte)0, Byte.class);
	}

	//Right click to put a carved pumpkin on the snowman's head
	@Override
	public boolean interact(Player entityplayer) {
		if (super.interact(entityplayer)) {
			return true;
		} else {
				ItemStack itemstack = entityplayer.inventory.getCurrentItem();
				if (itemstack != null && itemstack.getItem() == Blocks.PUMPKIN_CARVED_IDLE.asItem() && !shieldmod$isSnowJack()) {
					setTarget(null);
					//Snowman stops targeting player.
					entityData.set(22, (byte) 1);
					itemstack.consumeItem(entityplayer);

					return true;
				} else {
					return false;
				}
			}
		}

	@Override
	protected Entity findPlayerToAttack() {
		if (shieldmod$isSnowJack()) {
			return null;
		} else {
			//else statement for regular snowmen without carved pumpkins.
			Player entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
			return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
		}
	}

	@Override
	protected void updateAI(){
		if (this.closestFireflyEntity == null) {
			this.closestFireflyEntity = this.getClosestFireflyToEntity((int)this.x, (int)this.y, (int)this.z, 8.0F);
		}

		if (this.closestFireflyEntity != null) {
			double dX = this.x - this.closestFireflyEntity.x;
			double dZ = this.z - this.closestFireflyEntity.z;
			double hypotenuse = Math.sqrt(dX * dX + dZ * dZ);
			double scaleFactor = hypotenuse / 8.0;
			dX *= scaleFactor;
			dZ *= scaleFactor;
			Vec3 distanceXYZ = Vec3.getTempVec3(this.x + dX, this.y, this.z + dZ);
			this.pathToEntity = this.world.getEntityPathToXYZ(this, MathHelper.floor(distanceXYZ.x), MathHelper.floor(this.y), MathHelper.floor(distanceXYZ.z), 16.0F);
		}
		if (shieldmod$isSnowJack()) {
			//creates a bounding box and grabs a list of monsters to attack.
			//doing just monsters causes the snowman to attack itself, I also want to exclude creepers from being attacked.

			List<MobMonster> nearbyMon = this.world.getEntitiesWithinAABB(MobMonster.class, AABB.getTemporaryBB(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).grow(16.0, 4.0, 16.0));
			nearbyMon.removeAll(this.world.getEntitiesWithinAABB(MobSnowman.class, AABB.getTemporaryBB(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).grow(16.0, 4.0, 16.0)));
			nearbyMon.removeAll(this.world.getEntitiesWithinAABB(MobCreeper.class, AABB.getTemporaryBB(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).grow(16.0, 4.0, 16.0)));
			if (!nearbyMon.isEmpty()) {
				this.setTarget((Entity)nearbyMon.get(this.world.rand.nextInt(nearbyMon.size())));
			}

		}

		super.updateAI();
	}

	/**
	 * @author Rin
	 * @reason SnowBalls
	 */
	@Overwrite
	public void attackEntity(Entity entity, float distance) {
		if(expertMode) {
			if (distance < 10.0F && distance > 4.0F) {
				double dX = entity.x - this.x;
				double dZ = entity.z - this.z;
				if (this.attackTime == 0) {
					if (!this.world.isClientSide) {
						EntityIceBall snowball = new EntityIceBall(this.world, this);
						snowball.damage = 1;
						if (this.world.getBlockId((int) this.x, (int) this.y - 1, (int) this.z) == Blocks.GRAVEL.id()) {
							snowball.damage = 2;
						}

						double d2 = entity.y + (double) entity.getHeadHeight() - 0.2 - snowball.y;
						float f1 = MathHelper.sqrt(dX * dX + dZ * dZ) * 0.2F;
						this.world.playSoundAtEntity((Entity) null, this, "random.bow", 0.5F, 0.4F / (this.random.nextFloat() * 0.4F + 0.8F));
						this.world.entityJoinedWorld(snowball);
						snowball.setHeadingPrecise(dX, d2 + (double) f1, dZ, 0.8F);
					}

					this.attackTime = 30;
				}

				this.yRot = (float) (Math.atan2(dZ, dX) * 180.0 / Math.PI) - 90.0F;
				this.hasAttacked = true;
			} else if (distance <= 4.0F && attackTime <= 0) {
				this.attackTime = 20;
				entity.hurt(this, this.attackStrength, DamageType.COMBAT);
				ShieldEffects.add((Mob) target, ShieldEffects.weaknessEffect, 1, 100);
			}
		} else {
			if (distance < 8.0F && distance > 4.0F) {
				double dX = entity.x - this.x;
				double dZ = entity.z - this.z;
				if (this.attackTime == 0) {
					if (!this.world.isClientSide) {
						ProjectileSnowball snowball = new ProjectileSnowball(this.world, this);
						if (this.world.getBlockId((int)this.x, (int)this.y - 1, (int)this.z) == Blocks.GRAVEL.id()) {
							snowball.damage = 1;
						}

						++snowball.y;
						double d2 = entity.y + (double)entity.getHeadHeight() - 0.2 - snowball.y;
						float f1 = MathHelper.sqrt(dX * dX + dZ * dZ) * 0.2F;
						this.world.playSoundAtEntity((Entity)null, this, "random.bow", 0.5F, 0.4F / (this.random.nextFloat() * 0.4F + 0.8F));
						this.world.entityJoinedWorld(snowball);
						snowball.setHeadingPrecise(dX, d2 + (double)f1, dZ, 0.6F);
					}

					this.attackTime = 30;
				}

				this.yRot = (float)(Math.atan2(dZ, dX) * 180.0 / Math.PI) - 90.0F;
				this.hasAttacked = true;
			} else if (distance <= 4.0F) {
				super.attackEntity(entity, distance);
			}
		}
	}

	@Override
	public boolean shieldmod$isShieldZombie() {
		return false;
	}

	@Override
	public boolean shieldmod$isSnowJack() {
		return entityData.getByte(22) == 1;
	}


	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putByte("shieldmod$isSnowJack", shieldmod$isSnowJack() ? (byte) 1 : (byte)0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		entityData.set(22, tag.getByte("shieldmod$isSnowJack"));
	}
	@Override public boolean canDespawn() {
		if (shieldmod$isSnowJack()) {
			return false;
		} else {
			return true;
		}
	}
}
