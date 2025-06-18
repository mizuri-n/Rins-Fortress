package mizurin.shieldmod.mixins.client;

import mizurin.shieldmod.interfaces.IDazed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.HudComponentHealthBar;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.item.ItemBucketIceCream;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.player.gamemode.Gamemode;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;


@Mixin(value = HudComponentHealthBar.class, remap = false)
public abstract class IconMixin extends HudComponent {
	private final Random random = new Random();
	public IconMixin(String key, int xSize, int ySize, Layout layout) {
		super(key, xSize, ySize, layout);
	}


	@Override
	public void render(Minecraft mc, HudIngame hudIngame, int xSizeScreen, int ySizeScreen, float f) {
		int x = this.getLayout().getComponentX(mc, this, xSizeScreen);
		int y = this.getLayout().getComponentY(mc, this, ySizeScreen);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042);
		boolean heartsFlash = mc.thePlayer.heartsFlashTime / 3 % 2 == 1;
		if (mc.thePlayer.heartsFlashTime < 10) {
			heartsFlash = false;
		}

		int health = mc.thePlayer.getHealth();
		int prevHealth = mc.thePlayer.prevHealth;
		this.random.setSeed((long)hudIngame.updateCounter * 312871L);
		boolean isHardcore = mc.thePlayer.getGamemode() == Gamemode.hardcore;

		if (((IDazed) mc.thePlayer).shieldmod$getPoisonHurt() > 0) {
			for (int i = 0; i < mc.thePlayer.getMaxHealth()/2; ++i) {
				boolean heartOffset = false;
				if (heartsFlash) {
					heartOffset = true;
				}

				int xHeart = x + i * 8;
				int yHeart = y;
				if (health <= 4) {
					yHeart += this.random.nextInt(2);
				}
				if(i <= 12 && i >= 10){
					yHeart -= 10;
					xHeart = x + (i - 10) * 8;
				}

				hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, !heartOffset ? TextureRegistry.getTexture("shieldmod:gui/hud/heart/container") : TextureRegistry.getTexture("shieldmod:gui/hud/heart/container_blinking"));
				if (heartsFlash) {
					if (i * 2 + 1 < prevHealth) {
						hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("shieldmod:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "full_blinking"));
					}

					if (i * 2 + 1 == prevHealth) {
						hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("shieldmod:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "half_blinking"));
					}
				}

				if (i * 2 + 1 < health) {
					hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("shieldmod:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "full"));
				}

				if (i * 2 + 1 == health) {
					hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("shieldmod:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "half"));
				}

				if (mc.thePlayer.inventory.getCurrentItem() != null && (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood || mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBucketIceCream) && (Boolean) mc.gameSettings.foodHealthRegenOverlay.value) {
					int healing;
					if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood) {
						healing = ((ItemFood) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount();
					} else {
						healing = ((ItemBucketIceCream) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount();
					}

					if (i * 2 + 1 >= health) {
						if (i * 2 + 1 == health) {
							hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("shieldmod:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "preview_half_right"));
						} else if (i * 2 + 1 < health + healing) {
							hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("shieldmod:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "preview_full"));
						} else if (i * 2 + 1 == health + healing) {
							hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("shieldmod:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "preview_half"));
						}
					}
				}
			}
		} else {
			for (int i = 0; i < mc.thePlayer.getMaxHealth()/2; ++i) {

				boolean heartOffset = false;
				if (heartsFlash) {
					heartOffset = true;
				}

				int xHeart = x + i * 8;
				int yHeart = y;
				if (health <= 4) {
					yHeart += this.random.nextInt(2);
				}
				if(i <= 12 && i >= 10){
					yHeart -= 10;
					xHeart = x + (i - 10) * 8;
				}

				hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, !heartOffset ? TextureRegistry.getTexture("minecraft:gui/hud/heart/container") : TextureRegistry.getTexture("minecraft:gui/hud/heart/container_blinking"));
				if (heartsFlash) {
					if (i * 2 + 1 < prevHealth) {
						hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "full_blinking"));
					}

					if (i * 2 + 1 == prevHealth) {
						hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "half_blinking"));
					}
				}

				if (i * 2 + 1 < health) {
					hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "full"));
				}

				if (i * 2 + 1 == health) {
					hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "half"));
				}

				if (mc.thePlayer.inventory.getCurrentItem() != null && (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood || mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBucketIceCream) && (Boolean) mc.gameSettings.foodHealthRegenOverlay.value) {
					int healing;
					if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood) {
						healing = ((ItemFood) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount();
					} else {
						healing = ((ItemBucketIceCream) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount();
					}

					if (i * 2 + 1 >= health) {
						if (i * 2 + 1 == health) {
							hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "preview_half_right"));
						} else if (i * 2 + 1 < health + healing) {
							hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "preview_full"));
						} else if (i * 2 + 1 == health + healing) {
							hudIngame.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "preview_half"));
						}
					}
				}
			}
		}
	}







	@Override
	public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
		int x = layout.getComponentX(mc, this, xSizeScreen);
		int y = layout.getComponentY(mc, this, ySizeScreen);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042);
		int health = 11;

		if (((IDazed) mc.thePlayer).shieldmod$getPoisonHurt() > 0) {
			for (int i = 0; i < mc.thePlayer.getMaxHealth()/2; ++i) {
				int xHeart = x + i * 8;
				gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture("shieldmod:gui/hud/heart/container"));
				if (i * 2 + 1 < health) {
					gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture("shieldmod:gui/hud/heart/full"));
				}

				if (i * 2 + 1 == health) {
					gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture("shieldmod:gui/hud/heart/half"));
				}
			}

		}
		else {
			for (int i = 0; i < 10; ++i) {
				int xHeart = x + i * 8;
				gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/container"));
				if (i * 2 + 1 < health) {
					gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/full"));
				}

				if (i * 2 + 1 == health) {
					gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/half"));
				}
			}
		}
	}
}


