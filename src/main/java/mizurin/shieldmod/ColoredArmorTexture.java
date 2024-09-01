package mizurin.shieldmod;

import net.minecraft.core.util.collection.NamespaceID;

import java.awt.*;

public class ColoredArmorTexture {
	protected NamespaceID armorTexture;
	protected Color color;

	public ColoredArmorTexture(NamespaceID armorTexture, Color color){
		this.armorTexture = armorTexture;
		this.color = color;
	}
	public NamespaceID getArmorTexture(){
		return armorTexture;
	}
	public Color getColor(){
		return color;
	}
}
