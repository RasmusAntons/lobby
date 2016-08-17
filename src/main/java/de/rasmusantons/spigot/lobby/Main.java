package de.rasmusantons.spigot.lobby;

import de.rasmusantons.spigot.pets.ZooKeeper;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private ZooKeeper zooKeeper;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new NoDamage(), this);
		getServer().getPluginManager().registerEvents(new NoWeather(), this);
		getServer().getPluginManager().registerEvents(new ProtectWorld(), this);
		getServer().getPluginManager().registerEvents(new LobbyMenu(), this);
		getServer().getPluginManager().registerEvents(zooKeeper = new ZooKeeper(), this);
	}

	@Override
	public void onDisable() {
		zooKeeper.disable();
	}
}
