package mizurin.shieldmod.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ItemHelper;
import mizurin.shieldmod.ShieldMod;
import org.slf4j.Logger;

public class Shields {
	public static final String MOD_ID = ShieldMod.MOD_ID;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Item leatherShield = ItemHelper.createItem(MOD_ID, new ShieldColored("leather.shield", 17006, ShieldMaterials.TOOL_LEATHER), "wooden_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item woodenShield = ItemHelper.createItem(MOD_ID, new ShieldItem("wooden.shield", 17000, ShieldMaterials.TOOL_WOOD),"wooden_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item stoneShield = ItemHelper.createItem(MOD_ID, new ShieldItem("stone.shield", 17001, ShieldMaterials.TOOL_STONE), "stone_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item ironShield = ItemHelper.createItem(MOD_ID, new ShieldItem("iron.shield", 17002, ShieldMaterials.TOOL_IRON), "iron_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item goldShield = ItemHelper.createItem(MOD_ID, new ShieldItem("gold.shield", 17003, ShieldMaterials.TOOL_GOLD), "gold_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item diamondShield = ItemHelper.createItem(MOD_ID, new ShieldItem("diamond.shield", 17004, ShieldMaterials.TOOL_DIAMOND), "diamond_shield.png").withTags(ItemTags.preventCreativeMining);
	public static final Item steelShield = ItemHelper.createItem(MOD_ID, new ShieldItem("steel.shield", 17005, ShieldMaterials.TOOL_STEEL), "steel_shield.png").withTags(ItemTags.preventCreativeMining);

	public void initializeItems(){}
}
