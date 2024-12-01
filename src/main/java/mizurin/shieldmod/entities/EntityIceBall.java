package mizurin.shieldmod.entities;

import mizurin.shieldmod.interfaces.IDazed;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityProjectile;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class EntityIceBall extends EntityProjectile {
	public EntityIceBall(World world) {
		super(world);
		this.modelItem = Item.ammoSnowball;
	}
	public EntityIceBall(World world, EntityLiving entityliving) {
		super(world, entityliving);
		this.modelItem = Item.ammoSnowball;
	}

	public EntityIceBall(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		this.modelItem = Item.ammoSnowball;
	}

	public void init() {
		super.init();
	}

	@Override
	public void onHit(HitResult hitResult) {
		if (hitResult.entity instanceof EntityLiving) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
			((IDazed) hitResult.entity).shieldmod$freezeHurt(20);
			if (this.modelItem != null) {
				for(int j = 0; j < 8; ++j) {
					this.world.spawnParticle("item", this.x, this.y, this.z, 0.0, 0.0, 0.0, Item.ammoSnowball.id);
				}
			}
			this.remove();
			//Applies my custom status effect from the IFreeze interface.
		}
	}
}
