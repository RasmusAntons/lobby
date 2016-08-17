package de.rasmusantons.spigot.pets.ai_tasks;

import de.rasmusantons.spigot.pets.nms_wrapper.WrappedEntityLiving;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class EntityAILookAtOwner extends EntityAIBase {
	private final WrappedEntityLiving entity;
	private Player owner;

	public EntityAILookAtOwner(LivingEntity entity, Player owner) {
		this.entity = new WrappedEntityLiving(entity);
		this.owner = owner;
		setMutexBits(TaskBit.LOOK | TaskBit.WALK);
	}

	@Override
	public boolean shouldExecute() {
		return true;
	}

	@Override
	public void updateTask() {
		entity.getLookHelper().setLookPositionWithEntity(owner, entity.getHorizontalFaceSpeed(), entity.getVerticalFaceSpeed());
	}

}
