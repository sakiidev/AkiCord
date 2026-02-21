package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.player.AkiPlayer;
import net.akicord.proxy.AkiCord;
import net.akicord.utils.Logger;

public class ReloadCommand implements Command {
    
    @Override
    public String getName() {
        return "reload";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "rl" };
    }
    
    @Override
    public String getPermission() {
        return "akicord.command.reload";
    }
    
    @Override
    public String getDescription() {
        return "Recarrega as configurações";
    }
    
    @Override
    public String getUsage() {
        return "/reload";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        try {
            AkiCord.getInstance().getConfig().load();
            
            if (sender == null) {
                Logger.success("Configurações recarregadas!");
            } else {
                sender.sendMessage("§aConfigurações recarregadas!");
            }
        } catch (Exception e) {
            String error = "Erro ao recarregar: " + e.getMessage();
            if (sender == null) {
                Logger.error(error);
            } else {
                sender.sendMessage("§c" + error);
            }
        }
    }
}
