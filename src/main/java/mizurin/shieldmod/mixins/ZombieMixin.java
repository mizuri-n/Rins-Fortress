package mizurin.shieldmod.mixins;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.monster.EntityZombie;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static mizurin.shieldmod.ShieldMod.expertMode;

@Mixin(value = EntityZombie.class, remap = false)
public class ZombieMixin extends EntityMonster {
	public ZombieMixin(World world) {
		super(world);
	}

	@Override
	protected Entity findPlayerToAttack() {
		if(expertMode){
			EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 32.0);
			return entityplayer != null && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
		} else {
			EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 16.0);
			return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
		}
	}
}
