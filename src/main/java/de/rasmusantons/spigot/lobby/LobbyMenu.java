package de.rasmusantons.spigot.lobby;

import de.rasmusantons.spigot.inventorymenu.ChestMenu;
import de.rasmusantons.spigot.inventorymenu.InventoryMenu;
import de.rasmusantons.spigot.inventorymenu.InventoryMenuEntry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LobbyMenu extends InventoryMenu {
	public LobbyMenu() {
		super();

		final ChestMenu settingsMenu = new ChestMenu(27, "Settings");
		addSubMenu(settingsMenu);
		settingsMenu.setEntry(0, new InventoryMenuEntry("Anvil", Material.ANVIL) {
			@Override
			public ItemStack getIcon(int pos, Player player) {
				ItemStack itemStack = icon;
				ItemMeta itemMeta = this.icon.getItemMeta();
				itemMeta.setDisplayName(player.getName() + "'s anvil");
				itemStack.setItemMeta(itemMeta);
				return itemStack;
			}

			@Override
			public void action(int pos, Player player) {
				Bukkit.getServer().broadcastMessage(player.getName() + " clicked the anvil");
			}
		});

		setEntry(0, new InventoryMenuEntry("Settings", Material.CHEST) {
			@Override
			public void action(int pos, Player player) {
				settingsMenu.openForPlayer(player);
			}
		});
	}
}
