package de.rasmusantons.spigot.pets.nms_wrapper.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class EntityMetadataPacketBuilder {
	private int entityId;
	private EntityMetadata metadata;

	public EntityMetadataPacketBuilder setEntityId(int entityId) {
		this.entityId = entityId;
		return this;
	}

	public EntityMetadataPacketBuilder setMetadata(EntityMetadata metadata) {
		this.metadata = metadata;
		return this;
	}

	public PacketContainer build() {
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
		packet.getIntegers().write(0, entityId);
		metadata.writeTo(packet);
		return packet;
	}

	static abstract class EntityMetadata {
		protected void writeTo(PacketContainer packet) {

		}
	}

	/**
	 * Metadata for an item entity (dropped item on the ground)
	 */
	public static class ItemMetadata extends EntityMetadata {
		private static final int SLOT_INDEX = 6;
		private ItemStack item;

		public ItemMetadata setItem(ItemStack item) {
			this.item = item;
			return this;
		}

		@Override
		protected void writeTo(PacketContainer packet) {
			super.writeTo(packet);
			WrappedWatchableObject slot =
					new WrappedWatchableObject(
							new WrappedDataWatcher.WrappedDataWatcherObject(SLOT_INDEX,
									WrappedDataWatcher.Registry.getItemStackSerializer(true)),
							Optional.of(CraftItemStack.asNMSCopy(item)));
			packet.getWatchableCollectionModifier().write(0, Lists.newArrayList(slot));
		}
	}

}
