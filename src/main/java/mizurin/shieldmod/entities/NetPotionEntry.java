package mizurin.shieldmod.entities;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.entity.EntityTracker;
import net.minecraft.core.net.entity.EntityTrackerEntry;
import net.minecraft.core.net.entity.ITrackedEntry;
import net.minecraft.core.net.entity.IVehicleEntry;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//This is used to render custom entities on servers
//Entity Poison Bottle.
public class NetPotionEntry
	implements IVehicleEntry<EntityPB>,
	ITrackedEntry<EntityPB> {
	@Override
	@NotNull
	public Class<EntityPB> getAppliedClass() {
		return EntityPB.class;
	}

	@Override
	public int getTrackingDistance() {
		return 64;
	}

	@Override
	public int getPacketDelay() {
		return 20;
	}

	@Override
	public boolean sendMotionUpdates() {
		return false;
	}

	@Override
	public void onEntityTracked(EntityTracker tracker, EntityTrackerEntry trackerEntry, EntityPB trackedObject) {
	}


	@Override
	public Entity getEntity(World world, double d, double e, double f, int i, boolean bl, double g, double h, double j, Entity entity, @Nullable CompoundTag compoundTag) {
		if (entity instanceof Mob) {
			return new EntityPB(world, (Mob) entity);
		} else {
			return new EntityPB(world, d, e, f);
		}
	}

	@Override
	public PacketAddEntity getSpawnPacket(EntityTrackerEntry tracker, EntityPB trackedObject) {
		System.out.println("POTION");
		Mob entityliving = trackedObject.owner;
		return new PacketAddEntity(trackedObject, 0, entityliving == null ? -1 : entityliving.id, trackedObject.xd, trackedObject.yd, trackedObject.zd);
	}
}
