package mizurin.shieldmod;

import mizurin.shieldmod.entities.EntityPoisonFX;
import net.minecraft.client.entity.particle.ParticleFlame;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import turniplabs.halplibe.helper.ParticleHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import java.io.IOException;
import java.net.URISyntaxException;

import static mizurin.shieldmod.ShieldMod.LOGGER;
import static mizurin.shieldmod.ShieldMod.MOD_ID;

public class ShieldModClient implements ClientStartEntrypoint {
	@Override
	public void beforeClientStart() {
		Colorizers.registerColorizers();
		for (final AtlasStitcher stitcher : TextureRegistry.stitcherMap.values()) {
			try {
				TextureRegistry.initializeAllFiles(MOD_ID, stitcher, true);
			} catch (URISyntaxException | IOException e) {
				LOGGER.error("Failed to initialize texture files!", e);
			}
		}
	}

	@Override
	public void afterClientStart() {
		ParticleHelper.createParticle("purpleflame", ((world, d, e, f, g, h, i, j) -> new EntityPoisonFX(world, d, e, f, g, h, i, ParticleFlame.Type.BLUE)));
	}
}
