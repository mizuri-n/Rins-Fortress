package mizurin.shieldmod.mixins.client;


import mizurin.shieldmod.ShieldMod;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;



@Mixin(value = SoundEngine.class, remap = false)
public class SoundMixin {
	// TODO this mixin is fucked, idk why atm
//	@ModifyVariable(method = "playSound(Lnet/minecraft/client/sound/SoundEntry;Lnet/minecraft/core/sound/SoundCategory;FF)V", at = @At(value = "HEAD"), name = "entry")
//	private SoundEntry changePlaySound(SoundEntry entry) {
//		if (entry != null && ShieldMod.hurtSound && entry.name.equals("random.hurt")){
//			entry = SoundRepository.SOUNDS.getSoundEntry("damage.hurtflesh");
//		}
//		return entry;
//	}
//
//	@ModifyVariable(method = "playSoundAt(Lnet/minecraft/client/sound/SoundEntry;Lnet/minecraft/core/sound/SoundCategory;FFFFF)V", at = @At(value = "HEAD"), name = "entry")
//	private SoundEntry changePlaySoundAt(SoundEntry entry) {
//		if (entry != null && ShieldMod.hurtSound && entry.name.equals("random.hurt")){
//			entry = SoundRepository.SOUNDS.getSoundEntry("damage.hurtflesh");
//		}
//		return entry;
//	}
//
//	@ModifyVariable(method = "playSoundWithID(Lnet/minecraft/client/sound/SoundEntry;Lnet/minecraft/core/sound/SoundCategory;FFLjava/lang/String;Z)V", at = @At(value = "HEAD"), name = "entry")
//	private SoundEntry changePlaySoundWithID(SoundEntry entry) {
//		if (entry != null && ShieldMod.hurtSound && entry.name.equals("random.hurt")){
//			entry = SoundRepository.SOUNDS.getSoundEntry("damage.hurtflesh");
//		}
//		return entry;
//	}
}
