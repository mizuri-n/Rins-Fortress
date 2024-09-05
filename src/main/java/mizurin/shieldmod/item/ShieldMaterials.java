package mizurin.shieldmod.item;

import net.minecraft.core.item.material.ToolMaterial;

public class ShieldMaterials extends ToolMaterial {
	private float guard;
	public float getGuard(){
		return this.guard;
	}
	public ToolMaterial setGuard(float guard){
		this.guard = guard;
		return this;
	}
	public static final ToolMaterial TOOL_LEATHER = new ToolMaterial().setDurability(64).setDamage(-2).setEfficiency(2.0f, 0.85f);
	public static final ToolMaterial TOOL_WOOD = new ToolMaterial().setDurability(96).setDamage(-1).setEfficiency(2.0f, 0.80f);
	public static final ToolMaterial TOOL_STONE = new ToolMaterial().setDurability(128).setDamage(0).setEfficiency(2.0f, 0.70f);
	public static final ToolMaterial TOOL_IRON = new ToolMaterial().setDurability(256).setDamage(1).setEfficiency(2.0f, 0.60f);
	public static final ToolMaterial TOOL_GOLD = new ToolMaterial().setDurability(96).setDamage(-1).setEfficiency(2.0f, 0.50f).setSilkTouch(true);
	public static final ToolMaterial TOOL_DIAMOND = new ToolMaterial().setDurability(1536).setDamage(3).setEfficiency(2.0f, 0.40f);
	public static final ToolMaterial TOOL_STEEL = new ToolMaterial().setDurability(4608).setDamage(2).setEfficiency(2.0f, 0.60f);
	public static final ToolMaterial TOOL_TEAR = new ToolMaterial().setDurability(512).setDamage(1).setEfficiency(2.0F, 0.60F);
	//public static final ToolMaterial TOOL_TREASURE = new ToolMaterial().setDurability(512).setDamage(1).setEfficiency(2.0F, 0.5F);

	@Override
	public ToolMaterial setEfficiency(float efficiency, float guard) {
		this.guard = guard;
		return super.setEfficiency(efficiency, guard);
	}
}
