package de.rasmusantons.spigot.pets.nms_wrapper;

import net.minecraft.server.v1_10_R1.ControllerLook;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class WrappedEntityLookHelper {
	private ControllerLook lookHelper;

	public WrappedEntityLookHelper(ControllerLook lookHelper) {
		this.lookHelper = lookHelper;
	}

	public ControllerLook getEntityLookHelper() {
		return lookHelper;
	}

	/**
	 * Sets position to look at using entity
	 */
	public void setLookPositionWithEntity(WrappedEntity entityIn, float deltaYaw, float deltaPitch) {
		lookHelper.a(entityIn.getEntity(), deltaYaw, deltaPitch);
	}

	/**
	 * Sets position to look at using bukkit entity
	 */
	public void setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch) {
		lookHelper.a(((CraftEntity) entityIn).getHandle(), deltaYaw, deltaPitch);
	}

	/**
	 * Sets position to look at
	 */
	public void setLookPosition(double x, double y, double z, float deltaYaw, float deltaPitch) {
		lookHelper.a(x, y, z, deltaYaw, deltaPitch);
	}

	/**
	 * Updates look
	 */
	public void onUpdateLook() {
		lookHelper.a();
	}

	public boolean getIsLooking() {
		return lookHelper.b();
	}

	public double getLookPosX() {
		return lookHelper.e();
	}

	public double getLookPosY() {
		return lookHelper.f();
	}

	public double getLookPosZ() {
		return lookHelper.g();
	}
}
