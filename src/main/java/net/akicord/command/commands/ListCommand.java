package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.player.AkiPlayer;
import net.akicord.player.PlayerManager;
import net.akicord.utils.ConsoleColors;

public class ListCommand implements Command {
    
    @Override
    public String getName() {
        return "list";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "online", "players" };
    }
    
    @Override
    public String getPermission() {
        return "";
    }
    
    @Override
    public String getDescription() {
        return "Lista jogadores online";
    }
    
    @Override
    public String getUsage() {
        return "/list";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        int count = PlayerManager.getInstance().getPlayerCount();
        int max = net.akicord.config.Config.getInstance().getMaxPlayers();
        
        if (sender == null) {
            // Console
            System.out.println(ConsoleColors.AKI_INFO + "Jogadores online: " + 
                ConsoleColors.AKI_SECONDARY + count + "/" + max + ConsoleColors.RESET);
            
            if (count > 0) {
                System.out.print(ConsoleColors.WHITE + "  ");
                for (AkiPlayer player : PlayerManager.getInstance().getPlayers().values()) {
                    System.out.print(player.getName() + " ");
                }
                System.out.println(ConsoleColors.RESET);
            }
        } else {
            // Jogador
            sender.sendMessage("§7Jogadores online: §b" + count + "§7/§b" + max);
            
            if (count > 0) {
                StringBuilder list = new StringBuilder("§f");
                for (AkiPlayer player : PlayerManager.getInstance().getPlayers().values()) {
                    list.append(player.getName()).append(" ");
                }
                sender.sendMessage(list.toString());
            }
        }
    }
}
