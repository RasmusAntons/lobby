package de.rasmusantons.spigot.inventorymenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class InventoryMenu extends AbstractMenu {

	public InventoryMenu() {
		super(41);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Inventory playerInventory = player.getInventory();
		for (int pos = 0; pos < getSize(); ++pos) {
			player.getInventory().setItem(pos, (entries[pos] == null) ? null : entries[pos].getIcon(pos, player));
		}
	}
}
