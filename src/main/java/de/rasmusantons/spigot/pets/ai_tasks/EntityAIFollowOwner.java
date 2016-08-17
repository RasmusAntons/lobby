package de.rasmusantons.spigot.pets.ai_tasks;

import de.rasmusantons.spigot.pets.nms_wrapper.WrappedEntityLiving;
import de.rasmusantons.spigot.pets.nms_wrapper.WrappedPathNavigate;
import de.rasmusantons.spigot.pets.nms_wrapper.WrappedWorld;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class EntityAIFollowOwner extends EntityAIBase {
	public static final double START_FOLLOW_DIST = 3D * 3D;
	public static final double STOP_FOLLOW_DIST = 1.5D * 1.5D;
	public static final double TELEPORT_DIST = 12D * 12D;
	public static final double FOLLOW_SPEED = 1D;

	private final LivingEntity entity;
	private final WrappedEntityLiving wrappedEntity;
	private Player owner;
	private WrappedPathNavigate navigation;
	private int timeToRecalcPath;

	public EntityAIFollowOwner(LivingEntity entity, Player owner) {
		this.entity = entity;
		wrappedEntity = new WrappedEntityLiving(this.entity);
		this.owner = owner;
		navigation = wrappedEntity.getNavigator();
		setMutexBits(TaskBit.LOOK | TaskBit.WALK);
	}


	@Override
	public boolean shouldExecute() {
		return owner.getLocation().distanceSquared((entity.getLocation())) > START_FOLLOW_DIST;
	}

	@Override
	public boolean continueExecuting() {
		return !navigation.noPath() && owner.getLocation().distanceSquared((entity.getLocation())) > STOP_FOLLOW_DIST;
	}

	@Override
	public void startExecuting() {
		timeToRecalcPath = 0;
	}


	@Override
	public void resetTask() {
		navigation.clearPathEntity();
	}


	@Override
	public void updateTask() {
		wrappedEntity.getLookHelper().setLookPositionWithEntity(owner, 10.0F, wrappedEntity.getVerticalFaceSpeed());
		if (--timeToRecalcPath > 0)
			return;
		timeToRecalcPath = 10;
		navigation.tryMoveToEntityLiving(owner, FOLLOW_SPEED);
		if (owner.getLocation().distanceSquared((entity.getLocation())) > TELEPORT_DIST) {
			WrappedWorld world = new WrappedWorld(owner.getWorld());
			for (int xOffset = -2; xOffset <= 2; ++xOffset) {
				for (int zOffset = -2; zOffset <= 2; ++zOffset) {
					Location loc = owner.getLocation().getBlock().getLocation().add(xOffset, 0, zOffset);
					if (!(xOffset == -2 || xOffset == 2 || zOffset == -2 || zOffset == 2))
						continue;
					if (!world.isBlockSolid(loc.getX(), loc.getY() - 1, loc.getZ()))
						continue;
					if (owner.getWorld().getBlockAt(loc).isEmpty()
						&& owner.getWorld().getBlockAt(loc.add(0, 1, 0)).isEmpty())
						continue;
					entity.teleport(loc);
					wrappedEntity.getLookHelper().setLookPositionWithEntity(owner, 10.0F, wrappedEntity.getVerticalFaceSpeed());
					navigation.clearPathEntity();
					return;
				}
			}
		}
	}
}
