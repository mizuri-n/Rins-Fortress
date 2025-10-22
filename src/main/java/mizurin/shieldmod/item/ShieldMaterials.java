package mizurin.shieldmod.item;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.item.tool.ItemToolSword;
import teamport.aether.mixin.accessors.ItemToolSwordAccessor;

public class ShieldMaterials extends ToolMaterial {

	private float guard;
	public float getGuard(){
		return this.guard;
	}

	public ToolMaterial setGuard(float guard){
		this.guard = guard;
		return this;
	}

	//ToolMaterials for shields. Haste efficiency is overridden for the shield's block percent. ex: 0.85 is a 15% damage reduction.
	public static final ShieldMaterials TOOL_LEATHER = (ShieldMaterials) new ShieldMaterials().setGuard(0.85f).setDurability(64).setDamage(-1);
	public static final ShieldMaterials TOOL_WOOD = (ShieldMaterials) new ShieldMaterials().setGuard(0.80f).setDurability(96).setDamage(0);
	public static final ShieldMaterials TOOL_STONE = (ShieldMaterials) new ShieldMaterials().setGuard(0.70f).setDurability(128).setDamage(1);
	public static final ShieldMaterials TOOL_IRON = (ShieldMaterials) new ShieldMaterials().setGuard(0.60f).setDurability(384).setDamage(2);
	public static final ShieldMaterials TOOL_GOLD = (ShieldMaterials) new ShieldMaterials().setGuard(0.50f).setDurability(256).setDamage(1).setSilkTouch(true);
	public static final ShieldMaterials TOOL_DIAMOND = (ShieldMaterials) new ShieldMaterials().setGuard(0.40f).setDurability(1536).setDamage(4);
	public static final ShieldMaterials TOOL_STEEL = (ShieldMaterials) new ShieldMaterials().setGuard(0.60f).setDurability(4608).setDamage(3);
	public static final ShieldMaterials TOOL_TEAR = (ShieldMaterials) new ShieldMaterials().setGuard(0.60f).setDurability(512).setDamage(2);
	public static final ShieldMaterials TOOL_VALK = (ShieldMaterials) new ShieldMaterials().setGuard(0.75f).setDurability(768).setDamage(2);
	public static final ShieldMaterials TOOL_SKY = (ShieldMaterials) new ShieldMaterials().setGuard(0.80f).setDurability(96).setDamage(0).setBlockHitDelay(4);
	public static final ShieldMaterials TOOL_HOLYSTONE = (ShieldMaterials) new ShieldMaterials().setGuard(0.70f).setDurability(128).setDamage(1);
	public static final ShieldMaterials TOOL_ZANITE = (ShieldMaterials) new ShieldMaterials().setGuard(0.60f).setDurability(384).setDamage(2);

	public static boolean isHoldingSkyRootTool(Player player) {
		ItemStack held = player.getHeldItem();
		if (held == null)
			return false;
		else if (held.getItem() instanceof ItemTool && (((ItemTool) held.getItem()).getMaterial() == ShieldMaterials.TOOL_SKY)) {
			return true;
		} else
			return held.getItem() instanceof ItemToolSword && ((ItemToolSwordAccessor) held.getItem()).getMaterial() == ShieldMaterials.TOOL_SKY;
	}
}
