package de.rasmusantons.spigot.pets;

import de.rasmusantons.spigot.pets.ai_tasks.EntityAICreeperThreatening;
import de.rasmusantons.spigot.pets.ai_tasks.EntityAIFollowOwner;
import de.rasmusantons.spigot.pets.ai_tasks.EntityAILookAtOwner;
import de.rasmusantons.spigot.pets.nms_wrapper.WrappedEntityLiving;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

public class Pet {

	private Player owner;
	private LivingEntity entity;
	private WrappedEntityLiving wrappedEntity;

	public Pet(Player owner, PetInfo petInfo) {
		this.owner = owner;
		Entity bukkitEntity = owner.getWorld().spawnEntity(owner.getLocation(), petInfo.getType());
		if (!(bukkitEntity instanceof LivingEntity))
			throw new IllegalArgumentException("Only living creatures can be used as pets.");
		entity = (LivingEntity) bukkitEntity;
		entity.setCustomName(petInfo.getName());
		entity.setSilent(true);
		entity.setCollidable(false);
		entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(.4);
		if (entity instanceof Ageable) {
			((Ageable) entity).setBaby();
		}
		wrappedEntity = new WrappedEntityLiving(entity);
		wrappedEntity.clearTasks();
		setTasks();
	}

	public void remove() {
		entity.remove();
	}

	private void setTasks() {
		wrappedEntity.addTask(0, new EntityAIFollowOwner(entity, owner), WrappedEntityLiving.TaskType.GOAL);
		wrappedEntity.addTask(1, new EntityAILookAtOwner(entity, owner), WrappedEntityLiving.TaskType.GOAL);
		if (entity instanceof Creeper) {
			wrappedEntity.addTask(1, new EntityAICreeperThreatening((Creeper) entity), WrappedEntityLiving.TaskType.GOAL);
		}
	}
}
