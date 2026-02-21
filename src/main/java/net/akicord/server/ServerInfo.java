package net.akicord.server;

import java.net.SocketAddress;

public class ServerInfo {
    private final String name;
    private final String address;
    private final int port;
    private final String motd;
    private boolean online = true;
    private int playerCount = 0;
    private int maxPlayers = 100;
    private SocketAddress socketAddress;
    
    public ServerInfo(String name, String address, int port, String motd) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.motd = motd;
    }
    
    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getPort() { return port; }
    public String getMotd() { return motd; }
    public boolean isOnline() { return online; }
    public void setOnline(boolean online) { this.online = online; }
    public int getPlayerCount() { return playerCount; }
    public void setPlayerCount(int count) { this.playerCount = count; }
    public int getMaxPlayers() { return maxPlayers; }
    public void setMaxPlayers(int max) { this.maxPlayers = max; }
    public SocketAddress getSocketAddress() { return socketAddress; }
    public void setSocketAddress(SocketAddress addr) { this.socketAddress = addr; }
    
    public String getDisplayName() {
        return (online ? "§a" : "§c") + name + " §7(" + playerCount + "/" + maxPlayers + ")";
    }
}
