package mizurin.shieldmod.item;

import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.world.World;

public class ParryShield extends ThrowShield {

	public ParryShield(String name, int id, ToolMaterial toolMaterial) {
		super(name, id, toolMaterial);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
		((ParryInterface)entityPlayer).shieldmod$setIsBlock(true);
		((ParryInterface)entityPlayer).shieldmod$Block(5);
		onBlock(itemstack, world, entityPlayer);

		return itemstack;
	}

	//onBlock function is used so players can sneak right click as an action instead of assigning a new key.
	@Override
	public void onBlock(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.isSneaking()) {
			((ParryInterface) entityplayer).shieldmod$Parry(20);

		}
	}

}
