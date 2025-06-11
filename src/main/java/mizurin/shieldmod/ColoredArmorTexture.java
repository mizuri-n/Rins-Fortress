package mizurin.shieldmod;

import net.minecraft.core.util.collection.NamespaceID;


public class ColoredArmorTexture {
	protected NamespaceID armorTexture;
	protected int color;

	public ColoredArmorTexture(NamespaceID armorTexture, int color){
		this.armorTexture = armorTexture;
		this.color = color;
	}
	public NamespaceID getArmorTexture(){
		return armorTexture;
	}
	public int getColor(){
		return color;
	}
}
