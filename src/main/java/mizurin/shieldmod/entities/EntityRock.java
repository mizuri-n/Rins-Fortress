package mizurin.shieldmod.entities;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.ProjectilePebble;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

//This is just the pebble used for the stone shield to prevent infinite pebbles, give impact particles, and to increase the base damage.
public class EntityRock extends ProjectilePebble {
	public EntityRock(World world, Mob owner) {
		super(world, owner);
	}
	public void init() {
		this.damage = 2;
	}
	public void onHit(HitResult hitResult) {
		if (hitResult.entity != null) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
		}

		if (this.modelItem != null) {
			for(int j = 0; j < 8; ++j) {
				this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, Items.AMMO_PEBBLE.id);
			}
		}
		this.remove();
	}
}
