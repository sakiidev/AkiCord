package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.player.AkiPlayer;
import net.akicord.proxy.AkiCord;
import net.akicord.utils.Logger;

public class EndCommand implements Command {
    
    @Override
    public String getName() {
        return "end";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "stop", "shutdown" };
    }
    
    @Override
    public String getPermission() {
        return "akicord.command.end";
    }
    
    @Override
    public String getDescription() {
        return "Para o proxy";
    }
    
    @Override
    public String getUsage() {
        return "/end";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        if (sender == null) {
            Logger.warning("Console iniciou parada do proxy...");
        } else {
            Logger.warning("Jogador " + sender.getName() + " iniciou parada do proxy...");
            sender.sendMessage("§cParando proxy...");
        }
        
        // Parar o proxy em uma thread separada
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                AkiCord.getInstance().stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
