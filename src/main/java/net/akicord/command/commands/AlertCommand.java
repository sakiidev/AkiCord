package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.player.AkiPlayer;
import net.akicord.player.PlayerManager;

public class AlertCommand implements Command {
    
    @Override
    public String getName() {
        return "alert";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "broadcast", "say" };
    }
    
    @Override
    public String getPermission() {
        return "akicord.command.alert";
    }
    
    @Override
    public String getDescription() {
        return "Envia uma mensagem para todos os jogadores";
    }
    
    @Override
    public String getUsage() {
        return "/alert <mensagem>";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        if (args.length == 0) {
            if (sender == null) {
                System.out.println("Uso: /alert <mensagem>");
            } else {
                sender.sendMessage("§cUso: /alert <mensagem>");
            }
            return;
        }
        
        String message = String.join(" ", args);
        String formatted = "§c[Alerta] §f" + message;
        
        PlayerManager.getInstance().broadcastMessage(formatted);
        
        if (sender == null) {
            System.out.println("§aAlerta enviado: " + message);
        }
    }
}
