package mizurin.shieldmod.entities;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.ParticleHelper;

import java.util.List;

public class EntityHoming extends Projectile {
	private Mob target;
	private static final float homingPower = 0.15F;
	private static final float topSpeed = 0.5F;

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
		this.damage = 6;
		this.defaultGravity = 0.0F;
		this.defaultProjectileSpeed = 1.0F;
		this.setSize(1.0F, 1.0F);
		this.modelItem = Items.AMMO_SNOWBALL;
	}
	@Override
	public void tick() {
		++this.ticksInAir;
		if (ticksInAir > 100) {
			remove();
			world.spawnParticle("explode", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
			world.spawnParticle("smoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
			world.spawnParticle("largesmoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
			world.playSoundAtEntity(null, this, "mob.ghast.fireball", 1.0F, (random.nextFloat() * 1.4F + 1.8F));
		}

		if (this.target == null || !this.target.isAlive()) {
			AABB searchBox = AABB.getPermanentBB(this.x - 2.0, this.y - 2.0, this.z - 2.0, this.x + 2.0, this.y + 2.0, this.z + 2.0);
			List<Mob> entities = this.world.getEntitiesWithinAABB(Mob.class, searchBox);
			Mob closestMob = null;
			for (Mob entity : entities) {
				if (entity instanceof Mob && entity.isAlive() &! (entity instanceof Player)) {
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
					hitResult.entity.hurt(this.owner, this.damage, DamageType.GENERIC);
					this.remove();
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
