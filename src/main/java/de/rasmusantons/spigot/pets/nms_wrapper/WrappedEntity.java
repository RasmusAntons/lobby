package de.rasmusantons.spigot.pets.nms_wrapper;

import net.minecraft.server.v1_10_R1.Entity;

public class WrappedEntity {
	private Entity entity;

	public WrappedEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
