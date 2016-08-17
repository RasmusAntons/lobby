package de.rasmusantons.spigot.pets.ai_tasks;

import net.minecraft.server.v1_10_R1.EntityInsentient;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class EntityAILookAtOwner extends EntityAIBase {
	private final EntityInsentient nmsPet;
	private CraftPlayer owner;

	public EntityAILookAtOwner(CraftLivingEntity pet, Player owner) {
		this.owner = (CraftPlayer) owner;
		nmsPet = (EntityInsentient) pet.getHandle();
		setMutexBits(TaskBit.LOOK | TaskBit.WALK);
	}

	@Override
	public boolean shouldExecute() {
		return true;
	}

	@Override
	public void updateTask() {
		nmsPet.getControllerLook().a(owner.getHandle(), nmsPet.cJ(), nmsPet.N());
	}

}
