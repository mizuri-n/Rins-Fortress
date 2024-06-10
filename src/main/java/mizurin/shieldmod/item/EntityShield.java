package mizurin.shieldmod.item;

import mizurin.shieldmod.IThrownItem;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityPebble;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class EntityShield extends EntityPebble {
	public EntityShield(World world) {
		super(world);
		this.modelItem = Item.ammoSnowball;
		this.setSize(0.9F, 0.9F);
	}

	public EntityShield(World world, EntityLiving entityliving) {
		super(world, entityliving);
		this.modelItem = Item.ammoSnowball;
	}

	public EntityShield(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		this.modelItem = Item.ammoSnowball;
	}

	public void init() {
		super.init();
		this.damage = 0;
	}
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


	public void onHit(HitResult hitResult) {
		damage = ticksInAir /3 + 3;
		if (damage > 14){
			damage = 14;
		}
		if (hitResult.entity != null) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
			//hitResult.entity.push(this.xd * 0.2, 0, this.zd * 0.2);
		}
				if (!this.world.isClientSide) {
					this.world.playSoundAtEntity((Entity) null, this, "mob.ghast.fireball", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
				}
			if (this.modelItem != null) {
				for(int j = 0; j < 8; ++j) {
					this.world.spawnParticle("item", this.x, this.y, this.z, (double)this.modelItem.id, 0.0, 0.0);
				}
			}
		this.remove();
		storeOrDropItem((EntityPlayer) owner, ((IThrownItem)owner).getThrownItem());
		}
	}
