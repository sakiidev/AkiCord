package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.player.AkiPlayer;
import net.akicord.server.ServerInfo;
import net.akicord.server.ServerManager;
import net.akicord.utils.ConsoleColors;

public class ServerCommand implements Command {
    
    @Override
    public String getName() {
        return "server";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "servers", "goto" };
    }
    
    @Override
    public String getPermission() {
        return "akicord.command.server";
    }
    
    @Override
    public String getDescription() {
        return "Mostra ou muda de servidor";
    }
    
    @Override
    public String getUsage() {
        return "/server [nome]";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        if (sender == null) {
            System.out.println(ConsoleColors.AKI_INFO + "Servidores disponíveis:" + ConsoleColors.RESET);
            
            for (ServerInfo server : ServerManager.getInstance().getServers().values()) {
                String status = server.isOnline() ? "§aOnline" : "§cOffline";
                System.out.println("  " + server.getName() + " - " + status + 
                    " §7(" + server.getPlayerCount() + "/" + server.getMaxPlayers() + ")");
            }
            return;
        }
        
        if (args.length == 0) {
            sender.sendMessage("§8§m----------------------------------------");
            sender.sendMessage("§b§l    SERVIDORES DISPONÍVEIS");
            sender.sendMessage("§8§m----------------------------------------");
            
            ServerInfo current = sender.getServer();
            
            for (ServerInfo server : ServerManager.getInstance().getServers().values()) {
                String status = server.isOnline() ? "§aOnline" : "§cOffline";
                String currentMarker = (current != null && current.getName().equals(server.getName())) 
                    ? " §7(§aAtual§7)" : "";
                
                sender.sendMessage("§b" + server.getName() + " §7- " + status + 
                    " §7(" + server.getPlayerCount() + "/" + server.getMaxPlayers() + ")" + currentMarker);
            }
            
            sender.sendMessage("§8§m----------------------------------------");
            sender.sendMessage("§7Use §b/server <nome> §7para mudar de servidor");
            
        } else {
            String targetName = args[0];
            ServerInfo target = ServerManager.getInstance().getServer(targetName);
            
            if (target == null) {
                sender.sendMessage("§cServidor não encontrado: " + targetName);
                return;
            }
            
            if (!target.isOnline()) {
                sender.sendMessage("§cServidor offline: " + targetName);
                return;
            }
            
            sender.sendMessage("§aConectando ao servidor " + targetName + "...");
            sender.connect(target);
        }
    }
}
