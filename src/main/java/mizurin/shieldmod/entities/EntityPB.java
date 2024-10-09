package mizurin.shieldmod.entities;

import mizurin.shieldmod.item.IDazed;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityProjectile;
import net.minecraft.core.entity.projectile.EntitySnowball;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

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
		if (hitResult.entity != null) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
			((IDazed) hitResult.entity).better_with_defense$dazedHurt();
		}
		if (this.modelItem != null) {
			for(int j = 0; j < 8; ++j) {
				this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, Item.dye.id);
			}
		}
		super.onHit(hitResult);
	}

	@Override
	public HitResult getHitResult() {
		Vec3d currentPos = Vec3d.createVector(this.x, this.y, this.z);
		Vec3d nextPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd - 0.25, this.z + this.zd);
		HitResult hit = this.world.checkBlockCollisionBetweenPoints(currentPos, nextPos, false, true);
		return hit;
	}
}
