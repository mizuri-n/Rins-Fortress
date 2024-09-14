package mizurin.shieldmod.mixins;

import com.mojang.nbt.CompoundTag;
import mizurin.shieldmod.IShieldZombie;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityArmoredZombie;
import net.minecraft.core.entity.monster.EntityZombie;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(value = EntityArmoredZombie.class, remap = false)
public abstract class EntityZombieArmoredMixin extends EntityZombie implements IShieldZombie {
	@Shadow
	@Final
	private boolean isHoldingSword;


	public EntityZombieArmoredMixin(World world) {
		super(world);
	}
	@Inject(method = "init", at = @At("TAIL"))
	public void init(CallbackInfo ci){
		entityData.define(21, (byte)0);
	}
	@Override
	public void spawnInit(){
		super.spawnInit();
		if ((random.nextInt(5) == 0)){
			setHealthRaw(80);
			attackStrength = 6;
			this.mobDrops.add(new WeightedRandomLootObject(Item.ingotIron.getDefaultStack(), 1, 2));
			entityData.set(21, (byte)1);
		}
	}


	@Override
	public boolean better_with_defense$isShieldZombie() {
		return entityData.getByte(21) == 1;
	}


	@Inject(method = "getHeldItem()Lnet/minecraft/core/item/ItemStack;", at = @At("HEAD"), cancellable = true)
	private void sword(CallbackInfoReturnable<ItemStack> cir){
		if (better_with_defense$isShieldZombie()){
			cir.setReturnValue(new ItemStack(Shields.ironShield));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putByte("better_with_defense$isShieldZombie", better_with_defense$isShieldZombie() ? (byte) 1 : (byte)0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		entityData.set(21, tag.getByte("better_with_defense$isShieldZombie"));
	}

	@Override
	protected void attackEntity(Entity entity, float distance) {
		if (better_with_defense$isShieldZombie()) {
			if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
				this.attackTime = 20;
				entity.hurt(this, this.attackStrength, DamageType.COMBAT);
				entity.push(entity.xd, entity.yd, entity.zd);
			}
		} else {
			if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
				this.attackTime = 20;
				entity.hurt(this, this.attackStrength, DamageType.COMBAT);
			}
		}
	}
}
