package mizurin.shieldmod.item;

import mizurin.shieldmod.entities.EntityFire;
import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;



public class SteelShield extends ShieldItem{
	public SteelShield(String name, int id, ToolMaterial toolMaterial) {
		super(name, id, toolMaterial);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		((ParryInterface)entityplayer).shieldmod$setIsBlock(true);
		((ParryInterface)entityplayer).shieldmod$Block(5);
		onBlock(itemstack, world, entityplayer);

		return itemstack;
	}

	//currently working on fire.
	@Override
	public void onBlock(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.isSneaking() && ((ParryInterface)entityplayer).shieldmod$getFireTicks() == 0){
			if(entityplayer.inventory.consumeInventoryItem(Item.flint.id)){
				itemstack.damageItem(4, entityplayer);
					for (int i = 0; i < 4; i++) {
						Vec3d plylook = entityplayer.getLookAngle();
						EntityFire flame = new EntityFire(world, entityplayer);
						if (!world.isClientSide) {
							world.entityJoinedWorld(flame);

							flame.setHeading(plylook.xCoord, plylook.yCoord, plylook.zCoord, .5f, 10);
						}
					}
					world.playSoundAtEntity(entityplayer, entityplayer, "fire.ignite", 3.6F, 1.5F);

				((ParryInterface)entityplayer).shieldmod$Fire(10);

				}
			}

		}
}
