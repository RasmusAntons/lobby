package de.rasmusantons.spigot.pets.ai_tasks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import de.rasmusantons.spigot.pets.nms_wrapper.WrappedChicken;
import de.rasmusantons.spigot.pets.nms_wrapper.packets.EntityMetadataPacketBuilder;
import de.rasmusantons.spigot.pets.nms_wrapper.packets.EntityVelocityPacketBuilder;
import de.rasmusantons.spigot.pets.nms_wrapper.packets.SpawnObjectPacketBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

/**
 * Lets chicken pets randomly lay (fake) eggs. Also prevents real egg laying
 */
public class EntityAILayEgg extends EntityAIBase {
	private static final int BASE_EGG_ID = 100000;
	private static final int EGG_DESPAWN_TIME = 5 * 20;
	private final WrappedChicken chicken;
	private final Random random;
	private final ProtocolManager protocolManager;
	private Chicken bukkitChicken;
	private int ticksUntilNextEgg;
	private int eggDespawnTime;
	private int eggId;

	public EntityAILayEgg(Chicken chicken) {
		this.chicken = new WrappedChicken(chicken);
		bukkitChicken = chicken;
		this.chicken.setTimeUntilNextEgg(Integer.MAX_VALUE);
		random = new Random();
		protocolManager = ProtocolLibrary.getProtocolManager();
		nextEgg();
	}

	@Override
	public boolean shouldExecute() {
		return --ticksUntilNextEgg < 0;
	}

	@Override
	public void startExecuting() {
		eggId = BASE_EGG_ID + bukkitChicken.getEntityId();
		eggDespawnTime = EGG_DESPAWN_TIME;

		sendEggSpawnPacket();
		sendEggMetadataPacket();
		sendEggVelocityPacket();

		nextEgg();
	}

	@Override
	public boolean continueExecuting() {
		return --eggDespawnTime > 0;
	}

	@Override
	public void resetTask() {
		sendEggDespawnPacket();
	}

	private void nextEgg() {
		ticksUntilNextEgg = 60 * 20 + random.nextInt(60 * 20);
	}

	/**
	 * Sends an SPacketSpawnObject to all clients observing the chicken to spawn an ItemStack
	 */
	private void sendEggSpawnPacket() {
		Location eggLocation = bukkitChicken.getLocation().add(0, 0.1, 0);
		sendToChickenObservers(new SpawnObjectPacketBuilder()
				.setEntityId(eggId)
				.setType(SpawnObjectPacketBuilder.ObjectType.ITEM_STACK)
				.setLocation(eggLocation)
				.setUuid(UUID.randomUUID())
				.build());
	}

	/**
	 * Sends an SPacketEntityMetadata to all clients observing the chicken to make the ItemStack an egg
	 */
	private void sendEggMetadataPacket() {
		sendToChickenObservers(new EntityMetadataPacketBuilder()
				.setEntityId(eggId)
				.setMetadata(new EntityMetadataPacketBuilder.ItemMetadata()
						.setItem(new ItemStack(Material.EGG)))
				.build());
	}

	/**
	 * Sends an SPacketEntityVelocity to all clients observing the chicken.
	 */
	private void sendEggVelocityPacket() {
		Location chickenLoc = bukkitChicken.getLocation();
		chickenLoc.setPitch(0);
		chickenLoc.setYaw(((chicken.getRenderYawOffset()) % 360));
		Vector eggVelocity =
				chickenLoc.getDirection()
						.multiply(-2.5d)
						.setY(4.5d);
		sendToChickenObservers(new EntityVelocityPacketBuilder()
				.setEntityId(eggId)
				.setVelocity(eggVelocity)
				.build());
	}

	/**
	 * Sends an SPacketDestroyEntities to all clients observing the chicken to despawn the gake egg.
	 */
	private void sendEggDespawnPacket() {
		PacketContainer despawnPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
		despawnPacket.getIntegerArrays().write(0, new int[]{eggId});
		sendToChickenObservers(despawnPacket);
	}

	private void sendToChickenObservers(PacketContainer packet) {
		protocolManager.broadcastServerPacket(packet, bukkitChicken, false);
	}

}
