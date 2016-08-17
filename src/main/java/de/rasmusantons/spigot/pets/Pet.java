package de.rasmusantons.spigot.pets;

import de.rasmusantons.spigot.pets.ai_tasks.EntityAIFollowOwner;
import de.rasmusantons.spigot.pets.ai_tasks.EntityAILookAtOwner;
import de.rasmusantons.spigot.pets.nms_wrapper.WrappedEntityLiving;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Pet {

	private Player owner;
	private CraftLivingEntity craftEntity;
	private WrappedEntityLiving entity;

	public Pet(Player owner, PetInfo petInfo) {
		this.owner = owner;
		Entity bukkitEntity = owner.getWorld().spawnEntity(owner.getLocation(), petInfo.getType());
		if (!(bukkitEntity instanceof LivingEntity))
			throw new IllegalArgumentException("Only living creatures can be used as pets.");
		LivingEntity livingEntity = (LivingEntity) bukkitEntity;
		livingEntity.setCustomName(petInfo.getName());
		livingEntity.setSilent(true);
		livingEntity.setCollidable(false);
		livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(.4);
		if (livingEntity instanceof Ageable) {
			((Ageable) livingEntity).setBaby();
		}
		craftEntity = (CraftLivingEntity) livingEntity;
		entity = new WrappedEntityLiving(craftEntity);
		entity.clearTasks();
		setTasks();
	}

	public void remove() {
		craftEntity.remove();
	}

	private void setTasks() {
		entity.addTask(0, new EntityAIFollowOwner(craftEntity, owner), WrappedEntityLiving.TaskType.GOAL);
		entity.addTask(1, new EntityAILookAtOwner(craftEntity, owner), WrappedEntityLiving.TaskType.GOAL);
	}
}
