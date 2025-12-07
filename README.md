# FNS Locação de Veículos
Sistema desenvolvido para disciplina **Sistemas Distribuídos**, com aplicação prática em ambientes **monolíticos na AWS**.

### 1. Descrição Geral do Projeto
O **FNS Locações** é um sistema completo para gerenciamento de locação de veículos.  
A aplicação inclui:
- Cadastro de usuários, clientes, veículos e pagamentos
- Registro e controle de locações
- Histórico e status do veículo
- API REST completa em **Spring Boot**
- Front-end estático em **HTML + CSS + JS**
- Banco de dados **PostgreSQL**
- Arquitetura modular com Services Controllers e Repositories
  
O sistema é utilizado para demonstrar conceitos fundamentais de **arquitetura monolítica**, usando tecnologias reais da **AWS**.

### 2. Estrutura Geral da Arquitetura
```Usuário -> EC2 (App Spring Boot + PostgreSQL) -> VPC customizada```
- EC2 executa toda a aplicação (backend, vanco e arquivos estáticos).
- Banco local configurado diretamente na instância.

### 3. Estrutura do Repositório
```
fns-locacoes/
 ├── backend/
 │    ├── src/main/java/com/fns/...
 │    └── pom.xml
 ├── frontend/
 │    ├── index.html
 │    ├── css/
 │    ├── js/
 │    └── api.js
 ├── docker-compose.yml
 └── README.md
```

### 4. Tecnologias Usadas
**Backend**
- Java 25
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Maven

**Frontend**
- HTML
- CSS
- JavaScript

### 5. Como Rodar o Projeto Localmente
**Pré-requisitos**
- Java 25
- Docker