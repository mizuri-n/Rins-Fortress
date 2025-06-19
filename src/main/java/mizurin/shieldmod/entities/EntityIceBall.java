package mizurin.shieldmod.entities;

import mizurin.shieldmod.interfaces.IStatus;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class EntityIceBall extends Projectile {
	public EntityIceBall(World world) {
		super(world);
		this.modelItem = Items.AMMO_SNOWBALL;
	}
	public EntityIceBall(World world, Mob owner) {
		super(world, owner);
		this.modelItem = Items.AMMO_SNOWBALL;
	}

	public EntityIceBall(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		this.modelItem = Items.AMMO_SNOWBALL;
	}


	@Override
	public void onHit(HitResult hitResult) {
		if (hitResult.entity instanceof Mob) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
			((IStatus) hitResult.entity).shieldmod$freezeHurt(20);
			if (this.modelItem != null) {
				for(int j = 0; j < 8; ++j) {
					this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, Items.AMMO_SNOWBALL.id);
				}
			}
			this.remove();
			//Applies my custom status effect from the IFreeze interface.
		}
	}
}
