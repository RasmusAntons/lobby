package de.rasmusantons.spigot.pets.ai_tasks;

import net.minecraft.server.v1_10_R1.PathfinderGoal;

/**
 * Subclasses the NMS PathfinderGoal to hide obfuscated method names for subclasses.
 */
public abstract class EntityAIBase extends PathfinderGoal {

	@Override
	public final boolean a() {
		return shouldExecute();
	}

	/**
	 * @return whether the task should begin execution.
	 */
	public abstract boolean shouldExecute();


	@Override
	public final boolean b() {
		return continueExecuting();
	}

	/**
	 * @return whether an in-progress task should continue executing
	 */
	public boolean continueExecuting() {
		return super.b();
	}


	@Override
	public final boolean g() {
		return isInterruptable();
	}

	/**
	 * Determine if this AI Task is interruptable by a higher (= lower value) priority task. All vanilla AITask have
	 * this value set to true.
	 *
	 * @return whether this task is interruptable
	 */
	public boolean isInterruptable() {
		return super.g();
	}


	@Override
	public final void c() {
		startExecuting();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		super.c();
	}

	@Override
	public final void d() {
		resetTask();
	}

	/**
	 * Reset the task
	 */
	public void resetTask() {
		super.d();
	}

	@Override
	public final void a(int i) {
		super.a(i);
	}

	@Override
	public final int h() {
		return super.h();
	}

	/**
	 * Get a bitmask telling which other tasks may not run concurrently. The test is a simple bitwise AND - if it yields
	 * zero, the two tasks may run concurrently, if not - they must run exclusively from each other.
	 *
	 * @return bitmask
	 */
	public final int getMutexBits() {
		return this.h();
	}

	/**
	 * Sets a bitmask telling which other tasks may not run concurrently. The test is a simple bitwise AND - if it
	 * yields zero, the two tasks may run concurrently, if not - they must run exclusively from each other.
	 *
	 * @param bits bits
	 */
	protected final void setMutexBits(int bits) {
		this.a(bits);
	}

	@Override
	public final void e() {
		updateTask();
	}

	/**
	 * Update the task
	 */
	public void updateTask() {
		super.e();
	}
}
