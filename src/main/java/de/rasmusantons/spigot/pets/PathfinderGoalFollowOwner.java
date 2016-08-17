package de.rasmusantons.spigot.pets;

import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * a(int var1) === setMutexBits(int mutexBitsIn)
 * Sets a bitmask telling which other tasks may not run concurrently. The test is a simple bitwise AND - if it
 * yields zero, the two tasks may run concurrently, if not - they must run exclusively from each other.
 * <p>
 * h() === getMutexBits()
 * Get a bitmask telling which other tasks may not run concurrently. The test is a simple bitwise AND - if it yields
 * zero, the two tasks may run concurrently, if not - they must run exclusively from each other.
 */
public class PathfinderGoalFollowOwner extends PathfinderGoal {

	public static final double START_FOLLOW_DIST = 3D * 3D;
	public static final double STOP_FOLLOW_DIST = 1.5D * 1.5D;
	public static final double TELEPORT_DIST = 12D * 12D;
	public static final double FOLLOW_SPEED = 1D;
	private CraftEntity craftEntity;
	private EntityInsentient entity;
	private Player owner;
	private NavigationAbstract navigation;
	private int timeToRecalcPath;

	public PathfinderGoalFollowOwner(CraftEntity craftEntity, Player owner) {
		this.craftEntity = craftEntity;
		this.entity = ((EntityInsentient) craftEntity.getHandle());
		this.owner = owner;
		this.navigation = entity.getNavigation();
	}

	/**
	 * shouldExecute()
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean a() {
		return owner.getLocation().distanceSquared((craftEntity.getLocation())) > START_FOLLOW_DIST;
	}

	/**
	 * continueExecuting()
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean b() {
		//n() === noPath()
		return !navigation.n() && owner.getLocation().distanceSquared((craftEntity.getLocation())) > STOP_FOLLOW_DIST;
	}

	/**
	 * isInterruptible()
	 * Determine if this AI Task is interruptible by a higher (= lower value) priority task. All vanilla AITask have
	 * this value set to true.
	 */
	@Override
	public boolean g() {
		return true;
	}

	/**
	 * startExecuting()
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void c() {
		timeToRecalcPath = 0;
	}

	/**
	 * resetTask()
	 * Resets the task
	 */
	@Override
	public void d() {
		// o() === clearPathEntity()
		navigation.o();
	}

	/**
	 * updateTask()
	 * Updates the task
	 */
	@Override
	public void e() {
		// getControllerLook() === getLookHelper()
		//a(Entity var1, float var2, float var3) === setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch)
		//N() === getVerticalFaceSpeed()
		entity.getControllerLook().a(((CraftPlayer) owner).getHandle(), 10.0F, entity.N());
		if (--timeToRecalcPath > 0)
			return;
		timeToRecalcPath = 10;
		//a(Entity var1, double var2) === tryMoveToEntityLiving(Entity entityIn, double speedIn)
		navigation.a(((CraftPlayer) owner).getHandle(), FOLLOW_SPEED);
		if (owner.getLocation().distanceSquared((craftEntity.getLocation())) > TELEPORT_DIST) {
			World nmsWorld = ((CraftPlayer) owner).getHandle().getWorld();
			for (int xOffset = -2; xOffset <= 2; ++xOffset) {
				for (int zOffset = -2; zOffset <= 2; ++zOffset) {
					Location loc = owner.getLocation().getBlock().getLocation().add(xOffset, 0, zOffset);
					if (!(xOffset == -2 || xOffset == 2 || zOffset == -2 || zOffset == 2)) {
						continue;
					}
					if (!nmsWorld.getType(new BlockPosition(loc.getX(), loc.getY() - 1, loc.getZ())).getMaterial().isSolid()) {
						continue;
					}
					if (owner.getWorld().getBlockAt(loc).isEmpty()
						&& owner.getWorld().getBlockAt(loc.add(0, 1, 0)).isEmpty()) {
						craftEntity.teleport(loc);
						entity.getControllerLook().a(((CraftPlayer) owner).getHandle(), 10.0F, entity.N());
						// o() === clearPathEntity()
						navigation.o();
					}
				}
			}
		}
	}
}
