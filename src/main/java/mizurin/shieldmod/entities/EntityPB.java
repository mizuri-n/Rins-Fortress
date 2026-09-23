package mizurin.shieldmod.entities;

import mizurin.shieldmod.effects.ShieldEffects;
import mizurin.shieldmod.item.RFItems;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.joml.Vector3d;
import org.joml.primitives.AABBd;

import java.util.List;

//Entity Poison Bottle
public class EntityPB extends Projectile {
	public EntityPB(World world, Mob owner) {
		super(world, owner);
		this.modelItem = RFItems.poisonBottle;
	}
	public EntityPB(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		this.modelItem = RFItems.poisonBottle;
	}
	public EntityPB(World world) {
		super(world);
		this.modelItem = RFItems.poisonBottle;
	}
	public void initProjectile() {
		this.damage = 1;
		this.defaultGravity = 0.098F;
		this.defaultProjectileSpeed = 0.85F;
	}

	@Override
	public void onHit(HitResult hitResult) {
		if (hitResult instanceof HitResult.Entity hitentity) {
			hitentity.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
			ShieldEffects.add((Mob) hitentity.entity, ShieldEffects.poisonEffect, 1, 200);
		}
		//if(hitResult.hitType == HitResult.HitType.TILE || hitResult.hitType == HitResult.HitType.ENTITY){
			if(hitResult instanceof HitResult.Tile || hitResult instanceof HitResult.Entity){
			List<Mob> nearbyMon = this.world.getEntitiesWithinAABB(Mob.class, this.bb.translate(this.xd, this.yd, this.zd, new AABBd())); //.grow(2.0, 1.5, 2.0)
			for(Mob mon : nearbyMon){
				ShieldEffects.add((Entity) mon, ShieldEffects.poisonEffect,1, 200);
			}
		}
		if (this.modelItem != null) {
			double dx = world.rand.nextGaussian() * 0.002;
			double dy = world.rand.nextGaussian() * 0.002;
			double dz = world.rand.nextGaussian() * 0.002;
			float width = 2.0f;
			for(int j = 0; j < 8; ++j) {
				this.world.spawnParticle("smoke", this.x + (double) (world.rand.nextFloat() * width * 2.0F) - (double) width, this.y + (double) (world.rand.nextFloat() - 1), this.z + (double) (world.rand.nextFloat() * width * 2.0F) - (double) width, dx, dy, dz, this.modelItem.id, 5, false);
				//This does a loop to spawn particles on impact.
			}
		}
		super.onHit(hitResult);
	}

	@Override
	public HitResult getHitResult() {
		Vector3d currentPos = new Vector3d(this.x, this.y, this.z);
		Vector3d nextPos = new Vector3d(this.x + this.xd, this.y + this.yd - 0.25, this.z + this.zd);
		return this.world.checkBlockCollisionBetweenPoints(currentPos, nextPos, false, true, false);
	}
}
