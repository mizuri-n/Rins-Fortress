package mizurin.shieldmod.item;

import com.mojang.nbt.CompoundTag;
import mizurin.shieldmod.ShieldMod;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.ItemStack;
import useless.prismaticlibe.ColoredArmorTexture;
import useless.prismaticlibe.ColoredTexture;
import useless.prismaticlibe.IColored;
import useless.prismaticlibe.IColoredArmor;

import java.awt.*;

public class ArmorColored extends ItemArmor implements IColored, IColoredArmor {
	public static final String MOD_ID = ShieldMod.MOD_ID;
	private final IconCoordinate[] armorColor = new IconCoordinate[]{
		TextureRegistry.getTexture(MOD_ID + "item/armor/leather_helmet.png"),
		TextureRegistry.getTexture(MOD_ID + "item/armor/leather_chestplate.png"),
		TextureRegistry.getTexture(MOD_ID + "item/armor/leather_leggings.png"),
		TextureRegistry.getTexture(MOD_ID + "item/armor/leather_boots.png")};


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
	@Override
	public ColoredTexture[] getTextures(ItemStack itemStack) {
		return new ColoredTexture[]{new ColoredTexture(armorColor[armorPiece], getColor(itemStack))};
	}

	public ColoredArmorTexture[] getArmorTextures(ItemStack itemStack) {
		return new ColoredArmorTexture[]{new ColoredArmorTexture("leather", getColor(itemStack))};
	}

}
