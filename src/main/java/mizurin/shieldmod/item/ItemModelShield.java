package mizurin.shieldmod.item;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static mizurin.shieldmod.ShieldMod.MOD_ID;

public class ItemModelShield extends ItemModelColored {
	public ItemModelShield(Item item, ColoredTextureEntry[] textureEntries) {
		super(item, textureEntries);
	}

	public static int shieldColor(ItemStack itemStack){
		if (itemStack.getData().containsKey("dyed_color")){
			CompoundTag colorTag = itemStack.getData().getCompound("dyed_color");
			int red = colorTag.getShort("red");
			int green = colorTag.getShort("green");
			int blue = colorTag.getShort("blue");
			return (0xFF << 24 | red << 16 | green << 8 | blue);
		}
		return 0xFFFFFFFF;
	}

	@Override
	public void heldTransformThirdPerson(ItemRenderer renderer, Entity entity, ItemStack itemstack) {
		if (itemstack.getData().getBoolean("active")) {
			final float scale2 = 0.625F;
			GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
			GL11.glScalef(scale2, scale2, scale2);
			GL11.glRotatef(175, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(145F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-25F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.10F, -0.80075F, 0.375F);
		} else {
			final float scale = 0.625F;
			GL11.glTranslatef(-0.25F, 0.1875F, 0);
			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(35F, 0.0F, 1.0F, 0.0F); //y value
			GL11.glRotatef(-5F, 1.0F, 0.0F, 0.0F); //x value
			GL11.glRotatef(40F, 0.0F, 0.0F, 1.0F); //z value
			GL11.glRotatef(-25F, 0.0F, 0.0F, 1.0F); //z value
			GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F); //x value
			GL11.glTranslatef(0.31F, -0.20075F, -0.3F);
		}
	}
}
