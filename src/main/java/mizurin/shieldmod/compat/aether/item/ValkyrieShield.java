package mizurin.shieldmod.compat.aether.item;

import mizurin.shieldmod.compat.aether.entities.EntityHoming;
import mizurin.shieldmod.interfaces.ParryInterface;
import mizurin.shieldmod.item.ShieldItem;
import mizurin.shieldmod.item.ShieldMaterials;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;
import teamport.aether.item.AetherHasCustomDamageType;

public class ValkyrieShield extends ShieldItem implements AetherHasCustomDamageType {
	public ValkyrieShield(String name, String namespaceID, int id, ShieldMaterials shieldMaterials) {
		super(name, namespaceID, id, shieldMaterials);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
		((ParryInterface)entityplayer).shieldmod$setIsBlock(true);
		((ParryInterface)entityplayer).shieldmod$Block(5);
		onBlock(itemstack, world, entityplayer);

		return itemstack;
	}
	@Override
	public void onBlock(ItemStack itemstack, World world, Player entityplayer) {
		if (entityplayer.isSneaking() && ((ParryInterface)entityplayer).shieldmod$getFireTicks() == 0){
				itemstack.damageItem(4, entityplayer);
			if (!world.isClientSide) {
				double lookX = entityplayer.getLookAngle().x;
				double lookY = entityplayer.getLookAngle().y;
				double lookZ = entityplayer.getLookAngle().z;
				EntityHoming ball = new EntityHoming(world, entityplayer);
				ball.setHeading(lookX, lookY + entityplayer.getHeadHeight(), lookZ, 0.5f, 0.0f);
				world.entityJoinedWorld(ball);
			}
				((ParryInterface)entityplayer).shieldmod$Fire(35);

		}

	}
	public DamageType getDamageType() {
		return AetherMod.HOLY;
	}
}
