package de.rasmusantons.spigot.lobby;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProtectWorld implements Listener {

	public ProtectWorld() {
		for (World world : Bukkit.getServer().getWorlds()) {
			world.setGameRuleValue("mobGriefing", "false");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;
		event.setCancelled(true);
		event.getPlayer().updateInventory();
	}
}
