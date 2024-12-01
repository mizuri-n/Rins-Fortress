package mizurin.shieldmod.blocks;

import net.minecraft.client.render.block.color.BlockColorLeavesOak;
import net.minecraft.client.render.block.model.BlockModelAxisAligned;
import net.minecraft.client.render.block.model.BlockModelCrossedSquares;
import net.minecraft.client.render.block.model.BlockModelLeaves;
import net.minecraft.client.render.colorizer.Colorizer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLog;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.block.ItemBlockLeaves;
import net.minecraft.core.sound.BlockSound;
import turniplabs.halplibe.helper.BlockBuilder;

import static mizurin.shieldmod.ShieldMod.blockID;
import static mizurin.shieldmod.item.Shields.MOD_ID;

public class RinBlocks {

	public static Colorizer apple;
	public static Block logApple;
	public static Block leavesApple;
	public static Block leavesAppleFlowering;
	public static Block saplingApple;

	public void initializeBlockDetails() {

	}

	public void initializeBlocks() {
		BlockBuilder leaves = new BlockBuilder(MOD_ID)
			.setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
			.setHardness(0.2F)
			.setResistance(0.2F)
			.setFlammability(30, 60)
			.setTickOnLoad()
			.setVisualUpdateOnMetadata()
			.setItemBlock(ItemBlockLeaves::new)
			.setBlockColor(b -> new BlockColorLeavesOak(apple))
			.setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH);


		BlockBuilder sapling = new BlockBuilder(MOD_ID)
			.setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
			.setHardness(0.0f)
			.setResistance(0.0f)
			.setBlockModel(BlockModelCrossedSquares::new)
			.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR);

		BlockBuilder log = new BlockBuilder(MOD_ID)
			.setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
			.setHardness(2.0F)
			.setResistance(1.0f)
			.setFlammability(5, 5)
			.setBlockModel(BlockModelAxisAligned::new)
			.setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT);

		logApple = log
			.setBlockModel(block -> new BlockModelAxisAligned<>(block).withTextures("shieldmod:block/log_apple_top_test", "shieldmod:block/log_apple_side_test"))
			.build(new BlockLog("log.apple", ++blockID));
		leavesApple = leaves
			.setBlockModel(block -> new BlockModelLeaves<>(block, "shieldmod:block/leaves_apple"))
			.build(new BlockLeavesApple("leaves.apple", ++blockID));
		leavesAppleFlowering = leaves
			.setBlockModel(BlockModelAppleLeavesBloom::new)
			.build(new BlockLeavesAppleFlowering("leaves.apple.flowering", ++blockID));
		saplingApple = sapling
			.setBlockModel(block -> new BlockModelCrossedSquares<>(block).withTextures("shieldmod:block/sapling_apple"))
			.build(new BlockSaplingApple("sapling.apple", ++blockID));
	}
}
