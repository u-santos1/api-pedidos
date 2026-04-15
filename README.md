# API de Pedidos

API REST construída com Spring Boot e Spring Data JPA para gerenciamento de pedidos e usuários.

## Tecnologias

- Java 17
- Spring Boot 3
- Spring Data JPA / Hibernate
- H2 Database (em memória)
- Lombok
- Bean Validation

## Como rodar

**Pré-requisitos:** Java 17+ e Maven instalados.

```bash
# Clone o repositório
git clone https://github.com/u-santos1/api-pedidos.git
cd api-pedidos

# Rode a aplicação
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

O banco H2 é criado automaticamente em memória. Para acessar o console:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:pedidosdb`
- Usuário: `sa` | Senha: *(vazio)*

---

## Endpoints

### Usuários

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/usuarios` | Cadastrar novo usuário |
| `GET` | `/usuarios/{id}/pedidos` | Listar pedidos de um usuário |

#### POST /usuarios

```json
// Request body
{
  "nome": "João Silva",
  "email": "joao@email.com"
}

// Response 201
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@email.com"
}
```

#### GET /usuarios/{id}/pedidos

```json
// Response 200
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@email.com",
  "pedidos": [
    {
      "id": 1,
      "usuarioNome": "João Silva",
      "usuarioEmail": "joao@email.com",
      "statusPedido": "PENDENTE",
      "criadoEm": "2026-04-11T11:38:38"
    }
  ]
}
```

---

### Pedidos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/pedidos/usuario/{id}` | Criar pedido para um usuário |
| `GET` | `/pedidos/usuario/{id}` | Listar todos os pedidos de um usuário |
| `GET` | `/pedidos/{id}` | Buscar pedido por ID |
| `DELETE` | `/pedidos/usuario/{id}/cancelar-pendentes` | Cancelar todos os pedidos pendentes do usuário |

#### POST /pedidos/usuario/{id}

```json
// Response 201
{
  "id": 3,
  "usuarioNome": "João Silva",
  "usuarioEmail": "joao@email.com",
  "statusPedido": "PENDENTE",
  "criadoEm": "2026-04-11T11:38:42"
}
```

#### DELETE /pedidos/usuario/{id}/cancelar-pendentes

```
// Response 200
3 pedidos cancelados
```

---

## Estrutura do projeto

```
src/main/java/com/projeto/pedidos/
├── controller/
│   ├── PedidoController.java
│   └── UsuarioController.java
├── dto/
│   ├── PedidoDTO.java
│   ├── ItemPedidoDTO.java
│   ├── UsuarioDTO.java
│   ├── UsuarioComPedidosDTO.java
│   └── UsuarioRequest.java
├── entity/
│   ├── Pedido.java
│   ├── ItemPedido.java
│   ├── Usuario.java
│   └── StatusPedido.java
├── exception/
│   ├── PedidoNaoEncotradoException.java
│   └── TratadorDeErro.java
├── repository/
│   ├── PedidoRepository.java
│   └── UsuarioRepository.java
└── service/
    ├── PedidoService.java
    └── UsuarioService.java
```

## Decisões técnicas

**DTOs em vez de entidades** — os controllers nunca expõem entidades JPA diretamente, evitando problemas de serialização com proxies do Hibernate e vazamento de dados internos.

**FetchType.LAZY + JOIN FETCH** — todas as coleções usam carregamento lazy. Quando os dados relacionados são necessários, queries com `JOIN FETCH` buscam tudo em uma única consulta, evitando o problema N+1.

**@ControllerAdvice global** — erros de validação (`@Valid`) e exceções de negócio retornam JSON padronizado com mensagem legível, nunca stack trace.

**@Transactional nos services** — operações de escrita estão anotadas com `@Transactional` garantindo atomicidade.

## Tratamento de erros

Todas as respostas de erro seguem o formato:

```json
{
  "mensagem": "Pedido não encontrado: 99"
}
```

Erros de validação retornam a lista de campos inválidos:

```json
[
  { "campo": "email", "mensagem": "não deve estar em branco" },
  { "campo": "nome",  "mensagem": "não deve estar em branco" }
]
```

| Situação | Status HTTP |
|----------|-------------|
| Pedido não encontrado | `404 Not Found` |
| Usuário não encontrado | `404 Not Found` |
| Campos inválidos no body | `400 Bad Request` |
| Email já cadastrado | `409 Conflict` |