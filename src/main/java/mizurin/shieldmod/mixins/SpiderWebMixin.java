package mizurin.shieldmod.mixins;

import net.minecraft.core.block.BlockCobweb;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntitySpider;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockCobweb.class, remap = false)
public class SpiderWebMixin {

	@Inject(method = "onEntityCollidedWithBlock(Lnet/minecraft/core/world/World;IIILnet/minecraft/core/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
	public void injectWeb(World world, int x, int y, int z, Entity entity, CallbackInfo ci){
		if(entity instanceof EntitySpider){
			ci.cancel();
		}
	}
}
