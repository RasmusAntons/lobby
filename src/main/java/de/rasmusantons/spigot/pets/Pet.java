package de.rasmusantons.spigot.pets;

import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.entity.*;

import java.lang.reflect.Field;
import java.util.Set;

public class Pet {

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
