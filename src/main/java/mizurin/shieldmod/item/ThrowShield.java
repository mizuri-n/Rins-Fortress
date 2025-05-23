package mizurin.shieldmod.item;

import mizurin.shieldmod.entities.EntityShield;
import mizurin.shieldmod.interfaces.IThrownItem;
import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.world.World;

public class ThrowShield extends ShieldItem{


	public ThrowShield(String name, String namespaceID, int id, ToolMaterial toolMaterial) {
		super(name, namespaceID, id, toolMaterial);
		maxStackSize = 1;
		setMaxDamage(toolMaterial.getDurability());
		this.tool = toolMaterial;
		this.weaponDamage = 3 + toolMaterial.getDamage();

	}
	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
		((ParryInterface)entityplayer).shieldmod$setIsBlock(true);
		((ParryInterface)entityplayer).shieldmod$Block(5);
		onBlock(itemstack, world, entityplayer);

		return itemstack;
	}
	//onBlock used so the player can shift right click to throw the shield.
	@Override
	public void onBlock(ItemStack itemstack, World world, Player entityplayer){
		if (entityplayer.isSneaking()){
			((IThrownItem)entityplayer).setThrownItem(itemstack);
			//Sets the shield to the player's thrown item.

			//entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem,null);

			entityplayer.inventory.setItem(entityplayer.inventory.getCurrentItemIndex(), null);
			//Sets the shield to null to hide it and act as a cooldown.
			world.playSoundAtEntity(null, entityplayer, "mob.ghast.fireball", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				//For any devs looking at my code. This if statement is used for server compatibility, please use it when spawning items.
				world.entityJoinedWorld(new EntityShield(world, entityplayer));
				itemstack.damageItem(4, entityplayer);
			}
			((ParryInterface)entityplayer).shieldmod$setIsBlock(false);
		}
	}

}
