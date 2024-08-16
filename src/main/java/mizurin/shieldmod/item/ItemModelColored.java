package mizurin.shieldmod.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.Random;
import java.util.function.Function;

public class ItemModelColored extends ItemModelStandard {
	private int currentIndex;
	private final ColoredTextureEntry[] textureEntries; // Specifically an array list to guarantee consecutive order when indexing
	public ItemModelColored(Item item, ColoredTextureEntry[] textureEntries) {
		super(item, null);
		this.textureEntries = textureEntries;
	}

	@Override
	public void renderItemInWorld(Tessellator tessellator, Entity entity, ItemStack itemStack, float brightness, float alpha, boolean worldTransform) {
		currentIndex = 0;
		for (int i = 0; i < textureEntries.length; i++) {
			currentIndex = i;
			super.renderItemInWorld(tessellator, entity, itemStack, brightness, alpha, worldTransform);
		}
	}

	@Override
	public void renderItemIntoGui(Tessellator tessellator, FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemStack, int x, int y, float brightness, float alpha) {
		currentIndex = 0;
		for (int i = 0; i < textureEntries.length; i++) {
			currentIndex = i;
			super.renderItemIntoGui(tessellator, fontrenderer, renderengine, itemStack, x, y, brightness, alpha);
		}
	}

	@Override
	public void renderAsItemEntity(Tessellator tessellator, @Nullable Entity entity, Random random, ItemStack itemstack, int renderCount, float yaw, float brightness, float partialTick) {
		Minecraft mc = Minecraft.getMinecraft(this);
		if (mc.fullbright || this.itemfullBright) {
			brightness = 1.0F;
		}

		EntityRenderDispatcher renderDispatcher = EntityRenderDispatcher.instance;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		IconCoordinate tex = this.getIcon(entity, itemstack);
		tex.parentAtlas.bindTexture();
		int render;
		float r;
		float g;
		float b;

		if (mc.gameSettings.items3D.value) {
			GL11.glPushMatrix();
			GL11.glScaled(1.0, 1.0, 1.0);
			GL11.glRotated(yaw, 0.0, 1.0, 0.0);
			GL11.glTranslated(-0.5, 0.0, -0.05 * (double)(renderCount - 1));

			for(render = 0; render < renderCount; ++render) {
				GL11.glPushMatrix();
				GL11.glTranslated(0.0, 0.0, 0.1 * (double)render);
				for (int index = 0; index < textureEntries.length; index++) {
					currentIndex = index;
					ColoredTextureEntry e = textureEntries[index];
					int color = e.colorProcessor.apply(itemstack);
					r = (float)(color >> 16 & 255) / 255.0F;
					g = (float)(color >> 8 & 255) / 255.0F;
					b = (float)(color & 255) / 255.0F;
					GL11.glColor4f(r * brightness, g * brightness, b * brightness, 1.0F);
					this.renderItem(tessellator, renderDispatcher.itemRenderer, itemstack, entity, brightness, false);
				}
				currentIndex = 0;
				GL11.glPopMatrix();
			}

			GL11.glPopMatrix();
		} else {
			for(render = 0; render < renderCount; ++render) {
				GL11.glPushMatrix();
				if (render > 0) {
					float rOffX = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					float rOffY = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					float rOffZ = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					GL11.glTranslatef(rOffX, rOffY, rOffZ);
				}

				GL11.glRotatef(180.0F - renderDispatcher.viewLerpYaw, 0.0F, 1.0F, 0.0F);
				for (int index = 0; index < textureEntries.length; index++) {
					currentIndex = index;
					ColoredTextureEntry e = textureEntries[index];
					int color = e.colorProcessor.apply(itemstack);
					r = (float)(color >> 16 & 255) / 255.0F;
					g = (float)(color >> 8 & 255) / 255.0F;
					b = (float)(color & 255) / 255.0F;
					GL11.glColor4f(r * brightness, g * brightness, b * brightness, 1.0F);
					this.renderFlat(tessellator, e.coordinate);
				}
				currentIndex = 0;
				GL11.glPopMatrix();
			}
		}

	}


	@Override
	public @NotNull IconCoordinate getIcon(@Nullable Entity entity, ItemStack itemStack) {
		return textureEntries[currentIndex].coordinate;
	}

	@Override
	public int getColor(ItemStack stack) {
		return textureEntries[currentIndex].colorProcessor.apply(stack);
	}

	public static class ColoredTextureEntry {
		public final IconCoordinate coordinate;
		public final Function<ItemStack, Integer> colorProcessor;
		public ColoredTextureEntry(IconCoordinate coordinate, Function<ItemStack, Integer> colorProcessor) {
			this.coordinate = coordinate;
			this.colorProcessor = colorProcessor;
		}
	}
}
