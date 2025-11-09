package mizurin.shieldmod.item;

import mizurin.shieldmod.effects.ShieldEffects;
import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.World;

public class ShieldItem extends ItemToolSword {
	public ShieldMaterials tool;
	public int weaponDamage;
	public float guard;

	public ShieldItem(String name, String namespaceID, int id, ShieldMaterials shieldMaterials){
		super(name, namespaceID, id, shieldMaterials);
		maxStackSize = 1;
		setMaxDamage(shieldMaterials.getDurability());
		this.tool = shieldMaterials;
		this.weaponDamage = 3 + shieldMaterials.getDamage();
		this.guard = shieldMaterials.getGuard();


	}

	//Applies a knockback effect for all shields, bonus knockback to Leather Shields.
	@Override
	public boolean hitEntity(ItemStack itemstack, Mob target, Mob player) {
		if ((target.hurtTime == 10 || target instanceof Player)) {
			if (itemstack.getItem() == RFItems.leatherShield) {
				target.fling(target.xd * 0.4, target.yd * 0, target.zd * 0.4, 0.5F);
			} else {
				target.fling(target.xd * 0.2, target.yd * 0, target.zd * 0.2, 0.5F);
			}
			if (itemstack.getItem() == RFItems.goldShield) {
				ShieldEffects.add((Mob) target, ShieldEffects.slowEffect, 1, 100);
				target.fling(target.xd * 0.2, target.yd * 0, target.zd * 0.2, 0.5F);
			}

			itemstack.damageItem(1, player);
		}

		return true;
	}

	public int getDamageVsEntity(Entity entity, ItemStack is) {
		return this.weaponDamage;
	}

	public float getGuard(ItemStack is){
		return this.guard;
	}

	//Activates ticks that determine if the player is blocking.
	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
		//Set to true then add the ticks to the data.
		((ParryInterface)entityplayer).shieldmod$setIsBlock(true);
		((ParryInterface)entityplayer).shieldmod$Block(5);


		return itemstack;
	}
	//this function is used for shift right click abilities.
	public void onBlock(ItemStack itemstack, World world, Player entityplayer) {
		return;
	}
}

