package mizurin.shieldmod.item;

import com.mojang.nbt.CompoundTag;
import mizurin.shieldmod.ColoredArmorTexture;
import mizurin.shieldmod.IColorable;
import mizurin.shieldmod.IColoredArmor;
import mizurin.shieldmod.ShieldMod;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;

import java.awt.*;

public class ArmorColored extends ItemArmor implements IColoredArmor, IColorable {
	public static final String MOD_ID = ShieldMod.MOD_ID;


	public ArmorColored(String name, int id, ArmorMaterial material, int armorPiece) {
		super(name, id, material, armorPiece);
	}
	public Color getColor(ItemStack itemStack){
		if (itemStack.getData().containsKey("dyed_color")){
			CompoundTag colorTag = itemStack.getData().getCompound("dyed_color");
			int red = colorTag.getShort("red");
			int green = colorTag.getShort("green");
			int blue = colorTag.getShort("blue");
			return new Color(red, green, blue);
		}
		return new Color(255, 255,255);
	}

	public ColoredArmorTexture[] getArmorTextures(ItemStack itemStack) {
		return new ColoredArmorTexture[]{new ColoredArmorTexture(new NamespaceID("shieldmod","leather"), getColor(itemStack))};
	}

}
