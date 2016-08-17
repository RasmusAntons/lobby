package de.rasmusantons.spigot.lobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class NoWeather implements Listener {
	@EventHandler
	public void onWeatherChanged(WeatherChangeEvent event) {
		event.setCancelled(true);
	}
}
