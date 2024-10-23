package mizurin.shieldmod.interfaces;

import mizurin.shieldmod.ColoredArmorTexture;
import net.minecraft.core.item.ItemStack;

public interface IColoredArmor {
	ColoredArmorTexture[] getArmorTextures(ItemStack stack);
}
