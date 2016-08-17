package de.rasmusantons.spigot.pets.ai_tasks;


class TaskBit {
	private static int bit;

	static final int WALK = nextBit();
	static final int LOOK = nextBit();

	private static int nextBit() {
		return 1 << bit++;
	}
}
