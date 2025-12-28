package mizurin.shieldmod.item;

import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;

//For the Diamond Shield.
public class TreasureShield extends ShieldItem{
	public TreasureShield(String name, String namespaceID, int id, ShieldMaterials shieldMaterials) {
		super(name, namespaceID, id, shieldMaterials);
		maxStackSize = 1;
		setMaxDamage(shieldMaterials.getDurability());
		this.tool = shieldMaterials;
		this.weaponDamage = 3 + shieldMaterials.getDamage();
	}

	//If the ticks(Blocked) are active, then the player has bonus damage and knockback for the shield.
	@Override
	public boolean hitEntity(ItemStack itemstack, Mob target, Mob player) {
		if(((ParryInterface)player).shieldmod$getCounterTicks() > 0 && (target.hurtTime == 10)){
			target.fling(target.xd * 0.4, target.yd * 0, target.zd * 0.4, 0.5F);
			target.hurt(player, 14, DamageType.COMBAT);
			((ParryInterface)player).shieldmod$Counter(0);
			//After hitting an entity, set the ticksB to 0, ending the countdown immediately.
		}
		if ((target.hurtTime == 10)) {
			target.fling(target.xd * 0.2, target.yd * 0, target.zd * 0.2, 0.5F);
		}

		itemstack.damageItem(1, player);

		return true;
	}
}
