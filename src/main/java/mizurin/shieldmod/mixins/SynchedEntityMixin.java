package mizurin.shieldmod.mixins;


import com.mojang.nbt.CompoundTag;
import mizurin.shieldmod.interfaces.ParryInterface;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)

public class SynchedEntityMixin extends EntityLiving implements ParryInterface {
	public SynchedEntityMixin(World world) {
		super(world);
	}

	@Inject(method = "Lnet/minecraft/core/entity/player/EntityPlayer;<init>(Lnet/minecraft/core/world/World;)V", at = @At("TAIL"))
	private void injectEntity(World world, CallbackInfo ci){
		this.entityData.define(23, (byte)0);
	}

	@Inject(method = "Lnet/minecraft/core/entity/player/EntityPlayer;readAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("TAIL"))
		public void injectAddSaveData(CompoundTag tag, CallbackInfo ci){
		tag.putByte("shieldmod$getIsBlock", shieldmod$getIsBlock() ? (byte) 1 : (byte)0);
	}

	@Inject(method = "Lnet/minecraft/core/entity/player/EntityPlayer;addAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("TAIL"))
		public void injectReadSaveData(CompoundTag tag, CallbackInfo ci){
		entityData.set(23, tag.getByte("shieldmod$getIsBlock"));
	}

	@Override
	public int shieldmod$getParryTicks() {
		return 0;
	}

	@Override
	public void shieldmod$Parry(int parryTicks) {

	}

	@Override
	public int shieldmod$getBlockTicks() {
		return 0;
	}

	@Override
	public void shieldmod$Block(int blockTicks) {

	}

	@Override
	public boolean shieldmod$getIsBlock() {
		return this.entityData.getByte(23) != 0;
	}

	@Override
	public void shieldmod$setIsBlock(boolean bool) {
		this.entityData.set(23, bool ? (byte)1 : (byte)0);
	}

	@Override
	public int shieldmod$getCounterTicks() {
		return 0;
	}

	@Override
	public void shieldmod$Counter(int counterTicks) {

	}

	@Override
	public int shieldmod$getFireTicks() {
		return 0;
	}

	@Override
	public void shieldmod$Fire(int fireTicks) {

	}
}
