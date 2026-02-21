package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.player.AkiPlayer;
import net.akicord.player.PlayerManager;
import net.akicord.server.ServerInfo;
import net.akicord.server.ServerManager;

public class SendCommand implements Command {
    
    @Override
    public String getName() {
        return "send";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "move" };
    }
    
    @Override
    public String getPermission() {
        return "akicord.command.send";
    }
    
    @Override
    public String getDescription() {
        return "Envia um jogador para outro servidor";
    }
    
    @Override
    public String getUsage() {
        return "/send <jogador> <servidor>";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        if (args.length < 2) {
            if (sender == null) {
                System.out.println("Uso: /send <jogador> <servidor>");
            } else {
                sender.sendMessage("§cUso: /send <jogador> <servidor>");
            }
            return;
        }
        
        String playerName = args[0];
        String serverName = args[1];
        
        AkiPlayer target = PlayerManager.getInstance().getPlayer(playerName);
        if (target == null) {
            if (sender == null) {
                System.out.println("§cJogador não encontrado: " + playerName);
            } else {
                sender.sendMessage("§cJogador não encontrado: " + playerName);
            }
            return;
        }
        
        ServerInfo server = ServerManager.getInstance().getServer(serverName);
        if (server == null) {
            if (sender == null) {
                System.out.println("§cServidor não encontrado: " + serverName);
            } else {
                sender.sendMessage("§cServidor não encontrado: " + serverName);
            }
            return;
        }
        
        if (!server.isOnline()) {
            if (sender == null) {
                System.out.println("§cServidor offline: " + serverName);
            } else {
                sender.sendMessage("§cServidor offline: " + serverName);
            }
            return;
        }
        
        target.connect(server);
        
        if (sender == null) {
            System.out.println("§aJogador " + playerName + " enviado para " + serverName);
        } else {
            sender.sendMessage("§aJogador " + playerName + " enviado para " + serverName);
        }
    }
}
