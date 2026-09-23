package mizurin.shieldmod;

import java.util.Random;

import mizurin.shieldmod.blocks.RFBlocks;
import mizurin.shieldmod.item.RFItems;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.entity.TileEntityDispenser;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.block.entity.TileEntityStatue;
import net.minecraft.core.block.entity.TileEntityStatue.Pose;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.pos.TilePos;
import org.jetbrains.annotations.Nullable;

public class WorldFeatureSpiderLabyrinth extends WorldFeature {
	int dungeonSize = 0;
	int dungeonLimit;
	int dungeonCount = 0;
	boolean treasureGenerated = false;
	boolean libraryGenerated = false;
	boolean isCold = false;
	int wallBlockA;
	int wallBlockB;
	int brickBlockA;
	int brickBlockB;
	int slabBlock;
	int pressurePlateBlock;
	public ItemStack treasureItem;
	public WeightedRandomBag<WeightedRandomLootObject> chestLoot;
	public WeightedRandomBag<WeightedRandomLootObject> dispenserLoot;
	public WeightedRandomBag<String> spawnerMonsters;
	public static final int wallTrapChanceInv = 32;
	public static final int floorTrapChanceInv = 64;
	private int @Nullable [] platformPos;
	private int @Nullable [] lastDungeon;

	public void WorldFeatureLabyrinth() {
		this.wallBlockA = Blocks.BASALT.id();
		this.wallBlockB = Blocks.COBBLE_BASALT.id();
		this.brickBlockA = Blocks.BRICK_BASALT.id();
		this.brickBlockB = Blocks.BRICK_BASALT.id();
		this.slabBlock = Blocks.SLAB_PLANKS_PAINTED.id();
		this.pressurePlateBlock = Blocks.PRESSURE_PLATE_COBBLE_STONE.id();
		this.platformPos = null;
		this.lastDungeon = null;
	}

