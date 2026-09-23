package mizurin.shieldmod.entities;

import mizurin.shieldmod.effects.ShieldEffects;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

public class EntityWeb extends Projectile {
	public EntityWeb(World world, Mob owner) {
		super(world, owner);
		this.modelItem = Items.AMMO_SNOWBALL;
	}

	public EntityWeb(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		this.modelItem = Items.AMMO_SNOWBALL;
	}

	public EntityWeb(World world) {
		super(world);
		this.modelItem = Items.AMMO_SNOWBALL;
	}

	public void initProjectile() {
		this.defaultProjectileSpeed = 0.95F;
		this.defaultGravity = 0.03F;

	}
	@Override
	public void onHit(@NotNull HitResult hitResult) {
		if (hitResult instanceof HitResult.Entity hitResult1) {
			ShieldEffects.add((Mob) hitResult1.entity, ShieldEffects.webEffect, 1, 100);

			//Applies my custom status effect from the IFreeze interface.
		}
		if (this.modelItem != null) {
			for(int j = 0; j < 8; ++j) {
				this.world.spawnParticle("snowshovel", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0, 5, false);
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
