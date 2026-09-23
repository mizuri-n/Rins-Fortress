package mizurin.shieldmod.blocks;

import java.util.Random;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicLeavesCherry;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.IBonemealable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import net.minecraft.core.world.season.Seasons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockLogicLeavesAppleFlowering extends BlockLogicLeavesCherry implements IBonemealable, BlockLogic.MatcherDataEquivalency.Masked {
	public static final int MASK_GROWTH_DATA = 240;
	public static final int MAX_GROWTH_STATE = 1;

	public BlockLogicLeavesAppleFlowering(@NotNull Block<?> block) {
		super(block);
	}

	public ItemStack[] getBreakResult(@NotNull World world, @NotNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
		int growthRate = getGrowthRate(data);
		if (dropCause != EnumDropCause.PICK_BLOCK && dropCause != EnumDropCause.SILK_TOUCH) {
			return growthRate == 0 ? null : new ItemStack[]{new ItemStack(Items.FOOD_APPLE, 1)};
		} else {
			return new ItemStack[]{new ItemStack(this)};
		}
	}

	public void onAttacked(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Player player, @NotNull Side side, double xHit, double yHit) {
		this.onInteracted(world, tilePos, player, side, xHit, yHit);
	}

	public boolean onInteracted(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Player player, @Nullable Side side, double xHit, double yHit) {
		return this.harvest(world, tilePos, player);
	}

	public boolean harvest(@NotNull World world, @NotNull TilePosc tilePos, @Nullable Player player) {
		int meta = world.getBlockData(tilePos);
		int growthRate = getGrowthRate(meta);
		if (growthRate > 0) {
			if (player != null) {
				world.playSoundAtEntity(player, player, "item.pickup", 1.0F, 1.0F);
			}

			if (!world.isClientSide) {
				this.dropWithCause(world, EnumDropCause.WORLD, tilePos, meta, (TileEntity)null, (Player)null);
			}

			world.setBlockDataNotify(tilePos, setGrowthRate(meta, 0));
			world.scheduleBlockUpdate(tilePos, RFBlocks.leavesAppleFlowering, (long)this.tickDelay());
			return true;
		} else {
			return false;
		}
	}

	public void onActivatorInteracted(@NotNull World world, @NotNull TilePosc tilePos, @NotNull TileEntityActivator activator, @NotNull Direction direction) {
		this.harvest(world, tilePos, (Player)null);
	}

	public void updateTick(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Random rand, boolean isRandomTick) {
		super.updateTick(world, tilePos, rand, isRandomTick);
		int meta = world.getBlockData(tilePos);
		int growthRate = getGrowthRate(meta);
		if (world.getSeasonManager().getCurrentSeason() == Seasons.OVERWORLD_FALL) {
			if (rand.nextInt(80) == 0 && growthRate == 0) {
				world.setBlockDataNotify(tilePos, setGrowthRate(meta, 1));
				world.scheduleBlockUpdate(tilePos, RFBlocks.leavesAppleFlowering, (long)this.tickDelay());
			}
		} else if (growthRate > 0) {
			world.setBlockDataNotify(tilePos, meta & 15);
			world.scheduleBlockUpdate(tilePos, RFBlocks.leavesAppleFlowering, (long)this.tickDelay());
		}

	}

	public boolean onBonemealUsed(@NotNull ItemStack itemStack, @Nullable Player player, @NotNull World world, @NotNull TilePosc tilePos, @NotNull Side side, double xHit, double yHit) {
		int meta = world.getBlockData(tilePos);
		if (getGrowthRate(meta) != 0) {
			return false;
		} else {
			if (!world.isClientSide) {
				if (world.getSeasonManager().getCurrentSeason() != Seasons.OVERWORLD_FALL) {
					return true;
				}

				world.setBlockDataNotify(tilePos, setGrowthRate(meta, 1));
				if (player == null || player.getGamemode().hasBlockConsumption()) {
					--itemStack.stackSize;
				}
			}

			return true;
		}
	}

	public static int getGrowthRate(int meta) {
		return (meta & 240) >> 4;
	}

	public static int setGrowthRate(int meta, int growthRate) {
		return meta & -241 | growthRate << 4 & 240;
	}

	public int getMatcherDataEquivalencyMask() {
		return -16;
	}
}
