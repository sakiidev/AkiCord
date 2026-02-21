package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.player.AkiPlayer;
import net.akicord.proxy.AkiCord;
import net.akicord.utils.ConsoleColors;

public class InfoCommand implements Command {
    
    @Override
    public String getName() {
        return "info";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "version", "about" };
    }
    
    @Override
    public String getPermission() {
        return "";
    }
    
    @Override
    public String getDescription() {
        return "Mostra informações do proxy";
    }
    
    @Override
    public String getUsage() {
        return "/info";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        if (sender == null) {
            // Console
            System.out.println(ConsoleColors.AKI_PRIMARY + "\n╔════════════════════════════════════╗");
            System.out.println("║         AkiCord Proxy v1.0.0         ║");
            System.out.println("╚════════════════════════════════════╝" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.WHITE + "  Porta: " + AkiCord.getInstance().getConfig().getPort());
            System.out.println("  Jogadores: " + AkiCord.getInstance().getPlayerManager().getPlayerCount() + 
                              "/" + AkiCord.getInstance().getConfig().getMaxPlayers());
            System.out.println("  Servidores: " + AkiCord.getInstance().getServerManager().getServers().size());
            System.out.println("  Modo Online: " + AkiCord.getInstance().getConfig().isOnlineMode() + ConsoleColors.RESET);
        } else {
            // Jogador
            sender.sendMessage("§8§m----------------------------------------");
            sender.sendMessage("§b§l          AkiCord Proxy v1.0.0");
            sender.sendMessage("§8§m----------------------------------------");
            sender.sendMessage("§fPorta: §b" + AkiCord.getInstance().getConfig().getPort());
            sender.sendMessage("§fJogadores: §b" + AkiCord.getInstance().getPlayerManager().getPlayerCount() + 
                              "§7/§b" + AkiCord.getInstance().getConfig().getMaxPlayers());
            sender.sendMessage("§fServidores: §b" + AkiCord.getInstance().getServerManager().getServers().size());
            sender.sendMessage("§fModo Online: §b" + AkiCord.getInstance().getConfig().isOnlineMode());
        }
    }
}
