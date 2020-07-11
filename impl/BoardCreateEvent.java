package dev.slow.core.scoreboard.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BoardCreateEvent extends Event {
	
    private static HandlerList handlers;
    private Board board;
    private Player player;
    
    static {
        handlers = new HandlerList();
    }
    
    public BoardCreateEvent(Board board, Player player) {
        this.board = board;
        this.player = player;
    }
    
	@Override
    public HandlerList getHandlers() {
        return BoardCreateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BoardCreateEvent.handlers;
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    public Player getPlayer() {
        return this.player;
    }
}