package mizurin.shieldmod.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelLeaves;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class BlockModelAppleLeavesBloom<T extends BlockLogic> extends BlockModelLeaves<T> {
	private final IconCoordinate grownAppleOverlay = TextureRegistry.getTexture("shieldmod:block/leaves_apple_overlay");
	private final IconCoordinate floweringAppleOverlay = TextureRegistry.getTexture("shieldmod:block/leaves_apple_flowering_overlay");

	public BlockModelAppleLeavesBloom(Block<T> block) {
		super(block, "shieldmod:block/leaves_apple");
	}

	public boolean render(Tessellator tessellator, int x, int y, int z) {
		super.render(tessellator, x, y, z);
		int growthRate = BlockLeavesAppleFlowering.getGrowthRate(renderBlocks.blockAccess.getBlockMetadata(x, y, z));
		if (growthRate > 0) {
			renderBlocks.overrideBlockTexture = this.grownAppleOverlay;
		} else {
			renderBlocks.overrideBlockTexture = this.floweringAppleOverlay;
		}

		this.renderStandardBlock(tessellator, this.block.getBoundsRaw(), x, y, z, 1.0F, 1.0F, 1.0F);
		renderBlocks.overrideBlockTexture = null;
		return true;
	}

	public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
		super.renderBlockOnInventory(tessellator, metadata, brightness, alpha, lightmapCoordinate);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		AABB bounds = this.block.getBoundsRaw();
		IconCoordinate cherryCoord = this.grownAppleOverlay;
		GL11.glColor4f(brightness, brightness, brightness, alpha);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		this.renderBottomFace(tessellator, bounds, 0.0, 0.0, 0.0, cherryCoord);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		this.renderTopFace(tessellator, bounds, 0.0, 0.0, 0.0, cherryCoord);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		this.renderNorthFace(tessellator, bounds, 0.0, 0.0, 0.0, cherryCoord);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		this.renderSouthFace(tessellator, bounds, 0.0, 0.0, 0.0, cherryCoord);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		this.renderWestFace(tessellator, bounds, 0.0, 0.0, 0.0, cherryCoord);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		this.renderEastFace(tessellator, bounds, 0.0, 0.0, 0.0, cherryCoord);
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
