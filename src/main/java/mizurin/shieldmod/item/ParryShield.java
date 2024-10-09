package mizurin.shieldmod.item;

import mizurin.shieldmod.entities.EntityPB;
import mizurin.shieldmod.entities.EntityShield;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.projectile.*;
import net.minecraft.core.util.helper.DamageType;

import java.util.List;

public class ParryShield extends ThrowShield {
	public int parryDelay = 0;
	public EntityPlayer player;

	public ParryShield(String name, int id, ToolMaterial toolMaterial) {
		super(name, id, toolMaterial);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
		itemstack.getData().putBoolean("active", true);
		itemstack.getData().putInt("ticks", ticksToAdd);
		onBlock(itemstack, world, entityPlayer);

		return itemstack;
	}

	@Override
	public void onBlock(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.isSneaking()){
			if (this.parryDelay == 0) {
				this.parryDelay = 20;
			}

			this.player = entityplayer;

		}
	}

	public void parryHitbox(World world, EntityPlayer player) {
		double bound = 1.75;
		AABB aabb1 = new AABB(
			player.x - bound,
			player.y + player.getHeadHeight() - bound,
			player.z - bound,
			player.x + bound,
			player.y + player.getHeadHeight() + bound,
			player.z + bound
		);
	List<Entity> projectileList = player.world.getEntitiesWithinAABB(EntityProjectile.class, aabb1 );
		for (Entity entity : projectileList) {
		world.spawnParticle("largesmoke", entity.x, entity.y, entity.z, 0.0, 0.0, 0.0, 0);

		if(entity instanceof EntityArrow) {
			double oldArrowX = entity.x;
			double oldArrowY = entity.y;
			double oldArrowZ = entity.z;

			if (!((EntityArrow) entity).isGrounded()) {
				entity.remove();
				EntityArrow newArrow = new EntityArrow(world, player, true, ((EntityArrow) entity).getArrowType());
				if (!world.isClientSide) {
					world.entityJoinedWorld(newArrow);
					newArrow.setPos(oldArrowX, oldArrowY, oldArrowZ);
					world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
				}
			}
		}

		if(entity instanceof EntityFireball) {
			entity.hurt(player, 1, DamageType.COMBAT);
			world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
		}

		if(entity instanceof EntityCannonball) {

			double oldCBX = entity.x;
			double oldCBY = entity.y;
			double oldCBZ = entity.z;

			entity.remove();
			EntityCannonball newCB = new EntityCannonball(world, player);
			if (!world.isClientSide) {
				world.entityJoinedWorld(newCB);
				newCB.setPos(oldCBX, oldCBY, oldCBZ);
				double pushX = player.getLookAngle().xCoord;
				double pushY = player.getLookAngle().yCoord;
				double pushZ = player.getLookAngle().zCoord;
				newCB.push(pushX * 1.2, pushY * 1.2, pushZ * 1.2);
				world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
			}
		}
		if(entity instanceof EntitySnowball){
			double oldSBX = entity.x;
			double oldSBY = entity.y;
			double oldSBZ = entity.z;

			entity.remove();
			EntitySnowball newSB = new EntitySnowball(world, player);
			if (!world.isClientSide) {
				world.entityJoinedWorld(newSB);
				world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
			}
		}
			if(entity instanceof EntityShield){
				double oldSBX = entity.x;
				double oldSBY = entity.y;
				double oldSBZ = entity.z;

				entity.remove();
				EntityShield newTS = new EntityShield(world, player);
				if (!world.isClientSide) {
					world.entityJoinedWorld(newTS);
					world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
				}
			}
			if(entity instanceof EntityPB){
				double oldSBX = entity.x;
				double oldSBY = entity.y;
				double oldSBZ = entity.z;

				entity.remove();
				EntityPB newPS = new EntityPB(world, player);
				if (!world.isClientSide) {
					world.entityJoinedWorld(newPS);
					world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
				}
			}

	}

		player.swingItem();
		world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.3f, 1.0f);
}
	@Override
	public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if(itemstack.getData().getBoolean("active")) {
			entity.xd *= 0.4D;
			entity.zd *= 0.4D;
			int ticks = itemstack.getData().getInteger("ticks");

			if (ticks > 0) {
				itemstack.getData().putInt("ticks", ticks - 1);
			} else {
				itemstack.getData().putBoolean("active", false);
			}
		}
		if (this.parryDelay > 0) {
			this.parryDelay--;
		}

		if (this.parryDelay >= 10 && this.parryDelay <= 16) {
			((ParryInterface) player).shieldmod$Parry(1);
		}

		if (parryDelay == 18) {
			this.parryHitbox(world, this.player);
		}
	}

}
