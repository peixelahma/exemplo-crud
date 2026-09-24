# Projeto Didático: CRUD com Spring Boot

Este projeto é um exemplo simples de CRUD (Create, Read, Update, Delete) utilizando **Spring Boot** com arquitetura em camadas (Controller, Service, Repository) e banco de dados em memória **H2**.

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.0
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- Docker
- Jakarta Validation

## 📁 Estrutura do Projeto

```
src/main/java/com/example/exemplocrud/
├── controller/   # Camada de controle (REST API)
├── dto/          # Data Transfer Objects (para requisições/respostas)
├── entity/       # Entidades JPA (mapeamento tabela)
├── exception/    # Exceções customizadas
├── repository/   # Camada de persistência (interfaces JPA)
├── service/      # Camada de serviço (lógica de negócio)
└── ExemploCrudApplication.java  # Classe principal
```

## ▶️ Como Executar

### Pré-requisitos
- Java JDK 21 instalado
- Maven instalado
- Docker (opcional, para executar via container)

### Executando com Maven (modo desenvolvedor)

1. Clone o repositório (se ainda não tiver):
   ```bash
   git clone <url-do-repositorio>
   cd exemplo-crud
   ```

2. Compile e execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

3. A aplicação iniciará na porta **8080** (padrão definida em `application.properties`).

### Executando via Docker

1. Construa a imagem Docker:
   ```bash
   mvn clean package -DskipTests
   docker build -t exemplo-crud .
   ```

2. Execute o container:
   ```bash
   docker run -p 8080:8080 exemplo-crud
   ```

3. A aplicação estará disponível em `http://localhost:8080`.

### Acessando o Console H2 (opcional)

Para visualizar o banco de dados em memória durante a execução:
- Acesse: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:crud-db`
- Usuário: `sa`
- Senha: *(deixe em branco)*

## 🔌 Endpoints da API

A API REST está disponível no caminho base: **`/api/usuarios`**

| Método | Endpoint               | Descrição                                      | Corpo da Requisição (JSON)                     |
|--------|------------------------|------------------------------------------------|------------------------------------------------|
| GET    | `/api/usuarios`        | Lista todos os usuários                        | -                                              |
| GET    | `/api/usuarios/{id}`   | Busca um usuário pelo ID                       | -                                              |
| GET    | `/api/usuarios/buscar-nome?nome=<texto>` | Busca usuários por nome (parcial, case-insensitive) | - |
| GET    | `/api/usuarios/buscar-faixa-idade?minIdade=<n>&maxIdade=<n>` | Busca usuários por faixa de idade | - |
| POST   | `/api/usuarios`        | Cria um novo usuário                           | `{ "nome": "string", "email": "string", "idade": number }` |
| PUT    | `/api/usuarios/{id}`   | Atualiza um usuário existente                  | `{ "nome": "string", "email": "string", "idade": number }` |
| DELETE | `/api/usuarios/{id}`   | Remove um usuário pelo ID                      | -                                              |

### Exemplos de Requisições (usando `curl`)

#### Listar todos os usuários
```bash
curl -X GET http://localhost:8080/api/usuarios
```

#### Buscar usuário por ID
```bash
curl -X GET http://localhost:8080/api/usuarios/1
```

#### Criar um novo usuário
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","email":"joao@email.com","idade":30}'
```

#### Atualizar um usuário
```bash
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva Atualizado","email":"joaoatualizado@email.com","idade":31}'
```

#### Remover um usuário
```bash
curl -X DELETE http://localhost:8080/api/usuarios/1
```

#### Buscar por nome
```bash
curl -X GET "http://localhost:8080/api/usuarios/buscar-nome?name=João"
```

#### Buscar por faixa de idade
```bash
curl -X GET "http://localhost:8080/api/usuarios/buscar-faixa-idade?minIdade=20&maxIdade=40"
```

## 📝 Notas Importantes

- O banco de dados H2 é **em memória**, ou seja, os dados são perdidos toda vez que a aplicação é reiniciada.
- Validações de campos (como e-mail obrigatório, formato de e-mail, idade entre 0 e 150) são aplicadas via `@Valid` no controller e annotations no DTO.
- As exceções de negócio (como e-mail duplicado) retornam códigos HTTP apropriados:
  - `400 Bad Request` para erros de validação
  - `404 Not Found` para recurso não encontrado
  - `409 Conflict` para violação de regras de negócio (ex: e-mail já cadastrado)
  - `201 Created` para criação bem-sucedida
  - `200 OK` para operações de leitura e atualização
  - `204 No Content` para exclusão bem-sucedida

## 🎯 Objetivo Didático

Este projeto tem como objetivo demonstrar:
- Separação de responsabilidades por camadas (Controller → Service → Repository)
- Uso de DTOs para desacoplar a camada de API da camada de domínio
- Tratamento de exceções customizadas
- Validação de dados com Jakarta Validation
- Configuração de banco de dados em memória para testes e estudos
- Containerização com Docker para facilitar a reprodução do ambiente

---

**Desenvolvido para fins de estudo.** Sinta-se à vontade para clonar, modificar e usar este projeto como base para aprender Spring Boot e boas práticas de desenvolvimento.