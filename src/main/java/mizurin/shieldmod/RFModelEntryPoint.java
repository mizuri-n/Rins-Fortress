package mizurin.shieldmod;

import mizurin.shieldmod.blocks.BlockModelAppleLeavesBloom;
import mizurin.shieldmod.entities.*;
import mizurin.shieldmod.item.ArmorColored;
import mizurin.shieldmod.item.ItemModelColored;
import mizurin.shieldmod.item.ItemModelShield;
import mizurin.shieldmod.item.Shields;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.color.BlockColorLeavesOak;
import net.minecraft.client.render.block.model.BlockModelAxisAligned;
import net.minecraft.client.render.block.model.BlockModelCrossedSquares;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelLeaves;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.Side;
import org.jetbrains.annotations.NotNull;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import static mizurin.shieldmod.blocks.RinBlocks.*;

public class RFModelEntryPoint implements ModelEntrypoint {
	@Override
	public void initBlockModels(final BlockModelDispatcher dispatcher) {
		ModelHelper.setBlockModel(logApple, () -> new BlockModelAxisAligned<>(logApple)
			.setTex(0, "shieldmod:block/log_apple_side_test", Side.sides)
			.setTex(0, "shieldmod:block/log_apple_top_test", Side.TOP, Side.BOTTOM)
		);

		ModelHelper.setBlockModel(saplingApple, () -> new BlockModelCrossedSquares<>(saplingApple)
			.setTex(0, "shieldmod:block/sapling_apple", Side.sides)
		);

		ModelHelper.setBlockModel(leavesApple, () -> new BlockModelLeaves<>(leavesApple, "shieldmod:block/leaves_apple"));

		ModelHelper.setBlockModel(leavesAppleFlowering, () -> new BlockModelAppleLeavesBloom<>(leavesAppleFlowering));
	}



	@Override
	public void initItemModels(final ItemModelDispatcher dispatcher) {
		dispatcher.addDispatch(new ItemModelShield(Shields.woodenShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/wooden_shield"), (s) -> -1)).setFull3D());
		dispatcher.addDispatch(new ItemModelShield(Shields.stoneShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/stone_shield"), RFModelEntryPoint::white)).setFull3D());
		dispatcher.addDispatch(new ItemModelShield(Shields.ironShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/iron_shield"), RFModelEntryPoint::white)).setFull3D());
		dispatcher.addDispatch(new ItemModelShield(Shields.goldShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/gold_shield"), RFModelEntryPoint::white)).setFull3D());
		dispatcher.addDispatch(new ItemModelShield(Shields.diamondShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/diamond_shield"), RFModelEntryPoint::white)).setFull3D());
		dispatcher.addDispatch(new ItemModelShield(Shields.steelShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/steel_shield"), RFModelEntryPoint::white)).setFull3D());
		dispatcher.addDispatch(new ItemModelShield(Shields.leatherShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/colored"),ItemModelShield::shieldColor)));
		dispatcher.addDispatch(new ItemModelShield(Shields.tearShield,
			new ItemModelColored.ColoredTextureEntry(TextureRegistry.getTexture("shieldmod:item/tearstone_shield"), RFModelEntryPoint::white)).setFull3D());

		dispatcher.addDispatch(setIcon(new ItemModelStandard(Shields.ammotearShield, null), "shieldmod:item/tear_shield_ammo"));
		dispatcher.addDispatch(setIcon(new ItemModelStandard(Shields.poisonBottle, null), "shieldmod:item/poison_bottle"));
		dispatcher.addDispatch(setIcon(new ItemModelStandard(Shields.pumpkinStew, null), "shieldmod:item/pumpkin_stew"));
		dispatcher.addDispatch(setIcon(new ItemModelStandard(Shields.regenAmulet, null), "shieldmod:item/regen_amulet"));
		dispatcher.addDispatch(setIcon(new ItemModelStandard(Shields.rockyHelmet, null), "shieldmod:item/rocky_helmet"));

		dispatcher.addDispatch(new ItemModelColored(Items.ARMOR_BOOTS_LEATHER, new ItemModelColored.ColoredTextureEntry("shieldmod:item/leather_boots", ArmorColored::getColor)));
		dispatcher.addDispatch(new ItemModelColored(Items.ARMOR_LEGGINGS_LEATHER, new ItemModelColored.ColoredTextureEntry("shieldmod:item/leather_leggings", ArmorColored::getColor)));
		dispatcher.addDispatch(new ItemModelColored(Items.ARMOR_CHESTPLATE_LEATHER, new ItemModelColored.ColoredTextureEntry("shieldmod:item/leather_chestplate", ArmorColored::getColor)));
		dispatcher.addDispatch(new ItemModelColored(Items.ARMOR_HELMET_LEATHER, new ItemModelColored.ColoredTextureEntry("shieldmod:item/leather_helmet", ArmorColored::getColor)));
	}

	@Override
	public void initEntityModels(final EntityRenderDispatcher dispatcher) {
		ModelHelper.setEntityModel(EntityFire.class, () -> {
			final EntityRenderer<?> er = new EntityRendererSprite<EntityFire>(Blocks.FIRE.asItem());
				er.init(dispatcher);
			return er;
		});
		ModelHelper.setEntityModel(EntityWeb.class, () -> {
			final EntityRenderer<?> er = new EntityRendererSprite<EntityWeb>(Blocks.COBWEB.asItem());
			er.init(dispatcher);
			return er;
		});
		ModelHelper.setEntityModel(EntityPB.class, () -> {
			final EntityRenderer<?> er = new EntityRendererSprite<EntityPB>(Shields.poisonBottle);
			er.init(dispatcher);
			return er;
		});

		ModelHelper.setEntityModel(EntityShield.class, () -> {
			final EntityRenderer<?> er = new EntityRendererSprite<EntityShield>(Shields.ammotearShield);
			er.init(dispatcher);
			return er;
		});
		ModelHelper.setEntityModel(EntityRock.class, () -> {
			final EntityRenderer<?> er = new EntityRendererSprite<EntityRock>(Items.AMMO_PEBBLE);
			er.init(dispatcher);
			return er;
		});

		ModelHelper.setEntityModel(EntityIceBall.class, () -> {
			final EntityRenderer<?> er = new EntityRendererSprite<EntityIceBall>(Items.AMMO_SNOWBALL);
			er.init(dispatcher);
			return er;
		});

	}

	@Override
	public void initTileEntityModels(final TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(final BlockColorDispatcher dispatcher) {
		ModelHelper.setBlockColor(leavesApple, () -> new BlockColorLeavesOak(apple));
		ModelHelper.setBlockColor(leavesAppleFlowering, () -> new BlockColorLeavesOak(apple));

	}

	public static <T extends ItemModelStandard> T setIcon(final @NotNull T model, final @NotNull IconCoordinate icon) {
		model.icon = icon;
		return model;
	}

	public static <T extends ItemModelStandard> T setIcon(final @NotNull T model, final @NotNull String icon) {
		model.icon = TextureRegistry.getTexture(icon);
		return model;
	}

	public static int white(ItemStack stack) { return 0xFF_FF_FF_FF; }
}
