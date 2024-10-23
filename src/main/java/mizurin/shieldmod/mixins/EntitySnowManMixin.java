package mizurin.shieldmod.mixins;

import com.mojang.nbt.CompoundTag;
import mizurin.shieldmod.interfaces.IShieldZombie;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.*;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;


@Mixin(value = EntitySnowman.class, remap = false)
public abstract class EntitySnowManMixin extends EntityMonster implements IShieldZombie {
	public EntitySnowManMixin(World world) {
		super(world);
	}

	@Override
	protected void init() {
		super.init();
		entityData.define(22, (byte)0);
	}

	//Right click to put a carved pumpkin on the snowman's head
	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (super.interact(entityplayer)) {
			return true;
		} else {
				ItemStack itemstack = entityplayer.inventory.getCurrentItem();
				if (itemstack != null && itemstack.getItem() == Block.pumpkinCarvedIdle.asItem() && !shieldmod$isSnowJack()) {
					setTarget(null);
					//Snowman stops targeting player.
					entityData.set(22, (byte) 1);
					itemstack.consumeItem(entityplayer);

					return true;
				} else {
					return false;
				}
			}
		}

	@Override
	protected Entity findPlayerToAttack() {
		if (shieldmod$isSnowJack()) {
			//creates a bounding box and grabs a list of monsters to attack.
			//doing just monsters causes the snowman to attack itself, I also want to exclude creepers from being attacked.
			List<Entity> nearbyMon = this.world.getEntitiesWithinAABB(EntityZombie.class, AABB.getBoundingBoxFromPool(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0));
			nearbyMon.addAll(this.world.getEntitiesWithinAABB(EntitySkeleton.class, AABB.getBoundingBoxFromPool(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0)));
			nearbyMon.addAll(this.world.getEntitiesWithinAABB(EntitySpider.class, AABB.getBoundingBoxFromPool(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0)));
			nearbyMon.addAll(this.world.getEntitiesWithinAABB(EntitySlime.class, AABB.getBoundingBoxFromPool(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0)));
			if (!nearbyMon.isEmpty()) {
				super.setTarget((Entity) nearbyMon.get(this.world.rand.nextInt(nearbyMon.size())));
			}
		} else {
			//else statement for regular snowmen without carved pumpkins.
			EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
			return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
		}
		return entityToAttack;
	}


	@Override
	public boolean shieldmod$isShieldZombie() {
		return false;
	}

	@Override
	public boolean shieldmod$isSnowJack() {
		return entityData.getByte(22) == 1;
	}


	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putByte("shieldmod$isSnowJack", shieldmod$isSnowJack() ? (byte) 1 : (byte)0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		entityData.set(22, tag.getByte("shieldmod$isSnowJack"));
	}
	@Override public boolean canDespawn() {
		if (shieldmod$isSnowJack()) {
			return false;
		} else {
			return true;
		}
	}
}
