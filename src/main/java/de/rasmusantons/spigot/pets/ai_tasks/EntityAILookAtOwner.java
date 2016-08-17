package de.rasmusantons.spigot.pets.ai_tasks;

import de.rasmusantons.spigot.pets.nms_wrapper.WrappedEntityLiving;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class EntityAILookAtOwner extends EntityAIBase {
	private final WrappedEntityLiving entity;
	private CraftPlayer owner;

	public EntityAILookAtOwner(CraftLivingEntity craftEntity, Player owner) {
		this.owner = (CraftPlayer) owner;
		entity = new WrappedEntityLiving(craftEntity);
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
