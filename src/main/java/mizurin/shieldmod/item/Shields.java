package mizurin.shieldmod.item;

import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ItemBuilder;
import mizurin.shieldmod.ShieldMod;
import org.slf4j.Logger;
import net.minecraft.core.item.material.ArmorMaterial;

import static mizurin.shieldmod.ShieldMod.MOD_ID;
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

	public static Item armorLeatherHelmet;
	public static Item armorLeatherChest;
	public static Item armorLeatherLeg;
	public static Item armorLeatherBoot;


	@SuppressWarnings("unchecked")
	public void initializeItems(){
		woodenShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.setItemModel(item -> new ItemModelShield(item, new ItemModelColored.ColoredTextureEntry[]
				{
					new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/wooden_shield"), (s) -> -1)
				}).setFull3D())
			.build(new LightShield("wooden.shield", ++itemID, ShieldMaterials.TOOL_WOOD))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		stoneShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.setItemModel(item -> new ItemModelShield(item, new ItemModelColored.ColoredTextureEntry[]
				{
					new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/stone_shield"), (s) -> -1)
				}).setFull3D())
			.build(new ShieldItem("stone.shield", ++itemID, ShieldMaterials.TOOL_STONE))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		ironShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.setItemModel(item -> new ItemModelShield(item, new ItemModelColored.ColoredTextureEntry[]
				{
					new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/iron_shield"), (s) -> -1)
				}).setFull3D())
			.build(new ShieldItem("iron.shield", ++itemID, ShieldMaterials.TOOL_IRON))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		goldShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.setItemModel(item -> new ItemModelShield(item, new ItemModelColored.ColoredTextureEntry[]
				{
					new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/gold_shield"), (s) -> -1)
				}).setFull3D())
			.build(new ShieldItem("gold.shield", ++itemID, ShieldMaterials.TOOL_GOLD))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		diamondShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.setItemModel(item -> new ItemModelShield(item, new ItemModelColored.ColoredTextureEntry[]
				{
					new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/diamond_shield"), (s) -> -1)
				}).setFull3D())
			.build(new ShieldItem("diamond.shield", ++itemID, ShieldMaterials.TOOL_DIAMOND))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		steelShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.setItemModel(item -> new ItemModelShield(item, new ItemModelColored.ColoredTextureEntry[]
				{
					new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/steel_shield"), (s) -> -1)
				}).setFull3D())
			.build(new ShieldItem("steel.shield", ++itemID, ShieldMaterials.TOOL_STEEL))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		leatherShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.setItemModel(item -> new ItemModelShield(item, new ItemModelColored.ColoredTextureEntry[]
			{
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture(MOD_ID + ":item/colored"), ItemModelShield::shieldColor),
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture(MOD_ID + ":item/outline"), ItemModelShield::shieldColor)
			}).setFull3D())
			.build(new ShieldColored("leather.shield", ++itemID, ShieldMaterials.TOOL_LEATHER))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		tearShield = new ItemBuilder(MOD_ID)
			.setItemModel(item -> new ItemModelShield(item, new ItemModelColored.ColoredTextureEntry[]
				{
					new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/tearstone_shield"), (s) -> -1)
				}).setFull3D())
			.build(new ThrowShield("tear.shield", ++itemID, ShieldMaterials.TOOL_TEAR))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		ammotearShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.setItemModel(item -> new ItemModelShield(item, new ItemModelColored.ColoredTextureEntry[]
				{
					new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/ammotearstone_shield"), (s) -> -1)
				}).setFull3D())
			.build(new Item("tear.shield.ammo", ++itemID))
			.withTags(ItemTags.NOT_IN_CREATIVE_MENU);

		armorLeatherHelmet = new ItemBuilder(MOD_ID).build(new ArmorColored("armor.helmet.leather", 16426, ArmorMaterial.LEATHER, 0));
		armorLeatherChest = new ItemBuilder(MOD_ID).build(new ArmorColored("armor.chestplate.leather", 16427, ArmorMaterial.LEATHER, 1));
		armorLeatherLeg = new  ItemBuilder(MOD_ID).build(new ArmorColored("armor.leggings.leather", 16428, ArmorMaterial.LEATHER, 2));
		armorLeatherBoot = new ItemBuilder(MOD_ID).build(new ArmorColored("armor.boots.leather", 16429, ArmorMaterial.LEATHER, 3));
	}
}
