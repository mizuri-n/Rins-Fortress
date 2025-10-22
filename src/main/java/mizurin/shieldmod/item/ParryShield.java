package mizurin.shieldmod.item;

import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ParryShield extends ShieldItem {

	public ParryShield(String name, String namespaceID, int id, ShieldMaterials shieldMaterials) {
		super(name, namespaceID, id, shieldMaterials);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
		((ParryInterface)entityplayer).shieldmod$setIsBlock(true);
		((ParryInterface)entityplayer).shieldmod$Block(5);
		onBlock(itemstack, world, entityplayer);

		return itemstack;
	}

	//onBlock function is used so players can sneak right click as an action instead of assigning a new key.
	@Override
	public void onBlock(ItemStack itemstack, World world, Player entityplayer) {
		if (entityplayer.isSneaking()) {
			((ParryInterface) entityplayer).shieldmod$Parry(20);

		}
	}

}
