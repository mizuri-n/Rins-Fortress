package mizurin.shieldmod.entities;

import mizurin.shieldmod.interfaces.IDazed;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobSheep;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

import java.util.List;

//Entity Poison Bottle
public class EntityPB extends Projectile {
	public EntityPB(World world, Mob owner) {
		super(world, owner);
		this.modelItem = Shields.poisonBottle;
	}
	public EntityPB(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		this.modelItem = Shields.poisonBottle;
	}
	public EntityPB(World world) {
		super(world);
		this.modelItem = Shields.poisonBottle;
	}
	public void initProjectile() {
		this.damage = 1;
		this.defaultGravity = 0.098F;
		this.defaultProjectileSpeed = 0.95F;
	}

	@Override
	public void onHit(HitResult hitResult) {
		if (hitResult.entity instanceof Mob) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
			((IDazed) hitResult.entity).shieldmod$poisonHurt(200);
			//Applies my custom status effect from the IDazed interface.
		}
		if (this.modelItem != null) {
			for(int j = 0; j < 8; ++j) {
				this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, this.modelItem.id);
				//This does a loop to spawn particles on impact.
			}
		}
		super.onHit(hitResult);
	}

	@Override
	public HitResult getHitResult() {
		Vec3 oldPosition = Vec3.getTempVec3(this.x, this.y, this.z);
		Vec3 newPosition = Vec3.getTempVec3(this.x + this.xd, this.y + this.yd - 0.25, this.z + this.zd);
		assert this.world != null;
		HitResult hit = this.world.checkBlockCollisionBetweenPoints(oldPosition, newPosition, false, true, false);
		//I redid the getHitResult so that it can pass through non-solid blocks. (flag1: true).
		return hit;
	}
}
