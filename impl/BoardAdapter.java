package dev.slow.core.scoreboard.impl;

import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

public interface BoardAdapter {
	
    String getTitle(Player p0);
    
    List<String> getScoreboard(Player p0, Board p1, Set<BoardCooldown> p2);
}