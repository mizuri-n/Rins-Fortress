package mizurin.shieldmod.mixins;

import mizurin.shieldmod.entities.*;
import mizurin.shieldmod.interfaces.IDazed;
import mizurin.shieldmod.interfaces.ParryInterface;
import mizurin.shieldmod.item.ShieldItem;
import mizurin.shieldmod.item.ShieldMaterials;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.*;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;


// mixin to EntityPlayer, do not remap(forgot what remap does)
@Mixin(value = EntityPlayer.class, remap = false)

// extend Entity so we get access to entity methods and fields.
// abstract so we don't have to implement interfaces, constructor is not used but required.
public abstract class ShieldMixin extends EntityLiving implements ParryInterface{
	public ShieldMixin(World world) {
		super(world);
	}

	@Unique
	private static final int DATA_BLOCKING = 23;
	@Shadow
	public InventoryPlayer inventory;
	@Shadow
	public abstract boolean hurt(Entity attacker, int damage, DamageType type);
	@Shadow
	public Gamemode gamemode;
	@Shadow
	public abstract ItemStack getHeldItem();
	@Unique
	public EntityLiving thisObject = (EntityLiving) (Object) this;
	@Shadow
	public abstract int getMaxHealth();
	@Unique
	private int parryTicks;
	@Unique
	private int blockTicks;
	@Unique
	private int counterTicks;
	@Unique
	private int fireTicks;

	@Inject(method = "init", at = @At("TAIL"))
	public void defineSynchedData(CallbackInfo ci) {
		entityData.define(DATA_BLOCKING, (byte)0);
	}


	@Override
	public void shieldmod$Parry(int parryTicks) {
		this.parryTicks = parryTicks;
	}
	@Override
	public int shieldmod$getParryTicks() {
		return parryTicks;
	}
	@Override
	public int shieldmod$getBlockTicks() {
		return blockTicks;
	}
	@Override
	public void shieldmod$Block(int blockTicks) {
		this.blockTicks = blockTicks;
	}
	@Override
	public boolean shieldmod$getIsBlock() {
		return entityData.getByte(DATA_BLOCKING) != 0;
	}
	@Override
	public void shieldmod$setIsBlock(boolean bool) {
        this.entityData.set(DATA_BLOCKING, bool ? (byte)1 : (byte)0);
    }
	@Override
	public int shieldmod$getCounterTicks(){
		return counterTicks;
	}
	@Override
	public void shieldmod$Counter(int counterTicks){
		this.counterTicks = counterTicks;
	}
	@Override
	public int shieldmod$getFireTicks(){
		return fireTicks;
	}
	@Override
	public void shieldmod$Fire(int fireTicks){
		this.fireTicks = fireTicks;
	}
	//programmer note. I want to die after this lmao.

	@Unique
	public void parryHitbox(World world, EntityPlayer player) {

		double bound = 3.75;
		AABB aabb1 = new AABB(
			player.x - bound,
			player.y + player.getHeadHeight() - bound,
			player.z - bound,
			player.x + bound,
			player.y + player.getHeadHeight() + bound,
			player.z + bound
		);


		List<Entity> projectileList = player.world.getEntitiesWithinAABB(EntityProjectile.class, aabb1);
		for (Entity entity : projectileList) {
			world.spawnParticle("largesmoke", entity.x, entity.y, entity.z, 0.0, 0.0, 0.0, 0);
			if (entity instanceof EntityArrow) {
				if (((EntityArrow) entity).isGrounded()) {
					return;
				}
			}

			if (entity instanceof EntityArrow) {

				if (!((EntityArrow) entity).isGrounded()) {
					entity.remove();
					EntityArrow newArrow = new EntityArrow(world, player, false, ((EntityArrow) entity).getArrowType());
					newArrow.damage += 3;
					if (!world.isClientSide) {
						//For any devs looking at my code. This if statement (!world.isClientSide) is used for server compatibility, please use it when spawning items.
						//In this case, failure to do so can cause the player to parry their own clientside projectiles that do not disappear.
						world.entityJoinedWorld(newArrow);
						world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
					}
				}
			}

			if (entity instanceof EntityFireball) {
				entity.hurt(player, 1, DamageType.COMBAT);
				world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
			}

			if (entity instanceof EntityCannonball) {

				double oldCBX = entity.x;
				double oldCBY = entity.y;
				double oldCBZ = entity.z;

				entity.remove();
				EntityCannonball newCB = new EntityCannonball(world, player);
				if (!world.isClientSide) {
					world.entityJoinedWorld(newCB);
					newCB.setPos(oldCBX, oldCBY, oldCBZ);
					double pushX = player.getLookAngle().xCoord;
					double pushY = player.getLookAngle().yCoord;
					double pushZ = player.getLookAngle().zCoord;
					newCB.push(pushX * 1.2, pushY * 1.2, pushZ * 1.2);
					world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
				}
			}
			if (entity instanceof EntitySnowball) {

				entity.remove();
				EntitySnowball newSB = new EntitySnowball(world, player);
				newSB.damage +=1;
				if (!world.isClientSide) {
					world.entityJoinedWorld(newSB);
					world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
				}
			}
			if (entity instanceof EntityShield) {

				entity.remove();
				EntityShield newTS = new EntityShield(world, player);
				newTS.damage += 2;
				if (!world.isClientSide) {
					world.entityJoinedWorld(newTS);
					world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
				}
			}
			if (entity instanceof EntityPB) {

				entity.remove();
				EntityPB newPS = new EntityPB(world, player);
				newPS.damage +=1;
				if (!world.isClientSide) {
					world.entityJoinedWorld(newPS);
					world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
				}
			}
			if (entity instanceof EntityWeb) {

				entity.remove();
				EntityWeb newWB = new EntityWeb(world, player);
				newWB.damage +=1;
				if (!world.isClientSide) {
					world.entityJoinedWorld(newWB);
					world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
				}
			}
			if (entity instanceof EntityIceBall) {

				entity.remove();
				EntityIceBall newIB = new EntityIceBall(world, player);
				newIB.damage +=1;
				if (!world.isClientSide) {
					world.entityJoinedWorld(newIB);
					world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.66f, 1.0f);
				}
			}

		}

