package mizurin.shieldmod.compat.aether.item;

import mizurin.shieldmod.item.ShieldMaterials;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import turniplabs.halplibe.helper.ItemBuilder;

import static mizurin.shieldmod.ShieldMod.MOD_ID;
import static mizurin.shieldmod.ShieldMod.itemID;

public class RFAetherItems {

	public static Item valkyrieShield;
	public static Item holystoneShield;

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
		valkyrieShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new ValkyrieShield("valkyrie_shield", "shieldmod:item/valkyrie_shield", ++itemID, ShieldMaterials.TOOL_VALK))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);

		holystoneShield = new ItemBuilder(MOD_ID)
			.setStackSize(1)
			.build(new HolystoneShield("holystone_shield", "shieldmod:item/disc_shield"  ,++itemID, ShieldMaterials.TOOL_STONE))
			.withTags(ItemTags.PREVENT_CREATIVE_MINING);
	}
}
