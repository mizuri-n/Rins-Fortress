package mizurin.shieldmod.mixins;
import mizurin.shieldmod.item.Shields;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

//please god help me.
@Mixin(value = World.class, remap = false)
public class WorldMixin {

	@Unique
	World thisObject = (World)(Object)this;

	/**
	 * @author Rin
	 * @reason Leather armor reduce aggro range
	 */
	@Overwrite
	public EntityPlayer getClosestPlayer(double x, double y, double z, double radius) {
		double closestDistance = Double.POSITIVE_INFINITY;
		EntityPlayer entityplayer = null;
		if (radius < 0.0) {
			for (EntityPlayer entityPlayer1 : thisObject.players) {
				double currentDistance = entityPlayer1.distanceToSqr(x, y, z);
				if (!(currentDistance < closestDistance)) continue;
				closestDistance = currentDistance;
				entityplayer = entityPlayer1;
			}
		} else {
			double rSquared = radius * radius;
			for (EntityPlayer entityPlayer1 : thisObject.players) {
				double armorTotal = 16;
				double currentDistance = entityPlayer1.distanceToSqr(x, y, z);
				if (!(currentDistance < rSquared) || !(currentDistance < closestDistance)) continue;

				boolean plyInvis = false;

				ItemStack bootSlot = entityPlayer1.inventory.armorItemInSlot(0); //1
				ItemStack leggingsSlot = entityPlayer1.inventory.armorItemInSlot(1); //2
				ItemStack chestplateSlot = entityPlayer1.inventory.armorItemInSlot(2); //4
				ItemStack helmetSlot = entityPlayer1.inventory.armorItemInSlot(3); //1


				if (helmetSlot != null && helmetSlot.itemID == Shields.armorLeatherHelmet.id){
					armorTotal -= 1;
					plyInvis = true;
				} if (chestplateSlot != null && chestplateSlot.itemID == Shields.armorLeatherChest.id) {
					armorTotal -= 4;
					plyInvis = true;
				} if (leggingsSlot != null && leggingsSlot.itemID == Shields.armorLeatherLeg.id) {
					armorTotal -= 2;
					plyInvis = true;
				} if (bootSlot != null && bootSlot.itemID == Shields.armorLeatherBoot.id){
					armorTotal -= 1;
					plyInvis = true;
				}
				double rThirdSquared = (radius * armorTotal/16) * (radius * armorTotal/16);

				if(plyInvis & currentDistance < rThirdSquared || !plyInvis){
					entityplayer = entityPlayer1;
					closestDistance = currentDistance;
				}
			}
		}

		return entityplayer;
	}
}
