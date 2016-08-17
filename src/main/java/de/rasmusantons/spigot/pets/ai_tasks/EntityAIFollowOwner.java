package de.rasmusantons.spigot.pets.ai_tasks;

import de.rasmusantons.spigot.pets.nms_wrapper.WrappedEntityLiving;
import de.rasmusantons.spigot.pets.nms_wrapper.WrappedPathNavigate;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class EntityAIFollowOwner extends EntityAIBase {
	public static final double START_FOLLOW_DIST = 3D * 3D;
	public static final double STOP_FOLLOW_DIST = 1.5D * 1.5D;
	public static final double TELEPORT_DIST = 12D * 12D;
	public static final double FOLLOW_SPEED = 1D;
	private CraftEntity craftEntity;
	private WrappedEntityLiving entity;
	private Player owner;
	private WrappedPathNavigate navigation;
	private int timeToRecalcPath;

	public EntityAIFollowOwner(CraftLivingEntity craftEntity, Player owner) {
		this.craftEntity = craftEntity;
		entity = new WrappedEntityLiving(craftEntity);
		this.owner = owner;
		navigation = entity.getNavigator();
		setMutexBits(TaskBit.LOOK | TaskBit.WALK);
	}


	@Override
	public boolean shouldExecute() {
		return owner.getLocation().distanceSquared((craftEntity.getLocation())) > START_FOLLOW_DIST;
	}

	@Override
	public boolean continueExecuting() {
		return !navigation.noPath() && owner.getLocation().distanceSquared((craftEntity.getLocation())) > STOP_FOLLOW_DIST;
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
		entity.getLookHelper().setLookPositionWithEntity(owner, 10.0F, entity.getVerticalFaceSpeed());
		if (--timeToRecalcPath > 0)
			return;
		timeToRecalcPath = 10;
		navigation.tryMoveToEntityLiving(owner, FOLLOW_SPEED);
		if (owner.getLocation().distanceSquared((craftEntity.getLocation())) > TELEPORT_DIST) {
			World nmsWorld = ((CraftPlayer) owner).getHandle().getWorld();
			for (int xOffset = -2; xOffset <= 2; ++xOffset) {
				for (int zOffset = -2; zOffset <= 2; ++zOffset) {
					Location loc = owner.getLocation().getBlock().getLocation().add(xOffset, 0, zOffset);
					if (!(xOffset == -2 || xOffset == 2 || zOffset == -2 || zOffset == 2))
						continue;
					if (!nmsWorld.getType(new BlockPosition(loc.getX(), loc.getY() - 1, loc.getZ())).getMaterial().isSolid())
						continue;
					if (owner.getWorld().getBlockAt(loc).isEmpty()
						&& owner.getWorld().getBlockAt(loc.add(0, 1, 0)).isEmpty())
						continue;
					craftEntity.teleport(loc);
					entity.getLookHelper().setLookPositionWithEntity(owner, 10.0F, entity.getVerticalFaceSpeed());
					navigation.clearPathEntity();
					return;
				}
			}
		}
	}
}
