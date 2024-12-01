package mizurin.shieldmod.entities;

import mizurin.shieldmod.interfaces.IDazed;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityProjectile;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

//Entity Poison Bottle
public class EntityPB extends EntityProjectile {
	public EntityPB(World world, EntityLiving entityliving) {
		super(world, entityliving);
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
	public void init() {
		super.init();
		this.damage = 1;
		this.defaultGravity = 0.098F;
		this.defaultProjectileSpeed = 0.95F;
	}

	@Override
	public void onHit(HitResult hitResult) {
		if (hitResult.entity instanceof EntityLiving) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
			((IDazed) hitResult.entity).shieldmod$dazedHurt(450);
			//Applies my custom status effect from the IDazed interface.
		}
		if (this.modelItem != null) {
			for(int j = 0; j < 8; ++j) {
				this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, Item.dye.id);
				//This does a loop to spawn particles on impact.
			}
		}
		super.onHit(hitResult);
	}

	@Override
	public HitResult getHitResult() {
		Vec3d currentPos = Vec3d.createVector(this.x, this.y, this.z);
		Vec3d nextPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd - 0.25, this.z + this.zd);
		HitResult hit = this.world.checkBlockCollisionBetweenPoints(currentPos, nextPos, false, true);
		//I redid the getHitResult so that it can pass through non-solid blocks. (flag1: true).
		return hit;
	}
}
