package mizurin.shieldmod.entities;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

public class EntityFire extends Projectile {

	public EntityFire(World world) {
		super(world);
		this.modelItem = Items.AMMO_FIREBALL;
	}

	public EntityFire(World world, Mob owner){
		super(world, owner);
		this.modelItem = Items.AMMO_FIREBALL;
	}
	public EntityFire(World world, double x, double y, double z){
		super(world, x, y, z);
		this.modelItem = Items.AMMO_FIREBALL;
	}

	public EntityFire(World world, double x, double y, double z, double xd, double yd, double zd){
		super(world, x, y, z);
		this.modelItem = Blocks.FIRE.asItem();
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
	}

	public void initProjectile() {
		this.damage = 6;
		this.defaultGravity = 0.003F;
		this.defaultProjectileSpeed = 0.115F;
	}

	public void onHit(@NotNull HitResult hitResult) {
		if (hitResult instanceof HitResult.Entity hitEntity && hitEntity.entity != this.owner){
			hitEntity.entity.hurt(this.owner, this.damage, DamageType.FIRE);
			hitEntity.entity.xd *= .33;
			hitEntity.entity.yd = 0.0;
			hitEntity.entity.zd *= .33;

			hitEntity.entity.remainingFireTicks = 100;
			if (hitEntity.entity instanceof Player){
				remove();
			}
		}

	}

	@Override
	protected void checkOnWater(boolean addVelocity) {
		if (this.checkAndHandleWater(addVelocity)) {
			this.remove();
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (!world.isClientSide) {
			this.xd *= 7.5;
			this.yd *= 7.5;
			this.zd *= 7.5;

			if(this.tickCount > 8){
				this.remove();
			}
		}

		if(this.tickCount > 1) {
			world.spawnParticle("flame", this.x, this.y, this.z, this.random.nextFloat()*.1, this.random.nextFloat()*.1, this.random.nextFloat()*.1, 0, 5, false);
		}
	}
	@Override
	public HitResult getHitResult() {
		Vector3d currentPos = new Vector3d(this.x, this.y, this.z);
		Vector3d nextPos = new Vector3d(this.x + this.xd, this.y + this.yd - 0.25, this.z + this.zd);
		return this.world.checkBlockCollisionBetweenPoints(currentPos, nextPos, false, true, false);
	}
}
