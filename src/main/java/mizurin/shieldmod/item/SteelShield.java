package mizurin.shieldmod.item;

import mizurin.shieldmod.entities.EntityFire;
import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.entity.player.Player;



public class SteelShield extends ShieldItem{
	public SteelShield(String name, String namespaceID, int id, ToolMaterial toolMaterial) {
		super(name, namespaceID, id, toolMaterial);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
		((ParryInterface)entityplayer).shieldmod$setIsBlock(true);
		((ParryInterface)entityplayer).shieldmod$Block(5);
		onBlock(itemstack, world, entityplayer);

		return itemstack;
	}

	//currently working on fire.
	@Override
	public void onBlock(ItemStack itemstack, World world, Player entityplayer) {
		if (entityplayer.isSneaking() && ((ParryInterface)entityplayer).shieldmod$getFireTicks() == 0){
			if(entityplayer.inventory.consumeInventoryItem(Items.FLINT.id)){
				itemstack.damageItem(4, entityplayer);
					for (int i = 0; i < 4; i++) {
						Vec3 plylook = entityplayer.getLookAngle();
						EntityFire flame = new EntityFire(world, entityplayer);
						if (!world.isClientSide) {
							world.entityJoinedWorld(flame);

							flame.setHeading(plylook.x, plylook.y, plylook.z, .2f, 10);
						}
					}
					world.playSoundAtEntity(entityplayer, entityplayer, "fire.ignite", 3.6F, 1.5F);

				((ParryInterface)entityplayer).shieldmod$Fire(10);

				}
			}

		}
}
