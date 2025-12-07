# FNS Locações - Scripts de Inicialização

Este diretório contém scripts bash para facilitar o gerenciamento da aplicação FNS Locações.

## Scripts Disponíveis

### 1. `start.sh` - Inicialização Automática

Script simples que inicia todos os serviços (Docker + Spring Boot) em sequência.

**Uso:**
```bash
./start.sh
```

**O que faz:**
- ✓ Verifica se Docker e Docker Compose estão instalados
- ✓ Inicia os serviços PostgreSQL via Docker Compose
- ✓ Aguarda o PostgreSQL ficar pronto
- ✓ Inicia a aplicação Spring Boot
- ✓ Mantém a aplicação rodando

**Requisitos:**
- Docker instalado e em execução
- Docker Compose instalado
- Java 25+ instalado

---

### 2. `manage.sh` - Gerenciador Interativo

Script avançado com menu interativo para controlar todos os aspectos da aplicação.

**Uso Interativo:**
```bash
./manage.sh
```

**Uso com Argumentos:**
```bash
./manage.sh start      # Inicia todos os serviços
./manage.sh stop       # Para todos os serviços
./manage.sh restart    # Reinicia todos os serviços
./manage.sh status     # Mostra status dos serviços
```

**Menu Interativo oferece:**
1. Iniciar todos os serviços
2. Parar todos os serviços
3. Reiniciar todos os serviços
4. Ver status dos serviços
5. Ver logs do PostgreSQL (em tempo real)
6. Ver logs da Aplicação (em tempo real)
7. Sair

---

## Estrutura do Projeto

```
fns-locacoes-back/
├── start.sh                 # Script simples de inicialização
├── manage.sh               # Script gerenciador com menu
├── docker-compose.yml      # Configuração Docker
├── back/                   # Aplicação Spring Boot
│   ├── mvnw               # Maven Wrapper
│   ├── pom.xml
│   └── src/
└── front/                 # Aplicação Frontend
```

---

## Como Usar

### Primeira Execução - Opção 1 (Simples)

```bash
cd /home/manjanga/Work/fns-locacoes-back
chmod +x start.sh
./start.sh
```

Isso vai:
1. Iniciar PostgreSQL no Docker
2. Aguardar PostgreSQL ficar pronto
3. Compilar e iniciar a aplicação Spring Boot
4. Exibir logs em tempo real

A aplicação estará disponível em: `http://localhost:8080`

### Primeira Execução - Opção 2 (Com Menu)

```bash
cd /home/manjanga/Work/fns-locacoes-back
chmod +x manage.sh
./manage.sh
```

Menu interativo oferecerá várias opções.

---

## Informações de Acesso

### PostgreSQL (Docker)
- **Host:** localhost
- **Porta:** 5432
- **Usuário:** admin
- **Senha:** admin123
- **Banco:** locadora

### Spring Boot API
- **URL:** http://localhost:8080
- **Frontend:** http://localhost:8080/front (ou servir estaticamente)

### Variáveis de Ambiente (application.properties)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/locadora
spring.datasource.username=admin
spring.datasource.password=admin123
```

---

## Troubleshooting

### Docker não está instalado
```bash
# Ubuntu/Debian
sudo apt-get install docker.io docker-compose

# macOS com Homebrew
brew install docker docker-compose
```

### Porta 5432 já está em uso
Modifique o `docker-compose.yml` para usar outra porta:
```yaml
ports:
  - "5433:5432"  # Mudando para 5433
```

### PostgreSQL não responde
```bash
# Ver status dos containers
docker-compose ps

# Ver logs do PostgreSQL
docker-compose logs postgres

# Reiniciar containers
docker-compose restart
```

### Erro de compilação Maven
```bash
# Limpar cache Maven
./mvnw clean

# Recompilando
./mvnw clean compile
```

### Porta 8080 já está em uso
Modifique o `back/src/main/resources/application.properties`:
```properties
server.port=8081
```

---

## Parando a Aplicação

### Com `start.sh`
Pressione `Ctrl+C` no terminal

### Com `manage.sh`
- Escolha opção "2" para parar serviços
- Ou pressione `Ctrl+C` se em modo interativo

### Comandos Docker Diretos
```bash
# Parar apenas o Docker
docker-compose down

# Parar e remover volumes
docker-compose down -v
```

---

## Logs e Debug

### Ver logs em tempo real
```bash
# PostgreSQL
docker-compose logs -f postgres

# Spring Boot (com manage.sh, opção 6)
cd back
./mvnw spring-boot:run

# Ver logs de um container específico
docker logs -f fns_postgres
```

### Acessar PostgreSQL
```bash
# Entrar no container PostgreSQL
docker exec -it fns_postgres psql -U admin -d locadora

# Alguns comandos úteis no psql
\dt                    # Listar tabelas
\l                     # Listar bancos
SELECT * FROM usuario; # Ver usuários
\q                     # Sair
```

---

## Desenvolvimento

### Modificar código e recompilar
```bash
# Spring Boot detecção automática (se devtools ativado)
cd back
./mvnw spring-boot:run

# Ou compilar manualmente
./mvnw clean compile
```

### Resetar banco de dados
```bash
# Opção 1: Remover container
docker-compose down -v
docker-compose up -d

# Opção 2: Entrar no PostgreSQL e dropar tabelas
docker exec -it fns_postgres psql -U admin -d locadora
# DROP TABLE IF EXISTS locacao CASCADE;
# etc...
```

---

## Comandos Úteis

```bash
# Iniciar com script
./start.sh

# Menu gerenciador
./manage.sh

# Status dos serviços
./manage.sh status

# Parar tudo
./manage.sh stop

# Reiniciar tudo
./manage.sh restart

# Ver logs PostgreSQL
docker-compose logs -f postgres

# Limpar tudo e recomeçar
docker-compose down -v && docker-compose up -d
```

---

## Support

Para mais informações ou problemas, consulte a documentação do:
- [Docker](https://docs.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostgreSQL](https://www.postgresql.org/docs/)

---

**Última atualização:** Dezembro 7, 2025
