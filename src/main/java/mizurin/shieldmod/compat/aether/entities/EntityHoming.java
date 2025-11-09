package mizurin.shieldmod.compat.aether.entities;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.Items;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.helper.ParticleMaker;

import java.util.List;

public class EntityHoming extends Projectile {
	public String[] particles = {"explode", "lightning", "lightning"};
	private Mob target;
	private static final float homingPower = 0.15F;
	private static final float topSpeed = 0.4F;

	public EntityHoming(World world) {
		super(world);
		this.modelItem = Items.AMMO_SNOWBALL;
	}
	public EntityHoming(World world, Mob owner){
		super(world, owner);
		this.modelItem = Items.AMMO_SNOWBALL;
	}

	@Override
	public void initProjectile() {
		this.damage = 5;
		this.defaultGravity = 0.0F;
		this.defaultProjectileSpeed = 1.0F;
		this.setSize(1.0F, 1.0F);
		this.modelItem = Items.AMMO_SNOWBALL;
	}
	public void doExplosion() {
		if (target != null) {
			for (int particle = 0; particle < 16; particle++) {
				double XParticle = target.x + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
				double YParticle = target.y + 0.5F + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);
				double ZParticle = target.z + ((double) world.rand.nextFloat()) - ((double) world.rand.nextFloat() * 0.375F);

				ParticleMaker.spawnParticle(world, particles[world.rand.nextInt(particles.length)], XParticle, YParticle, ZParticle, 0, 0, 0, 0);
			}

			world.playSoundEffect(target, SoundCategory.ENTITY_SOUNDS, target.x, target.y - 1, target.z, "aether:zap", 0.5F, (1.3F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
		}
	}
	@Override
	public void tick() {
		for (int j = 0; j < 2; j++) {
			ParticleMaker.spawnParticle(world, "lightning", this.x, this.y + 0.5, this.z, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), world.rand.nextFloat() * 0.25F * -1, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0);
		}
		++this.ticksInAir;
		if (ticksInAir > 150) {
			remove();
			world.spawnParticle("explode", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
			world.spawnParticle("smoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
			world.spawnParticle("largesmoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
			world.playSoundAtEntity(null, this, "mob.ghast.fireball", 1.0F, (random.nextFloat() * 1.4F + 1.8F));
		}

		if (this.target == null || !this.target.isAlive()) {
			AABB searchBox = AABB.getPermanentBB(this.x - 2.5, this.y - 2.5, this.z - 2.5, this.x + 2.5, this.y + 2.5, this.z + 2.5);
			List<Mob> entities = this.world.getEntitiesWithinAABB(Mob.class, searchBox);
			entities.remove(this.owner);
			//I do not like this code
			entities.removeAll(this.world.getEntitiesWithinAABB(MobAerbunny.class, searchBox));
			Mob closestMob = null;
			for (Mob entity : entities) {
				if (entity instanceof Mob && entity.isAlive()) {
					double distance = this.distanceTo(entity);
					if (distance < 32.0f) {
						closestMob = entity;
					}
				}
			}
			this.target = closestMob;
		}

		if (this.target != null && this.target.isAlive()) {
			double dx = this.target.x - this.x;
			double dy = this.target.y + this.target.getHeadHeight() - this.y;
			double dz = this.target.z - this.z;
			double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
			if (dist > 0) {
				double targetXd = dx / dist * topSpeed;
				double targetYd = dy / dist * topSpeed;
				double targetZd = dz / dist * topSpeed;
				this.xd += (targetXd - this.xd) * homingPower;
				this.yd += (targetYd - this.yd) * homingPower;
				this.zd += (targetZd - this.zd) * homingPower;
				double speed = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
				if (speed > topSpeed) {
					this.xd = this.xd / speed * topSpeed;
					this.yd = this.yd / speed * topSpeed;
					this.zd = this.zd / speed * topSpeed;
				}
			}
		}

		super.tick();
	}

	@Override
	public void onHit(HitResult hitResult) {
		if (!this.world.isClientSide) {
			if (hitResult.entity != null) {
				if (!(hitResult.entity instanceof Projectile)) {
					hitResult.entity.fling(xd * 1.5, yd * 0, zd * 1.5, 0.5F);
				}
			}
				if (hitResult.entity instanceof Mob) {
					hitResult.entity.hurt(this.owner, this.damage, AetherMod.LIGHTNING);
					this.remove();
					doExplosion();
					return;
				}
		}
		super.onHit(hitResult);
	}
	@Override
	public HitResult getHitResult() {
		Vec3 currentPos = Vec3.getTempVec3(this.x, this.y, this.z);
		Vec3 nextPos = Vec3.getTempVec3(this.x + this.xd, this.y + this.yd - 0.25, this.z + this.zd);
		HitResult hit = this.world.checkBlockCollisionBetweenPoints(currentPos, nextPos, false, true, false);
		return hit;
	}
}
