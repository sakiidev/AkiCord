package net.akicord.proxy;

import net.akicord.config.Config;
import net.akicord.utils.Logger;
import net.akicord.utils.ConsoleColors;
import net.akicord.player.PlayerManager;
import net.akicord.server.ServerManager;
import net.akicord.command.CommandManager;
import net.akicord.network.NetworkServer;

import java.util.Scanner;

public class AkiCord {
    private static AkiCord instance;
    private Config config;
    private PlayerManager playerManager;
    private ServerManager serverManager;
    private CommandManager commandManager;
    private NetworkServer networkServer;
    private boolean running = true;
    
    public AkiCord() {
        instance = this;
    }
    
    public void start() {
        // Banner
        System.out.println(ConsoleColors.AKI_PRIMARY + "   ╔════════════════════════════════════╗");
        System.out.println("   ║         A K I C O R D            ║");
        System.out.println("   ║     Proxy Minecraft v1.0.0       ║");
        System.out.println("   ╚════════════════════════════════════╝" + ConsoleColors.RESET);
        
        // Inicializar configuração
        Logger.info("Carregando configuração...");
        config = new Config();
        
        // Inicializar gerenciadores
        Logger.info("Inicializando gerenciadores...");
        playerManager = new PlayerManager();
        serverManager = new ServerManager();
        commandManager = new CommandManager();
        
        // Iniciar servidor de rede
        networkServer = new NetworkServer(config.getHost(), config.getPort());
        networkServer.start();
        
        Logger.success("AkiCord iniciado com sucesso!");
        Logger.info("Digite 'help' para ver os comandos");
        
        // Console thread
        startConsoleReader();
    }
    
    private void startConsoleReader() {
        Scanner scanner = new Scanner(System.in);
        
        while (running) {
            System.out.print(ConsoleColors.AKI_SECONDARY + "AkiCord> " + ConsoleColors.RESET);
            
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    if (input.equalsIgnoreCase("stop") || input.equalsIgnoreCase("end")) {
                        stop();
                        break;
                    } else {
                        commandManager.dispatchConsole(input);
                    }
                }
            }
        }
        
        scanner.close();
    }
    
    public void stop() {
        Logger.warning("Parando AkiCord...");
        running = false;
        
        // Desconectar todos os jogadores
        if (playerManager != null) {
            playerManager.disconnectAll();
        }
        
        // Parar servidor de rede
        if (networkServer != null) {
            networkServer.stop();
        }
        
        Logger.success("AkiCord parado.");
    }
    
    public static AkiCord getInstance() { return instance; }
    public Config getConfig() { return config; }
    public PlayerManager getPlayerManager() { return playerManager; }
    public ServerManager getServerManager() { return serverManager; }
    public CommandManager getCommandManager() { return commandManager; }
    public boolean isRunning() { return running; }
    
    public static void main(String[] args) {
        AkiCord proxy = new AkiCord();
        
        // Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (proxy.isRunning()) {
                proxy.stop();
            }
        }));
        
        proxy.start();
    }
}
