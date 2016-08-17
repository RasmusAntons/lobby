package de.rasmusantons.spigot.pets.ai_tasks;

import de.rasmusantons.spigot.pets.nms_wrapper.WrappedCreeper;
import org.bukkit.entity.Creeper;

import java.util.Random;

/**
 * Task for creepers that ignites them randomly (they will not explode).
 */
public class EntityAICreeperThreatening extends EntityAIBase {
	/**
	 * Average delay between two executions, in ticks
	 */
	private static final int AVG_INTERVAL = 20 * 120;

	private final Random random;
	private int time;
	private int coolDown = 0;
	private WrappedCreeper creeper;

	public EntityAICreeperThreatening(Creeper creeper) {
		this.creeper = new WrappedCreeper(creeper);
		random = new Random();
	}

	@Override
	public boolean shouldExecute() {
		if (coolDown > 0) {
			coolDown--;
			return false;
		}
		return random.nextInt(AVG_INTERVAL) == 0;
	}

	@Override
	public void startExecuting() {
		time = 25;
		creeper.setCreeperState(1);
	}

	@Override
	public boolean continueExecuting() {
		return time > 0;
	}

	@Override
	public void resetTask() {
		creeper.setCreeperState(-1);
		time = 0;
	}

	@Override
	public void updateTask() {
		time--;
		coolDown++;
		if (time < 0) {
			creeper.setCreeperState(-1);
		}
	}
}
