package mizurin.shieldmod.item;

import mizurin.shieldmod.ShieldMod;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemSoup;
import net.minecraft.core.item.Items;
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
			.build(new ParryShield("gold_shield", "shieldmod:item/gold_shield" ,++itemID, ShieldMaterials.TOOL_GOLD))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		diamondShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new TreasureShield("diamond_shield", "shieldmod:item/diamond_shield" ,++itemID, ShieldMaterials.TOOL_DIAMOND))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		steelShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new SteelShield("steel_shield", "shieldmod:item/steel_shield" ,++itemID, ShieldMaterials.TOOL_STEEL))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		leatherShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ShieldColored("leather_shield", "shieldmod:item/leather_shield" ,++itemID, ShieldMaterials.TOOL_LEATHER))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		tearShield = new ItemBuilder(MOD_ID)
			.build(new ThrowShield("tear_shield", "shieldmod:item/tearstone_shield" ,++itemID, ShieldMaterials.TOOL_TEAR))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);


		ammotearShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new Item("tear_shield_ammo", MOD_ID + ":tear_shield_ammo", ++itemID))
			.withTags(ItemTags.NOT_IN_CREATIVE_MENU);


		pumpkinStew = new ItemBuilder(MOD_ID)
			.build(new ItemSoup("food_stew_pumpkin", MOD_ID + ":food_stew_pumpkin" ,++itemID, 20, 50));


		poisonBottle = new ItemBuilder(MOD_ID).setStackSize(16).build(new ItemPB("poison_bottle", MOD_ID + ":poison_bottle" ,++itemID));


		rockyHelmet = new ItemBuilder(MOD_ID)
			.build(new ItemArmor("helmet_rock", MOD_ID + ":helmet_rock" ,++itemID,  rockyArmor, 0));


		regenAmulet = new ItemBuilder(MOD_ID)
			.build(new ItemArmor("amulet_heart", MOD_ID + ":amulet_heart" ,++itemID, heartAmulet, 1));
	}

	public static void replaceVanillaItems() {
		Item.itemsList[Items.ARMOR_HELMET_LEATHER.id] = null;
		Item.itemsMap.remove(Items.ARMOR_HELMET_LEATHER.namespaceID);
		Items.ARMOR_HELMET_LEATHER = new ItemBuilder(MOD_ID)
			.build(new ArmorColored("armor_helmet_leather", Items.ARMOR_HELMET_LEATHER.namespaceID.toString() ,Items.ARMOR_HELMET_LEATHER.id, ArmorMaterial.LEATHER, IArmorItem.PIECE_HEAD));

		Item.itemsList[Items.ARMOR_CHESTPLATE_LEATHER.id] = null;
		Item.itemsMap.remove(Items.ARMOR_CHESTPLATE_LEATHER.namespaceID);
		Items.ARMOR_CHESTPLATE_LEATHER = new ItemBuilder(MOD_ID)
			.build(new ArmorColored("armor_chestplate_leather", Items.ARMOR_CHESTPLATE_LEATHER.namespaceID.toString() ,Items.ARMOR_CHESTPLATE_LEATHER.id, ArmorMaterial.LEATHER, IArmorItem.PIECE_CHEST));


		Item.itemsList[Items.ARMOR_LEGGINGS_LEATHER.id] = null;
		Item.itemsMap.remove(Items.ARMOR_LEGGINGS_LEATHER.namespaceID);
		Items.ARMOR_LEGGINGS_LEATHER = new  ItemBuilder(MOD_ID)
			.build(new ArmorColored("armor_leggings_leather", Items.ARMOR_LEGGINGS_LEATHER.namespaceID.toString() ,Items.ARMOR_LEGGINGS_LEATHER.id, ArmorMaterial.LEATHER, IArmorItem.PIECE_LEGS));


		Item.itemsList[Items.ARMOR_BOOTS_LEATHER.id] = null;
		Item.itemsMap.remove(Items.ARMOR_BOOTS_LEATHER.namespaceID);
		Items.ARMOR_BOOTS_LEATHER = new ItemBuilder(MOD_ID)
			.build(new ArmorColored("armor_boots_leather", Items.ARMOR_BOOTS_LEATHER.namespaceID.toString() ,Items.ARMOR_BOOTS_LEATHER.id, ArmorMaterial.LEATHER, IArmorItem.PIECE_BOOTS));
	}
}
