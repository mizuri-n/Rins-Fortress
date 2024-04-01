package mizurin.shieldmod.item;

import mizurin.shieldmod.ShieldAchievements;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.World;

public class ShieldItem extends ItemToolSword {
	public ToolMaterial tool;
	public int weaponDamage;
	private static final int ticksToAdd = 5;


	public ShieldItem(String name, int id, ToolMaterial toolMaterial){
		super(name, id, toolMaterial);
		maxStackSize = 1;
		setMaxDamage(toolMaterial.getDurability());
		this.tool = toolMaterial;
		this.weaponDamage = 4 + toolMaterial.getDamage();


	}
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving target, EntityLiving player) {
		if(itemstack.getItem() == Shields.leatherShield){
			target.knockBack(player, 1, (player.x - target.x), (player.z - target.z ));
			target.push((target.x - player.x)/7, 0, (target.z - player.z)/7);
		} else {
			target.knockBack(player, 3, player.x - target.x, player.z - target.z);
		}
		itemstack.damageItem(1, player);

		return true;
	}
	public int getDamageVsEntity(Entity entity) {
		return this.weaponDamage;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		itemstack.getData().putBoolean("active", true);
		itemstack.getData().putInt("ticks",ticksToAdd);
		return itemstack;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
		if(itemstack.getData().getBoolean("active")){
			entity.xd *= 0.4D;
			entity.zd *= 0.4D;
			int ticks = itemstack.getData().getInteger("ticks");

			if (ticks > 0){
				itemstack.getData().putInt("ticks", ticks - 1);
			} else {
				itemstack.getData().putBoolean("active", false);
			}
		}
		//if (itemstack != null && itemstack.getItem() instanceof ShieldItem) {
		//	EntityPlayer thePlayer = Minecraft.getMinecraft(this).thePlayer;
		//	thePlayer.triggerAchievement(ShieldAchievements.SHIELD_GOT);
		//	ShieldItem shield = ((ShieldItem) itemstack.getItem());
		//	if(shield.tool == ShieldMaterials.TOOL_STEEL){
		//		thePlayer.triggerAchievement(ShieldAchievements.MODERN_AGE);
		//	}
		//}
	}
}
