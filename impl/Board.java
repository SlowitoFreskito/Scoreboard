package dev.slow.core.scoreboard.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Board {
	
    private static Set<Board> boards;
    private Scoreboard scoreboard;
    private Player player;
    private Objective objective;
    private Set<String> keys;
    private List<BoardEntry> entries;
    private Set<BoardCooldown> cooldowns;
    private Aether aether;
    private AetherOptions options;
    
    static {
        Board.boards = new HashSet<Board>();
    }
    
    public Board(Player player, Aether aether, AetherOptions options) {
        this.player = player;
        this.aether = aether;
        this.options = options;
        this.keys = new HashSet<String>();
        this.cooldowns = new HashSet<BoardCooldown>();
        this.entries = new ArrayList<BoardEntry>();
        this.setup();
    }
    
    private void setup() {
        if (this.options.hook() && !this.player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            this.scoreboard = this.player.getScoreboard();
        }
        else {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }
        (this.objective = this.scoreboard.registerNewObjective("glaedr_is_shit", "dummy")).setDisplaySlot(DisplaySlot.SIDEBAR);
        if (this.aether.getAdapter() != null) {
            this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.aether.getAdapter().getTitle(this.player)));
        }
        else {
            this.objective.setDisplayName("Default Title");
        }
        Board.boards.add(this);
    }
    
    public String getNewKey(BoardEntry entry) {
        ChatColor[] values;
        for (int length = (values = ChatColor.values()).length, i = 0; i < length; ++i) {
            ChatColor color = values[i];
            String colorText = new StringBuilder().append(color).append(ChatColor.WHITE).toString();
            if (entry.getText().length() > 16) {
                String sub = entry.getText().substring(0, 16);
                colorText = String.valueOf(colorText) + ChatColor.getLastColors(sub);
            }
            if (!this.keys.contains(colorText)) {
                this.keys.add(colorText);
                return colorText;
            }
        }
        throw new IndexOutOfBoundsException("No more keys available!");
    }
    
    public List<String> getBoardEntriesFormatted() {
        List<String> toReturn = new ArrayList<String>();
        for (BoardEntry entry : new ArrayList<BoardEntry>(this.entries)) {
            toReturn.add(entry.getText());
        }
        return toReturn;
    }
    
    public BoardEntry getByPosition(int position) {
        int i = 0;
        for (BoardEntry board : this.entries) {
            if (i == position) {
                return board;
            }
            ++i;
        }
        return null;
    }
    
    public BoardCooldown getCooldown(String id) {
        for (BoardCooldown cooldown : this.getCooldowns()) {
            if (cooldown.getId().equals(id)) {
                return cooldown;
            }
        }
        return null;
    }
    
    public Set<BoardCooldown> getCooldowns() {
        Iterator<BoardCooldown> iterator = this.cooldowns.iterator();
        while (iterator.hasNext()) {
            BoardCooldown cooldown = iterator.next();
            if (System.currentTimeMillis() >= cooldown.getEnd()) {
                iterator.remove();
            }
        }
        return this.cooldowns;
    }
    
    public static Board getByPlayer(Player player) {
        for (Board board : Board.boards) {
            if (board.getPlayer().getName().equals(player.getName())) {
                return board;
            }
        }
        return null;
    }
    
    public static Set<Board> getBoards() {
        return Board.boards;
    }
    
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Objective getObjective() {
        return this.objective;
    }
    
    public Set<String> getKeys() {
        return this.keys;
    }
    
    public List<BoardEntry> getEntries() {
        return this.entries;
    }
}