package de.rasmusantons.spigot.pets;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class ZooKeeper implements Listener {
	private Map<Player, Pet> cage;
	private Map<String, PetInfo> database;

	public ZooKeeper() {
		cage = new HashMap<>();

		database = new HashMap<>();
		database.put("MagnificentSpam", new PetInfo("Bear", EntityType.VILLAGER));
		database.put("nullEuro", new PetInfo("Chicken", EntityType.CREEPER));

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			spawnPet(player);
		}
	}

	public void disable() {
		for (Pet pet : cage.values()) {
			pet.remove();
		}
	}

	private void spawnPet(Player player) {
		PetInfo petInfo = database.get(player.getName());
		if (petInfo != null) {
			cage.put(player, new Pet(player, petInfo.getType(), petInfo.getName()));
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		spawnPet(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Pet pet = cage.get(event.getPlayer());
		if (pet != null) {
			pet.remove();
		}
	}
}
