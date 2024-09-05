package mizurin.shieldmod.mixins;

import net.minecraft.client.render.stitcher.AtlasStitcher;
import net.minecraft.client.render.stitcher.TextureRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.io.IOException;

import java.net.URISyntaxException;
import java.util.HashMap;

import static net.minecraft.client.render.stitcher.TextureRegistry.artAtlas;

@Mixin(TextureRegistry.class)
public abstract class PaintingMixin {
	@Shadow
	public static HashMap<String, AtlasStitcher> stitcherMap;

	@Shadow
	public static void initializeAllFiles(String namespace, AtlasStitcher atlas, boolean subDirectories) throws URISyntaxException, IOException, NullPointerException {
	}

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void inject(CallbackInfo ci) {
		try {
			for (AtlasStitcher stitcher : stitcherMap.values()) {
				initializeAllFiles("shieldmod", stitcher, stitcher != artAtlas);
			}
		} catch (Exception var2) {
			(new RuntimeException(var2)).printStackTrace();
			System.out.println("Failed to fully initialize assets, some issue may occur!");
		}
	}
}
