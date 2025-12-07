#!/bin/bash

# Script to manage FNS Locações services (start, stop, restart)
# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Get the directory where the script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

show_menu() {
    echo -e "${BLUE}========================================${NC}"
    echo -e "${BLUE}FNS Locações - Gerenciador de Serviços${NC}"
    echo -e "${BLUE}========================================${NC}\n"
    echo "1) Iniciar todos os serviços"
    echo "2) Parar todos os serviços"
    echo "3) Reiniciar todos os serviços"
    echo "4) Ver status dos serviços"
    echo "5) Ver logs do PostgreSQL"
    echo "6) Ver logs da Aplicação"
    echo "0) Sair"
    echo ""
}

start_services() {
    echo -e "${YELLOW}[1/3]${NC} Iniciando serviços Docker..."
    cd "$SCRIPT_DIR"
    
    docker-compose up -d
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Serviços Docker iniciados${NC}\n"
    else
        echo -e "${RED}✗ Erro ao iniciar serviços Docker${NC}"
        return 1
    fi
    
    echo -e "${YELLOW}[2/3]${NC} Aguardando PostgreSQL ficar pronto..."
    sleep 5
    
    MAX_RETRIES=30
    RETRY_COUNT=0
    
    while [ $RETRY_COUNT -lt $MAX_RETRIES ]; do
        if docker exec fns_postgres pg_isready -U admin &> /dev/null; then
            echo -e "${GREEN}✓ PostgreSQL está pronto${NC}\n"
            break
        fi
        RETRY_COUNT=$((RETRY_COUNT + 1))
        echo -n "."
        sleep 1
    done
    
    if [ $RETRY_COUNT -eq $MAX_RETRIES ]; then
        echo -e "\n${RED}✗ PostgreSQL não respondeu no tempo esperado${NC}"
        return 1
    fi
    
    echo -e "${YELLOW}[3/3]${NC} Iniciando aplicação Spring Boot...\n"
    cd "$SCRIPT_DIR/back"
    
    ./mvnw clean spring-boot:run
}

stop_services() {
    echo -e "${YELLOW}Parando serviços...${NC}"
    cd "$SCRIPT_DIR"
    docker-compose down
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Serviços parados com sucesso${NC}\n"
    else
        echo -e "${RED}✗ Erro ao parar serviços${NC}"
        return 1
    fi
}

restart_services() {
    echo -e "${YELLOW}Reiniciando serviços...${NC}"
    stop_services
    sleep 2
    start_services
}

show_status() {
    echo -e "${YELLOW}Status dos serviços:${NC}\n"
    docker-compose ps
    echo ""
}

show_postgres_logs() {
    echo -e "${YELLOW}Logs do PostgreSQL (últimas 50 linhas):${NC}\n"
    docker-compose logs -f postgres --tail=50
}

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo -e "${RED}✗ Docker não está instalado${NC}"
    exit 1
fi

# If argument provided, execute directly
if [ $# -eq 1 ]; then
    case $1 in
        start)
            start_services
            ;;
        stop)
            stop_services
            ;;
        restart)
            restart_services
            ;;
        status)
            show_status
            ;;
        *)
            echo "Uso: $0 {start|stop|restart|status}"
            exit 1
            ;;
    esac
else
    # Interactive menu mode
    while true; do
        show_menu
        read -p "Escolha uma opção: " choice
        
        case $choice in
            1)
                start_services
                ;;
            2)
                stop_services
                ;;
            3)
                restart_services
                ;;
            4)
                show_status
                ;;
            5)
                show_postgres_logs
                ;;
            6)
                echo -e "${YELLOW}Logs da Aplicação (pressione Ctrl+C para sair):${NC}\n"
                cd "$SCRIPT_DIR/back"
                ./mvnw spring-boot:run
                ;;
            0)
                echo -e "${BLUE}Encerrando...${NC}"
                exit 0
                ;;
            *)
                echo -e "${RED}Opção inválida${NC}"
                ;;
        esac
        
        read -p "Pressione Enter para continuar..."
    done
fi
