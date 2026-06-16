<div align="center">

# 🏥 Sistema de Gestão Hospitalar

### Projeto desenvolvido para a disciplina de Desenvolvimento Back-End com Spring Boot

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

</div>


## 📋 Sobre o Projeto

Olá, professor! 👋

Este projeto é uma **API REST** para gerenciamento hospitalar, desenvolvida utilizando **Spring Boot** com integração ao banco de dados **PostgreSQL**. A aplicação permite o cadastro e gerenciamento de **pacientes**, **médicos**, **consultas** e **internações**, seguindo as boas práticas de arquitetura em camadas (Controller → Service → Repository).

A aplicação cobre todos os requisitos propostos nos exercícios, incluindo entidades JPA, relacionamentos, endpoints REST, dados iniciais automáticos, testes unitários e de integração, além de deploy com Docker.


## 🛠️ Tecnologias Utilizadas

| Tecnologia | Finalidade |
|---|---|
| ☕ Java | Linguagem principal |
| 🍃 Spring Boot | Framework principal |
| 🗄️ Spring Data JPA | Persistência de dados |
| ✅ Spring Validation | Validação de entrada |
| 🩺 Spring Actuator | Monitoramento da aplicação |
| 🐘 PostgreSQL | Banco de dados relacional |
| 🐳 Docker | Containerização |
| 🧪 JUnit 5 + Mockito | Testes unitários |
| 🔗 MockMvc | Testes de integração |


## 🗃️ Modelagem do Banco de Dados

Abaixo está a representação dos relacionamentos entre as entidades do sistema:

```
┌─────────────────┐         ┌─────────────────────┐
│    PACIENTE      │         │       MEDICO         │
│─────────────────│         │─────────────────────│
│ id (PK)         │         │ id (PK)             │
│ nome            │         │ nome                │
│ cpf             │         │ crm                 │
│ dataNascimento  │         │ especialidade       │
│ telefone        │         └──────────┬──────────┘
└────────┬────────┘                    │
         │ 1:N                         │ 1:N
         │                             │
         └──────────┬──────────────────┘
                    │
             ┌──────▼──────────┐
             │    CONSULTA      │
             │─────────────────│
             │ id (PK)         │
             │ dataConsulta    │
             │ observacoes     │
             │ paciente_id(FK) │
             │ medico_id (FK)  │
             └─────────────────┘

┌─────────────────┐
│    INTERNACAO    │
│─────────────────│
│ id (PK)         │
│ dataEntrada     │
│ dataAlta        │
│ quarto          │
│ paciente_id(FK) │  ← 1 Paciente → N Internações
└─────────────────┘
```


## 🚀 Como Executar o Projeto

### ▶️ Opção 1 — Execução Local

**Pré-requisitos:** Java 17+, Maven e PostgreSQL instalados.

**1. Clone o repositório:**
```bash
git clone https://github.com/Nivaldof12/hospital.git
cd hospital
```

**2. Configure o banco de dados no `application.properties`:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hospital_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
management.endpoints.web.exposure.include=health
```

**3. Execute a aplicação:**
```bash
./mvnw spring-boot:run
```


### 🐳 Opção 2 — Execução com Docker (Recomendada)

**Pré-requisito:** Docker e Docker Compose instalados.

```bash
# Subir todos os serviços (aplicação + banco)
docker-compose up --build
```

A aplicação ficará disponível em: `http://localhost:8080`


## 📡 Endpoints da API

### 👤 Pacientes — `/api/pacientes`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/pacientes` | 📝 Cadastrar novo paciente |
| `GET` | `/api/pacientes` | 📋 Listar todos os pacientes |
| `GET` | `/api/pacientes/{id}` | 🔍 Buscar paciente por ID |
| `DELETE` | `/api/pacientes/{id}` | 🗑️ Remover paciente |

**Exemplo de corpo da requisição (POST):**
```json
{
  "nome": "Nivaldo Filho",
  "cpf": "122.535.044-16",
  "dataNascimento": "2002-01-24",
  "telefone": "(81) 99468-5930"
}
```


