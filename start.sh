#!/bin/bash

# Script to start Docker PostgreSQL and Spring Boot application
# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}FNS Locações - Sistema de Inicialização${NC}"
echo -e "${BLUE}========================================${NC}\n"

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo -e "${RED}✗ Docker não está instalado. Por favor, instale o Docker primeiro.${NC}"
    exit 1
fi

# Check if docker-compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo -e "${RED}✗ Docker Compose não está instalado. Por favor, instale o Docker Compose.${NC}"
    exit 1
fi

# Get the directory where the script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo -e "${YELLOW}[1/3]${NC} Iniciando serviços Docker..."
cd "$SCRIPT_DIR"

# Start Docker containers
docker-compose up -d

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Serviços Docker iniciados com sucesso${NC}\n"
else
    echo -e "${RED}✗ Erro ao iniciar serviços Docker${NC}"
    exit 1
fi

# Wait for PostgreSQL to be ready
echo -e "${YELLOW}[2/3]${NC} Aguardando PostgreSQL ficar pronto..."
sleep 5

# Check if PostgreSQL is responding
MAX_RETRIES=30
RETRY_COUNT=0

while [ $RETRY_COUNT -lt $MAX_RETRIES ]; do
    if docker exec fns_postgres pg_isready -U admin &> /dev/null; then
        echo -e "${GREEN}✓ PostgreSQL está pronto${NC}\n"
        break
    fi
    RETRY_COUNT=$((RETRY_COUNT + 1))
    echo -e "${YELLOW}Tentativa ${RETRY_COUNT}/${MAX_RETRIES}...${NC}"
    sleep 1
done

if [ $RETRY_COUNT -eq $MAX_RETRIES ]; then
    echo -e "${RED}✗ PostgreSQL não respondeu no tempo esperado${NC}"
    exit 1
fi

# Build and run Spring Boot application
echo -e "${YELLOW}[3/3]${NC} Iniciando aplicação Spring Boot..."
cd "$SCRIPT_DIR/back"

./mvnw clean spring-boot:run

# Handle exit
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Aplicação encerrada com sucesso${NC}"
else
    echo -e "${RED}✗ Erro ao executar aplicação Spring Boot${NC}"
    exit 1
fi
