package mizurin.shieldmod.entities;

import mizurin.shieldmod.interfaces.IDazed;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityProjectile;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;

public class EntityWeb extends EntityProjectile {
	public EntityWeb(World world, EntityLiving entityliving) {
		super(world, entityliving);
		this.modelItem = Item.ammoSnowball;
	}

	public EntityWeb(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		this.modelItem = Item.ammoSnowball;
	}

	public EntityWeb(World world) {
		super(world);
		this.modelItem = Item.ammoSnowball;
	}

	public void init() {
		super.init();

	}
	@Override
	public void onHit(HitResult hitResult) {
		if (hitResult.entity != null) {
			((IDazed) hitResult.entity).shieldmod$freezeHurt(60);

			//Applies my custom status effect from the IFreeze interface.
		}
		if (this.modelItem != null) {
			for(int j = 0; j < 8; ++j) {
				this.world.spawnParticle("snowshovel", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
				//This does a loop to spawn particles on impact.
			}
		}
		super.onHit(hitResult);
	}

//	@Override
//	public HitResult getHitResult() {
//		Vec3d currentPos = Vec3d.createVector(this.x, this.y, this.z);
//		Vec3d nextPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd - 0.25, this.z + this.zd);
//		HitResult hit = this.world.checkBlockCollisionBetweenPoints(currentPos, nextPos, false, true);
//		//I redid the getHitResult so that it can pass through non-solid blocks. (flag1: true).
//		return hit;
//	}
}
