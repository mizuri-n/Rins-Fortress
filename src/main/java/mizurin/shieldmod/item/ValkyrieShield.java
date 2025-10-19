package mizurin.shieldmod.item;

import mizurin.shieldmod.entities.EntityHoming;
import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.world.World;

public class ValkyrieShield extends ShieldItem{
	public ValkyrieShield(String name, String namespaceID, int id, ToolMaterial toolMaterial) {
		super(name, namespaceID, id, toolMaterial);
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
				itemstack.damageItem(16, entityplayer);
			if (!world.isClientSide) {
				world.entityJoinedWorld(new EntityHoming(world, entityplayer));

			}
				((ParryInterface)entityplayer).shieldmod$Fire(25);

		}

	}
}
