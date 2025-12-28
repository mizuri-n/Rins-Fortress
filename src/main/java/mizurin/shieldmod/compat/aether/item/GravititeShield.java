package mizurin.shieldmod.compat.aether.item;

import mizurin.shieldmod.effects.ShieldEffects;
import mizurin.shieldmod.item.ShieldItem;
import mizurin.shieldmod.item.ShieldMaterials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.MobUtil;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.item.AetherItemTags;
import teamport.aether.item.accessory.IAccessory;

public class GravititeShield extends ShieldItem implements AetherHasCustomDamageType, IAccessory, IArmorItem {
	public final String name;
	public final ArmorMaterial material;
	public final float knockbackStrength;
	public GravititeShield(String translationKey, String namespaceID, int id, ShieldMaterials shieldMaterials, String name, ArmorMaterial material) {
		super(translationKey, namespaceID, id, shieldMaterials);
		this.name = name;
		this.material = material;
		this.knockbackStrength = 1.0F;
		this.withTags(AetherItemTags.TRINKET);
	}
	@Override
	public boolean hitEntity(ItemStack itemstack, Mob target, Mob player) {
		if ((target.hurtTime == 10 || target instanceof Player) && target.onGround) {
			MobUtil.knockback(target, player, 0.4F, 0.5F);
		}
		if ((target.hurtTime == 10 || target instanceof Player) && !target.onGround) {
			ShieldEffects.add(target, ShieldEffects.strongSlowEffect, 1, 200);
		}
		itemstack.damageItem(1, player);

		return true;
	}

	@Override
	public int getDamageVsEntity(Entity entity, ItemStack is) {
		int damage = super.getDamageVsEntity(entity, is);
		if(!entity.onGround){
			damage += 5;
		}
		return damage;
	}

	public DamageType getDamageType() {
		return DamageType.FALL;
	}

	@Override
	public @Nullable ArmorMaterial getArmorMaterial() {
		return this.material;
	}

	@Override
	public int getArmorPiece() {
		return 0;
	}

	@Override
	public String name() {
		return name;
	}
	@Override
	public int armorPieceProtection() {
		return 1;
	}

	@Override
	public float getArmorPieceProtectionPercentage() {
		return (float) this.armorPieceProtection() / 20.0F;
	}
}
