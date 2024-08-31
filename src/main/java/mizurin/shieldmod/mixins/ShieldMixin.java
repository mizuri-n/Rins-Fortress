package mizurin.shieldmod.mixins;

import mizurin.shieldmod.item.ShieldItem;
import mizurin.shieldmod.item.ShieldMaterials;
import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.EntityTNT;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.entity.projectile.EntityCannonball;
import net.minecraft.core.entity.projectile.EntityProjectile;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



// mixin to EntityPlayer, do not remap(forgot what remap does)
@Mixin(value = EntityPlayer.class, remap = false)

// extend Entity so we get access to entity methods and fields.
// abstract so we don't have to implement interfaces, constructor is not used but required.
public abstract class ShieldMixin extends EntityLiving {
	public ShieldMixin(World world) {
		super(world);
	}

	// shadows allow us to access variables in a class we are mixing to.
	@Shadow
	public InventoryPlayer inventory;

	@Shadow
	public abstract boolean hurt(Entity attacker, int damage, DamageType type);

	@Shadow
	public Gamemode gamemode;


	@Shadow
	public abstract ItemStack getHeldItem();

	@Shadow
	public abstract void addStat(Stat statbase, int i);


	@Shadow
	public abstract void triggerAchievement(Stat statbase);

	@Shadow
	private ChunkCoordinates playerSpawnCoordinate;

	// inject at the top(HEAD) of hurt(), allow us to call return(cancel/set return value)
	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	public void injectHurt(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> ci) {
		if (attacker instanceof EntityMonster || attacker instanceof EntityArrow) {
			if (this.world.difficultySetting == 0) {
				damage = 0;
			}

			if (this.world.difficultySetting == 1) {
				damage = damage / 3 + 1;
			}

			if (this.world.difficultySetting == 3) {
				damage = damage * 3 / 2;
			}
		}

		// check if we are holding the shield item.
		ItemStack stack = inventory.mainInventory[inventory.currentItem];
		if (stack != null) {
			if (stack.getItem() instanceof ShieldItem) {

				ShieldItem shield = ((ShieldItem) stack.getItem());
				{
					if (attacker != null) {
						World world = attacker.world;

						if (!this.gamemode.isPlayerInvulnerable()) {
							if(shield.tool == ShieldMaterials.TOOL_TEAR && getHealth() <= 6){
								damage = Math.round(damage * 0.5f);
							}
							if (stack.getData().getBoolean("active")) {
								int newDamage = Math.round(damage * (shield.tool.getEfficiency(true)));

								double _dx = attacker.x - this.x;
								double _dz = attacker.z - this.z;
								double length = Math.hypot(_dx, _dz);
								_dx /= length;
								_dz /= length;

								if (shield.tool == ShieldMaterials.TOOL_LEATHER){
									attacker.push(_dx * 1.2 ,0.75,_dz * 1.2);
									//addStat(ShieldAchievements.FLY_HIGH, 1);
								}
								if(shield.tool == ShieldMaterials.TOOL_GOLD){
									attacker.hurt(attacker, newDamage, type);
									//addStat(ShieldAchievements.GOLD_RETAL, 1);
								}
								super.hurt(attacker, newDamage, type);
									//addStat(ShieldAchievements.BLOCK, 1);

								//if( damage > 20 && isAlive()){
									//addStat(ShieldAchievements.INVINCIBLE, 1);
								//}

								world.playSoundAtEntity(attacker,
									attacker, ("mob.ghast.fireball"),
									1.0F,
									9.0F + world.rand.nextFloat()
								);
								// create a cloud of particles
								float width = 1.0f;
								for (int i = 0; i < 20; ++i) {
									double dx = world.rand.nextGaussian() * 0.02;
									double dy = world.rand.nextGaussian() * 0.02;
									double dz = world.rand.nextGaussian() * 0.02;
									world.spawnParticle(
										"snowshovel",
										attacker.x + (double) (world.rand.nextFloat() * width * 2.0F) - (double) width,
										attacker.y - attacker.bbHeight + (double) (world.rand.nextFloat() * width),
										attacker.z + (double) (world.rand.nextFloat() * width * 2.0F) - (double) width,
										dx, dy, dz, 0
									);
								}

								stack.damageItem(4, inventory.player);
							} else {
								super.hurt(attacker, damage, type);
							}
						}
						ci.setReturnValue(false);
					}
				}
			}
		}
	}

}
