package turniplabs.shieldmod.mixins;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.shieldmod.ShieldMod;
import turniplabs.shieldmod.item.ShieldItem;
import turniplabs.shieldmod.item.ShieldMaterials;


// mixin to EntityPlayer, do not remap(forgot what remap does)
@Mixin(value = EntityPlayer.class, remap = false)

// extend Entity so we get access to entity methods and fields.
// abstract so we don't have to implement interfaces, constructor is not used but required.
public abstract class ShieldMixin extends Entity {
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



	// inject at the top(HEAD) of hurt(), allow us to call return(cancel/set return value)
	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	public void injectHurt(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> ci) {

		// check if we are holding the shield item.
		ItemStack stack = inventory.mainInventory[inventory.currentItem];
		if (stack != null) {
			if (stack.getItem() instanceof ShieldItem) {
				ShieldItem shield = ((ShieldItem) stack.getItem());
				{
					if (attacker != null) {
						World world = attacker.world;

						int newDamage = Math.round(damage * (shield.tool.getEfficiency(true)));
						if (!this.gamemode.isPlayerInvulnerable()) {
							if (stack.getData().getBoolean("active")) {
								//if (shield.tool == ShieldMaterials.TOOL_WOOD){
								//	attacker.push(1, 1, 1);
								//}
								super.hurt(attacker, newDamage, type);
								world.playSoundAtEntity(
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
										dx, dy, dz
									);
								}
								stack.damageItem(4, inventory.player);
							} else {
								super.hurt(attacker, damage, type);
							}
						}


						// yeet attacker
						//attacker.push(world.rand.nextFloat(), world.rand.nextFloat(), world.rand.nextFloat());
						// play some sound

					}
					ci.setReturnValue(false);
				}
			}
		}
	}

}
