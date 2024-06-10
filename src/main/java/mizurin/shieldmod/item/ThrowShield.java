package mizurin.shieldmod.item;

import mizurin.shieldmod.IThrownItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.world.World;

public class ThrowShield extends ShieldItem{
	int ticksToAdd = 5;


	public ThrowShield(String name, int id, ToolMaterial toolMaterial) {
		super(name, id, toolMaterial);
		maxStackSize = 1;
		setMaxDamage(toolMaterial.getDurability());
		this.tool = toolMaterial;
		this.weaponDamage = 4 + toolMaterial.getDamage();

	}
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		itemstack.getData().putBoolean("active", true);
		itemstack.getData().putInt("ticks", ticksToAdd);
		onBlock(itemstack, world, entityplayer);

		return itemstack;
	}

	public void onBlock(ItemStack itemstack, World world, EntityPlayer entityplayer){
		if (entityplayer.isSneaking()){
			((IThrownItem)entityplayer).setThrownItem(itemstack);
			entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem,null);
			world.playSoundAtEntity(null, entityplayer, "mob.ghast.fireball", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isClientSide) {
				world.entityJoinedWorld(new EntityShield(world, entityplayer));
				itemstack.damageItem(4, entityplayer);
				itemstack.getData().putBoolean("active", false);
			}
		}
	}

}
