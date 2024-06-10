package mizurin.shieldmod.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ItemHelper;
import mizurin.shieldmod.ShieldMod;
import org.slf4j.Logger;
import net.minecraft.core.item.material.ArmorMaterial;
import static mizurin.shieldmod.ShieldMod.itemID;

public class Shields {
	public static final String MOD_ID = ShieldMod.MOD_ID;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Item woodenShield = ItemHelper.createItem(MOD_ID, new LightShield("wooden.shield", ++itemID, ShieldMaterials.TOOL_WOOD),"wooden_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item stoneShield = ItemHelper.createItem(MOD_ID, new ShieldItem("stone.shield", ++itemID, ShieldMaterials.TOOL_STONE), "stone_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item ironShield = ItemHelper.createItem(MOD_ID, new ShieldItem("iron.shield", ++itemID, ShieldMaterials.TOOL_IRON), "iron_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item goldShield = ItemHelper.createItem(MOD_ID, new ShieldItem("gold.shield", ++itemID, ShieldMaterials.TOOL_GOLD), "gold_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item diamondShield = ItemHelper.createItem(MOD_ID, new ShieldItem("diamond.shield", ++itemID, ShieldMaterials.TOOL_DIAMOND), "diamond_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item steelShield = ItemHelper.createItem(MOD_ID, new ShieldItem("steel.shield", ++itemID, ShieldMaterials.TOOL_STEEL), "steel_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item leatherShield = ItemHelper.createItem(MOD_ID, new ShieldColored("leather.shield", ++itemID, ShieldMaterials.TOOL_LEATHER), "wooden_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item tearShield = ItemHelper.createItem(MOD_ID, new ThrowShield("tear.shield", ++itemID, ShieldMaterials.TOOL_TEAR), "tearstone_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item ammotearShield = ItemHelper.createItem(MOD_ID, new Item("tear.shield.ammo", ++itemID), "ammotearstone_shield.png").setNotInCreativeMenu();

	public static final Item armorLeatherHelmet = ItemHelper.createItem(MOD_ID, new ArmorColored("Leather Cap", 16426,ArmorMaterial.LEATHER , 0 ), "armor.helmet.leather");
	public static final Item armorLeatherChest = ItemHelper.createItem(MOD_ID, new ArmorColored("Leather Tunic", 16427,ArmorMaterial.LEATHER , 1 ), "armor.chestplate.leather");
	public static final Item armorLeatherLeg = ItemHelper.createItem(MOD_ID, new ArmorColored("Leather Pants", 16428,ArmorMaterial.LEATHER , 2 ), "armor.leggings.leather");
	public static final Item armorLeatherBoot = ItemHelper.createItem(MOD_ID, new ArmorColored("Leather Boots", 16429,ArmorMaterial.LEATHER , 3 ), "armor.boots.leather");

	public void initializeItems(){}
}
