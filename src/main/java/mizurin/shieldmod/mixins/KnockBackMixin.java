package mizurin.shieldmod.mixins;

import mizurin.shieldmod.item.ShieldItem;
import mizurin.shieldmod.item.ShieldMaterials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.EntitySkeleton;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityLiving.class, remap = false)


public abstract class KnockBackMixin {


	@Shadow
	protected abstract void damageEntity(int i, DamageType damageType);

	@Shadow
	public abstract boolean interact(EntityPlayer entityplayer);

	@Inject(method = "knockBack(Lnet/minecraft/core/entity/Entity;IDD)V", at = @At("HEAD"), cancellable = true)
	public void injectKnockBack(Entity entity, int i, double d, double d1, CallbackInfo ci) {
		if (((Object)this) instanceof EntityPlayer) {
			ItemStack stack = ((EntityPlayer)(Object)this).inventory.mainInventory[((EntityPlayer)(Object)this).inventory.currentItem];


			if (stack != null) {
				if (stack.getItem() instanceof ShieldItem) {

					ShieldItem shield = ((ShieldItem) stack.getItem());
					if (stack.getData().getBoolean("active") && shield.tool == ShieldMaterials.TOOL_IRON) {
						ci.cancel();
					}
					if (stack.getData().getBoolean("active") && shield.tool != ShieldMaterials.TOOL_IRON){
						float f = MathHelper.sqrt_double(d * d + d1 * d1);
						float f1 = 0.9F;
						((EntityPlayer)(Object)this).xd /= 2.0;
						((EntityPlayer)(Object)this).yd /= 2.0;
						((EntityPlayer)(Object)this).zd /= 2.0;
						((EntityPlayer)(Object)this).xd -= d / (double)f * (double)f1;
						((EntityPlayer)(Object)this).zd -= d1 / (double)f * (double)f1;


						ci.cancel();
					}
				}
			}
		}
	}
}
