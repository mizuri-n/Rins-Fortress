package mizurin.shieldmod.mixins;
import com.mojang.nbt.CompoundTag;
import mizurin.shieldmod.interfaces.IThrownItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//Mixin for the throwable shield.
@Mixin(value = EntityPlayer.class, remap = false)
public abstract class FieldMixin implements IThrownItem {
	@Shadow
	public abstract ChunkCoordinates getLastDeathCoordinate();

	@Unique
	private ItemStack thrownItem;

	@Override
	public ItemStack getThrownItem() {
		return thrownItem;
	}
	public void storeOrDropItem(EntityPlayer player, ItemStack stack){
		if(stack == null || stack.stackSize <= 0){
			return;
		}
		InventoryPlayer inventory = player.inventory;
		inventory.insertItem(stack, false);
		if (stack.stackSize > 0){
			player.dropPlayerItem(stack);
		}
	}

	//Sets the thrown item to save for later.
	@Override
	public ItemStack setThrownItem(ItemStack itemStack) {
		this.thrownItem = itemStack;
		return itemStack;
	}
	//adds save data for when the player quits after throwing the shield
	@Inject(method = "addAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("TAIL"))
	private void addData(CompoundTag tag, CallbackInfo ci){
		ItemStack thrownItem = getThrownItem();
		if(thrownItem != null) {
			tag.putCompound("item", thrownItem.writeToNBT(new CompoundTag()));
		}
	}
	//reads the save data after the player rejoins the world after throwing the shield
	@Inject(method = "readAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("TAIL"))
	private  void loadData(CompoundTag tag, CallbackInfo ci){
		this.thrownItem = ItemStack.readItemStackFromNbt(tag.getCompound("item"));
		storeOrDropItem((EntityPlayer)(Object)this, this.thrownItem);
		//gives the player their thrown item back. good failsafe so they do not lose the item.
	}
}
