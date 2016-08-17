package de.rasmusantons.spigot.inventorymenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class InventoryMenuEntry {

	protected ItemStack icon;

	public InventoryMenuEntry(ItemStack icon) {
		this.icon = icon;
	}

	public InventoryMenuEntry(String name, Material icon) {
		this(new ItemStack(icon));
		ItemMeta itemMeta = this.icon.getItemMeta();
		itemMeta.setDisplayName(name);
		this.icon.setItemMeta(itemMeta);
	}

	public ItemStack getIcon(int pos, Player player) {
		return icon;
	}

	public abstract void action(int pos, Player player);
}
