package de.rasmusantons.spigot.pets;

import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.NavigationAbstract;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.entity.*;

import java.lang.reflect.Field;
import java.util.Set;

public class Pet {

	private final static float WALKING_SPEED = 1.25F;
	private final static float MAX_DEST_DIFF = 3F;
	private final static float TP_DIST = 16F;

	private Player owner;
	private CraftLivingEntity craftEntity;
	private Location destination;

	public Pet(Player owner, EntityType animalType, String name) {
		this.owner = owner;
		Entity entity = owner.getWorld().spawnEntity(owner.getLocation(), animalType);
		if (!(entity instanceof LivingEntity))
			throw new IllegalArgumentException("Only living creatures can be used as pets.");
		entity.setCustomName(name);
		entity.setSilent(true);
		((LivingEntity) entity).setCollidable(false);
		((LivingEntity) entity).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(.4);
		if (entity instanceof Ageable) {
			((Ageable) entity).setBaby();
		}
		craftEntity = (CraftLivingEntity) entity;
		clearGoals();
		setGoal();
	}

	public void remove() {
		craftEntity.remove();
	}

	public void moveTo(Location location) {
		if (destination != null && destination.distance(location) <= MAX_DEST_DIFF) {
			return;
		} else if (destination != null && destination.distance(location) > TP_DIST) {
			craftEntity.teleport(location);
		}
		destination = location;
		NavigationAbstract navigation = ((EntityInsentient) craftEntity.getHandle()).getNavigation();
		navigation.a(navigation.a(location.getX(), location.getY(), location.getZ()), WALKING_SPEED);
	}

	public void moveTo(Player player) {
		moveTo(player.getLocation());
	}

	private Set getGoals(GoalFieldType goalFieldType) {
		try {
			EntityInsentient entityInsentient = (EntityInsentient) craftEntity.getHandle();
			PathfinderGoalSelector goalSelector = null;
			switch (goalFieldType) {
				case GOAL:
				case GOAL_EXE:
					goalSelector = entityInsentient.goalSelector;
					break;
				case TARGET:
				case TARGET_EXE:
					goalSelector = entityInsentient.targetSelector;
					break;
			}
			String fieldName = null;
			switch (goalFieldType) {
				case GOAL:
				case TARGET:
					fieldName = "b";
					break;
				case GOAL_EXE:
				case TARGET_EXE:
					fieldName = "c";
					break;
			}
			Field goalField = PathfinderGoalSelector.class.getDeclaredField(fieldName);
			goalField.setAccessible(true);
			return (Set) goalField.get(goalSelector);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private void setGoal() {
		EntityInsentient entityInsentient = (EntityInsentient) craftEntity.getHandle();
		PathfinderGoalSelector goalSelector = entityInsentient.goalSelector;
		goalSelector.a(0, new PathfinderGoalFollowOwner(craftEntity, owner));
	}

	private void clearGoals() {
		for (GoalFieldType goalFieldType : GoalFieldType.values()) {
			getGoals(goalFieldType).clear();
		}
	}

	private enum GoalFieldType {GOAL, GOAL_EXE, TARGET, TARGET_EXE}
}
