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
//Entity Fire.
public class NetFireEntry
	implements IVehicleEntry<EntityFire>,
	ITrackedEntry<EntityFire> {
	@Override
	@NotNull
	public Class<EntityFire> getAppliedClass() {
		return EntityFire.class;
	}

	@Override
	public int getTrackingDistance() {
		return 128;
	}

	@Override
	public int getPacketDelay() {
		return 1;
	}

	@Override
	public boolean sendMotionUpdates() {
		return true;
	}

	@Override
	public void onEntityTracked(EntityTracker tracker, EntityTrackerEntry trackerEntry, EntityFire trackedObject) {
	}

	@Override
	public Entity getEntity(World world, double d, double e, double f, int i, boolean bl, double g, double h, double j, Entity entity, @Nullable CompoundTag compoundTag) {
		EntityFire fire = new EntityFire(world, d, e, f, g, h, j);
		if (entity instanceof Mob) {
			fire.owner = (Mob) entity;
		}
		return fire;
	}

	@Override
	public PacketAddEntity getSpawnPacket(EntityTrackerEntry tracker, EntityFire trackedObject) {
		System.out.println("FIRE");
		Mob entityliving = trackedObject.owner;
		return new PacketAddEntity(trackedObject, 0, entityliving == null ? -1 : entityliving.id, trackedObject.xd, trackedObject.yd, trackedObject.zd);
	}
}

