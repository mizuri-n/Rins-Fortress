package mizurin.shieldmod.blocks;

import net.minecraft.client.render.colorizer.Colorizer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLog;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSound;
import turniplabs.halplibe.helper.BlockBuilder;

import static mizurin.shieldmod.ShieldMod.blockID;
import static mizurin.shieldmod.item.RFItems.MOD_ID;

public class RFBlocks {

	public static Colorizer apple;
	public static Block<?> logApple;
	public static Block<?> leavesApple;
	public static Block<?> leavesAppleFlowering;
	public static Block<?> saplingApple;


	public void initializeBlocks() {
		BlockBuilder leaves = new BlockBuilder(MOD_ID)
			.setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
			.setHardness(0.2F)
			.setResistance(0.2F)
			.setFlammability(30, 60)
			.setTickOnLoad()
			.setVisualUpdateOnMetadata()
			.setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH);


		BlockBuilder sapling = new BlockBuilder(MOD_ID)
			.setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
			.setHardness(0.0f)
			.setResistance(0.0f)
			.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR);

		BlockBuilder log = new BlockBuilder(MOD_ID)
			.setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
			.setHardness(2.0F)
			.setResistance(1.0f)
			.setFlammability(5, 5)
			.setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT);

		logApple = log
			.build("log.apple", ++blockID, BlockLogicLog::new);
		leavesApple = leaves
			.build("leaves.apple", ++blockID, BlockLeavesApple::new);
		leavesAppleFlowering = leaves
			.build("leaves.apple.flowering", ++blockID, BlockLeavesAppleFlowering::new);
		saplingApple = sapling
			.build("sapling.apple", ++blockID, BlockSaplingApple::new);
	}
}
