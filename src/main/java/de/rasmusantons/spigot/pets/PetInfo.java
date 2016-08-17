package de.rasmusantons.spigot.pets;

import org.bukkit.entity.EntityType;

public class PetInfo {
	private String name;
	private EntityType type;

	public PetInfo(String name, EntityType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public EntityType getType() {
		return type;
	}
}
