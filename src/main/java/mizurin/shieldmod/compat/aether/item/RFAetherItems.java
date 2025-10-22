package mizurin.shieldmod.compat.aether.item;

import mizurin.shieldmod.item.ShieldItem;
import mizurin.shieldmod.item.ShieldMaterials;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import turniplabs.halplibe.helper.ItemBuilder;

import static mizurin.shieldmod.ShieldMod.MOD_ID;
import static mizurin.shieldmod.ShieldMod.itemID;

public class RFAetherItems {

	public static Item valkyrieShield;
	public static Item skyrootShield;
	public static Item holystoneShield;
	public static Item zaniteShield;
	public static Item gravititeShield;

	private static boolean hasInit = false;

	@SuppressWarnings("unused")
	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeAetherItems();
		}
	}

	@SuppressWarnings("unchecked")
	public static void initializeAetherItems() {

		skyrootShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ShieldItem("skyroot_shield", "shieldmod:item/skyroot_shield"  ,++itemID, ShieldMaterials.TOOL_SKY))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		holystoneShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new HolystoneShield("holystone_shield", "shieldmod:item/disc_shield"  ,++itemID, ShieldMaterials.TOOL_HOLYSTONE))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		zaniteShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ZaniteShield("zanite_shield", "shieldmod:item/zanite_shield"  ,++itemID, ShieldMaterials.TOOL_ZANITE))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		gravititeShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ZaniteShield("gravitite_shield", "shieldmod:item/gravitite_shield"  ,++itemID, ShieldMaterials.TOOL_IRON))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		valkyrieShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ValkyrieShield("valkyrie_shield", "shieldmod:item/valkyrie_shield", ++itemID, ShieldMaterials.TOOL_VALK))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);
	}
}
