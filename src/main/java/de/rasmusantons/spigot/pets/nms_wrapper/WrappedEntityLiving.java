package de.rasmusantons.spigot.pets.nms_wrapper;

import de.rasmusantons.spigot.pets.ai_tasks.EntityAIBase;
import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.Field;
import java.util.Set;

public class WrappedEntityLiving extends WrappedEntity {
	private EntityInsentient entity;

	public WrappedEntityLiving(EntityInsentient entity) {
		super(entity);
		this.entity = entity;
	}

	public WrappedEntityLiving(CraftLivingEntity entity) {
		this((EntityInsentient) entity.getHandle());
	}

	public WrappedEntityLiving(LivingEntity entity) {
		this((EntityInsentient) ((CraftLivingEntity) entity).getHandle());
	}

	@Override
	public EntityInsentient getEntity() {
		return entity;
	}

	public WrappedEntityLookHelper getLookHelper() {
		return new WrappedEntityLookHelper(entity.getControllerLook());
	}

	public WrappedPathNavigate getNavigator() {
		return new WrappedPathNavigate(entity.getNavigation());
	}

	public int getVerticalFaceSpeed() {
		return entity.N();
	}

	public int getHorizontalFaceSpeed() {
		return entity.cJ();
	}

	private Set getTasks(TaskType type, boolean executing) {
		try {
			Field goalField = PathfinderGoalSelector.class.getDeclaredField(executing ? "b" : "c");
			goalField.setAccessible(true);
			switch (type) {
				case GOAL:
					return (Set) goalField.get(entity.goalSelector);
				case TARGET:
					return (Set) goalField.get(entity.targetSelector);
				default:
					throw new AssertionError("invalid TaskType: " + type.toString());
			}
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public void clearTasks() {
		for (TaskType type : TaskType.values()) {
			getTasks(type, false).clear();
			getTasks(type, true).clear();
		}
	}

	public void addTask(int priority, EntityAIBase task, TaskType type) {
		switch (type) {
			case GOAL:
				entity.goalSelector.a(priority, task);
				return;
			case TARGET:
				entity.targetSelector.a(priority, task);
		}
	}

	public enum TaskType {GOAL, TARGET}

}
