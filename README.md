# 🚀 AkiCord - Proxy Minecraft de Alta Performance

<div align="center">

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Netty](https://img.shields.io/badge/Netty-4.1-2CA5E0?style=for-the-badge&logo=netty&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.8-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

<img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&weight=600&size=24&duration=3000&pause=1000&color=6A5ACD&center=true&vCenter=true&random=false&width=435&lines=Proxy+Minecraft+completo;Baseado+no+BungeeCord;Alta+performance+com+Netty;Suporte+a+múltiplos+servidores" alt="Typing SVG" />

</div>

## 📋 Sobre o Projeto

**AkiCord** é um proxy Minecraft de alta performance desenvolvido do zero em Java, totalmente inspirado no BungeeCord. Ele permite conectar múltiplos servidores Minecraft em uma única rede, oferecendo uma experiência unificada para os jogadores, com suporte a fallback automático, balanceamento de carga e muito mais.

### ✨ Características Principais

- 🔥 **Alta Performance** - Construído com Netty para rede assíncrona não-bloqueante
- 🌐 **Múltiplos Servidores** - Conecte vários servidores em uma única rede
- 🔄 **Fallback Automático** - Redireciona jogadores automaticamente se um servidor cair
- ⚖️ **Balanceamento de Carga** - Distribui jogadores entre servidores
- 🎨 **Interface Colorida** - Console com cores ANSI vibrantes
- 📝 **Configuração YAML** - Fácil de configurar e personalizar
- 🎮 **Comandos Completos** - Sistema de comandos extensível
- 🔌 **Sistema de Plugins** - Arquitetura preparada para plugins (em desenvolvimento)
- 👥 **Gerenciamento de Jogadores** - Controle total sobre os jogadores conectados
- 📊 **Métricas e Estatísticas** - Acompanhe o desempenho do seu proxy

## 📁 Estrutura do Projeto

```

AkiCord/
├── src/main/java/net/akicord/
│   ├── proxy/          # Classe principal e inicialização
│   ├── network/        # Gerenciamento de rede com Netty
│   │   ├── NetworkServer.java
│   │   ├── PipelineFactory.java
│   │   ├── MinecraftHandler.java
│   │   ├── MinecraftDecoder.java
│   │   ├── MinecraftEncoder.java
│   │   └── MinecraftPacket.java
│   ├── player/         # Gerenciamento de jogadores
│   │   ├── PlayerManager.java
│   │   └── AkiPlayer.java
│   ├── server/         # Gerenciamento de servidores
│   │   ├── ServerManager.java
│   │   └── ServerInfo.java
│   ├── command/        # Sistema de comandos
│   │   ├── CommandManager.java
│   │   ├── Command.java
│   │   └── commands/   # Comandos específicos
│   │       ├── HelpCommand.java
│   │       ├── ListCommand.java
│   │       ├── ServerCommand.java
│   │       ├── SendCommand.java
│   │       ├── AlertCommand.java
│   │       ├── ReloadCommand.java
│   │       ├── EndCommand.java
│   │       ├── InfoCommand.java
│   │       ├── PluginsCommand.java
│   │       └── PermissionsCommand.java
│   ├── config/         # Configurações
│   │   └── Config.java
│   └── utils/          # Utilitários
│       ├── Logger.java
│       └── ConsoleColors.java
├── config.yml          # Arquivo de configuração principal
├── pom.xml            # Configuração do Maven
├── build.sh           # Script de compilação
├── run.sh             # Script de execução
└── README.md          # Documentação

```

## 🎮 Comandos Disponíveis

| Comando | Descrição | Permissão | Uso |
|---------|-----------|-----------|-----|
| `/help` | Mostra todos os comandos | Todos | `/help [comando]` |
| `/list` | Lista jogadores online | Todos | `/list` |
| `/info` | Informações do proxy | Todos | `/info` |
| `/server` | Mostra ou muda de servidor | Todos | `/server [nome]` |
| `/send` | Envia jogador para servidor | `akicord.command.send` | `/send <jogador> <servidor>` |
| `/alert` | Envia alerta global | `akicord.command.alert` | `/alert <mensagem>` |
| `/reload` | Recarrega configurações | `akicord.command.reload` | `/reload` |
| `/end` | Para o proxy | `akicord.command.end` | `/end` |
| `/plugins` | Lista plugins carregados | `akicord.command.plugins` | `/plugins` |
| `/permissions` | Gerencia permissões | `akicord.command.permissions` | `/permissions <list\|check>` |

## ⚙️ Configuração

### config.yml

```yaml
# AkiCord Configuration
# Proxy Minecraft de alta performance

host: 0.0.0.0
port: 25577
max-players: 100
online-mode: false
ip-forward: true
motd: "&bAkiCord &7- &fProxy Minecraft"

servers:
  lobby:
    address: localhost
    port: 25565
    motd: "&aLobby Server"
  survival:
    address: localhost
    port: 25566
    motd: "&2Survival Server"
  creative:
    address: localhost
    port: 25567
    motd: "&eCreative Server"

priorities:
  - lobby
  - survival
  - creative

netty:
  threads: 4
  epoll: true

debug: false
```

🚀 Instalação e Uso

Pré-requisitos

· Java 8 ou superior - Download
· Maven (opcional, para compilar) - Download
· Git (opcional, para clonar) - Download

Método 1: Compilar com Maven

```bash
# Clone o repositório
git clone https://github.com/sakiidev/AkiCord.git
cd AkiCord

# Compile com Maven
mvn clean package

# Execute
java -jar target/AkiCord-1.0.0.jar
```

Método 2: Usando os scripts

```bash
# Dê permissão de execução
chmod +x build.sh run.sh

# Compile
./build.sh

# Execute
./run.sh
```

Método 3: Download direto

Baixe o JAR mais recente da seção Releases e execute:

```bash
java -jar AkiCord.jar
```

🎨 Cores no Console

O AkiCord utiliza cores ANSI para uma experiência visual agradável:

· 🟣 Roxo - Títulos e bordas
· 🔵 Azul - Informações e destaques
· 🟢 Verde - Sucesso e confirmações
· 🔴 Vermelho - Erros e alertas críticos
· 🟡 Laranja - Avisos
· ⚪ Branco - Texto normal

📊 Performance

O AkiCord foi desenvolvido com foco em performance:

· ✅ Netty - Framework de rede assíncrono não-bloqueante
· ✅ Epoll - Suporte nativo a epoll no Linux para melhor performance
· ✅ Threading otimizado - Gerenciamento eficiente de threads
· ✅ Pool de conexões - Reutilização de conexões
· ✅ Buffer pooling - Redução de alocação de memória

🔧 Personalização

Adicionando um novo comando

1. Crie uma classe que implemente a interface Command
2. Implemente os métodos necessários
3. Registre o comando no CommandManager

```java
package net.akicord.command.commands;

import net.akicord.command.Command;
import net.akicord.player.AkiPlayer;

public class MeuComando implements Command {
    
    @Override
    public String getName() {
        return "meucomando";
    }
    
    @Override
    public String[] getAliases() {
        return new String[] { "mc" };
    }
    
    @Override
    public String getPermission() {
        return "akicord.command.meucomando";
    }
    
    @Override
    public String getDescription() {
        return "Descrição do meu comando";
    }
    
    @Override
    public String getUsage() {
        return "/meucomando";
    }
    
    @Override
    public void execute(AkiPlayer sender, String[] args) {
        // Lógica do comando
    }
}
```

🐛 Solução de Problemas

Erro "Address already in use"

A porta 25577 já está em uso. Altere a porta no config.yml ou pare o processo que está usando a porta.

Erro de compilação

Certifique-se de ter o Java 8+ e Maven instalados corretamente.

Jogadores não conseguem conectar

Verifique se o firewall está permitindo a porta configurada.

🤝 Contribuindo

Contribuições são sempre bem-vindas! Siga os passos:

1. Fork o projeto
2. Crie uma branch (git checkout -b feature/AmazingFeature)
3. Commit suas mudanças (git commit -m 'Add some AmazingFeature')
4. Push para a branch (git push origin feature/AmazingFeature)
5. Abra um Pull Request

📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

👨‍💻 Autor

<div align="center">

sakiidev

https://img.shields.io/badge/GitHub-sakiidev-100000?style=for-the-badge&logo=github&logoColor=white
https://img.shields.io/badge/Discord-sakiidev-5865F2?style=for-the-badge&logo=discord&logoColor=white
https://img.shields.io/badge/YouTube-@kiisute-FF0000?style=for-the-badge&logo=youtube&logoColor=white
https://img.shields.io/badge/TikTok-@sakiidev-000000?style=for-the-badge&logo=tiktok&logoColor=white

</div>

⭐ Apoie o Projeto

Se você gostou do AkiCord e ele te ajudou de alguma forma, considere dar uma estrela no GitHub! Isso ajuda o projeto a crescer e alcançar mais pessoas.

<div align="center">

https://img.shields.io/badge/⭐-Dar%20Estrela-yellow?style=for-the-badge
https://img.shields.io/badge/🍴-Fazer%20Fork-blue?style=for-the-badge
https://img.shields.io/badge/👀-Watch-red?style=for-the-badge

</div>

---

<div align="center">

⚡ Desenvolvido com ❤️ por sakiidev ⚡

https://capsule-render.vercel.app/api?type=waving&color=gradient&height=100&section=footer&gradient=6A5ACD,4B0082,8A2BE2

</div>
