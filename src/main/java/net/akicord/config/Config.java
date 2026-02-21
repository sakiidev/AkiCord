package net.akicord.config;

import org.yaml.snakeyaml.Yaml;
import net.akicord.utils.Logger;
import net.akicord.server.ServerInfo;

import java.io.*;
import java.util.*;

public class Config {
    private static Config instance;
    private Map<String, Object> config;
    private File configFile;
    private Map<String, ServerInfo> servers = new HashMap<>();
    
    private String host = "0.0.0.0";
    private int port = 25577;
    private int maxPlayers = 100;
    private boolean onlineMode = false;
    private String motd = "&bAkiCord &7- &fProxy Minecraft";
    private List<String> priorities = new ArrayList<>();
    
    public Config() {
        instance = this;
        configFile = new File("config.yml");
        load();
    }
    
    public void load() {
        if (!configFile.exists()) {
            createDefaultConfig();
        }
        
        Yaml yaml = new Yaml();
        try (InputStream input = new FileInputStream(configFile)) {
            config = yaml.load(input);
            
            if (config != null) {
                host = getString("host", "0.0.0.0");
                port = getInt("port", 25577);
                maxPlayers = getInt("max-players", 100);
                onlineMode = getBoolean("online-mode", false);
                motd = getString("motd", "&bAkiCord &7- &fProxy Minecraft");
                
                // Carregar servidores
                Map<String, Map<String, Object>> serversConfig = (Map<String, Map<String, Object>>) config.get("servers");
                if (serversConfig != null) {
                    for (Map.Entry<String, Map<String, Object>> entry : serversConfig.entrySet()) {
                        String name = entry.getKey();
                        Map<String, Object> serverData = entry.getValue();
                        String address = (String) serverData.get("address");
                        int serverPort = (int) serverData.get("port");
                        String serverMotd = (String) serverData.get("motd");
                        
                        servers.put(name.toLowerCase(), new ServerInfo(name, address, serverPort, serverMotd));
                    }
                }
                
                // Carregar prioridades
                priorities = (List<String>) config.get("priorities");
                if (priorities == null) priorities = new ArrayList<>();
            }
            
            Logger.success("Configuração carregada com sucesso!");
            
        } catch (IOException e) {
            Logger.error("Erro ao carregar config: " + e.getMessage());
        }
    }
    
    private void createDefaultConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("# AkiCord Configuration\n");
            writer.write("# Proxy Minecraft baseado no BungeeCord\n\n");
            
            writer.write("host: 0.0.0.0\n");
            writer.write("port: 25577\n");
            writer.write("max-players: 100\n");
            writer.write("online-mode: false\n");
            writer.write("motd: \"&bAkiCord &7- &fProxy Minecraft\"\n\n");
            
            writer.write("servers:\n");
            writer.write("  lobby:\n");
            writer.write("    address: localhost\n");
            writer.write("    port: 25565\n");
            writer.write("    motd: \"&aLobby Server\"\n");
            writer.write("  survival:\n");
            writer.write("    address: localhost\n");
            writer.write("    port: 25566\n");
            writer.write("    motd: \"&2Survival Server\"\n");
            writer.write("  creative:\n");
            writer.write("    address: localhost\n");
            writer.write("    port: 25567\n");
            writer.write("    motd: \"&eCreative Server\"\n\n");
            
            writer.write("priorities:\n");
            writer.write("  - lobby\n");
            writer.write("  - survival\n");
            writer.write("  - creative\n");
            
            Logger.info("Configuração padrão criada.");
            
        } catch (IOException e) {
            Logger.error("Erro ao criar config padrão: " + e.getMessage());
        }
    }
    
    private String getString(String path, String def) {
        Object val = config.get(path);
        return val != null ? val.toString() : def;
    }
    
    private int getInt(String path, int def) {
        Object val = config.get(path);
        return val instanceof Number ? ((Number) val).intValue() : def;
    }
    
    private boolean getBoolean(String path, boolean def) {
        Object val = config.get(path);
        return val instanceof Boolean ? (Boolean) val : def;
    }
    
    public static Config getInstance() { return instance; }
    public String getHost() { return host; }
    public int getPort() { return port; }
    public int getMaxPlayers() { return maxPlayers; }
    public boolean isOnlineMode() { return onlineMode; }
    public String getMotd() { return motd; }
    public Map<String, ServerInfo> getServers() { return servers; }
    public List<String> getPriorities() { return priorities; }
}
