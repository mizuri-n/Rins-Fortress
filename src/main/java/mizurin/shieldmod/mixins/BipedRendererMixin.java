package mizurin.shieldmod.mixins;

import mizurin.shieldmod.IShieldZombie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MobRenderer.class, remap = false)
public class BipedRendererMixin<T extends EntityLiving> extends LivingRenderer<T> {

	@Shadow
	protected ModelBiped modelBipedMain;

	public BipedRendererMixin(ModelBiped model, float shadowSize) {
		super(model, shadowSize);
	}

	@Inject(method = "renderEquippedItems", at = @At("HEAD"))
	public void inject(T entity, float f, CallbackInfo ci){
		if (entity instanceof IShieldZombie && ((IShieldZombie)entity).better_with_defense$isSnowJack()) {

			ItemStack itemstack = Block.pumpkinCarvedIdle.getDefaultStack();
			if (itemstack != null && itemstack.getItem().id < Block.blocksList.length) {
				GL11.glPushMatrix();
				this.modelBipedMain.bipedHead.postRender(0.0625f);
				if (((BlockModel<?>) BlockModelDispatcher.getInstance().getDispatch(Block.blocksList[itemstack.itemID])).shouldItemRender3d()) {
					float f1 = 0.625f;
					GL11.glTranslatef(0.0f, -0.25f, 0.0f);
					GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
					GL11.glScalef(f1, -f1, f1);
				}
				ItemModelDispatcher.getInstance().getDispatch(itemstack).renderItem(Tessellator.instance, this.renderDispatcher.itemRenderer, entity, itemstack);
				GL11.glPopMatrix();
			}
		}
	}
}
