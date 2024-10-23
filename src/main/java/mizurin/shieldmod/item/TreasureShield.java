package mizurin.shieldmod.item;

import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;

//For the Diamond Shield.
public class TreasureShield extends ShieldItem{
	public TreasureShield(String name, int id, ToolMaterial toolMaterial) {
		super(name, id, toolMaterial);
		maxStackSize = 1;
		setMaxDamage(toolMaterial.getDurability());
		this.tool = toolMaterial;
		this.weaponDamage = 4 + toolMaterial.getDamage();
	}

	//If the ticks(Blocked) are active, then the player has bonus damage and knockback for the shield.
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving target, EntityLiving player) {
		if(((ParryInterface)player).shieldmod$getCounterTicks() > 0){
			target.knockBack(player, 1, (player.x - target.x), (player.z - target.z ));
			target.push((target.x - player.x)/7, 1, (target.z - player.z)/7);
			target.push(target.xd, target.yd, target.zd);
			target.hurt(player, 14, DamageType.COMBAT);
			((ParryInterface)player).shieldmod$Counter(0);
			//After hitting an entity, set the ticksB to 0, ending the countdown immediately.
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
