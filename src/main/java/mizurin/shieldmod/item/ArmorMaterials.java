package mizurin.shieldmod.item;

import net.minecraft.core.item.material.ArmorMaterial;
import turniplabs.halplibe.helper.ArmorHelper;

import static mizurin.shieldmod.item.RFItems.MOD_ID;

public class ArmorMaterials {
	public static final ArmorMaterial rockyArmor = ArmorHelper.createArmorMaterial(MOD_ID, "stone", 240, 30f, 30f, 30f, 30f);
	public static final ArmorMaterial heartAmulet = ArmorHelper.createArmorMaterial(MOD_ID, "amulet", 240, 5f, 5f, 30f, 30f);
}
