package de.rasmusantons.spigot.lobby;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

public class NoDamage implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player) && event.getCause() == EntityDamageEvent.DamageCause.VOID)
			return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onVoidFall(PlayerMoveEvent event) {
		if (!(event.getPlayer().getLocation().getY() < 0))
			return;
		Player player = event.getPlayer();
		player.teleport(player.getWorld().getSpawnLocation());
		player.setFallDistance(0);
	}

	@EventHandler
	public void onFoodLevelChanged(FoodLevelChangeEvent event) {
		event.setCancelled(true);
		if ((event.getEntity() instanceof Player)) {
			((Player) event.getEntity()).setFoodLevel(20);
			((Player) event.getEntity()).setSaturation(20);
			((Player) event.getEntity()).setExhaustion(0);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Default");
		if (team == null) {
			team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("Default");
			team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
		}
		team.addEntry(player.getName());
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Default");
		if (team != null)
			team.removeEntry(player.getName());
	}

	@EventHandler
	public void onTeamRequest(AsyncPlayerChatEvent event) {
		if (event.getMessage().contains("dia"))
			event.setMessage(event.getMessage().replaceAll("dia", "diamond"));
	}
}
