package mizurin.shieldmod.compat.aether.model;

import mizurin.shieldmod.RFModelEntryPoint;
import mizurin.shieldmod.compat.aether.entities.EntityHoming;
import mizurin.shieldmod.compat.aether.item.RFAetherItems;
import mizurin.shieldmod.item.ItemModelColored;
import mizurin.shieldmod.item.ItemModelShield;
import mizurin.shieldmod.item.ItemModelSky;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererSprite;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import teamport.aether.item.AetherItems;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

public class RFAetherModels {
	public void initBlockModels(BlockModelDispatcher dispatcher) {

	}

	public void initItemModels(ItemModelDispatcher dispatcher) {
		dispatcher.addDispatch(new ItemModelShield(RFAetherItems.valkyrieShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/valkyrie_shield"), RFModelEntryPoint::white)).setFull3D());

		dispatcher.addDispatch(new ItemModelSky(RFAetherItems.skyrootShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/skyroot_shield"), RFModelEntryPoint::white)).setFull3D());

		dispatcher.addDispatch(new ItemModelShield(RFAetherItems.holystoneShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/disc_shield"), RFModelEntryPoint::white)).setFull3D());

		dispatcher.addDispatch(new ItemModelShield(RFAetherItems.zaniteShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/zanite_shield"), RFModelEntryPoint::white)).setFull3D());

		dispatcher.addDispatch(new ItemModelShield(RFAetherItems.gravititeShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/gravitite_shield"), RFModelEntryPoint::white)).setFull3D());
	}

	public void initEntityModels(EntityRenderDispatcher dispatcher) {
		ModelHelper.setEntityModel(EntityHoming.class, () -> {
			final EntityRenderer<?> er = new EntityRendererSprite<EntityHoming>(AetherItems.PROJECTILE_LIGHTNING).setScale(4.0F);
			er.init(dispatcher);
			return er;
		});
	}

	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}
	
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
