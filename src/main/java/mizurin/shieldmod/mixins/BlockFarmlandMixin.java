package mizurin.shieldmod.mixins;


import mizurin.shieldmod.item.Shields;
import net.minecraft.core.block.BlockFarmland;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlockFarmland.class, remap = false)
public class BlockFarmlandMixin {
	@Redirect(method = "onEntityWalking(Lnet/minecraft/core/world/World;IIILnet/minecraft/core/entity/Entity;)V",
		at = @At(value = "INVOKE",target = "Lnet/minecraft/core/item/ItemStack;getItem()Lnet/minecraft/core/item/Item;"))
	public Item addCustomBoots(ItemStack instance){
		Item item = instance.getItem();
		if (item == Shields.armorLeatherBoot){
			return Item.armorBootsLeather;
		}
		return item;
	}
}

