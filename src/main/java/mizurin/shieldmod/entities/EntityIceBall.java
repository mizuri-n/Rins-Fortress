package mizurin.shieldmod.entities;

import mizurin.shieldmod.interfaces.IDazed;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntitySnowball;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class EntityIceBall extends EntitySnowball {
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
		if (hitResult.entity != null) {
			hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
			((IDazed) hitResult.entity).shieldmod$freezeHurt(20);

			//Applies my custom status effect from the IFreeze interface.
		}
	}
}
