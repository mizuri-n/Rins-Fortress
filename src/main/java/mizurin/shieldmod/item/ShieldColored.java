package mizurin.shieldmod.item;

import com.mojang.nbt.CompoundTag;
import mizurin.shieldmod.ShieldMod;
import turniplabs.halplibe.helper.TextureHelper;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import useless.prismaticlibe.ColoredTexture;
import useless.prismaticlibe.IColored;
import java.awt.*;

public class ShieldColored extends ShieldItem implements IColored {
	public static final String MOD_ID = ShieldMod.MOD_ID;
	public static final int[] baseColor = TextureHelper.getOrCreateItemTexture(ShieldMod.MOD_ID, "colored.png");
	public static final int[] overlayShield = TextureHelper.getOrCreateItemTexture(ShieldMod.MOD_ID, "outline.png");
	public ShieldColored(String name, int id, ToolMaterial toolMaterial) {
		super(name, id, toolMaterial);
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
		return new ColoredTexture[]{new ColoredTexture(baseColor, getColor(itemStack)), new ColoredTexture(overlayShield, getColor(itemStack))};
	}
}
