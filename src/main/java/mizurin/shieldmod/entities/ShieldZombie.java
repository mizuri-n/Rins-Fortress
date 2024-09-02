package mizurin.shieldmod.entities;

import mizurin.shieldmod.item.Shields;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.EntityArmoredZombie;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class ShieldZombie extends EntityArmoredZombie {
	public ShieldZombie(World world) {
		super(world);
		this.attackStrength += 1;
	}

	@Override
	public int getMaxHealth() {
		return 80;
	}

	@Override
	public ItemStack getHeldItem() {
		return new ItemStack(Shields.ironShield);
	}

	@Override
	protected void attackEntity(Entity entity, float distance) {
		if (this.attackTime <= 0 && distance < 2.0F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
			this.attackTime = 20;
			entity.hurt(this, this.attackStrength, DamageType.COMBAT);
			entity.push(entity.xd, entity.yd, entity.zd);
		}
	}
}
