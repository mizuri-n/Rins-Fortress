package mizurin.shieldmod.entities;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.net.entity.ITrackedEntry;
import net.minecraft.core.net.entity.IVehicleEntry;
import net.minecraft.core.net.packet.Packet23VehicleSpawn;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.EntityTracker;
import net.minecraft.server.entity.EntityTrackerEntry;
import org.jetbrains.annotations.NotNull;

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
	public Entity getEntity(World world, double x, double y, double z, int metadata, boolean hasVelocity, double xd, double yd, double zd, Entity owner) {
		EntityFire fire = new EntityFire(world, x, y, z, xd, yd, zd);
		if (owner instanceof EntityLiving) {
			fire.owner = (EntityLiving) owner;
		}
		return fire;
	}

	@Override
	public Packet23VehicleSpawn getSpawnPacket(EntityTrackerEntry tracker, EntityFire trackedObject) {
		System.out.println("FIRE");
		EntityLiving entityliving = trackedObject.owner;
		return new Packet23VehicleSpawn(trackedObject, 0, entityliving == null ? -1 : entityliving.id, trackedObject.xd, trackedObject.yd, trackedObject.zd);
	}
}

