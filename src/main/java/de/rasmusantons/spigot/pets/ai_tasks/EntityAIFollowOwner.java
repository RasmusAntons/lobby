package de.rasmusantons.spigot.pets.ai_tasks;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.NavigationAbstract;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class EntityAIFollowOwner extends EntityAIBase {
	public static final double START_FOLLOW_DIST = 3D * 3D;
	public static final double STOP_FOLLOW_DIST = 1.5D * 1.5D;
	public static final double TELEPORT_DIST = 12D * 12D;
	public static final double FOLLOW_SPEED = 1D;
	private CraftEntity craftEntity;
	private EntityInsentient entity;
	private Player owner;
	private NavigationAbstract navigation;
	private int timeToRecalcPath;

	public EntityAIFollowOwner(CraftEntity craftEntity, Player owner) {
		this.craftEntity = craftEntity;
		this.entity = ((EntityInsentient) craftEntity.getHandle());
		this.owner = owner;
		this.navigation = entity.getNavigation();
		setMutexBits(TaskBit.LOOK | TaskBit.WALK);
	}


	@Override
	public boolean shouldExecute() {
		return owner.getLocation().distanceSquared((craftEntity.getLocation())) > START_FOLLOW_DIST;
	}

	@Override
	public boolean continueExecuting() {
		//n() === noPath()
		return !navigation.n() && owner.getLocation().distanceSquared((craftEntity.getLocation())) > STOP_FOLLOW_DIST;
	}

	@Override
	public void startExecuting() {
		timeToRecalcPath = 0;
	}


	@Override
	public void resetTask() {
		// o() === clearPathEntity()
		navigation.o();
	}


	@Override
	public void updateTask() {
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
