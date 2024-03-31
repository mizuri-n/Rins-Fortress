package mizurin.shieldmod;
import net.minecraft.client.render.TextureFX;
import net.minecraft.core.Global;
import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.achievement.AchievementList;
import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import org.lwjgl.opengl.GL11;
import turniplabs.halplibe.util.achievements.AchievementPage;
import turniplabs.halplibe.util.achievements.GuiAchievements;

import java.util.Random;

public class ShieldAchievements extends AchievementPage {
	public static final Achievement SHIELD_GOT = new Achievement(AchievementList.achievementList.size() + 1, "shieldmod.shield.got", 0, 0, Block.cobbleStone, null);
	public static final Achievement BLOCK = new Achievement(AchievementList.achievementList.size() + 1, "shieldmod.block", 2, 1, Block.brickStone, SHIELD_GOT);
	public static final Achievement MODERN_AGE = new Achievement(AchievementList.achievementList.size() + 1, "shieldmod.modern.age", 2, 0, Block.blockSteel, SHIELD_GOT);
	public static final Achievement FLY_HIGH = new Achievement(AchievementList.achievementList.size() + 1, "shieldmod.fly.high", 2, 2, Block.wool, BLOCK);
	public static final Achievement GOLD_RETAL = new Achievement(AchievementList.achievementList.size() + 1, "shieldmod.gold.retal", 3, 2, Block.blockGold, BLOCK);
	public ShieldAchievements(){
		super("Better with defense", "achievements.page.defense");
		((Stat) BLOCK).registerStat();
		achievementList.add(SHIELD_GOT);
		achievementList.add(BLOCK);
		achievementList.add(MODERN_AGE);
		achievementList.add(FLY_HIGH);
		achievementList.add(GOLD_RETAL);
	}
	@Override
	public void getBackground(GuiAchievements guiAchievements, Random random, int iOffset, int jOffset, int blockX1, int blockY1, int blockX2, int blockY2) {
		int l7 = 0;
		while (l7 * 16 - blockY2 < 155) {
			float f5 = 0.6f - (float)(blockY1 + l7) / 25.0f * 0.3f;
			GL11.glColor4f(f5, f5, f5, 1.0f);
			int i8 = 0;
			while (i8 * 16 - blockX2 < 224) {
				int k8 = Block.cobbleStone.getBlockTextureFromSideAndMetadata(Side.BOTTOM,0);
				guiAchievements.drawTexturedModalRect(iOffset + i8 * 16 - blockX2, jOffset + l7 * 16 - blockY2, k8 % Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain, k8 / Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain, 16, 16, TextureFX.tileWidthTerrain, 1.0f / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain));
				++i8;
			}
			++l7;
		}
	}
}
