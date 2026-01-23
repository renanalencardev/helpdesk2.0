# Análise do Fluxo de Autenticação - Auth Service API

Esta análise detalha o processo de autenticação implementado na API, cobrindo desde a requisição inicial até a geração do token JWT, com foco especial nas abstrações do Spring Security.

## 1. Visão Geral
A autenticação utiliza o padrão **JWT (JSON Web Token)** e é construída sobre o **Spring Security**. O fluxo é iniciado através de um endpoint REST (`/api/auth/login`), delega a validação para o `AuthenticationManager` do Spring e, em caso de sucesso, gera um token assinado.

## 2. Componentes Principais

### 2.1 Configuração de Segurança (`SecurityConfig.java`)
- **Estado**: A aplicação é configurada como `STATELESS` (sem sessão no servidor), ideal para APIs REST com JWT.
- **Endpoints Públicos**: As rotas do Swagger e o endpoint de login (`/api/auth/login`) são liberados.
- **Proteção**: Qualquer outra requisição exige autenticação (`.anyRequest().authenticated()`).
- **CSRF**: Desabilitado, pois tokens JWT são imunes a CSRF se armazenados corretamente (ex: localStorage/cookie seguro sem sessão).

### 2.2 Controller (`AuthControllerImpl.java`)
É a porta de entrada. Recebe a requisição POST com `AuthenticateRequest` (email e senha) e chama o `JWTAuthenticationImpl`.

### 2.3 Orquestrador Customizado (`JWTAuthenticationImpl.java`)
Uma classe wrapper que encapsula a lógica de chamar o `AuthenticationManager` e gerar o token. Isso desacopla o Controller da lógica pura de segurança e geração de token.

### 2.4 Utilitário JWT (`JWTUtils.java`)
Responsável por criar o token JWT, assinando-o com um segredo (`hs256`) e definindo claims (dados do usuário no payload do token).

## 3. Fluxo Detalhado (Passo a Passo)

### Passo 1: Requisição de Login
O cliente envia um POST para `/api/auth/login` com:
```json
{
  "email": "usuario@exemplo.com",
  "password": "senha123"
}
```
O `AuthControllerImpl` recebe estes dados no objeto `AuthenticateRequest`.

### Passo 2: Delegação para Autenticação
O `AuthControllerImpl` instancia (ou usa injetado) o `JWTAuthenticationImpl` e chama o método `authenticate(request)`.
Dentro deste método, é criado um token de autenticação *não autenticado* do Spring:
```java
new UsernamePasswordAuthenticationToken(request.email(), request.password())
```

### Passo 3: O "Grande Abstrator" - AuthenticationManager
O `JWTAuthenticationImpl` chama `authenticationManager.authenticate(token)`.
- **O que acontece aqui?** O `AuthenticationManager` (geralmente implementado pelo `ProviderManager`) percorre uma lista de `AuthenticationProvider`s configurados.
- **DaoAuthenticationProvider**: O provider padrão para login com usuário/senha entra em ação.

### Passo 4: Carregamento do Usuário (`UserDetailsServiceImpl`)
O `DaoAuthenticationProvider` precisa dos dados do usuário do banco para comparar. Ele chama o método `loadUserByUsername(email)` da implementação de `UserDetailsService`, que neste projeto é a classe `UserDetailsServiceImpl`.

**Fluxo Interno do `UserDetailsServiceImpl`:**
1.  Busca a entidade `User` no banco de dados via `UserRepository.findByEmail(email)`.
2.  Se não encontrar, lança `UsernameNotFoundException`.
3.  Se encontrar, converte a entidade `User` para `UserDetailsDTO`.
    - **Mapeamento**:
        - `username` -> email
        - `password` -> senha (hash) do banco
        - `authorities` -> converte os `Profiles` do usuário em `SimpleGrantedAuthority`.

### Passo 5: Validação da Senha (Abstração do Spring)
De posse do `UserDetailsDTO` (retornado no passo 4) e da senha enviada na requisição (Passo 1), o `DaoAuthenticationProvider` realiza a verificação:
- Ele usa o `BCryptPasswordEncoder` (definido no `SecurityConfig`) para verificar se a senha enviada ("senha123") bate com o hash armazenado no `UserDetailsDTO`.
- Se falhar: Lança `BadCredentialsException`.
- Se sucesso: Retorna um objeto `Authentication` totalmente preenchido e marcado como autenticado.

### Passo 6: Retorno e Geração do Token
O controle volta para o `JWTAuthenticationImpl`.
1.  Ele extrai o `UserDetailsDTO` do resultado da autenticação.
2.  Chama `jwtUtil.generateToken(detailsDTO)`.

**Dentro do `JWTUtils`:**
- Cria um token JWT contendo:
    - **Subject**: email do usuário.
    - **Claims**: `id`, `name`, `authorities`.
    - **Assinatura**: Assinado com o segredo configurado.
    - **Validade**: Data de expiração definida.

### Passo 7: Resposta
O `JWTAuthenticationImpl` monta o `AuthenticateResponse` com o token (prefixo "Bearer ") e retorna ao Controller, que responde com status 200 OK.

## Diagrama Resumo das Abstrações

1.  **Requisição** -> `AuthController`
2.  `AuthController` -> `JWTAuthenticationImpl`
3.  `JWTAuthenticationImpl` -> `AuthenticationManager` (Spring Security Core)
4.  `AuthenticationManager` -> `DaoAuthenticationProvider` (Validador de senha)
5.  `DaoAuthenticationProvider` -> `UserDetailsServiceImpl` (Carregador de dados)
6.  `UserDetailsServiceImpl` -> `UserRepository` (Banco de dados)
7.  `UserDetailsServiceImpl` <- Retorna `UserDetailsDTO`
8.  `DaoAuthenticationProvider` -> Valida senha (BCrypt)
9.  `JWTAuthenticationImpl` <- Recebe sucesso
10. `JWTAuthenticationImpl` -> `JWTUtils` (Gera Token)
11. **Resposta** <- Token JWT

Este design separa bem as responsabilidades, mantendo a lógica de negócio (validar senha, buscar usuário) dentro das abstrações padrões do Spring Security, enquanto a geração do token permanece em um utilitário dedicado.
