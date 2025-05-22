package mizurin.shieldmod;

import mizurin.shieldmod.entities.EntityFire;
import mizurin.shieldmod.item.ItemModelColored;
import mizurin.shieldmod.item.ItemModelShield;
import mizurin.shieldmod.item.Shields;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Blocks;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import static mizurin.shieldmod.ShieldMod.MOD_ID;

public class RFModelEntryPoint implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {

	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		ModelHelper.setItemModel(Shields.woodenShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.woodenShield, new ItemModelColored.ColoredTextureEntry[]
				{new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/wooden_shield_test"), (s) -> -1)}).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.woodenShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.stoneShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.stoneShield, new ItemModelColored.ColoredTextureEntry[]
				{new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/stone_shield_test"), (s) -> -1)}).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.stoneShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.ironShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.ironShield, new ItemModelColored.ColoredTextureEntry[]
				{new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/iron_shield_test"), (s) -> -1)}).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.ironShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.goldShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.goldShield, new ItemModelColored.ColoredTextureEntry[]
				{new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/gold_shield_test"), (s) -> -1)}).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.goldShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.diamondShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.diamondShield, new ItemModelColored.ColoredTextureEntry[]
				{new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/diamond_shield_test"), (s) -> -1)}).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.diamondShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.steelShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.steelShield, new ItemModelColored.ColoredTextureEntry[]
				{new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/steel_shield"), (s) -> -1)}).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.steelShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.leatherShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.leatherShield, new ItemModelColored.ColoredTextureEntry[]
				{new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/colored"),ItemModelShield::shieldColor),
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/outline"), (s) -> -1)}).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.leatherShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.tearShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.tearShield, new ItemModelColored.ColoredTextureEntry[]
				{new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/tearstone_shield_test"), (s) -> -1)}).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.tearShield.namespaceID);
			return im;
		});
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {
		ModelHelper.setEntityModel(EntityFire.class, () -> {
			EntityRenderer<?> er = new EntityRendererSprite<EntityFire>(Blocks.FIRE.asItem());
				er.init(dispatcher);
			return er;
		});
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
