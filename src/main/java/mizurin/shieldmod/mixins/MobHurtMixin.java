//package mizurin.shieldmod.mixins;
//
//import mizurin.shieldmod.effects.ShieldEffects;
//import net.minecraft.core.entity.Entity;
//import net.minecraft.core.entity.Mob;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.ModifyArgs;
//import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
//import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
//
//
//@Mixin(value = Mob.class, remap = false)
//public class MobHurtMixin {
//
//	@ModifyArgs(method = "hurt", at = @At(value = "TAIL"))
//	public void injectHurt(Args args){
//		Entity attacker = args.get(0);
//		int damage = args.get(1);
//		if(attacker != null && ((IHasEffects) attacker).getContainer().hasEffect(ShieldEffects.weaknessEffect)){
//			damage = damage *4/5;
//		}
//		args.set(1, damage);
//	}
//}
