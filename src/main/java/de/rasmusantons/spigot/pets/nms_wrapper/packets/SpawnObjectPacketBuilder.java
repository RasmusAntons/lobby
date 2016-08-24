package de.rasmusantons.spigot.pets.nms_wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;

import java.util.UUID;

public class SpawnObjectPacketBuilder {
	private int entityId;
	private UUID uuid;
	private Location location;
	private ObjectType type;

	public SpawnObjectPacketBuilder setEntityId(int entityId) {
		this.entityId = entityId;
		return this;
	}

	public SpawnObjectPacketBuilder setUuid(UUID uuid) {
		this.uuid = uuid;
		return this;
	}

	public SpawnObjectPacketBuilder setLocation(Location location) {
		this.location = location;
		return this;
	}

	public SpawnObjectPacketBuilder setType(ObjectType type) {
		this.type = type;
		return this;
	}

	public PacketContainer build() {
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
		packet.getDoubles()
				.write(0, location.getX())
				.write(1, location.getY())
				.write(2, location.getZ());
		packet.getIntegers()
				.write(0, entityId)
				.write(6, type.typeId);
		packet.getUUIDs()
				.write(0, uuid);
		return packet;
	}

	public enum ObjectType {
		BOAT(1),
		ITEM_STACK(2),
		AREA_EFFECT_CLOUD(3),
		MINECART(10),
		ACTIVATED_TNT(50),
		ENDER_CRYSTAL(51),
		TIPPED_ARROW(60),
		SNOWBALL(61),
		EGG(62),
		FIRE_BALL(63),
		FIRE_CHARGE(64),
		THROWN_ENDERPEARL(65),
		WITHER_SKULL(66),
		SHULKER_BULLET(67),
		FALLING_OBJECT(70),
		ITEMFRAME(71),
		EYE_OF_ENDER(72),
		THROWN_POTION(73),
		THROWN_EXP_BOTTLE(75),
		FIREWORK_ROCKET(76),
		LEASH_KNOT(77),
		ARMOR_STAND(78),
		FISHING_FLOAT(90),
		SPECTRAL_ARROW(91),
		DRAGON_FIREBALL(93);

		public final int typeId;

		ObjectType(int typeId) {
			this.typeId = typeId;
		}
	}
}
