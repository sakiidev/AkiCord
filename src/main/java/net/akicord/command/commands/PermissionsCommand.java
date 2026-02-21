package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.player.AkiPlayer;

public class PermissionsCommand implements Command {
    
    @Override
    public String getName() {
        return "permissions";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "perms", "perm" };
    }
    
    @Override
    public String getPermission() {
        return "akicord.command.permissions";
    }
    
    @Override
    public String getDescription() {
        return "Gerencia permissões";
    }
    
    @Override
    public String getUsage() {
        return "/permissions <list|check> [jogador]";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        if (args.length == 0) {
            if (sender == null) {
                System.out.println("Uso: /permissions <list|check> [jogador]");
            } else {
                sender.sendMessage("§cUso: /permissions <list|check> [jogador]");
            }
            return;
        }
        
        String subCommand = args[0].toLowerCase();
        
        if (subCommand.equals("list")) {
            if (sender == null) {
                System.out.println("§aPermissões disponíveis:");
                System.out.println("  §f- akicord.command.*");
                System.out.println("  §f- akicord.command.help");
                System.out.println("  §f- akicord.command.list");
                System.out.println("  §f- akicord.command.server");
                System.out.println("  §f- akicord.command.send");
                System.out.println("  §f- akicord.command.alert");
                System.out.println("  §f- akicord.command.reload");
                System.out.println("  §f- akicord.command.end");
                System.out.println("  §f- akicord.command.plugins");
                System.out.println("  §f- akicord.command.permissions");
            } else {
                sender.sendMessage("§aPermissões disponíveis:");
                sender.sendMessage("  §f- akicord.command.*");
                sender.sendMessage("  §f- akicord.command.help");
                sender.sendMessage("  §f- akicord.command.list");
                sender.sendMessage("  §f- akicord.command.server");
                sender.sendMessage("  §f- akicord.command.send");
                sender.sendMessage("  §f- akicord.command.alert");
                sender.sendMessage("  §f- akicord.command.reload");
                sender.sendMessage("  §f- akicord.command.end");
                sender.sendMessage("  §f- akicord.command.plugins");
                sender.sendMessage("  §f- akicord.command.permissions");
            }
        } else if (subCommand.equals("check")) {
            String target = (args.length > 1) ? args[1] : (sender != null ? sender.getName() : "console");
            
            if (sender == null) {
                System.out.println("§aPermissões para " + target + ":");
                System.out.println("  §f- akicord.command.* ✓");
                System.out.println("  §f- akicord.command.help ✓");
                System.out.println("  §f- akicord.command.list ✓");
                System.out.println("  §f- akicord.command.server ✓");
                System.out.println("  §f- akicord.command.send ✓");
                System.out.println("  §f- akicord.command.alert ✓");
                System.out.println("  §f- akicord.command.reload ✓");
                System.out.println("  §f- akicord.command.end ✓");
                System.out.println("  §f- akicord.command.plugins ✓");
            } else {
                sender.sendMessage("§aPermissões para " + target + ":");
                sender.sendMessage("  §f- akicord.command.* ✓");
                sender.sendMessage("  §f- akicord.command.help ✓");
                sender.sendMessage("  §f- akicord.command.list ✓");
                sender.sendMessage("  §f- akicord.command.server ✓");
            }
        }
    }
}
