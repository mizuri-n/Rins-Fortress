package mizurin.shieldmod.item;

import mizurin.shieldmod.interfaces.IDazed;
import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.World;

public class ShieldItem extends ItemToolSword {
	public ToolMaterial tool;
	public int weaponDamage;


	public ShieldItem(String name, int id, ToolMaterial toolMaterial){
		super(name, id, toolMaterial);
		maxStackSize = 1;
		setMaxDamage(toolMaterial.getDurability());
		this.tool = toolMaterial;
		this.weaponDamage = 4 + toolMaterial.getDamage();


	}

	//Applies a knockback effect for all shields, bonus knockback to Leather Shields, and sets hitEntities on fire when hit by a Steel Shield.
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving target, EntityLiving player) {
		if ((target.hurtTime == 10 || target instanceof EntityPlayer)) {
			if (itemstack.getItem() == Shields.leatherShield) {
				target.knockBack(player, 1, (player.x - target.x), (player.z - target.z));
				target.push((target.x - player.x) / 11, 0, (target.z - player.z) / 11);
			} else {
				target.push((target.x - player.x) / 11, 0, (target.z - player.z) / 11);
			}
			if (itemstack.getItem() == Shields.goldShield) {
				((IDazed) target).shieldmod$dazedHurt(300);
				target.push((target.x - player.x) / 20, 0, (target.z - player.z) / 20);
			}
			itemstack.damageItem(1, player);
		}

		return true;
	}
	public int getDamageVsEntity(Entity entity) {
		return this.weaponDamage;
	}


	//Activates ticks that determine if the player is blocking.
	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		//Set to true then add the ticks to the data.
		((ParryInterface)entityplayer).shieldmod$setIsBlock(true);
		((ParryInterface)entityplayer).shieldmod$Block(5);

		return itemstack;
	}
	//this function is used for shift right click abilities.
	public void onBlock(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		return;
	}

}

