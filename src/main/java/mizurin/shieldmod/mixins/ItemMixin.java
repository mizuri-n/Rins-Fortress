package mizurin.shieldmod.mixins;
import mizurin.shieldmod.item.ShieldItem;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerRenderer.class, remap = false)
public abstract class ItemMixin extends LivingRenderer<EntityPlayer> {
	public ItemMixin(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}

	@Shadow
	private ModelBiped modelBipedMain;

	@Inject(method = "renderSpecials", at = @At("HEAD"), cancellable = true)
	public void injectRenderer(EntityPlayer entity, float f, CallbackInfo ci) {
		ItemStack itemstack = entity.inventory.getCurrentItem();

		if (itemstack != null && itemstack.getItem() instanceof ShieldItem) {
			if (itemstack.getData().getBoolean("active")) {
				GL11.glPushMatrix();
				modelBipedMain.bipedRightArm.postRender(0.0625F);

				final float scale2 = 0.625F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(scale2, scale2, scale2);
				GL11.glRotatef(175, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(145F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-25F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(0.10F, -0.80075F, 0.375F);

				renderDispatcher.itemRenderer.renderItem(entity, itemstack);
				GL11.glPopMatrix();
				ci.cancel();
			} else {
			//GL11.glPushMatrix();
			modelBipedMain.bipedRightArm.postRender(0.0625F);

			final float scale = 0.625F;
			GL11.glTranslatef(-0.25F, 0.1875F, 0);
			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(35F, 0.0F, 1.0F, 0.0F); //y value
			GL11.glRotatef(-5F, 1.0F, 0.0F, 0.0F); //x value
			GL11.glRotatef(40F, 0.0F, 0.0F, 1.0F); //z value
			GL11.glRotatef(-25F, 0.0F, 0.0F, 1.0F); //z value
			GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F); //x value
			GL11.glTranslatef(0.31F, -0.20075F, -0.3F);


			renderDispatcher.itemRenderer.renderItem(entity, itemstack);
			GL11.glPopMatrix();
			ci.cancel();
			}


		}
	}

}
