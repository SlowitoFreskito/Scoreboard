package dev.slow.core.scoreboard;

import dev.slow.core.main.Main;
import dev.slow.core.scoreboard.impl.Board;
import dev.slow.core.scoreboard.impl.BoardAdapter;
import dev.slow.core.scoreboard.impl.BoardCooldown;
import dev.slow.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Sidebar implements BoardAdapter {
    @Override
    public String getTitle(Player p0) {
        return Utils.color("&5VrathMC &7┃ &fHub ");
    }

    @Override
    public List<String> getScoreboard(Player p0, Board p1, Set<BoardCooldown> p2) {
        List<String> lines = new ArrayList<>();

        lines.add(Utils.color("&7&m-----------------------"));
        lines.add(" ");
        lines.add(Utils.color("&dName&7: &f"+ p0.getName()));
        lines.add(Utils.color("&dRank&7: &r" + Main.getPermissions().getPrimaryGroup(p0)));
        lines.add(Utils.color("&dGamemode&7: &f" + p0.getGameMode()));
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss z");
        lines.add(Utils.color("&dTime&7: &f"+ format.format(date)));
        lines.add(" ");
        lines.add(Utils.color("&dPlayers Online&7: &f"+ Bukkit.getServer().getOnlinePlayers().size()));
        lines.add(" ");
        lines.add(Utils.color("&7vrathmc.net"));
        lines.add(Utils.color("&7&m-----------------------"));

        return lines;
    }
}
