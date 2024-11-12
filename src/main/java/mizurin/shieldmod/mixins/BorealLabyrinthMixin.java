package mizurin.shieldmod.mixins;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.generate.feature.WorldFeatureLabyrinth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = WorldFeatureLabyrinth.class, remap = false)
public class BorealLabyrinthMixin {

	@Shadow
	int wallBlockA;
	@Shadow
	int wallBlockB;
	@Shadow
	int brickBlockA;
	@Shadow
	int brickBlockB;
	@Shadow
	int slabBlock;

	@Unique
	private boolean isBoreal;

	@Unique
	private boolean isHot;

	@Inject(method = "generate", at = @At("HEAD"))
	public void generate(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		Biome biome = world.getBlockBiome(x, y, z);
		if (biome == Biomes.OVERWORLD_BOREAL_FOREST || biome == Biomes.OVERWORLD_MEADOW){
			this.wallBlockA = Block.basalt.id;
			this.wallBlockB = Block.cobbleBasalt.id;
			this.brickBlockA = Block.brickBasalt.id;
			this.brickBlockB = Block.brickBasalt.id;
			this.slabBlock = Block.slabPlanksOak.id;
			isBoreal = true;
		}

		if(biome == Biomes.OVERWORLD_CAATINGA || biome == Biomes.OVERWORLD_DESERT || biome == Biomes.OVERWORLD_OUTBACK || biome == Biomes.OVERWORLD_OUTBACK_GRASSY){
			isHot = true;
		}
	}
	@Inject(method = "pickMobSpawner(Ljava/util/Random;)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
	private void pickMobSpawner(Random random, CallbackInfoReturnable<String> cir) {
		int r = random.nextInt(2);
		if(isBoreal) {
			cir.setReturnValue("Spider");
//			switch (r) {
//				case 0:
//					cir.setReturnValue("ArmouredZombie");
//					break;
//				case 1:
//					cir.setReturnValue("Spider");
//					break;
//			}
		}
	}

	@Inject(method = "generateCorridor(Lnet/minecraft/core/world/World;Ljava/util/Random;IIIII)V", at = @At(value = "HEAD"))
	private void injectCobWeb(World world, Random random, int blockX, int blockY, int blockZ, int rot, int corridorIteration, CallbackInfo ci) {
		byte height = 2;
		int width = 2;
		int length = 2;

		for (int x = blockX - width; x <= blockX + width; ++x) {
			boolean xWallCheck = x == blockX - width || x == blockX + width;

			for (int y = blockY - height; y <= blockY + (height - 1); ++y) {
				boolean yWallCheck = y == blockY - height;

				for (int z = blockZ - length; z <= blockZ + length; ++z) {
					boolean zWallCheck = z == blockZ - length || z == blockZ + length;
					if (this.canReplace(world, x, y, z) && (!xWallCheck && !zWallCheck && !yWallCheck || world.getBlockId(x, y + 1, z) != 0 || random.nextInt(3) <= 0)) {
						if (rot == 0) {
							if (xWallCheck) {
								world.setBlockWithNotify(x, y, z, this.wallBlockA);
							} else if (z == blockZ + length) {
								world.setBlockWithNotify(x, y, z, this.wallBlockA);
							} else if (yWallCheck) {
								if (random.nextInt(3) == 0) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								} else {
									world.setBlockWithNotify(x, y, z, this.wallBlockA);
								}
							} else {
								world.setBlockWithNotify(x, y, z, 0);
							}
						} else if (rot == 1) {
							if (x == blockX - width) {
								world.setBlockWithNotify(x, y, z, this.wallBlockA);
							} else if (zWallCheck) {
								world.setBlockWithNotify(x, y, z, this.wallBlockA);
							} else if (yWallCheck) {
								if (random.nextInt(3) == 0) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								} else {
									world.setBlockWithNotify(x, y, z, this.wallBlockA);
								}
							} else {
								world.setBlockWithNotify(x, y, z, 0);
							}
						} else if (rot == 2) {
							if (xWallCheck) {
								world.setBlockWithNotify(x, y, z, this.wallBlockA);
							} else if (z == blockZ - length) {
								world.setBlockWithNotify(x, y, z, this.wallBlockA);
							} else if (yWallCheck) {
								if (random.nextInt(3) == 0) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								} else {
									world.setBlockWithNotify(x, y, z, this.wallBlockA);
								}
							} else {
								world.setBlockWithNotify(x, y, z, 0);
							}
						} else if (x == blockX + width) {
							world.setBlockWithNotify(x, y, z, this.wallBlockA);
						} else if (zWallCheck) {
							world.setBlockWithNotify(x, y, z, this.wallBlockA);
						} else if (yWallCheck) {
							if (random.nextInt(3) == 0) {
								world.setBlockWithNotify(x, y, z, this.wallBlockB);
							} else {
								world.setBlockWithNotify(x, y, z, this.wallBlockA);
							}
						} else {
							world.setBlockWithNotify(x, y, z, 0);
						}
						if(isBoreal){
							if (y == blockY + (height - 3) && !zWallCheck && !xWallCheck && random.nextInt(5) == 0) {
								world.setBlockWithNotify(x, y, z, Block.cobweb.id);
							}
						} else {
							if (y == blockY + (height - 1) && !zWallCheck && !xWallCheck && random.nextInt(20) == 0) {
								world.setBlockWithNotify(x, y, z, Block.cobweb.id);
							}
						}
					}
				}
			}
		}
	}

	@Unique
	private boolean canReplace(World world, int x, int y, int z) {
		if (y <= 11) {
			return false;
		} else if (world.getBlockId(x, y, z) != this.brickBlockA && world.getBlockId(x, y, z) != Block.planksOak.id && world.getBlockId(x, y, z) != Block.cobweb.id && world.getBlockId(x, y, z) != Block.bookshelfPlanksOak.id && world.getBlockId(x, y, z) != Block.mobspawner.id && world.getBlockId(x, y, z) != this.brickBlockB) {
			if (world.getBlockId(x, y, z) != Block.motionsensorIdle.id && world.getBlockId(x, y, z) != Block.dispenserCobbleStone.id && world.getBlockId(x, y, z) != Block.motionsensorActive.id) {
				return world.getBlockMaterial(x, y, z) == Material.grass || world.getBlockMaterial(x, y, z) == Material.dirt || world.getBlockMaterial(x, y, z) == Material.stone || world.getBlockMaterial(x, y, z) == Material.sand || world.getBlockMaterial(x, y, z) == Material.moss;
			} else {
				world.removeBlockTileEntity(x, y, z);
				world.setBlockWithNotify(x, y, z, 0);
				return true;
			}
		} else {
			return false;
		}
	}
}

