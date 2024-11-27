package mizurin.shieldmod.mixins.client;


import mizurin.shieldmod.ShieldMod;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;



@Mixin(value = SoundManager.class, remap = false)
public class SoundMixin {
	@ModifyVariable(method = "playSound(Ljava/lang/String;Lnet/minecraft/core/sound/SoundCategory;FF)V", at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
	private String changeSoundId1(String soundPath) {
		if (soundPath != null){
			if (ShieldMod.hurtSound && soundPath.equals("random.hurt")) {
				soundPath = "damage.hurtflesh";
			}
	}
		return soundPath;
	}

	@ModifyVariable(method = "playSound(Ljava/lang/String;Lnet/minecraft/core/sound/SoundCategory;FFLjava/lang/String;)V", at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
	private String changeSoundId2(String soundPath) {
		if (soundPath != null) {
			if (ShieldMod.hurtSound && soundPath.equals("random.hurt")) {
				soundPath = "damage.hurtflesh";
			}
		}
		return soundPath;
	}

	@ModifyVariable(method = "playSound(Ljava/lang/String;Lnet/minecraft/core/sound/SoundCategory;FFFFF)V", at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
	private String changeSoundId3(String soundPath) {
		if (soundPath != null) {
			if (ShieldMod.hurtSound && soundPath.equals("random.hurt")) {
				soundPath = "damage.hurtflesh";
			}
		}
		return soundPath;
	}
}
