package de.rasmusantons.spigot.pets.nms_wrapper;

import net.minecraft.server.v1_10_R1.EntityChicken;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftChicken;
import org.bukkit.entity.Chicken;

/**
 * Wraps an NMS chicken
 */
public class WrappedChicken extends WrappedEntityLiving {
	private EntityChicken chicken;

	public WrappedChicken(EntityChicken chicken) {
		super(chicken);
		this.chicken = chicken;
	}

	public WrappedChicken(CraftChicken chicken) {
		this(chicken.getHandle());
	}

	public WrappedChicken(Chicken chicken) {
		this((CraftChicken) chicken);
	}

	/**
	 * Sets the time until the chicken lays an egg
	 *
	 * @param time Time in ticks
	 */
	public void setTimeUntilNextEgg(int time) {
		chicken.bD = time;
	}
}
