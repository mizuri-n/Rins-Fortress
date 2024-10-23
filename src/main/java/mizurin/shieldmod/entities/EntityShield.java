package mizurin.shieldmod.entities;

import mizurin.shieldmod.interfaces.IThrownItem;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityPebble;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

import java.util.List;

public class EntityShield extends EntityPebble {
	private int bounce = 4; //Amount of bounces allowed. I set a limit as a failsafe as the entity can get stuck inside blocks and or fences.
	public EntityShield(World world) {
		super(world);
		this.modelItem = Shields.ammotearShield;
		this.setSize(0.9F, 0.9F);
	}

	public EntityShield(World world, EntityLiving entityliving) {
		super(world, entityliving);
		this.modelItem = Shields.ammotearShield;
	}

	public EntityShield(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		this.modelItem = Shields.ammotearShield;
	}

	public void init() {
		super.init();
		this.damage = 0;
		this.defaultGravity = 0.03F;
		this.defaultProjectileSpeed = 0.99F;
	}
	//storeOrDropItem is a failsafe if the player's inventory is full. if not directly put the shield into the inventory.
	public void storeOrDropItem(EntityPlayer player, ItemStack stack){
		if(stack == null || stack.stackSize <= 0){
			return;
		}
		InventoryPlayer inventory = player.inventory;
		inventory.insertItem(stack, false);
		if (stack.stackSize > 0){
			player.dropPlayerItem(stack);
		}
	}
		public void tick () {
		this.baseTick();
			++this.ticksInAir; //Used for damage calc
		float velocity;
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			velocity = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
			this.yRotO = this.yRot = (float) (Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);
			this.xRotO = this.xRot = (float) (Math.atan2(this.yd, (double) velocity) * 180.0 / Math.PI);
		}

		velocity = MathHelper.sqrt_double(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
		Vec3d currentPos = Vec3d.createVector(this.x, this.y, this.z);
		Vec3d nextPos = Vec3d.createVector(this.x + this.xd, this.y + this.yd - 0.25, this.z + this.zd);
		HitResult hit = this.world.checkBlockCollisionBetweenPoints(currentPos, nextPos, false, true);
		float otherAxisScale;
		float deceleration;
		//This section is used to invert the velocity of the entity after it hits a tile. inverting it basically turns it around.
		if (hit != null && hit.hitType == HitResult.HitType.TILE) {
			float bounceAxisScale = 0.4F;
			otherAxisScale = 0.6F;
			deceleration = 0.2F;
			Side side = hit.side;
			if (side == Side.TOP && (Math.abs(this.yd) < 0.25 || velocity < deceleration)) {
				this.yd = 0.0;
				this.y = (double) ((float) hit.y + 1.0F + 0.25F);
				this.xd = 0.0;
				this.zd = 0.0;
				return;
			}

			if (side.getAxis() == Axis.Y) {
				this.yd = -this.yd * (double) bounceAxisScale;
				this.xd *= (double) otherAxisScale;
				this.zd *= (double) otherAxisScale;
			}

			if (side.getAxis() == Axis.X) {
				this.xd = -this.xd * (double) bounceAxisScale;
				this.yd *= (double) otherAxisScale;
				this.zd *= (double) otherAxisScale;
			}

			if (side.getAxis() == Axis.Z) {
				this.zd = -this.zd * (double) bounceAxisScale;
				this.xd *= (double) otherAxisScale;
				this.yd *= (double) otherAxisScale;
			}
			--this.bounce;
		}


		List<Entity> collidingEntities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.getOffsetBoundingBox(this.xd, this.yd, this.zd).expand(0.5, 0.5, 0.5));
		if ((collidingEntities == null || collidingEntities.size() <= 0) && this.bounce > 0 || collidingEntities != null && collidingEntities.size() == 1 && collidingEntities.get(0) == this.owner && this.tickCount < 4) {
			//This grabs a list of entities inside the bounding box
			this.x += this.xd;
			this.y += this.yd;
			this.z += this.zd;
			otherAxisScale = MathHelper.sqrt_double(this.xd * this.xd + this.zd * this.zd);
			this.yRot = (float) (Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);

			for (this.xRot = (float) (Math.atan2(this.yd, (double) otherAxisScale) * 180.0 / Math.PI); this.xRot - this.xRotO < -180.0F; this.xRotO -= 360.0F) {
			}

			while (this.xRot - this.xRotO >= 180.0F) {
				this.xRotO += 360.0F;
			}

			while (this.yRot - this.yRotO < -180.0F) {
				this.yRotO -= 360.0F;
			}

			while (this.yRot - this.yRotO >= 180.0F) {
				this.yRotO += 360.0F;
			}

			this.xRot = this.xRotO + (this.xRot - this.xRotO) * 0.2F;
			this.yRot = this.yRotO + (this.yRot - this.yRotO) * 0.2F;
			deceleration = 0.99F;
			float gravity = 0.06F;
			if (this.isInWater()) {
				for (int i = 0; i < 4; ++i) {
					double particleDistance = 0.25;
					this.world.spawnParticle("bubble", this.x - this.xd * particleDistance, this.y - this.yd * particleDistance, this.z - this.zd * particleDistance, this.xd, this.yd, this.zd, 0);
				}

				deceleration = 0.8F;
			}

			this.xd *= (double) deceleration;
			this.yd *= (double) deceleration;
			this.zd *= (double) deceleration;
			this.yd -= (double) gravity;
			this.setPos(this.x, this.y, this.z);
			if (velocity == 0) {
				this.remove();
				//failsafe to prevent the entity from not removing itself
				if (this.owner != null) {
					storeOrDropItem((EntityPlayer) owner, ((IThrownItem) owner).getThrownItem());
					//returns the item to the player who threw it.
				}
			}
		} else {
			if (this.modelItem != null) {
				for (int j = 0; j < 8; ++j) {
					this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, Item.ammoSnowball.id);
					this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, this.modelItem.id);
					//spawns particles on impact.
				}
			}
			damage = ticksInAir /3 + 3;
			if (damage > 14){
				damage = 14;
			}
			if(collidingEntities != null && collidingEntities.size() == 1) {
				if (collidingEntities.get(0) != null) {
					collidingEntities.get(0).hurt((EntityPlayer)owner, this.damage, DamageType.COMBAT);
				}
				//this is used to hurt the entities hit directly with the entity through the bounding box.
			}
			this.remove();
			if (this.owner != null) {
				storeOrDropItem((EntityPlayer) owner, ((IThrownItem) owner).getThrownItem());
				//returns item to the player who threw it.
			}
		}
	}

	//this is all leftover code for the original throwing mechanic. will be added to a config in the future.
	public void onHit(HitResult hitResult) {
		damage = ticksInAir /3 + 3;
		if (damage > 14){
			damage = 14;
		}
		if (hitResult.entity != null) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
		}
				if (!this.world.isClientSide) {
					this.world.playSoundAtEntity((Entity) null, this, "mob.ghast.fireball", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				}
			if (this.modelItem != null) {
				for(int j = 0; j < 8; ++j) {
					this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, Item.ammoSnowball.id);
					this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, this.modelItem.id);
				}
			}
		this.remove();
			if (this.owner != null) {
				storeOrDropItem((EntityPlayer) owner, ((IThrownItem) owner).getThrownItem());
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
