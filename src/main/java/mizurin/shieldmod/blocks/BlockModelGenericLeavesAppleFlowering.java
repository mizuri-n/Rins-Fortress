package mizurin.shieldmod.blocks;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.color.BlockColor;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGenericLeaves;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicLeavesCherryFlowering;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelGenericLeavesAppleFlowering<T extends BlockLogic> extends BlockModelGenericLeaves<T> {
	public @NotNull StaticBlockModel overlay = BlockModelDispatcher.loadDataModel("shieldmod:block/leaves_apple_overlay").asModel();
	public @NotNull StaticBlockModel overlayFlowering = BlockModelDispatcher.loadDataModel("shieldmod:block/leaves_apple_flowering_overlay").asModel();

	public BlockModelGenericLeavesAppleFlowering(@NotNull Block<T> block) {
		super(block, "shieldmod:block/leaves_apple");
	}

	public void renderStandalone(@NotNull TessellatorGeneral tessellator, int metadata, byte lightIndex) {
		super.renderStandalone(tessellator, metadata, lightIndex);
		this.overlay.renderStandalone(this, tessellator, (double)0.0F, (double)0.0F, (double)0.0F, metadata, lightIndex, (BlockColor)BlockColorDispatcher.getInstance().getDispatch(this.block));
	}

	public boolean renderAttached(@NotNull TessellatorGeneral tessellator, @NotNull WorldSource worldSource, @NotNull TilePosc tilePos, boolean cullFaces, @Nullable IconCoordinate overrideTexture) {
		boolean didRender = super.renderAttached(tessellator, worldSource, tilePos, cullFaces, overrideTexture);
		int growthRate = BlockLogicLeavesCherryFlowering.getGrowthRate(worldSource.getBlockData(tilePos));
		if (growthRate > 0) {
			didRender |= this.overlay.renderAttached(this, tessellator, worldSource, tilePos, 0, 0, 0, (double)0.0F, (double)0.0F, (double)0.0F, false, cullFaces, overrideTexture);
		} else {
			didRender |= this.overlayFlowering.renderAttached(this, tessellator, worldSource, tilePos, 0, 0, 0, (double)0.0F, (double)0.0F, (double)0.0F, false, cullFaces, overrideTexture);
		}

		return didRender;
	}
}
