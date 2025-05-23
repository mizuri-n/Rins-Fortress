package mizurin.shieldmod.entities;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;

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
		this.damage = 7;
		this.defaultGravity = 0.003F;
		this.defaultProjectileSpeed = 0.115F;
	}

	public void onHit(HitResult hitResult) {
		if (hitResult.hitType == HitResult.HitType.ENTITY && hitResult.entity != this.owner) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.FIRE);

			hitResult.entity.xd *= .33;
			hitResult.entity.yd = 0.0;
			hitResult.entity.zd *= .33;

			hitResult.entity.remainingFireTicks = 150;
			if (hitResult.entity instanceof Player){
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
			world.spawnParticle("flame", this.x, this.y, this.z, this.random.nextFloat()*.1, this.random.nextFloat()*.1, this.random.nextFloat()*.1, 0);
		}
	}
	@Override
	public HitResult getHitResult() {
		Vec3 currentPos = Vec3.getTempVec3(this.x, this.y, this.z);
		Vec3 nextPos = Vec3.getTempVec3(this.x + this.xd, this.y + this.yd - 0.25, this.z + this.zd);
		assert this.world != null;
		HitResult hit = this.world.checkBlockCollisionBetweenPoints(currentPos, nextPos, false, true, false);
		return hit;
	}
}
