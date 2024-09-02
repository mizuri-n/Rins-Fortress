package mizurin.shieldmod.item;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class TreasureShield extends ShieldItem{
	public TreasureShield(String name, int id, ToolMaterial toolMaterial) {
		super(name, id, toolMaterial);
		maxStackSize = 1;
		setMaxDamage(toolMaterial.getDurability());
		this.tool = toolMaterial;
		this.weaponDamage = 4 + toolMaterial.getDamage();
	}
	@Override
	public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if(itemstack.getData().getBoolean("active")){
			entity.xd *= 0.4D;
			entity.zd *= 0.4D;
			int ticks = itemstack.getData().getInteger("ticks");

			if (ticks > 0){
				itemstack.getData().putInt("ticks", ticks - 1);
			} else {
				itemstack.getData().putBoolean("active", false);
			}
		}
		if(itemstack.getData().getBoolean("blocked")){
			int ticksB = itemstack.getData().getInteger("ticksB");

			if (ticksB > 0){
				itemstack.getData().putInt("ticksB", ticksB - 1);
			} else {
				itemstack.getData().putBoolean("blocked", false);
			}
		}
	}
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving target, EntityLiving player) {
		if(itemstack.getData().getBoolean("blocked")){
			target.knockBack(player, 1, (player.x - target.x), (player.z - target.z ));
			target.push((target.x - player.x)/7, 1, (target.z - player.z)/7);
			target.hurt(player, 5, DamageType.COMBAT);
			itemstack.getData().putBoolean("blocked", false);
		}

		if(itemstack.getItem() == Shields.leatherShield){
			target.knockBack(player, 1, (player.x - target.x), (player.z - target.z ));
			target.push((target.x - player.x)/7, 0, (target.z - player.z)/7);
		} else {
			target.knockBack(player, 3, player.x - target.x, player.z - target.z);
		}

		itemstack.damageItem(1, player);

		return true;
	}
}
