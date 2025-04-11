package mizurin.shieldmod.entities;

import net.minecraft.client.entity.particle.ParticleFlame;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;

public class EntityPoisonFX extends ParticleFlame {
	public EntityPoisonFX(World world, double d, double d1, double d2, double d3, double d4, double d5, Type type) {
		super(world, d, d1, d2, d3, d4, d5, type);
		this.tex = TextureRegistry.getTexture("shieldmod:item/purple_fire");
	}

}
