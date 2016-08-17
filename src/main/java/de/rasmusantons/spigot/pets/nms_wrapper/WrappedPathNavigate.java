package de.rasmusantons.spigot.pets.nms_wrapper;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.NavigationAbstract;
import net.minecraft.server.v1_10_R1.PathEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;

import javax.annotation.Nullable;

public class WrappedPathNavigate {
	private NavigationAbstract navigation;

	public WrappedPathNavigate(NavigationAbstract navigation) {
		this.navigation = navigation;
	}

	public NavigationAbstract getPathNavigate() {
		return navigation;
	}

	/**
	 * Sets the speed
	 */
	public void setSpeed(double speedIn) {
		navigation.a(speedIn);
	}

	/**
	 * Gets the maximum distance that the path finding will search in.
	 */
	public float getPathSearchRange() {
		return navigation.h();
	}

	/**
	 * Returns true if path can be changed by {@link de.rasmusantons.spigot.pets.nms_wrapper.WrappedPathNavigate#onUpdateNavigation()
	 * onUpdateNavigation()}
	 */
	public boolean canUpdatePathOnTimeout() {
		return navigation.i();
	}

	public void updatePath() {
		navigation.j();
	}

	/**
	 * Returns the path to the given coordinates. Args : x, y, z
	 *
	 * @Deprecated If you use this, you probably should wrap the nms class PathEntity.
	 */
	@Deprecated
	@Nullable
	public final PathEntity getPathToXYZ(double x, double y, double z) {
		return navigation.a(x, y, z);
	}

	/**
	 * Returns path to given BlockPos
	 *
	 * @Deprecated If you use this, you probably should wrap the nms classes PathEntity and BlockPosition.
	 */
	@Deprecated
	@Nullable
	public PathEntity getPathToPos(BlockPosition pos) {
		return navigation.a(pos);
	}

	/**
	 * Returns the path to the given EntityLiving. Args : entity
	 *
	 * @Deprecated If you use this, you probably should wrap the nms class PathEntity.
	 */
	@Deprecated
	@Nullable
	public PathEntity getPathToEntityLiving(WrappedEntity entityIn) {
		return navigation.a(entityIn.getEntity());
	}

	/**
	 * Try to find and set a path to XYZ. Returns true if successful. Args : x, y, z, speed
	 */
	public boolean tryMoveToXYZ(double x, double y, double z, double speedIn) {
		return navigation.a(x, y, z, speedIn);
	}

	/**
	 * Try to find and set a path to EntityLiving. Returns true if successful. Args : entity, speed
	 */
	public boolean tryMoveToEntityLiving(WrappedEntity entityIn, double speedIn) {
		return navigation.a(entityIn.getEntity(), speedIn);
	}

	/**
	 * Try to find and set a path to bukkit entity. Returns true if successful. Args : entity, speed
	 */
	public boolean tryMoveToEntityLiving(org.bukkit.entity.Entity entityIn, double speedIn) {
		return navigation.a(((CraftEntity) entityIn).getHandle(), speedIn);
	}

	/**
	 * Sets a new path. If it's diferent from the old path. Checks to adjust path for sun avoiding, and stores start
	 * coords. Args : path, speed
	 *
	 * @Deprecated If you use this, you probably should wrap the nms class PathEntity.
	 */
	@Deprecated
	public boolean setPath(@Nullable PathEntity pathentityIn, double speedIn) {
		return navigation.a(pathentityIn, speedIn);
	}

	/**
	 * gets the actively used PathEntity
	 *
	 * @Deprecated If you use this, you probably should wrap the nms class PathEntity.
	 */
	@Deprecated
	@Nullable
	public PathEntity getPath() {
		return navigation.k();
	}

	public void onUpdateNavigation() {
		navigation.l();
	}

	/**
	 * If null path or reached the end
	 */
	public boolean noPath() {
		return navigation.n();
	}

	/**
	 * sets active PathEntity to null
	 */
	public void clearPathEntity() {
		navigation.o();
	}

	/**
	 * Returns path to given BlockPos
	 *
	 * @Deprecated If you use this, you probably should wrap the nms class BlockPosition.
	 */
	@Deprecated
	public boolean canEntityStandOnPos(BlockPosition pos) {
		return navigation.b(pos);
	}
}
