package de.rasmusantons.spigot.inventorymenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ChestMenu extends AbstractMenu {

	private String title;

	public ChestMenu(int size, String title) {
		super(size);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void openForPlayer(Player player) {
		Inventory inventory = Bukkit.createInventory(player, getSize(), title);
		for (int pos = 0; pos < getSize(); ++pos) {
			inventory.setItem(pos, (entries[pos] == null) ? null : entries[pos].getIcon(pos, player));
		}
		player.openInventory(inventory);
	}
}
