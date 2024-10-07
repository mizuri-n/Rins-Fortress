package mizurin.shieldmod.item;

import mizurin.shieldmod.entities.EntityPB;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemPB extends Item {
	public ItemPB(String name, int id) {
		super(name, id);
		this.maxStackSize = 16;
	}
	public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		itemstack.consumeItem(entityplayer);
		world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!world.isClientSide) {
			world.entityJoinedWorld(new EntityPB(world, entityplayer));
		}

		return itemstack;
	}
}
