package mizurin.shieldmod;

import com.mojang.nbt.CompoundTag;
import mizurin.shieldmod.interfaces.IColorable;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCraftingDynamic;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.util.helper.Color;
import goocraft4evr.nonamedyes.item.ModItems;
import turniplabs.halplibe.helper.ModVersionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Special thanks to UselessBullets for being extremely awesome https://github.com/UselessBullets/Lunacy/tree/7.1
//Mixin for custom recipe colors.
public class RecipeColor extends RecipeEntryCraftingDynamic {
	public static boolean nonamedyesOn = ModVersionHelper.isModPresent("nonamedyes");

	public static HashMap<Item, Map<Integer, Color>> dyeMap = new HashMap<>();
	private static final Map<Integer, Color> vanillaDye;
	private static final Map<Integer, Color> nonameDye;
	static {
		vanillaDye = new HashMap<>();
		nonameDye = new HashMap<>();
		ItemDye.field_31002_bk[7] = 13027014;
		ItemDye.field_31002_bk[15] = 16777215;
		for (int color = 0; color < 16; color++) {
			vanillaDye.put(color, new Color().setARGB(ItemDye.field_31002_bk[color]));
		}
		if (nonamedyesOn){
			//checks if nonamedyes is enabled, then adds them to the hasmap.
			nonameDye.put(0, new Color().setARGB(11540279)); //crimson
			nonameDye.put(1, new Color().setARGB(6627104)); //maroon
			nonameDye.put(2, new Color().setARGB(10005149)); //ash.gray
			nonameDye.put(3, new Color().setARGB(7236357)); // olive
			nonameDye.put(4, new Color().setARGB(13007915)); //ochre
			nonameDye.put(5, new Color().setARGB(14063170)); //buff L
			nonameDye.put(6, new Color().setARGB(3591599)); //verdigris
			nonameDye.put(7, new Color().setARGB(16772730)); //light.yellow L
			nonameDye.put(8, new Color().setARGB(3878540)); //indigo
			nonameDye.put(9, new Color().setARGB(13887232)); //xanthic
			nonameDye.put(10, new Color().setARGB(9191711)); //cinnamon
			nonameDye.put(11, new Color().setARGB(2171241)); //navy.blue
			nonameDye.put(12, new Color().setARGB(6631797)); //royal.purple
			nonameDye.put(13, new Color().setARGB(2788193)); //viridian

			dyeMap.put(ModItems.dye, nonameDye);
		}
		dyeMap.put(Item.dye, vanillaDye);
	}
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		ItemStack shieldStack = null;
		List<ItemStack> dyeStacks = new ArrayList<>();
		for (int x = 0; x < 3; ++x) {
			for (int y = 0; y < 3; ++y) {
				ItemStack stack = inventorycrafting.getItemStackAt(x, y);
				if (stack == null) continue;
				if (stack.getItem() instanceof IColorable) {
					shieldStack = stack;
				} else if (dyeMap.containsKey(stack.getItem())) {
					dyeStacks.add(stack);
				}
			}
		}
		if (shieldStack != null && !dyeStacks.isEmpty()) {
			ItemStack outStack = shieldStack.copy();
			int r = -1;
			int g = -1;
			int b = -1;
			int count = 0;
			if (outStack.getData().containsKey("dyed_color")){
				CompoundTag armorColorTag = outStack.getData().getCompound("dyed_color");
				r = armorColorTag.getShort("red");
				g = armorColorTag.getShort("green");
				b = armorColorTag.getShort("blue");
				count += 1;
			}

			for (ItemStack dyeStack : dyeStacks){
				Color color = dyeMap.getOrDefault(dyeStack.getItem(), vanillaDye).getOrDefault(dyeStack.getMetadata(), vanillaDye.get(0));
				if (r == -1 || g == -1 || b == -1){
					r = (int) (color.getRed() * 0.85f);
					g = (int) (color.getGreen() * 0.85f);
					b = (int) (color.getBlue() * 0.85f);
				} else {
					r += color.getRed();
					g += color.getGreen();
					b += color.getBlue();
				}
				count += 1;
			}

			if (count > 0){
				r /= count;
				g /= count;
				b /= count;
				CompoundTag colorTag = new CompoundTag();
				colorTag.putShort("red", (short) r);
				colorTag.putShort("green", (short) g);
				colorTag.putShort("blue", (short) b);
				outStack.getData().putCompound("dyed_color", colorTag);
			}

			outStack.stackSize = 1;
			return outStack;
		}
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public boolean matches(InventoryCrafting crafting) {
		ItemStack shieldStack = null;
		ItemStack dyeStack = null;
		for (int x = 0; x < 3; ++x) {
			for (int y = 0; y < 3; ++y) {
				ItemStack stack = crafting.getItemStackAt(x, y);
				if (stack == null) continue;
				if (stack.getItem() instanceof IColorable) {
					if (shieldStack != null) {
						return false;
					}
					shieldStack = stack;
					continue;
				}
				if (dyeMap.containsKey(stack.getItem())) {
					dyeStack = stack;
					continue;
				}
				return false;
			}
		}
		return shieldStack != null && dyeStack != null;
	}

	@Override
	public boolean matchesQuery(SearchQuery query) {
		return false;
	}

	@Override
	public ItemStack[] onCraftResult(InventoryCrafting crafting) {
		ItemStack[] returnStack = new ItemStack[9];
		for (int x = 0; x < 3; ++x) {
			for (int y = 0; y < 3; ++y) {
				ItemStack stack = crafting.getItemStackAt(x, y);
				if (stack == null) continue;
				--stack.stackSize;
				if (stack.stackSize > 0) continue;
				crafting.setSlotContentsAt(x, y, null);
			}
		}
		return returnStack;
	}
}
