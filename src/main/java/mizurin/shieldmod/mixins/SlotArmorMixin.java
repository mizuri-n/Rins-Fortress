package mizurin.shieldmod.mixins;


import com.llamalad7.mixinextras.sugar.Local;
import mizurin.shieldmod.item.Shields;
import net.minecraft.client.Minecraft;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotArmor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SlotArmor.class, remap = false)
public class SlotArmorMixin extends Slot{

	public SlotArmorMixin(Container container, int index, int x, int y) {
		super(container, index, x, y);
	}
	@Final
	@Shadow
	MenuInventory menu;

	@Inject(method = "setChanged()V", at = @At(value = "HEAD"))
	public void injectSlot(CallbackInfo ci){
		ItemStack stack = this.menu.inventory.armorItemInSlot(2);
		if(stack != null && (stack.itemID == Shields.regenAmulet.id)){
			if(Minecraft.getMinecraft().thePlayer.getHealth() > 20){
				Minecraft.getMinecraft().thePlayer.setHealth(20);
			}
		}
	}
}
