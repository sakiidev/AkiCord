package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.command.CommandManager;
import net.akicord.player.AkiPlayer;
import net.akicord.utils.ConsoleColors;

public class HelpCommand implements Command {
    
    @Override
    public String getName() {
        return "help";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "?" };
    }
    
    @Override
    public String getPermission() {
        return "";
    }
    
    @Override
    public String getDescription() {
        return "Mostra todos os comandos disponíveis";
    }
    
    @Override
    public String getUsage() {
        return "/help [comando]";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        if (sender == null) {
            // Console
            System.out.println(ConsoleColors.AKI_PRIMARY + "\n╔════════════════════════════════════╗");
            System.out.println("║         COMANDOS DISPONÍVEIS         ║");
            System.out.println("╚════════════════════════════════════╝" + ConsoleColors.RESET);
            
            for (Command cmd : CommandManager.getInstance().getCommands().values()) {
                System.out.println(ConsoleColors.AKI_INFO + "  /" + cmd.getName() + 
                    ConsoleColors.WHITE + " - " + cmd.getDescription() + ConsoleColors.RESET);
            }
        } else {
            // Jogador
            sender.sendMessage("§8§m----------------------------------------");
            sender.sendMessage("§b§l    COMANDOS DISPONÍVEIS");
            sender.sendMessage("§8§m----------------------------------------");
            
            for (Command cmd : CommandManager.getInstance().getCommands().values()) {
                sender.sendMessage("§b/" + cmd.getName() + " §7- §f" + cmd.getDescription());
            }
        }
    }
}
