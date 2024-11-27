package mizurin.shieldmod.blocks;

import java.util.Random;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLeavesBase;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import net.minecraft.core.world.season.Seasons;

public class BlockLeavesApple extends BlockLeavesBase {
	public BlockLeavesApple(String key, int id) {
		super(key, id, Material.leaves);
	}

	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (world.getSeasonManager().getCurrentSeason() != null && world.getSeasonManager().getCurrentSeason() == Seasons.OVERWORLD_FALL && rand.nextInt(40) == 0) {
			world.spawnParticle("fallingleaf", (double)x, (double)y - 0.10000000149011612, (double)z, 0.0, 0.0, 0.0, 0);
		}

	}

	protected Block getSapling() {
		return RinBlocks.saplingApple;
	}
}
