package mizurin.shieldmod.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSaplingBase;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureCherryTreeFancy;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeCherry;

import java.util.Random;

public class BlockSaplingApple extends BlockLogicSaplingBase {
	public BlockSaplingApple(Block<?> block) {
		super(block);
	}

	public void growTree(World world, int x, int y, int z, Random random) {
		WorldFeature treeBig = new WorldFeatureCherryTreeFancy(RinBlocks.leavesApple.id(), RinBlocks.logApple.id());
		WorldFeature treeSmall = new WorldFeatureTreeCherry(RinBlocks.leavesApple.id(), RinBlocks.logApple.id(), 4);
		world.setBlock(x, y, z, 0);
		if (!treeSmall.place(world, random, x, y, z) && !treeBig.place(world, random, x, y, z)) {
			world.setBlock(x, y, z, this.id());
		}

	}
}
