package mizurin.shieldmod.item;

import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ItemModelShield extends ItemModelStandard {
	public ItemModelShield(Item item, String namespace) {
		super(item, namespace);
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
