# ProjetoSeguranca

API Spring Boot com autenticacao baseada em sessao HTTP (sem token JWT), persistencia em PostgreSQL e documentacao via Swagger.

## Como o projeto funciona

- `POST /login/criarUsuario`: cria um usuario com senha criptografada.
- `POST /login`: autentica email/senha e grava o contexto de seguranca na sessao (`JSESSIONID`).
- Requisicoes seguintes podem usar o cookie de sessao para acessar endpoints protegidos (conforme regras de `SecurityConfig`).
- Swagger/OpenAPI fica disponivel para testar os endpoints.

Arquivos principais:

- `src/main/java/io/github/lucasbxavier/projetoseguranca/config/SecurityConfig.java`
- `src/main/java/io/github/lucasbxavier/projetoseguranca/controller/UserController.java`
- `src/main/java/io/github/lucasbxavier/projetoseguranca/service/LoginService.java`
- `src/main/resources/application.properties`

## Rodar com Docker (jeito simples)

### Pre-requisitos

- Docker
- Docker Compose (plugin `docker compose`)

### Subir tudo (API + banco)

```bash
docker compose up --build
```

Servicos:

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/proj-seguranca/swagger-ui.html`
- API Docs: `http://localhost:8080/proj-seguranca/v3/api-docs`
- PostgreSQL (host): `localhost:5433`

Para parar:

```bash
docker compose down
```

Para parar e remover volumes (reset do banco):

```bash
docker compose down -v
```

## Variaveis de ambiente

A aplicacao usa estas variaveis (com fallback local):

- `SPRING_DATASOURCE_URL` (default: `jdbc:postgresql://localhost:5433/login_db`)
- `SPRING_DATASOURCE_USERNAME` (default: `user`)
- `SPRING_DATASOURCE_PASSWORD` (default: `123321ABC`)

No Docker Compose, a API usa o host interno `db:5432` automaticamente.

## Fluxo rapido de teste

1) Criar usuario

```bash
curl -X POST http://localhost:8080/login/criarUsuario \
  -H "Content-Type: application/json" \
  -d '{"username":"lucas","email":"lucas@example.com","password":"Senha123!","name":"Lucas"}'
```

2) Fazer login (salvar cookie)

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"email":"lucas@example.com","password":"Senha123!"}' \
  -c cookies.txt
```

3) Chamar endpoint com sessao

```bash
curl -X POST http://localhost:8080/login/buscarUser/lucas@example.com \
  -b cookies.txt
```

## Observacoes

- O `Dockerfile` e multi-stage: compila com Maven + Java 21 e executa com JRE 21.
- O `docker-compose.yml` sobe `app` e `db`, com healthcheck no PostgreSQL.
- Se quiser rodar local sem Docker, mantenha o banco em `localhost:5433` ou sobrescreva as variaveis de datasource.

