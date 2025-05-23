package mizurin.shieldmod.mixins;

import mizurin.shieldmod.interfaces.ParryInterface;
import mizurin.shieldmod.item.ShieldItem;
import mizurin.shieldmod.item.ShieldMaterials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobZombie;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static mizurin.shieldmod.ShieldMod.expertMode;

//Mixin for knockback when blocking.
@Mixin(value = Mob.class, remap = false)


public abstract class KnockBackMixin {


	@Shadow
	protected abstract void damageEntity(int i, DamageType damageType);

	@Shadow
	public abstract boolean interact(Player entityplayer);

	@Shadow
	public abstract @Nullable ItemStack getHeldItem();

	@Inject(method = "knockBack(Lnet/minecraft/core/entity/Entity;IDD)V", at = @At("HEAD"), cancellable = true)
	public void injectKnockBack(Entity entity, int i, double d, double d1, CallbackInfo ci) {
		if (((Object)this) instanceof Player) {
			ItemStack stack = ((Player)(Object)this).getCurrentEquippedItem();
			//hope works


			if (stack != null) {
				if (stack.getItem() instanceof ShieldItem) {
					//checks if the player is holding a shield.

					ShieldItem shield = ((ShieldItem) stack.getItem());
					if (((ParryInterface)((Player)(Object)this)).shieldmod$getIsBlock() && shield.tool == ShieldMaterials.TOOL_IRON) {
						//checks if the player is blocking and holding an iron shield
						// cancels early and ignores knockback.
						ci.cancel();
					}
					if (((ParryInterface)((Player)(Object)this)).shieldmod$getIsBlock() && shield.tool != ShieldMaterials.TOOL_IRON){
						//checks if the player is blocking and not holding an iron shield.
						//removed the jump to the y value when being hit.
						float f = MathHelper.sqrt(d * d + d1 * d1);
						float f1 = 0.9F;
						((Player)(Object)this).xd /= 2.0;
						((Player)(Object)this).yd /= 2.0;
						((Player)(Object)this).zd /= 2.0;
						((Player)(Object)this).xd -= d / (double)f * (double)f1;
						((Player)(Object)this).zd -= d1 / (double)f * (double)f1;


						ci.cancel();
					}

				}
			}
		}
		if(((Object)this) instanceof MobZombie && expertMode){
			float f = MathHelper.sqrt(d * d + d1 * d1);
			float f1 = 0.2F;
			((MobZombie)(Object)this).xd /= 2.0;
			((MobZombie)(Object)this).yd /= 2.0;
			((MobZombie)(Object)this).zd /= 2.0;
			((MobZombie)(Object)this).xd -= d / (double)f * (double)f1;
			((MobZombie)(Object)this).zd -= d1 / (double)f * (double)f1;
			if (((MobZombie)(Object)this).yd > 0.4000000059604645) {
				((MobZombie)(Object)this).yd = 0.4000000059604645;
			}

			ci.cancel();
		}
	}
}
