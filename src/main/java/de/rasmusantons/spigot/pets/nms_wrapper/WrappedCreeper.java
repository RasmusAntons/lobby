package de.rasmusantons.spigot.pets.nms_wrapper;

import net.minecraft.server.v1_10_R1.EntityCreeper;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftCreeper;
import org.bukkit.entity.Creeper;

/**
 * Wraps an NMS creeper to hide obfuscated code
 */
public class WrappedCreeper extends WrappedEntityLiving {
	private final EntityCreeper creeper;

	public WrappedCreeper(Creeper creeper) {
		this((CraftCreeper) creeper);
	}

	public WrappedCreeper(CraftCreeper entity) {
		this(entity.getHandle());
	}

	public WrappedCreeper(EntityCreeper creeper) {
		super(creeper);
		this.creeper = creeper;
	}

	/**
	 * @return the current state of creeper, -1 is idle, 1 is 'in fuse'
	 * @see #setCreeperState(int)
	 */
	public int getCreeperState() {
		return creeper.df();
	}

	/**
	 * Set the creeper state
	 *
	 * @param state the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	public void setCreeperState(int state) {
		creeper.a(state);
	}

	public boolean hasIgnited() {
		return creeper.isIgnited();
	}

	/**
	 * Ignites the creeper (force it to explode)
	 */
	public void ignite() {
		creeper.dh();
	}

}
