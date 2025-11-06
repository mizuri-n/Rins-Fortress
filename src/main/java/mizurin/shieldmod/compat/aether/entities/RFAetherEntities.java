package mizurin.shieldmod.compat.aether.entities;

import net.minecraft.core.net.entity.NetEntityHandler;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.EntityHelper;

import static mizurin.shieldmod.ShieldMod.MOD_ID;
import static mizurin.shieldmod.ShieldMod.entityID;

public class RFAetherEntities {
	private static boolean hasInit = false;

	@SuppressWarnings("unused")
	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeAetherEntities();

		}
	}
	public static void initializeAetherEntities(){
		EntityHelper.createEntity(EntityHoming.class, NamespaceID.getPermanent(MOD_ID, "homing"), null, "homingMass", ++entityID);
		NetEntityHandler.registerNetworkEntry(new NetValkEntry(), 8004);
	}
}
