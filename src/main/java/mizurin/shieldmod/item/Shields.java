package mizurin.shieldmod.item;

import mizurin.shieldmod.ShieldMod;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemSoup;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.tag.ItemTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.ItemBuilder;

import static mizurin.shieldmod.ShieldMod.itemID;

public class Shields {
	public static final String MOD_ID = ShieldMod.MOD_ID;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Item woodenShield;
	public static Item stoneShield;
	public static Item ironShield;
	public static Item goldShield;
	public static Item diamondShield;
	public static Item steelShield;
	public static Item leatherShield;
	public static Item tearShield;
	public static Item ammotearShield;
	public static Item pumpkinStew;

	public static Item armorLeatherHelmet;
	public static Item armorLeatherChest;
	public static Item armorLeatherLeg;
	public static Item armorLeatherBoot;
	public static Item poisonBottle;
	public static Item rockyHelmet;
	public static Item regenAmulet;

	public static ArmorMaterial rockyArmor = ArmorHelper.createArmorMaterial(MOD_ID, "armor_stone", 256, 30f, 30f, 30f, 30f);
	public static ArmorMaterial heartAmulet = ArmorHelper.createArmorMaterial(MOD_ID, "armor_stone", 384, 0f, 0f, 0f, 0f);


	@SuppressWarnings("unchecked")
	//Initialize items at main.
	public void initializeItems(){
		woodenShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ShieldItem("wooden_shield", "shieldmod:item/wooden_shield_test", ++itemID, ShieldMaterials.TOOL_WOOD))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		stoneShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ShieldItem("stone_shield", "shieldmod:item/stone_shield_test",++itemID, ShieldMaterials.TOOL_STONE))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		ironShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ShieldItem("iron_shield", "shieldmod:item/iron_shield_test"  ,++itemID, ShieldMaterials.TOOL_IRON))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		goldShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ParryShield("gold_shield", "shieldmod:item/gold_shield_test" ,++itemID, ShieldMaterials.TOOL_GOLD))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		diamondShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new TreasureShield("diamond_shield", "shieldmod:item/diamond_shield_test" ,++itemID, ShieldMaterials.TOOL_DIAMOND))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		steelShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new SteelShield("steel_shield", "shieldmod:item/steel_shield_test" ,++itemID, ShieldMaterials.TOOL_STEEL))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		leatherShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ShieldColored("leather_shield", "shieldmod:item/leather_shield_test" ,++itemID, ShieldMaterials.TOOL_LEATHER))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		tearShield = new ItemBuilder(MOD_ID)
			.build(new ThrowShield("tear_shield", "shieldmod:item/tearstone_shield_test" ,++itemID, ShieldMaterials.TOOL_TEAR))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		ammotearShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new Item("tear_shield_ammo", MOD_ID + ":tear_shield_ammo", ++itemID))
			.withTags(ItemTags.NOT_IN_CREATIVE_MENU);


		pumpkinStew = new ItemBuilder(MOD_ID)
			.build(new ItemSoup("food_stew_pumpkin", MOD_ID + ":food_stew_pumpkin" ,++itemID, 20, 50));


		// TODO need to bypass vanilla id check
//		armorLeatherHelmet = new ItemBuilder(MOD_ID)
//			.build(new ArmorColored("armor_helmet_leather", MOD_ID + ":armor_helmet_leather" ,16426, ArmorMaterial.LEATHER, 0));
//
//
//		armorLeatherChest = new ItemBuilder(MOD_ID)
//			.build(new ArmorColored("armor_chestplate_leather", MOD_ID + ":armor_chestplate_leather" ,16427, ArmorMaterial.LEATHER, 1));
//
//
//		armorLeatherLeg = new  ItemBuilder(MOD_ID)
//			.build(new ArmorColored("armor_leggings_leather", MOD_ID + ":armor_leggings_leather" ,16428, ArmorMaterial.LEATHER, 2));
//
//
//		armorLeatherBoot = new ItemBuilder(MOD_ID)
//			.build(new ArmorColored("armor_boots_leather", MOD_ID + ":armor_boots_leather" ,16429, ArmorMaterial.LEATHER, 3));


		poisonBottle = new ItemBuilder(MOD_ID).setStackSize(16).build(new ItemPB("poison_bottle", MOD_ID + ":poison_bottle" ,++itemID));


		rockyHelmet = new ItemBuilder(MOD_ID)
			.build(new ItemArmor("helmet_rock", MOD_ID + ":helmet_rock" ,++itemID,  rockyArmor, 0));


		regenAmulet = new ItemBuilder(MOD_ID)
			.build(new ItemArmor("amulet_heart", MOD_ID + ":amulet_heart" ,++itemID, heartAmulet, 1));
	}
}
