package mizurin.shieldmod.blocks;

import java.util.Random;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import net.minecraft.core.world.season.Seasons;
import org.jetbrains.annotations.NotNull;

public class BlockLogicLeavesApple extends BlockLogicLeavesBase {
	public BlockLogicLeavesApple(@NotNull Block<?> block) {
		super(block, Materials.LEAVES, RFBlocks.saplingApple);
	}

	public void animationTick(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Random rand) {
		if (world.getSeasonManager().getCurrentSeason() != null && world.getSeasonManager().getCurrentSeason() == Seasons.OVERWORLD_FALL && rand.nextInt(40) == 0) {
			world.spawnParticle("fallingleaf", (double)tilePos.x(), (double)tilePos.y() - (double)0.1F, (double)tilePos.z(), (double)0.0F, (double)0.0F, (double)0.0F, 0, false);
		}

	}
}

