package net.akicord.server;

import net.akicord.config.Config;
import net.akicord.utils.Logger;
import net.akicord.player.AkiPlayer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerManager {
    private static ServerManager instance;
    private final Map<String, ServerInfo> servers = new ConcurrentHashMap<>();
    private final Map<UUID, String> playerServer = new ConcurrentHashMap<>();
    
    public ServerManager() {
        instance = this;
        loadServers();
    }
    
    private void loadServers() {
        servers.putAll(Config.getInstance().getServers());
        Logger.success("Carregados " + servers.size() + " servidores");
    }
    
    public void registerServer(String name, ServerInfo server) {
        servers.put(name.toLowerCase(), server);
        Logger.info("Servidor registrado: " + name);
    }
    
    public void unregisterServer(String name) {
        servers.remove(name.toLowerCase());
        Logger.info("Servidor removido: " + name);
    }
    
    public ServerInfo getServer(String name) {
        return servers.get(name.toLowerCase());
    }
    
    public Map<String, ServerInfo> getServers() {
        return servers;
    }
    
    public ServerInfo getPlayerServer(UUID playerId) {
        String serverName = playerServer.get(playerId);
        return serverName != null ? servers.get(serverName) : null;
    }
    
    public void setPlayerServer(UUID playerId, String serverName) {
        if (serverName == null) {
            playerServer.remove(playerId);
        } else {
            playerServer.put(playerId, serverName.toLowerCase());
        }
    }
    
    public void removePlayer(UUID playerId) {
        playerServer.remove(playerId);
    }
    
    public ServerInfo getFallbackServer() {
        List<String> priorities = Config.getInstance().getPriorities();
        
        for (String priority : priorities) {
            ServerInfo server = servers.get(priority.toLowerCase());
            if (server != null && server.isOnline()) {
                return server;
            }
        }
        
        // Se não encontrar nos priorities, pega qualquer servidor online
        for (ServerInfo server : servers.values()) {
            if (server.isOnline()) {
                return server;
            }
        }
        
        return null;
    }
    
    public static ServerManager getInstance() { return instance; }
}
