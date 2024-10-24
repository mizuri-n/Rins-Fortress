package mizurin.shieldmod.entities;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityProjectile;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

public class EntityFire extends EntityProjectile {

	public EntityFire(World world) {
		super(world);
		this.modelItem = Item.ammoFireball;
	}

	public EntityFire(World world, EntityLiving entityliving){
		super(world, entityliving);
		this.modelItem = Item.ammoFireball;
	}
	public EntityFire(World world, double x, double y, double z){
		super(world, x, y, z);
		this.modelItem = Item.ammoFireball;
	}

	public EntityFire(World world, double x, double y, double z, double xd, double yd, double zd){
		super(world, x, y, z);
		this.modelItem = Item.ammoFireball;
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
	}

	public void init() {
		this.damage = 6;
		this.defaultGravity = 0.003F;
		this.defaultProjectileSpeed = 0.15F;
	}

	public void onHit(HitResult hitResult) {
		if (hitResult.hitType == HitResult.HitType.ENTITY && hitResult.entity != this.owner) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.FIRE);

			hitResult.entity.xd *= .33;
			hitResult.entity.yd = 0.0;
			hitResult.entity.zd *= .33;

			hitResult.entity.remainingFireTicks = 300;
			if (hitResult.entity instanceof EntityPlayer){
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
		Vec3d currentPos = Vec3d.createVector(this.x, this.y, this.z);
		Vec3d nextPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd - 0.25, this.z + this.zd);
		HitResult hit = this.world.checkBlockCollisionBetweenPoints(currentPos, nextPos, false, true);
		return hit;
	}
}
