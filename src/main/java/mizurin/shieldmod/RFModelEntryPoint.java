package mizurin.shieldmod;

import mizurin.shieldmod.entities.*;
import mizurin.shieldmod.item.ArmorColored;
import mizurin.shieldmod.item.ItemModelColored;
import mizurin.shieldmod.item.ItemModelShield;
import mizurin.shieldmod.item.Shields;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelAxisAligned;
import net.minecraft.client.render.block.model.BlockModelCrossedSquares;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelLeaves;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.Side;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import static mizurin.shieldmod.ShieldMod.MOD_ID;
import static mizurin.shieldmod.blocks.RinBlocks.*;

public class RFModelEntryPoint implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {
		ModelHelper.setBlockModel(logApple, () -> new BlockModelAxisAligned<>(logApple)
			.setTex(0, "shieldmod:block/log_apple_side_test", Side.sides)
			.setTex(0, "shieldmod:block/log_apple_top_test", Side.TOP, Side.BOTTOM)
		);

		ModelHelper.setBlockModel(saplingApple, () -> new BlockModelCrossedSquares<>(saplingApple)
			.setTex(0, "shieldmod:block/sapling_apple", Side.sides)
		);

		ModelHelper.setBlockModel(leavesApple, () -> new BlockModelLeaves<>(leavesApple, "shieldmod:block/leaves_apple"));

	}



	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		ModelHelper.setItemModel(Shields.woodenShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.woodenShield,
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/wooden_shield"), (s) -> -1)).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.woodenShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.stoneShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.stoneShield,
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/stone_shield"), (s) -> -1)).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.stoneShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.ironShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.ironShield,
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/iron_shield"), (s) -> -1)).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.ironShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.goldShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.goldShield,
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/gold_shield"), (s) -> -1)).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.goldShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.diamondShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.diamondShield,
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/diamond_shield"), (s) -> -1)).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.diamondShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.steelShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.steelShield,
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/steel_shield"), (s) -> -1)).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.steelShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.leatherShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.leatherShield,
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/colored"),ItemModelShield::shieldColor),
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/outline"), (s) -> -1)).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.leatherShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.tearShield, () -> {
			ItemModelShield im = (ItemModelShield) new ItemModelShield(Shields.tearShield,
				new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/tearstone_shield"), (s) -> -1)).setFull3D();
			im.icon	= TextureRegistry.getTexture(Shields.tearShield.namespaceID);
			return im;
		});

		ModelHelper.setItemModel(Shields.poisonBottle, () -> {
			ItemModelStandard im = new ItemModelStandard(Shields.poisonBottle, MOD_ID);
			im.icon = TextureRegistry.getTexture("shieldmod:item/poison_bottle");
			return  im;
		});

		ModelHelper.setItemModel(Shields.pumpkinStew, () -> {
			ItemModelStandard im = new ItemModelStandard(Shields.pumpkinStew, MOD_ID);
			im.icon = TextureRegistry.getTexture("shieldmod:item/pumpkin_stew");
			return  im;
		});

		ModelHelper.setItemModel(Shields.regenAmulet, () -> {
			ItemModelStandard model = new ItemModelStandard(Shields.regenAmulet, MOD_ID);
			model.icon = TextureRegistry.getTexture("shieldmod:item/regen_amulet");
			return model;
		});

		dispatcher.addDispatch(new ItemModelColored(Items.ARMOR_BOOTS_LEATHER, new ItemModelColored.ColoredTextureEntry("shieldmod:item/leather_boots", ArmorColored::getColor)));
		dispatcher.addDispatch(new ItemModelColored(Items.ARMOR_LEGGINGS_LEATHER, new ItemModelColored.ColoredTextureEntry("shieldmod:item/leather_leggings", ArmorColored::getColor)));
		dispatcher.addDispatch(new ItemModelColored(Items.ARMOR_CHESTPLATE_LEATHER, new ItemModelColored.ColoredTextureEntry("shieldmod:item/leather_chestplate", ArmorColored::getColor)));
		dispatcher.addDispatch(new ItemModelColored(Items.ARMOR_HELMET_LEATHER, new ItemModelColored.ColoredTextureEntry("shieldmod:item/leather_helmet", ArmorColored::getColor)));
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {
		ModelHelper.setEntityModel(EntityFire.class, () -> {
			EntityRenderer<?> er = new EntityRendererSprite<EntityFire>(Blocks.FIRE.asItem());
				er.init(dispatcher);
			return er;
		});
		ModelHelper.setEntityModel(EntityWeb.class, () -> {
			EntityRenderer<?> er = new EntityRendererSprite<EntityWeb>(Blocks.COBWEB.asItem());
			er.init(dispatcher);
			return er;
		});
		ModelHelper.setEntityModel(EntityPB.class, () -> {
			EntityRenderer<?> er = new EntityRendererSprite<EntityPB>(Shields.poisonBottle);
			er.init(dispatcher);
			return er;
		});

		ModelHelper.setEntityModel(EntityShield.class, () -> {
			EntityRenderer<?> er = new EntityRendererSprite<EntityShield>(Shields.ammotearShield);
			er.init(dispatcher);
			return er;
		});
		ModelHelper.setEntityModel(EntityRock.class, () -> {
			EntityRenderer<?> er = new EntityRendererSprite<EntityRock>(Items.AMMO_PEBBLE);
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
