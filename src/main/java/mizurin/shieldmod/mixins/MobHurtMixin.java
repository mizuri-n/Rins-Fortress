package mizurin.shieldmod.mixins;

import mizurin.shieldmod.effects.ShieldEffects;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;


@Mixin(value = MobMonster.class, remap = false)
public abstract class MobHurtMixin {


	@ModifyArgs(method = "hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/MobPathfinder;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"))
	public void injectHurt(Args args){
		Entity attacker = args.get(0);
		int damage = args.get(1);
		if(attacker != null && ((IHasEffects) attacker).getContainer().hasEffect(ShieldEffects.weaknessEffect)){
			damage = Math.round(damage * 0.80f);
		}
		args.set(1, damage);
	}
}
