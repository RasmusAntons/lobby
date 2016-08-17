package de.rasmusantons.spigot.pets.nms_wrapper;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;

public class WrappedWorld {
	private World world;

	public WrappedWorld(World world) {
		this.world = world;
	}

	public WrappedWorld(CraftWorld world) {
		this(world.getHandle());
	}

	public WrappedWorld(org.bukkit.World world) {
		this((CraftWorld) world);
	}

	public World getWorld() {
		return world;
	}

	public boolean isBlockSolid(double x, double y, double z) {
		return world.getType(new BlockPosition(x, y, z)).getMaterial().isSolid();
	}
}
