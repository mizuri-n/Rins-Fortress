package mizurin.shieldmod.item;

import mizurin.shieldmod.interfaces.IColorable;

//Colored Shield, same as Light Shield for movement speed but implements IColorable.
public class ShieldColored extends ShieldItem implements IColorable {
	public ShieldColored(String name, String namespaceID, int id, ShieldMaterials shieldMaterials) {
		super(name, namespaceID, id, shieldMaterials);
	}

}
