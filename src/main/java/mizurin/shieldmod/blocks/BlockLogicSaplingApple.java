package mizurin.shieldmod.blocks;

import java.util.Random;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSaplingBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureCherryTreeFancy;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeCherry;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

public class BlockLogicSaplingApple extends BlockLogicSaplingBase {
	public BlockLogicSaplingApple(@NotNull Block<?> block) {
		super(block);
	}

	public void growTree(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Random random) {
		WorldFeature treeBig = new WorldFeatureCherryTreeFancy(RFBlocks.leavesApple.id(), RFBlocks.logApple.id());
		WorldFeature treeSmall = new WorldFeatureTreeCherry(RFBlocks.leavesApple.id(), RFBlocks.logApple.id(), 4);
		world.setBlockType(tilePos, Blocks.AIR);
		if (!treeSmall.place(world, random, tilePos.x(), tilePos.y(), tilePos.z()) && !treeBig.place(world, random, tilePos.x(), tilePos.y(), tilePos.z())) {
			world.setBlockType(tilePos, this.block);
		}

	}
}
