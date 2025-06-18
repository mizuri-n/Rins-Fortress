package mizurin.shieldmod.mixins;


import mizurin.shieldmod.item.Shields;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotArmor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SlotArmor.class, remap = false)
public class SlotArmorMixin extends Slot{

	public SlotArmorMixin(final Container container, final int index, final int x, final int y) {
		super(container, index, x, y);
	}
	@Final
	@Shadow
	MenuInventory menu;

	@Unique
	ItemStack lastItem;

	@Inject(method = "setChanged()V", at = @At(value = "HEAD"))
	public void injectSlot(final CallbackInfo ci){
		if(this.lastItem != null && (this.lastItem.itemID == Shields.regenAmulet.id) && getItemStack() == null){
			if(this.menu.inventory.player.getHealth() > this.menu.inventory.player.getMaxHealth()){
				this.menu.inventory.player.setHealthRaw(this.menu.inventory.player.getMaxHealth());
			}
		}
	}

	@Inject(method = "set", at = @At("HEAD"))
	public void setInject(final ItemStack itemstack, final CallbackInfo ci) {
		this.lastItem = getItemStack();
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void initInject(final MenuInventory menu, final Container container, final int index, final int x, final int y, final int armorType, final CallbackInfo ci) {
		this.lastItem = getItemStack();
	}
}
