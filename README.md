# Projeto de Rede Social com Spring Security 6 e JWT

Este projeto é uma aplicação de rede social implementada com **Spring Boot 3** e **Spring Security 6**, utilizando **JWT (JSON Web Tokens)** para autenticação e autorização. A API permite a criação de posts, com um controle de acesso granular através de **ROLES** (perfis de acesso), além de integrar com um banco de dados **MySQL** via **Docker**.

## Funcionalidades

- Autenticação e autorização via JWT.
- Controle de acesso granular utilizando perfis de usuário (**ADMIN**, **USER**, etc.).
- Uso de **anotações de segurança** como `@PreAuthorize` para controlar o acesso nas rotas da API.
- Integração com banco de dados **MySQL** para armazenar dados de usuários e posts.
- Desenvolvimento utilizando **Java 17** e **Spring Boot 3**.

## Tecnologias Utilizadas

- **Spring Boot 3**
- **Spring Security 6**
- **JWT (JSON Web Tokens)**
- **MySQL**
- **Docker**
- **Java 17**

## Pré-requisitos

Antes de rodar o projeto, verifique se você possui os seguintes pré-requisitos:

- **Docker** e **Docker Compose** instalados.
- **Java 17** instalado.
- **Maven** ou **Gradle** para gerenciar as dependências.

## Rodando o Projeto

### 1. Subindo o Banco de Dados MySQL com Docker

O arquivo `docker-compose.yml` para configurar o MySQL está localizado no diretório `docker/`. Para rodar o MySQL facilmente, use o Docker Compose. 

No diretório raiz do projeto, execute o seguinte comando:

```bash
cd docker
docker-compose up
````

### 2. Rodando o Backend (Spring Boot)

Clone este repositório e rode o projeto com o seguinte comando:

```bash
git clone https://github.com/henriquelima12/social-network-jwt-authentication-spring-security.git
cd springboot-rabbitmq
./mvnw spring-boot:run
```

Ou se você estiver usando o Maven globalmente:

```bash
mvn spring-boot:run
```

A aplicação será iniciada e estará disponível em http://localhost:8080.

## Endpoints

A autenticação é realizada através de um token JWT. Ao realizar o login, o sistema gera um token que deve ser passado no cabeçalho Authorization das requisições subsequentes.

### 1. POST /login
Envia uma requisição POST para o endpoint /login com as credenciais do usuário:

**Exemplo de requisição (JSON no corpo da requisição):**

```bash
{
  "username": "admin",
  "password": "123"
}
```

Para acessar os endpoints que exigem autenticação, passe o token JWT no cabeçalho Authorization da requisição:

```bash
Authorization: Bearer jwt_token_aqui
```

## Controle de Acesso Granular com roles
O sistema possui diferentes perfis de acesso, que são definidos através de roles. Algumas ações estão disponíveis apenas para administradores (ADMIN), enquanto outras estão disponíveis para usuários comuns (BASIC).

**Exemplo de Controle de Acesso nas Controllers**

Nas controllers, você pode utilizar anotações de segurança como @PreAuthorize para controlar o acesso. Exemplo:

```bash
@GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> listUsers() {
      ...
```

### Observações

Roles e Usuário Admin: ao iniciar o projeto, as roles (perfis de acesso) e o usuário ADMIN são automaticamente cadastrados no banco de dados. O usuário admin possui permissões totais para criar e excluir posts, enquanto o usuário com ROLE_USER tem permissões limitadas.