### 🩺 Médicos — `/api/medicos`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/medicos` | 📝 Cadastrar novo médico |
| `GET` | `/api/medicos` | 📋 Listar todos os médicos |
| `GET` | `/api/medicos/ranking` | 🏆 Médicos com mais consultas |

**Exemplo de corpo da requisição (POST):**
```json
{
  "nome": "Dr. Zé Carlos",
  "crm": "CRM-PE 12345",
  "especialidade": "Cardiologista"
}
```


### 🗓️ Consultas — `/api/consultas`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/consultas` | 📝 Cadastrar nova consulta |

**Exemplo de corpo da requisição (POST):**
```json
{
  "dataConsulta": "2024-06-15T10:00:00",
  "observacoes": "Paciente com pressão elevada",
  "pacienteId": 1,
  "medicoId": 1
}
```


### 🩺 Health Check — Spring Actuator

```
GET http://localhost:8080/actuator/health
```

```json
{
  "status": "UP"
}
```


## 🌱 Dados Iniciais (Seed)

Ao iniciar a aplicação, dois médicos e dois pacientes são inseridos automaticamente no banco de dados via `CommandLineRunner`:

| Tipo | Nome | Especialidade / CPF |
|---|---|---|
| 🩺 Médico | Dr. Cardiologista | Cardiologia |
| 🩺 Médico | Dr. Ortopedista | Ortopedia |
| 👤 Paciente | João Silva | — |
| 👤 Paciente | Maria Oliveira | — |


## 🧪 Testes

O projeto conta com duas camadas de testes para garantir a qualidade do código:

### 🔬 Testes Unitários (JUnit 5 + Mockito)

Os repositórios são simulados com **Mocks**, testando apenas a lógica da camada de serviço de forma isolada:

- ✅ Cadastro de paciente
- ✅ Cadastro de médico
- ✅ Busca de paciente por ID
- ✅ Exclusão de paciente
- ✅ Comportamento quando o paciente não existe (`ResourceNotFoundException`)

**Executar apenas testes unitários:**
```bash
./mvnw test -Dtest="*UnitTest"
```


### 🔗 Testes de Integração (Spring Boot Test + MockMvc)

Testam o fluxo completo da requisição HTTP até o banco de dados:

| Teste | Descrição | Validações |
|---|---|---|
| Teste 1 | `POST /api/pacientes` | Status `201 Created` + JSON de retorno |
| Teste 2 | `GET /api/pacientes/{id}` | Status `200 OK` + dados corretos |
| Teste 3 | `GET /api/pacientes` | Status `200 OK` + lista não vazia |
| Teste 4 | `DELETE /api/pacientes/{id}` | Status `204 No Content` + dado removido |

**Executar todos os testes:**
```bash
./mvnw test
```

## 📚 Exercícios Resolvidos

| # | Exercício | Status |
|---|---|---|
| 01 | Criação do projeto Spring Boot com as dependências obrigatórias | ✅ Concluído |
| 02 | Configuração do `application.properties` (PostgreSQL + Actuator) | ✅ Concluído |
| 03 | Entidade JPA `Paciente` + `PacienteRepository` | ✅ Concluído |
| 04 | Entidade JPA `Medico` + `MedicoRepository` | ✅ Concluído |
| 05 | Entidade JPA `Consulta` com relacionamentos | ✅ Concluído |
| 06 | Entidade JPA `Internacao` com relacionamento ao `Paciente` | ✅ Concluído |
| 07 | `CommandLineRunner` para inserção de dados iniciais | ✅ Concluído |
| 08 | Camada Service + Controller com endpoints REST | ✅ Concluído |
| 09 | Query Method personalizado — ranking de médicos por consultas | ✅ Concluído |
| 10 | Testes unitários com JUnit e Mockito | ✅ Concluído |
| 11 | Testes de integração com Spring Boot Test e MockMvc | ✅ Concluído |
| 12 | Deploy com Docker (`Dockerfile` + `docker-compose.yml`) | ✅ Concluído |

## 👨‍💻 Autor

<div align="center">

**Desenvolvido por Nivaldo**

[![GitHub](https://img.shields.io/badge/GitHub-Nivaldof12-181717?style=for-the-badge&logo=github)](https://github.com/Nivaldof12)

</div>
