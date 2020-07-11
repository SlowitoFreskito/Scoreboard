package dev.slow.core.scoreboard.impl;

import java.text.DecimalFormat;

import org.apache.commons.lang.time.DurationFormatUtils;

public class BoardCooldown {
	
    private static DecimalFormat SECONDS_FORMATTER;
    private Board board;
    private String id;
    private double duration;
    private long end;
    
    static {
        SECONDS_FORMATTER = new DecimalFormat("#0.0");
    }
    
    public BoardCooldown(Board board, String id, double duration) {
        this.board = board;
        this.id = id;
        this.duration = duration;
        this.end = (long)(System.currentTimeMillis() + duration * 1000.0);
        board.getCooldowns().add(this);
    }
    
    public String getFormattedString(BoardFormat format) {
        if (format == null) {
            throw new NullPointerException();
        }
        if (format == BoardFormat.SECONDS) {
            return BoardCooldown.SECONDS_FORMATTER.format((this.end - System.currentTimeMillis()) / 1000.0f);
        }
        return DurationFormatUtils.formatDuration(this.end - System.currentTimeMillis(), "mm:ss");
    }
    
    public void cancel() {
        this.board.getCooldowns().remove(this);
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    public String getId() {
        return this.id;
    }
    
    public double getDuration() {
        return this.duration;
    }
    
    public long getEnd() {
        return this.end;
    }
}
