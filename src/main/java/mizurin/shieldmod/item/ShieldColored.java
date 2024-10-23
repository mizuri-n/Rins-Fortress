package mizurin.shieldmod.item;

import mizurin.shieldmod.interfaces.IColorable;
import net.minecraft.core.item.material.ToolMaterial;

//Colored Shield, same as Light Shield for movement speed but implements IColorable.
public class ShieldColored extends ShieldItem implements IColorable {
	public ShieldColored(String name, int id, ToolMaterial toolMaterial) {
		super(name, id, toolMaterial);
	}

}
