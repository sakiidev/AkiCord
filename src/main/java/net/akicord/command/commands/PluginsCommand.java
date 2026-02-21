package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.player.AkiPlayer;

import java.util.ArrayList;
import java.util.List;

public class PluginsCommand implements Command {
    
    @Override
    public String getName() {
        return "plugins";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "pl" };
    }
    
    @Override
    public String getPermission() {
        return "akicord.command.plugins";
    }
    
    @Override
    public String getDescription() {
        return "Lista plugins carregados";
    }
    
    @Override
    public String getUsage() {
        return "/plugins";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        // Placeholder - sistema de plugins será implementado depois
        List<String> plugins = new ArrayList<>();
        plugins.add("AkiCord-Core v1.0.0");
        
        if (sender == null) {
            System.out.println("§aPlugins (" + plugins.size() + "): §f" + String.join("§7, §f", plugins));
        } else {
            sender.sendMessage("§aPlugins (" + plugins.size() + "): §f" + String.join("§7, §f", plugins));
        }
    }
}
