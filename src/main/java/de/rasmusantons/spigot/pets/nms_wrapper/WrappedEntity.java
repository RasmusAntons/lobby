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

	public double getX() {
		return entity.locX;
	}

	public double getY() {
		return entity.locY;
	}

	public double getZ() {
		return entity.locZ;
	}

	public WrappedWorld getWorld() {
		return new WrappedWorld(entity.getWorld());
	}
}
