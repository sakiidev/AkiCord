package net.akicord.player;

import net.akicord.utils.Logger;
import net.akicord.server.ServerManager;
import net.akicord.config.Config;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    private static PlayerManager instance;
    private final Map<UUID, AkiPlayer> players = new ConcurrentHashMap<>();
    private final Map<String, UUID> nameToId = new ConcurrentHashMap<>();
    private final Map<Channel, UUID> channelToId = new ConcurrentHashMap<>();
    
    public PlayerManager() {
        instance = this;
    }
    
    public void addPlayer(AkiPlayer player) {
        players.put(player.getUniqueId(), player);
        nameToId.put(player.getName().toLowerCase(), player.getUniqueId());
        channelToId.put(player.getChannel(), player.getUniqueId());
        
        Logger.success("Jogador conectado: " + player.getName() + 
                      " [" + players.size() + "/" + Config.getInstance().getMaxPlayers() + "]");
    }
    
    public void removePlayer(UUID uuid) {
        AkiPlayer player = players.remove(uuid);
        if (player != null) {
            nameToId.remove(player.getName().toLowerCase());
            channelToId.remove(player.getChannel());
            
            // Remover do servidor atual
            ServerManager.getInstance().removePlayer(uuid);
            
            Logger.info("Jogador desconectado: " + player.getName());
        }
    }
    
    public AkiPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }
    
    public AkiPlayer getPlayer(String name) {
        UUID uuid = nameToId.get(name.toLowerCase());
        return uuid != null ? players.get(uuid) : null;
    }
    
    public AkiPlayer getPlayer(Channel channel) {
        UUID uuid = channelToId.get(channel);
        return uuid != null ? players.get(uuid) : null;
    }
    
    public int getPlayerCount() {
        return players.size();
    }
    
    public Map<UUID, AkiPlayer> getPlayers() {
        return players;
    }
    
    public void broadcastMessage(String message) {
        for (AkiPlayer player : players.values()) {
            player.sendMessage(message);
        }
    }
    
    public void disconnectAll() {
        for (AkiPlayer player : players.values()) {
            player.disconnect("Proxy reiniciando");
        }
        players.clear();
        nameToId.clear();
        channelToId.clear();
        Logger.info("Todos os jogadores desconectados");
    }
    
    public static PlayerManager getInstance() { return instance; }
}
