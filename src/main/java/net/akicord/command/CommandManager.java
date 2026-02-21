package net.akicord.command;

import net.akicord.utils.Logger;
import net.akicord.player.AkiPlayer;
import net.akicord.command.commands.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
    private static CommandManager instance;
    private final Map<String, Command> commands = new ConcurrentHashMap<>();
    private final Map<String, Command> aliasMap = new ConcurrentHashMap<>();
    
    public CommandManager() {
        instance = this;
        registerDefaultCommands();
    }
    
    private void registerDefaultCommands() {
        registerCommand(new HelpCommand());
        registerCommand(new ListCommand());
        registerCommand(new ServerCommand());
        registerCommand(new SendCommand());
        registerCommand(new AlertCommand());
        registerCommand(new ReloadCommand());
        registerCommand(new EndCommand());
        registerCommand(new InfoCommand());
        registerCommand(new PluginsCommand());
        registerCommand(new PermissionsCommand());
    }
    
    public void registerCommand(Command cmd) {
        commands.put(cmd.getName().toLowerCase(), cmd);
        
        for (String alias : cmd.getAliases()) {
            aliasMap.put(alias.toLowerCase(), cmd);
        }
        
        Logger.debug("Comando registrado: /" + cmd.getName());
    }
    
    public boolean dispatch(AkiPlayer sender, String input) {
        if (input == null || input.isEmpty()) return false;
        
        String[] parts = input.split(" ");
        String commandName = parts[0].toLowerCase();
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
        
        Command cmd = commands.get(commandName);
        if (cmd == null) {
            cmd = aliasMap.get(commandName);
        }
        
        if (cmd != null) {
            // Verificar permissão
            if (cmd.getPermission() != null && !cmd.getPermission().isEmpty()) {
                // Implementar verificação de permissão
                // if (!sender.hasPermission(cmd.getPermission())) {
                //     sender.sendMessage("§cVocê não tem permissão para usar este comando.");
                //     return true;
                // }
            }
            
            try {
                cmd.execute(sender, args);
            } catch (Exception e) {
                if (sender != null) {
                    sender.sendMessage("§cErro ao executar comando: " + e.getMessage());
                } else {
                    Logger.error("Erro no comando " + commandName + ": " + e.getMessage());
                }
            }
            return true;
        }
        
        return false;
    }
    
    public void dispatchConsole(String input) {
        if (input == null || input.isEmpty()) return;
        
        String[] parts = input.split(" ");
        String commandName = parts[0].toLowerCase();
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
        
        Command cmd = commands.get(commandName);
        if (cmd == null) {
            cmd = aliasMap.get(commandName);
        }
        
        if (cmd != null) {
            try {
                cmd.execute(null, args);
            } catch (Exception e) {
                Logger.error("Erro no comando: " + e.getMessage());
            }
        } else {
            Logger.warning("Comando desconhecido: " + commandName);
        }
    }
    
    public Map<String, Command> getCommands() {
        return commands;
    }
    
    public static CommandManager getInstance() { return instance; }
}
