package mizurin.shieldmod.mixins;


import mizurin.shieldmod.item.RFItems;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.catalyst.effects.helper.HealthHelper;

@Mixin(value = Player.class, remap = false)
public class BonusHealthMixin extends Mob {
	@Shadow
	public ContainerInventory inventory;

	public BonusHealthMixin(@Nullable World world) {
		super(world);
	}


	@Inject(method = "getMaxHealth()I", at = @At("HEAD"), cancellable = true)
	public void injectBonusHealth(CallbackInfoReturnable<Integer> cir){
		if(this.inventory != null){
			ItemStack chest_item = this.inventory.armorItemInSlot(IArmorItem.PIECE_CHEST);
			if (chest_item != null && chest_item.getItem().equals(RFItems.regenAmulet)){
			}
		}
	}
}
