package de.rasmusantons.spigot.pets.nms_wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.util.Vector;

public class EntityVelocityPacketBuilder {
	private int entityId;
	private Vector velocity;

	public EntityVelocityPacketBuilder setEntityId(int entityId) {
		this.entityId = entityId;
		return this;
	}

	/**
	 * @param velocity Velocity in blocks per second
	 * @return this builder for method chaining
	 */
	public EntityVelocityPacketBuilder setVelocity(Vector velocity) {
		this.velocity = velocity;
		return this;
	}

	public PacketContainer build() {
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_VELOCITY);
		Vector velocity = this.velocity.multiply(8000d / 20d);
		packet.getIntegers()
				.write(0, entityId)
				.write(1, (int) (velocity.getX()))
				.write(2, (int) (velocity.getY()))
				.write(3, (int) (velocity.getZ()));
		return packet;
	}
}
