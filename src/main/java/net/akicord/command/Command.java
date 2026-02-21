package net.akicord.command;

import net.akicord.player.AkiPlayer;

public interface Command {
    String getName();
    String[] getAliases();
    String getPermission();
    String getDescription();
    String getUsage();
    void execute(AkiPlayer sender, String[] args);
}