	public boolean place(World world, Random random, int x, int y, int z) {

		this.chestLoot = new WeightedRandomBag<>();
		this.chestLoot.addEntry(new WeightedRandomLootObject(Items.INGOT_IRON.getDefaultStack(), 1, 6), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Items.INGOT_GOLD.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(RFBlocks.saplingApple.getDefaultStack(), 1, 3), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Items.DIAMOND.getDefaultStack(), 1, 4), 2.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Items.FOOD_APPLE_GOLD.getDefaultStack()), 2.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Items.DUST_REDSTONE.getDefaultStack(), 1, 4), 100.0);

		for(int i = 0; i < 9; ++i) {
			this.chestLoot.addEntry(new WeightedRandomLootObject(new ItemStack(Item.itemsList[Items.RECORD_13.id + i])), 1.0);
		}

		this.chestLoot.addEntry(new WeightedRandomLootObject(Items.FOOD_APPLE_GOLD.getDefaultStack()), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Blocks.WOOL.getDefaultStack(), 1, 2), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Items.HANDCANNON_LOADED.getDefaultStack()), 0.5);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Items.HANDCANNON_UNLOADED.getDefaultStack()), 4.5);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Items.ARMOR_HELMET_CHAINMAIL.getDefaultStack())).setRandomMetadata(Items.ARMOR_HELMET_CHAINMAIL.getMaxDamage() / 2, Items.ARMOR_HELMET_CHAINMAIL.getMaxDamage()), 20.0);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Items.ARMOR_CHESTPLATE_CHAINMAIL.getDefaultStack())).setRandomMetadata(Items.ARMOR_CHESTPLATE_CHAINMAIL.getMaxDamage() / 2, Items.ARMOR_CHESTPLATE_CHAINMAIL.getMaxDamage()), 20.0);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Items.ARMOR_LEGGINGS_CHAINMAIL.getDefaultStack())).setRandomMetadata(Items.ARMOR_LEGGINGS_CHAINMAIL.getMaxDamage() / 2, Items.ARMOR_LEGGINGS_CHAINMAIL.getMaxDamage()), 20.0);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Items.ARMOR_BOOTS_CHAINMAIL.getDefaultStack())).setRandomMetadata(Items.INGOT_STEEL_CRUDE.getMaxDamage() / 2, Items.INGOT_STEEL_CRUDE.getMaxDamage()), 20.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Items.INGOT_STEEL_CRUDE.getDefaultStack()), 10.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject((ItemStack)null), 892.0);
		this.dispenserLoot = new WeightedRandomBag<>();
		this.dispenserLoot.addEntry(new WeightedRandomLootObject(Items.AMMO_ARROW.getDefaultStack(), 5, 7), 300.0);
		this.dispenserLoot.addEntry(new WeightedRandomLootObject(Items.AMMO_ARROW_GOLD.getDefaultStack()), 10.0);
		this.dispenserLoot.addEntry(new WeightedRandomLootObject(Items.AMMO_CHARGE_EXPLOSIVE.getDefaultStack()), 0.5);
		this.dispenserLoot.addEntry(new WeightedRandomLootObject((ItemStack)null), 289.5);
		this.spawnerMonsters = new WeightedRandomBag<>();
		this.spawnerMonsters.addEntry("Spider", 3.0);
		this.spawnerMonsters.addEntry("ArmouredZombie", 1.0);
		int r = random.nextInt(5);
		switch (r) {
			case 0:
			case 1:
				this.treasureItem = (new ItemStack(RFItems.tearShield));
				break;
			case 2:
			case 3:
				this.treasureItem = (new ItemStack(RFItems.rockyHelmet));
				break;
			case 4:
				this.treasureItem = (new ItemStack(RFItems.regenAmulet));
				break;
		}

		if (this.canReplace(world, x, y, z)) {
			this.dungeonLimit = 1;
			this.generateBranch(world, random, x, y, z);
			if (!this.treasureGenerated && this.lastDungeon != null) {
				this.generateTrophyRoom(world, random, this.lastDungeon[1], this.lastDungeon[2], this.lastDungeon[3], this.lastDungeon[0]);
			}

			if (this.platformPos != null) {
				this.generateStatue(world, this.platformPos[0], this.platformPos[1], this.platformPos[2], this.platformPos[3]);
			}

			return true;
		} else {
			return false;
		}
	}

	public void generateBranch(World world, Random random, int blockX, int blockY, int blockZ) {
		int xDir = Direction.EAST.id;

		for(int x = blockX - 2; x <= blockX + 2; ++x) {
			boolean xWallCheck = x == blockX - 2 || x == blockX + 2;
			if (x == blockX) {
				xDir ^= 1;
			}

			for(int y = blockY - 2; y <= blockY + 1; ++y) {
				boolean yWallCheck = y == blockY - 2;
				int zDir = Direction.SOUTH.id;

				for(int z = blockZ - 2; z <= blockZ + 2; ++z) {
					boolean zWallCheck = z == blockZ - 2 || z == blockZ + 2;
					if (z == blockZ) {
						zDir ^= 1;
					}

					if (this.canReplace(world, x, y, z)) {
						int block = 0;
						if (xWallCheck && zWallCheck) {
							block = random.nextInt(4) == 0 ? this.brickBlockB : this.brickBlockA;
						} else if (xWallCheck || zWallCheck || yWallCheck) {
							block = this.wallBlockB;
						}

						world.setBlockWithNotify(x, y, z, block);
						switch (y - blockY) {
							case -1:
								if (block == 0 && random.nextInt(64) == 0) {
									this.placeFloorTrap(world, random, x, y, z);
								}
								break;
							case 0:
								if (block != 0 && random.nextInt(32) == 0) {
									this.placeWallTrap(world, random, x, y, z, xWallCheck ? xDir : zDir);
								}
						}
					}
				}
			}
		}

		if (this.dungeonSize < 10) {
			++this.dungeonSize;
			int corridorsToSpawn = random.nextInt(4);

			for(int i = 0; i <= corridorsToSpawn; ++i) {
				this.createCorridor(world, random, blockX, blockY, blockZ, random.nextInt(4), 0);
			}
		}

	}

	private void placeWallTrap(World world, Random random, int x, int y, int z, int dir) {
		world.setBlockAndMetadataWithNotify(x, y, z, Blocks.MOTION_SENSOR_IDLE.id(), dir);
		world.setBlockAndMetadataWithNotify(x, y - 1, z, Blocks.DISPENSER_COBBLE_STONE.id(), dir);
		TileEntityDispenser dispenser = (TileEntityDispenser)world.getTileEntity(x, y - 1, z);

		for(int i = 0; i < 3; ++i) {
			ItemStack itemstack = this.pickDispenserLootItem(random);
			if (itemstack != null) {
				dispenser.setItem(random.nextInt(dispenser.getContainerSize()), itemstack);
			}
		}

	}

	private void placeFloorTrap(World world, Random random, int x, int y, int z) {
		world.setBlockAndMetadataWithNotify(x, y - 1, z, Blocks.DISPENSER_COBBLE_STONE.id(), Direction.UP.id);
		world.setBlockRaw(x, y, z, this.pressurePlateBlock);
		TileEntityDispenser dispenser = (TileEntityDispenser)world.getTileEntity(x, y - 1, z);

		for(int i = 0; i < 3; ++i) {
			ItemStack itemstack = this.pickDispenserLootItem(random);
			if (itemstack != null) {
				dispenser.setItem(random.nextInt(dispenser.getContainerSize()), itemstack);
			}
		}

	}

	public void generateDrop(World world, Random random, int blockX, int blockY, int blockZ) {
		if (random.nextBoolean()) {
			this.generateDungeon(world, random, blockX, blockY, blockZ, false);
		}

		int dropHeight = random.nextInt(10) + 10;

		for(int x = blockX - 2; x <= blockX + 2; ++x) {
			for(int y = blockY - dropHeight; y <= blockY + 1; ++y) {
				for(int z = blockZ - 2; z <= blockZ + 2; ++z) {
					boolean xWallCheck = x == blockX - 2 || x == blockX + 2;
					boolean zWallCheck = z == blockZ - 2 || z == blockZ + 2;
					boolean yWallCheck = y == blockY - dropHeight;
					if (this.canReplace(world, x, y, z)) {
						if (!xWallCheck && !zWallCheck) {
							if (yWallCheck) {
								world.setBlockWithNotify(x, y, z, this.wallBlockB);
								if (this.dungeonSize >= 10) {
									world.setBlockWithNotify(x, y + 1, z, Blocks.SPIKES.id());
								}
							} else if (x != blockX && z != blockZ && random.nextInt(20) == 0 && world.getBlockId(x, y + 1, z) != this.slabBlock) {
								world.setBlockAndMetadataWithNotify(x, y, z, this.slabBlock, 12 << 4);
							} else {
								world.setBlockWithNotify(x, y, z, 0);
							}
						} else {
							world.setBlockWithNotify(x, y, z, this.wallBlockB);
						}
					}
				}
			}
		}

		if (this.dungeonSize < 10) {
			++this.dungeonSize;
			int corridorsToSpawn = random.nextInt(4) + 1;

			for(int i = 1; i <= corridorsToSpawn; ++i) {
				this.createCorridor(world, random, blockX, blockY - (dropHeight - 2), blockZ, random.nextInt(4), 0);
			}
		}

	}

	private boolean canReplace(World world, int x, int y, int z) {
		if (y <= 11) {
			return false;
		} else {
			int block = world.getBlockId(x, y, z);
			if (block != this.brickBlockA && block != Blocks.PLANKS_OAK_PAINTED.id() && block != Blocks.COBWEB.id() && block != Blocks.BOOKSHELF_PLANKS_OAK.id() && block != Blocks.MOBSPAWNER.id() && block != this.brickBlockB) {
				if (block != Blocks.MOTION_SENSOR_IDLE.id() && block != Blocks.MOTION_SENSOR_ACTIVE.id() && block != Blocks.DISPENSER_COBBLE_STONE.id()) {
					Material material = world.getBlockMaterial(x, y, z);
					return BlockTags.CAVES_CUT_THROUGH.appliesTo(Blocks.getBlock(block)) || material == Materials.GRASS || material == Materials.DIRT || material.isStone() || material == Materials.SAND || material == Materials.MOSS;
				} else {
					world.removeBlockTileEntity(x, y, z);
					if (block == Blocks.DISPENSER_COBBLE_STONE.id() && (world.getBlockMetadata(x, y, z) & 7) == Direction.UP.id && world.getBlockId(x, y + 1, z) == this.pressurePlateBlock) {
						world.setBlockWithNotify(x, y + 1, z, 0);
					}

					world.setBlockWithNotify(x, y, z, 0);
					return true;
				}
			} else {
				return false;
			}
		}
	}

	private void generateCorridor(World world, Random random, int blockX, int blockY, int blockZ, int rot, int corridorIteration) {
		int height = 2;
		int width = 2;
		int length = 2;
		int[] xz = new int[2];
		boolean[] xzWallCheck = new boolean[2];
		int[] xzDir = new int[2];
		int xOrZ = rot & 1;
		int zOrX = xOrZ ^ 1;
		int xzCenter = xOrZ != 0 ? blockX : blockZ;
		byte var10000;
		switch (rot) {
			case 0 -> var10000 = 2;
			case 1 -> var10000 = -2;
			case 2 -> var10000 = -2;
			case 3 -> var10000 = 2;
			default -> var10000 = 0;
		}

		int xzWidth = var10000;
		xzDir[0] = Direction.EAST.id;

		int var23;
		for(xz[0] = blockX - 2; xz[0] <= blockX + 2; var23 = xz[0]++) {
			if (xz[0] == blockX) {
				xzDir[0] ^= 1;
			}

			xzWallCheck[0] = Math.abs(xz[0] - blockX) == 2;

			for(int y = blockY - 2; y <= blockY + 1; ++y) {
				boolean yWallCheck = y == blockY - 2;
				xzDir[1] = Direction.SOUTH.id;

				for(xz[1] = blockZ - 2; xz[1] <= blockZ + 2; var23 = xz[1]++) {
					if (xz[1] == blockZ) {
						xzDir[1] ^= 1;
					}

					xzWallCheck[1] = Math.abs(xz[1] - blockZ) == 2;
					if (this.canReplace(world, xz[0], y, xz[1])) {
						if (xzWallCheck[0] || xzWallCheck[1] || yWallCheck) {
							boolean isHollow = world.getBlockId(xz[0], y + 1, xz[1]) == 0;
							if (isHollow && random.nextInt(3) > 0) {
								continue;
							}
						}

						int block = 0;
						if (yWallCheck) {
							block = random.nextInt(3) == 0 ? this.wallBlockB : this.wallBlockA;
						} else if (xzWallCheck[xOrZ]) {
							block = this.wallBlockA;
						} else if (xz[zOrX] == xzCenter + xzWidth) {
							block = this.wallBlockA;
						}

						boolean isHollowSection = block == 0;
						if (isHollowSection && !xzWallCheck[0] && !xzWallCheck[1] && y == blockY + 2 - 1 && random.nextInt(20) == 0) {
							block = Blocks.COBWEB.id();
						}

						world.setBlockWithNotify(xz[0], y, xz[1], block);
						switch (y - blockY) {
							case -1:
								if (isHollowSection && random.nextInt(64) == 0) {
									this.placeFloorTrap(world, random, xz[0], y, xz[1]);
								}
								break;
							case 0:
								if (!isHollowSection && random.nextInt(32) == 0) {
									this.placeWallTrap(world, random, xz[0], y, xz[1], xzDir[xOrZ]);
								}
						}
						if (y == blockY + (height - 3) && !xzWallCheck[0] && !xzWallCheck[1] && random.nextInt(5) == 0) {
							world.setBlockWithNotify(xz[0], y, xz[1], Blocks.COBWEB.id());
						}
						if (y == blockY + (height - 2) && !xzWallCheck[0] && !xzWallCheck[1] && random.nextInt(20) == 0) {
							world.setBlockWithNotify(xz[0], y, xz[1], Blocks.COBWEB.id());
						}
					}
				}
			}
		}

		if (random.nextInt(2) == 0 && corridorIteration > 1) {
			if (random.nextInt(2) == 0) {
				this.generateBranch(world, random, blockX, blockY, blockZ);
			} else {
				this.generateDrop(world, random, blockX, blockY, blockZ);
			}
		} else if ((random.nextInt(2) != 0 || corridorIteration <= 1 || this.dungeonSize <= 3) && (this.dungeonSize < 10 || this.dungeonCount >= this.dungeonLimit)) {
			if (random.nextInt(10) == 0 && corridorIteration > 1 && this.dungeonSize > 5) {
				return;
			}

			this.createCorridor(world, random, blockX, blockY, blockZ, rot, corridorIteration + 1);
		} else {
			this.createDungeon(world, random, blockX, blockY, blockZ, rot);
			++this.dungeonCount;
		}

	}

	private void createCorridor(World world, Random random, int blockX, int blockY, int blockZ, int rot, int size) {
		if (rot == 0) {
			this.generateCorridor(world, random, blockX, blockY, blockZ + 4, 0, size);
		}

		if (rot == 1) {
			this.generateCorridor(world, random, blockX - 4, blockY, blockZ, 1, size);
		}

		if (rot == 2) {
			this.generateCorridor(world, random, blockX, blockY, blockZ - 4, 2, size);
		}

		if (rot == 3) {
			this.generateCorridor(world, random, blockX + 4, blockY, blockZ, 3, size);
		}

	}

	private void generateDungeon(World world, Random random, int blockX, int blockY, int blockZ, boolean doSpawner) {
		int size = 4;
		if (blockY >= 10) {
			for(int x = blockX - size; x <= blockX + size; ++x) {
				for(int y = blockY - 2; y <= blockY + 2; ++y) {
					for(int z = blockZ - size; z <= blockZ + size; ++z) {
						boolean xWallCheck = x == blockX - size || x == blockX + size;
						boolean zWallCheck = z == blockZ - size || z == blockZ + size;
						boolean yWallCheck = y == blockY - 2;
						if (this.canReplace(world, x, y, z)) {
							if (!xWallCheck && !zWallCheck) {
								if (yWallCheck) {
									if (random.nextInt(5) == 0) {
										world.setBlockWithNotify(x, y, z, this.wallBlockB);
									} else {
										world.setBlockWithNotify(x, y, z, this.wallBlockA);
									}
								} else {
									world.setBlockWithNotify(x, y, z, 0);
								}
							} else {
								world.setBlockWithNotify(x, y, z, this.wallBlockB);
							}
							if (y == blockY + (3 - 3) && !zWallCheck && !xWallCheck && random.nextInt(5) == 0) {
								world.setBlockWithNotify(x, y, z, Blocks.COBWEB.id());
							}
							if (y == blockY + (3 - 2) && !zWallCheck && !xWallCheck && random.nextInt(20) == 0) {
								world.setBlockWithNotify(x, y, z, Blocks.COBWEB.id());
							}
						}
					}
				}
			}

			int chestX = blockX + random.nextInt(size - 1) - (size - 1);
			int chestZ = blockZ + random.nextInt(size - 1) - (size - 1);
			if (this.canReplace(world, chestX, blockY - 2, chestZ)) {
				world.setBlockWithNotify(chestX, blockY - 1, chestZ, Blocks.CHEST_PLANKS_OAK.id());
				BlockLogicChest.setDefaultDirection(world, new TilePos(chestX, blockY - 1, chestZ));
				TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(chestX, blockY - 1, chestZ);

				for(int k4 = 0; k4 < 10; ++k4) {
					ItemStack itemstack = this.pickCheckLootItem(random);
					if (itemstack != null) {
						tileentitychest.setItem(random.nextInt(tileentitychest.getContainerSize()), itemstack);
					}
				}
			}

			if (doSpawner) {
				world.setBlockWithNotify(blockX, blockY - 1, blockZ, Blocks.MOBSPAWNER.id());
				TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(blockX, blockY - 1, blockZ);
				if (tileentitymobspawner != null) {
					tileentitymobspawner.setMobId(this.pickMobSpawner(random));
				}
			}

		}
	}

	private void generateLibrary(World world, Random random, int blockX, int blockY, int blockZ) {
		int size = 10;
		if (blockY >= 10) {
			for(int x = blockX - size; x <= blockX + size; ++x) {
				for(int y = blockY - 2; y <= blockY + 3; ++y) {
					for(int z = blockZ - size; z <= blockZ + size; ++z) {
						int xRoom = x - blockX + size;
						int zRoom = z - blockZ + size;
						boolean xWallCheck = x == blockX - size || x == blockX + size;
						boolean zWallCheck = z == blockZ - size || z == blockZ + size;
						boolean yWallCheck = y == blockY - 2;
						if (this.canReplace(world, x, y, z)) {
							if (xWallCheck) {
								if (zRoom % 4 != 0) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								} else {
									world.setBlockWithNotify(x, y, z, this.brickBlockA);
								}
							} else if (zWallCheck) {
								if (xRoom % 4 != 0) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								} else {
									world.setBlockWithNotify(x, y, z, this.brickBlockA);
								}
							} else if (yWallCheck) {
								if ((x <= blockX - 2 || x >= blockX + 2) && (z <= blockZ - 2 || z >= blockZ + 2)) {
									if (random.nextInt(5) == 0) {
										world.setBlockWithNotify(x, y, z, this.wallBlockB);
									} else {
										world.setBlockWithNotify(x, y, z, this.wallBlockA);
									}
								} else {
									world.setBlockAndMetadataWithNotify(x, y, z, Blocks.PLANKS_OAK_PAINTED.id(), 12);
								}

								if (x > blockX - 3 && x < blockX + 3 && z > blockZ - 3 && z < blockZ + 3) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								}
							} else if (y == blockY + 3) {
								if (random.nextInt(5) == 0) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								} else {
									world.setBlockWithNotify(x, y, z, this.wallBlockA);
								}
							} else if (x > blockX - size && x < blockX + size || z > blockZ - size && z < blockX + size) {
								if (xRoom % 4 != 0 && zRoom % 2 == 0 && (x <= blockX - 2 || x >= blockX + 2) && (z <= blockZ - 1 || z >= blockZ + 1) && y < blockY + 2) {
									if (xRoom % 2 == 0) {
										world.setBlockWithNotify(x, y, z, Blocks.BOOKSHELF_PLANKS_OAK.id());
									} else if (random.nextInt(5) == 0) {
										world.setBlockWithNotify(x, y, z, Blocks.LOG_PINE.id());
									} else {
										world.setBlockWithNotify(x, y, z, Blocks.LOG_PINE.id());
									}
								} else {
									boolean placesTrap = y == blockY - 1 && Math.min(Math.abs(x - blockX), Math.abs(z - blockZ)) >= 2 && random.nextInt(64) == 0;
									if (placesTrap) {
										this.placeFloorTrap(world, random, x, y, z);
									} else {
										world.setBlockWithNotify(x, y, z, 0);
									}
								}
							} else {
								world.setBlockWithNotify(x, y, z, 0);
							}

							if (zRoom % 2 == 0 && (x == blockX - 2 || x == blockX + 2) && (z == blockZ - 2 || z == blockZ + 2)) {
								world.setBlockWithNotify(x, y, z, this.brickBlockA);
							}
							if (y == blockY + (2) && !zWallCheck && !xWallCheck && random.nextInt(20) == 0) {
								world.setBlockWithNotify(x, y, z, Blocks.COBWEB.id());
							}
						}
					}
				}
			}

			this.generateDrop(world, random, blockX, blockY, blockZ);
		}
	}

	private void generateTrophyRoom(World world, Random random, int blockX, int blockY, int blockZ, int rot) {
		int size = 8;
		if (blockY >= 10) {
			for(int x = blockX - size; x <= blockX + size; ++x) {
				for(int y = blockY - 2; y <= blockY + 6; ++y) {
					for(int z = blockZ - size; z <= blockZ + size; ++z) {
						int xRoom = x - blockX + size;
						int zRoom = z - blockZ + size;
						boolean xWallCheck = x == blockX - size || x == blockX + size;
						boolean zWallCheck = z == blockZ - size || z == blockZ + size;
						boolean yWallCheck = y == blockY - 2;
						if (this.canReplace(world, x, y, z)) {
							if (xWallCheck) {
								if (zRoom % 4 != 0) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								} else {
									world.setBlockWithNotify(x, y, z, this.brickBlockA);
								}
							} else if (zWallCheck) {
								if (xRoom % 4 != 0) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								} else {
									world.setBlockWithNotify(x, y, z, this.brickBlockA);
								}
							} else if (yWallCheck) {
								if (x > blockX - 2 && x < blockX + 2 || z > blockZ - 2 && z < blockZ + 2) {
									if (x != blockX && z != blockZ) {
										world.setBlockAndMetadataWithNotify(x, y, z, this.slabBlock, 1);
									} else {
										world.setBlockWithNotify(x, y, z, this.isCold ? Blocks.OBSIDIAN.id() : Blocks.FLUID_LAVA_FLOWING.id());
									}
								} else if (random.nextInt(5) == 0) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								} else {
									world.setBlockWithNotify(x, y, z, this.wallBlockA);
								}

								if (x > blockX - 3 && x < blockX + 3 && z > blockZ - 3 && z < blockZ + 3) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								}
							} else if (y == blockY + 6) {
								if (random.nextInt(5) == 0) {
									world.setBlockWithNotify(x, y, z, this.wallBlockB);
								} else {
									world.setBlockWithNotify(x, y, z, this.wallBlockA);
								}
							} else if ((x <= blockX - size || x >= blockX + size) && (z <= blockZ - size || z >= blockX + size)) {
								world.setBlockWithNotify(x, y, z, 0);
							} else {
								world.setBlockWithNotify(x, y, z, 0);
							}

							if (zRoom % 2 == 0 && (x == blockX - 2 || x == blockX + 2) && (z == blockZ - 2 || z == blockZ + 2)) {
								world.setBlockWithNotify(x, y, z, this.brickBlockA);
							}
						}
					}
				}
			}

			this.platformPos = new int[]{rot, blockX, blockY, blockZ};
		}
	}

	private void generateStatue(World world, int rot, int blockX, int blockY, int blockZ) {
		for(int x = blockX - 1; x <= blockX + 1; ++x) {
			for(int z = blockZ - 1; z <= blockZ + 1; ++z) {
				if (x == blockX && z == blockZ) {
					world.setBlockAndMetadataWithNotify(x, blockY - 1, z, this.slabBlock, 1);
				} else {
					world.setBlockWithNotify(x, blockY - 1, z, this.slabBlock);
				}
			}
		}

		int meta = 0;
		switch (rot) {
			case 0 -> meta = 8;
			case 1 -> meta = 12;
			case 2 -> meta = 0;
			case 3 -> meta = 4;
		}

		world.setBlockAndMetadata(blockX, blockY, blockZ, Blocks.STATUE_PIGMAN_LOWER.id(), meta);
		world.setBlockAndMetadata(blockX, blockY + 1, blockZ, Blocks.STATUE_PIGMAN_UPPER.id(), meta);
		TileEntityStatue statue = (TileEntityStatue)world.getTileEntity(blockX, blockY, blockZ);
		Item treasureItem = this.treasureItem.getItem();
		if (treasureItem instanceof IArmorItem && ((IArmorItem)treasureItem).getArmorShape() instanceof HumanArmorShape) {
			statue.setItemInArmorSlot((HumanArmorShape)((IArmorItem)treasureItem).getArmorShape(), this.treasureItem.copy());
		} else {
			statue.setHeldItem(this.treasureItem.copy());
		}

		statue.setPose(Pose.LOOK_UP);
	}

	private void createDungeon(World world, Random random, int blockX, int blockY, int blockZ, int rot) {
		int dx = 0;
		int dz = 0;
		if (rot == 0) {
			dz = 1;
		}

		if (rot == 1) {
			dx = -1;
		}

		if (rot == 2) {
			dz = -1;
		}

		if (rot == 3) {
			dx = 1;
		}

		if (this.canReplace(world, blockX + dx * 5, blockY, blockZ + dz * 5)) {
			if (!this.treasureGenerated && this.dungeonSize == 10) {
				this.treasureGenerated = true;
				this.generateTrophyRoom(world, random, blockX + dx * 4, blockY, blockZ + dz * 4, rot);
			} else if (!this.libraryGenerated && random.nextInt(3) == 0) {
				this.libraryGenerated = true;
				this.generateLibrary(world, random, blockX + dx * 4, blockY, blockZ + dz * 4);
			} else {
				this.generateDungeon(world, random, blockX + dx * 4, blockY, blockZ + dz * 4, true);
				this.lastDungeon = new int[]{rot, blockX + dx * 4, blockY, blockZ + dx * 4};
			}
		}

	}

	private ItemStack pickDispenserLootItem(Random random) {
		return ((WeightedRandomLootObject)this.dispenserLoot.getRandom(random)).getItemStack(random);
	}

	private ItemStack pickCheckLootItem(Random random) {
		return ((WeightedRandomLootObject)this.chestLoot.getRandom(random)).getItemStack(random);
	}

	private String pickMobSpawner(Random random) {
		return (String)this.spawnerMonsters.getRandom(random);
	}
}
