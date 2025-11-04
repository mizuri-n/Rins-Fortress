package mizurin.shieldmod.item;

import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import sunsetsatellite.catalyst.effects.api.attribute.Attributes;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import sunsetsatellite.catalyst.effects.api.modifier.IItemWithModifiers;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import sunsetsatellite.catalyst.effects.api.modifier.ModifierType;
import sunsetsatellite.catalyst.effects.api.modifier.type.IntModifier;

import java.util.HashMap;
import java.util.Map;

public class ItemAmulet extends ItemArmor implements IItemWithModifiers {
	public ItemAmulet(String name, String namespaceId, int id, ArmorMaterial material, int armorPiece) {
		super(name, namespaceId, id, material, armorPiece);
	}

	@Override
	public Map<Modifier<?>, Boolean> getModifiers(IHasEffects<?> iHasEffects, ItemStack itemStack, int slot) {
		if (slot < 0 || slot > 3) {
			return new HashMap<>();
		}

		HashMap<Modifier<?>, Boolean> map = new HashMap<>();

		map.put(new IntModifier(Attributes.EXTRA_HEALTH, ModifierType.ADD,6),true);

		return map;
	}
}
