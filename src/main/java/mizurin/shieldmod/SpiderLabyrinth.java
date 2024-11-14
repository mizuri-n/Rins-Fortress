package mizurin.shieldmod;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.entity.TileEntityDispenser;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class SpiderLabyrinth extends WorldFeature {
	int dungeonSize = 0;
	int dungeonLimit;
	int dungeonCount = 0;
	boolean treasureGenerated = false;
	boolean libraryGenerated = false;
	int wallBlockA;
	int wallBlockB;
	int brickBlockA;
	int brickBlockB;
	int slabBlock;
	public ItemStack treasureItem;
	public WeightedRandomBag<WeightedRandomLootObject> chestLoot;
	public WeightedRandomBag<WeightedRandomLootObject> dispenserLoot;
	public WeightedRandomBag<String> spawnerMonsters;

	public SpiderLabyrinth() {
			this.wallBlockA = Block.basalt.id;
			this.wallBlockB = Block.cobbleBasalt.id;
			this.brickBlockA = Block.brickBasalt.id;
			this.brickBlockB = Block.brickBasalt.id;
			this.slabBlock = Block.slabPlanksOak.id;
	}

	public boolean generate(World world, Random random, int x, int y, int z) {

		this.chestLoot = new WeightedRandomBag();
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.ingotIron.getDefaultStack(), 1, 6), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.ingotGold.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.sulphur.getDefaultStack(), 3, 8), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.diamond.getDefaultStack(), 1, 4), 2.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.foodAppleGold.getDefaultStack()), 1.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.dustRedstone.getDefaultStack(), 1, 4), 100.0);

		for(int i = 0; i < 9; ++i) {
			this.chestLoot.addEntry(new WeightedRandomLootObject(new ItemStack(Item.itemsList[Item.record13.id + i])), 1.0);
		}

		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.foodApple.getDefaultStack()), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Block.wool.getDefaultStack(), 1, 4), 100.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.handcannonLoaded.getDefaultStack()), 0.5);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.handcannonUnloaded.getDefaultStack()), 4.5);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Item.armorHelmetChainmail.getDefaultStack())).setRandomMetadata(Item.armorHelmetChainmail.getMaxDamage() / 2, Item.armorHelmetChainmail.getMaxDamage()), 20.0);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Item.armorChestplateChainmail.getDefaultStack())).setRandomMetadata(Item.armorChestplateChainmail.getMaxDamage() / 2, Item.armorChestplateChainmail.getMaxDamage()), 20.0);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Item.armorLeggingsChainmail.getDefaultStack())).setRandomMetadata(Item.armorLeggingsChainmail.getMaxDamage() / 2, Item.armorLeggingsChainmail.getMaxDamage()), 20.0);
		this.chestLoot.addEntry((new WeightedRandomLootObject(Item.armorBootsChainmail.getDefaultStack())).setRandomMetadata(Item.armorBootsChainmail.getMaxDamage() / 2, Item.armorBootsChainmail.getMaxDamage()), 20.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject(Item.ingotSteelCrude.getDefaultStack()), 10.0);
		this.chestLoot.addEntry(new WeightedRandomLootObject((ItemStack)null), 892.0);
		this.dispenserLoot = new WeightedRandomBag();
		this.dispenserLoot.addEntry(new WeightedRandomLootObject(Item.ammoArrow.getDefaultStack(), 5, 7), 300.0);
		this.dispenserLoot.addEntry(new WeightedRandomLootObject(Item.ammoArrowGold.getDefaultStack()), 10.0);
		this.dispenserLoot.addEntry(new WeightedRandomLootObject(Item.ammoChargeExplosive.getDefaultStack()), 0.5);
		this.dispenserLoot.addEntry(new WeightedRandomLootObject((ItemStack)null), 289.5);
		this.spawnerMonsters = new WeightedRandomBag();


		this.spawnerMonsters.addEntry("Spider", 3.0);
		this.spawnerMonsters.addEntry("ArmouredZombie", 1.0);

		if (this.canReplace(world, x, y, z)) {
			this.dungeonLimit = 1;
			this.generateBranch(world, random, x, y, z);
			return true;
		} else {
			return false;
		}
	}

	public void generateBranch(World world, Random random, int blockX, int blockY, int blockZ) {
		boolean generateTrapOnWall = false;

		int x;
		for(x = blockX - 2; x <= blockX + 2; ++x) {
			boolean xWallCheck = x == blockX - 2 || x == blockX + 2;

			for(int y = blockY - 2; y <= blockY + 1; ++y) {
				boolean yWallCheck = y == blockY - 2;

				for(int z = blockZ - 2; z <= blockZ + 2; ++z) {
					boolean zWallCheck = z == blockZ - 2 || z == blockZ + 2;
					if (this.canReplace(world, x, y, z)) {
						if (xWallCheck && zWallCheck) {
							world.setBlockWithNotify(x, y, z, random.nextInt(4) == 0 ? this.brickBlockB : this.brickBlockA);
						} else if (!xWallCheck && !zWallCheck) {
							if (yWallCheck) {
								world.setBlockWithNotify(x, y, z, this.wallBlockB);
							} else {
								world.setBlockWithNotify(x, y, z, 0);
							}
						} else {
							world.setBlockWithNotify(x, y, z, this.wallBlockB);
						}

						if (!generateTrapOnWall && (xWallCheck || zWallCheck) && (x == blockZ || z == blockZ) && y == blockY) {
							world.setBlockWithNotify(x, y, z, Block.motionsensorIdle.id);
							world.setBlockWithNotify(x, y - 1, z, Block.dispenserCobbleStone.id);
							TileEntityDispenser tileEntityDispenser = (TileEntityDispenser)world.getBlockTileEntity(x, blockY - 1, z);

							for(int k4 = 0; k4 < 3; ++k4) {
								ItemStack itemstack = this.pickDispenserLootItem(random);
								if (itemstack != null) {
									tileEntityDispenser.setInventorySlotContents(random.nextInt(tileEntityDispenser.getSizeInventory()), itemstack);
								}
							}

							generateTrapOnWall = true;
						}
					}
				}
			}
		}

		if (this.dungeonSize < 10) {
			++this.dungeonSize;
			x = random.nextInt(4);

			for(int i = 0; i <= x; ++i) {
				this.createCorridor(world, random, blockX, blockY, blockZ, random.nextInt(4), 0);
			}
		}

	}

	public void generateDrop(World world, Random random, int blockX, int blockY, int blockZ) {
		if (random.nextBoolean()) {
			this.generateDungeon(world, random, blockX, blockY, blockZ, false);
		}

		int dropHeight = random.nextInt(10) + 10;

		int x;
		int y;
		for(x = blockX - 2; x <= blockX + 2; ++x) {
			for(y = blockY - dropHeight; y <= blockY + 1; ++y) {
				for(int z = blockZ - 2; z <= blockZ + 2; ++z) {
					boolean xWallCheck = x == blockX - 2 || x == blockX + 2;
					boolean zWallCheck = z == blockZ - 2 || z == blockZ + 2;
					boolean yWallCheck = y == blockY - dropHeight;
					if (this.canReplace(world, x, y, z)) {
						if (!xWallCheck && !zWallCheck) {
							if (yWallCheck) {
								world.setBlockWithNotify(x, y, z, this.wallBlockB);
								if (this.dungeonSize >= 10) {
									world.setBlockWithNotify(x, y + 1, z, Block.spikes.id);
								}
							} else if (x != blockX && z != blockZ && random.nextInt(20) == 0 && world.getBlockId(x, y + 1, z) != this.slabBlock) {
								world.setBlockWithNotify(x, y, z, this.slabBlock);
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
			x = random.nextInt(4) + 1;

			for(y = 1; y <= x; ++y) {
				this.createCorridor(world, random, blockX, blockY - (dropHeight - 2), blockZ, random.nextInt(4), 0);
			}
		}

	}

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

	private void generateCorridor(World world, Random random, int blockX, int blockY, int blockZ, int rot, int corridorIteration) {
		byte height = 2;
		int width = 2;
		int length = 2;

		for(int x = blockX - width; x <= blockX + width; ++x) {
			boolean xWallCheck = x == blockX - width || x == blockX + width;

			for(int y = blockY - height; y <= blockY + (height - 1); ++y) {
				boolean yWallCheck = y == blockY - height;

				for(int z = blockZ - length; z <= blockZ + length; ++z) {
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

						if (y == blockY + (height - 3) && !zWallCheck && !xWallCheck && random.nextInt(5) == 0) {
							world.setBlockWithNotify(x, y, z, Block.cobweb.id);
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
			int x;
			int y;
			for(x = blockX - size; x <= blockX + size; ++x) {
				for(y = blockY - 2; y <= blockY + 2; ++y) {
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
						}
					}
				}
			}

			x = blockX + random.nextInt(size - 1) - (size - 1);
			y = blockZ + random.nextInt(size - 1) - (size - 1);
			if (this.canReplace(world, x, blockY - 2, y)) {
				world.setBlockWithNotify(x, blockY - 1, y, Block.chestPlanksOak.id);
				TileEntityChest tileentitychest = (TileEntityChest)world.getBlockTileEntity(x, blockY - 1, y);

				for(int k4 = 0; k4 < 10; ++k4) {
					ItemStack itemstack = this.pickCheckLootItem(random);
					if (itemstack != null) {
						tileentitychest.setInventorySlotContents(random.nextInt(tileentitychest.getSizeInventory()), itemstack);
					}
				}
			}

			if (doSpawner) {
				world.setBlockWithNotify(blockX, blockY - 1, blockZ, Block.mobspawner.id);
				TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getBlockTileEntity(blockX, blockY - 1, blockZ);
				if (tileentitymobspawner != null) {
					tileentitymobspawner.setMobId(this.pickMobSpawner(random));
				}
			}

		}
	}

	private void generateLibrary(World world, Random random, int blockX, int blockY, int blockZ) {
		int size = 10;
		int sideFacing = random.nextInt(2);
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
									world.setBlockWithNotify(x, y, z, Block.planksOak.id);
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
										world.setBlockWithNotify(x, y, z, Block.bookshelfPlanksOak.id);
									} else if (random.nextInt(5) == 0) {
										world.setBlockWithNotify(x, y, z, Block.logOakMossy.id);
									} else {
										world.setBlockWithNotify(x, y, z, Block.logOak.id);
									}
								} else {
									world.setBlockWithNotify(x, y, z, 0);
								}
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

			this.generateDrop(world, random, blockX, blockY, blockZ);
		}
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
			if (this.dungeonSize == 10 && !this.libraryGenerated) {
				this.libraryGenerated = true;
				this.generateLibrary(world, random, blockX + dx * 4, blockY, blockZ + dz * 4);
			} else {
				this.generateDungeon(world, random, blockX + dx * 4, blockY, blockZ + dz * 4, true);
			}
		}

	}

	private ItemStack pickDispenserLootItem(Random random) {
		return ((WeightedRandomLootObject)this.dispenserLoot.getRandom(random)).getItemStack(random);
	}

	private ItemStack pickCheckLootItem(Random random) {
		if (!this.treasureGenerated && this.dungeonSize > 7) {
			this.treasureGenerated = true;
			return this.treasureItem.copy();
		} else {
			return ((WeightedRandomLootObject)this.chestLoot.getRandom(random)).getItemStack(random);
		}
	}

	private String pickMobSpawner(Random random) {
		return (String)this.spawnerMonsters.getRandom(random);
	}
}
