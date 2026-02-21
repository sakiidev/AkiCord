#!/bin/bash
echo "📦 Compilando AkiCord..."

# Criar diretório de saída
mkdir -p target/classes

# Compilar todos os arquivos Java
javac -d target/classes \
  -cp "libs/*" \
  $(find src/main/java -name "*.java")

if [ $? -eq 0 ]; then
    echo "✅ Compilação bem-sucedida!"
    
    # Criar JAR
    jar cfe AkiCord.jar net.akicord.proxy.AkiCord -C target/classes .
    echo "✅ JAR criado: AkiCord.jar"
    
    # Executar
    echo ""
    echo "🚀 Iniciando AkiCord..."
    echo ""
    java -jar AkiCord.jar
else
    echo "❌ Erro na compilação"
    exit 1
fi
