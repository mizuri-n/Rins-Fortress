package mizurin.shieldmod.mixins;

import com.mojang.nbt.CompoundTag;
import mizurin.shieldmod.interfaces.IShieldZombie;
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
public class EntityZombieArmoredMixin extends EntityZombie implements IShieldZombie {
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
			//chance of spawning
			setHealthRaw(80);
			attackStrength = 6;
			this.mobDrops.add(new WeightedRandomLootObject(Item.ingotIron.getDefaultStack(), 1, 2));
			//guaranteed drop of 1-2 iron as a reward for killing the shielded zombie.
			entityData.set(21, (byte)1);
			//if it spawns, set the data true.
		}
	}


	@Override
	public boolean shieldmod$isShieldZombie() {
		return entityData.getByte(21) == 1;
	}

	@Override
	public boolean shieldmod$isSnowJack() {
		return false;
	}

	@Override
	public boolean shieldmod$isExpertSkeleton() {
		return false;
	}

	@Override
	public boolean shieldmod$isExpertSpider() {
		return false;
	}

	//Used to give the zombie a shield.
	@Inject(method = "getHeldItem()Lnet/minecraft/core/item/ItemStack;", at = @At("HEAD"), cancellable = true)
	private void sword(CallbackInfoReturnable<ItemStack> cir){
		if (shieldmod$isShieldZombie()){
			cir.setReturnValue(new ItemStack(Shields.ironShield));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putByte("shieldmod$isShieldZombie", shieldmod$isShieldZombie() ? (byte) 1 : (byte)0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		entityData.set(21, tag.getByte("shieldmod$isShieldZombie"));
	}


	@Override
	protected void attackEntity(Entity entity, float distance) {
		if (shieldmod$isShieldZombie()) {
			//really fun knockback that sends the entity in an arc when hit
			if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
				this.attackTime = 20;
				entity.hurt(this, this.attackStrength, DamageType.COMBAT);
				entity.push(entity.xd, entity.yd, entity.zd);
			}
		} else {
			//else statement for normal zombie attack pattern.
			if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
				this.attackTime = 20;
				entity.hurt(this, this.attackStrength, DamageType.COMBAT);
			}
		}
	}
}