		player.swingItem();
		world.playSoundAtEntity(player, player, "mob.ghast.fireball", 0.3f, 1.0f);
	}

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
		//check if we are wearing the helmet.
		ItemStack helmet_item = this.inventory.armorItemInSlot(3);
		if(attacker != null) { //need this or you will get a null pointer.
			if (!this.gamemode.isPlayerInvulnerable()) {
				if ((helmet_item != null && helmet_item.getItem().equals(Shields.rockyHelmet)) && attacker != this) {
					attacker.hurt(attacker, 2, DamageType.COMBAT);
				}
			}
		}

		if (stack != null) {
			if (stack.getItem() instanceof ShieldItem) {

				ShieldItem shield = ((ShieldItem) stack.getItem());
				{
					if (attacker != null) {
						World world = attacker.world;

						if (!this.gamemode.isPlayerInvulnerable()) {
							if(shield.tool == ShieldMaterials.TOOL_TEAR && getHealth() <= getMaxHealth() * 0.3){
								damage = Math.round(damage * 0.5f);
							}
							//tear shield provides a damage resistance when the player is low health. it can stack with blocking too.
							if (shieldmod$getIsBlock()) {
								//checks if the player is blocking to apply the damage resistance.

								int newDamage = Math.round(damage * (shield.tool.getEfficiency(true)));

								double _dx = attacker.x - this.x;
								double _dz = attacker.z - this.z;
								double length = Math.hypot(_dx, _dz);
								_dx /= length;
								_dz /= length;


								if (shield.tool == ShieldMaterials.TOOL_LEATHER && attacker != this){
									attacker.push(_dx * 1.2 ,0.65,_dz * 1.2);
									//applies funny knockback to attack when hit.
								}
								if (shield.tool == ShieldMaterials.TOOL_STONE && attacker != this){
									if ((helmet_item != null && helmet_item.getItem().equals(Shields.rockyHelmet))) {
										attacker.hurt(attacker, 5, DamageType.FALL);
									} else {
										attacker.hurt(this, 3, DamageType.FALL);
									}
									if (!world.isClientSide) {
										world.entityJoinedWorld(new EntityRock(world, this));
									}
									//stone shield works similarly to the rocky helmet, but it also spawns a pebble.
								}

								if (shield.tool == ShieldMaterials.TOOL_DIAMOND){
									shieldmod$Counter(20);
									//adds the ticks(Blocked) when hit.
								}
								super.hurt(attacker, newDamage, type);


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
						ci.setReturnValue(true);
					}
				}
			}
		}
	}
	@Unique
	private int tickCounter = 0;

	@Unique
	private int parryDelay = 0;
	@Inject(
		method = "onLivingUpdate()V",
		at = @At(value = "HEAD")
	)
	public void tickMixin(CallbackInfo ci){
		ItemStack stack = inventory.mainInventory[inventory.currentItem];
		if (stack != null) {
			if (stack.getItem() instanceof ShieldItem) {

				ShieldItem shield = ((ShieldItem) stack.getItem());
				if (shieldmod$getIsBlock() && (shield.tool == ShieldMaterials.TOOL_LEATHER || shield.tool == ShieldMaterials.TOOL_WOOD)) {
					this.xd *= 0.65D;
					this.zd *= 0.65D;
				}
				else if (shieldmod$getIsBlock() && shield.tool == ShieldMaterials.TOOL_DIAMOND){
					this.xd *= 0.20D;
					this.zd *= 0.20D;
				}
				else if (shieldmod$getIsBlock()){
					this.xd *= 0.4D;
					this.zd *= 0.4D;
				}

					if (blockTicks > 0) {
						blockTicks--;
					} else {
						shieldmod$setIsBlock(false);
					}
				if(this.fireTicks > 0) {
					this.fireTicks--;
				}
				if(this.counterTicks > 0) {
					this.counterTicks--;
				}

			}
		}
		ItemStack helmet_item = this.inventory.armorItemInSlot(2);
		if ((helmet_item != null && helmet_item.getItem().equals(Shields.regenAmulet))) {
			++this.tickCounter;
			if (this.tickCounter >= 600) {
				this.tickCounter = 0;
				this.heal(1);
			}

		}
		if(this.parryTicks != 0) {
			this.parryTicks--;
		}
		if(parryDelay > 0){
			parryDelay--;
		}

		if(this.parryTicks == 18 && thisObject instanceof EntityPlayer && parryDelay == 0){
			parryHitbox(thisObject.world, (EntityPlayer)thisObject);
			parryDelay = 30;
		}


	}
}
