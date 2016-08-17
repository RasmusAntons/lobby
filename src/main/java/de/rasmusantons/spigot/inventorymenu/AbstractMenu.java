package de.rasmusantons.spigot.inventorymenu;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMenu implements Listener {
	protected InventoryMenuEntry[] entries;
	protected List<ChestMenu> subMenus;
	protected int size;

	public AbstractMenu(int size) {
		this.size = size;
		entries = new InventoryMenuEntry[size];
		subMenus = new ArrayList<>();
	}

	public int getSize() {
		return size;
	}

	public void setEntry(int position, InventoryMenuEntry listener) {
		entries[position] = listener;
	}

	public void removeEntry(int pos) {
		entries[pos] = null;
	}

	public void addSubMenu(ChestMenu menu) {
		for (ChestMenu subMenu : subMenus) {
			if (subMenu.getTitle().equals(menu.getTitle())) {
				throw new IllegalArgumentException("A submenu with this name already exists: " + menu.getTitle());
			}
		}
		subMenus.add(menu);
	}

	public void removeSubMenu(ChestMenu menu) {
		subMenus.remove(menu);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getWhoClicked().getGameMode() == GameMode.CREATIVE || !(event.getWhoClicked() instanceof Player))
			return;
		Player player = (Player) event.getWhoClicked();
		if (event.getClickedInventory().equals(player.getInventory())) {
			onItemActivated(event.getSlot(), (Player) event.getWhoClicked());
		} else {
			for (ChestMenu subMenu : subMenus) {
				if (event.getClickedInventory().getTitle().equals(subMenu.getTitle())) {
					subMenu.onItemActivated(event.getSlot(), (Player) event.getWhoClicked());
					break;
				}
			}
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getAction() == Action.PHYSICAL)
			return;
		onItemActivated(event.getPlayer().getInventory().getHeldItemSlot(), event.getPlayer());
		event.setCancelled(true);
	}

	protected void onItemActivated(int pos, Player player) {
		if (entries[pos] != null) {
			entries[pos].action(pos, player);
		}
	}
}
