package mizurin.shieldmod;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;

public interface IThrownItem {
public ItemStack getThrownItem();
public ItemStack setThrownItem(ItemStack itemStack);
}
