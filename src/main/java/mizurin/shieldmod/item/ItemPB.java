package mizurin.shieldmod.item;

import mizurin.shieldmod.entities.EntityPB;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

//ItemPB is short for Item Poison Bottle.
public class ItemPB extends Item {
	public ItemPB(String name, String namespaceID, int id) {
		super(name, namespaceID, id);
		this.maxStackSize = 16;
	}
	public ItemStack onUse(ItemStack itemstack, World world, @NotNull Player entityplayer) {
		itemstack.consumeItem(entityplayer);
		world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!world.isClientSide) {
			world.entityJoinedWorld(new EntityPB(world, entityplayer));
		}
		//For any devs looking at my code. This if statement (!world.isClientSide) is used for server compatibility, please use it when spawning items.

		return itemstack;
	}
}
