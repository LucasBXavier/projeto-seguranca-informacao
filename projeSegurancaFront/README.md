# Front-end - Sistema de Segurança

Um front-end simples e responsivo para um sistema de login, cadastro e gerenciamento de perfil de usuários.

## Funcionalidades

✅ **Login** - Autenticação segura com token JWT
✅ **Cadastro** - Criar nova conta de usuário
✅ **Dashboard** - Visualizar informações do perfil
✅ **Logout** - Sair da conta com segurança
✅ **Responsivo** - Design adaptável para mobile, tablet e desktop

## Arquivos

- `index.html` - Estrutura HTML das 3 telas
- `styles.css` - Estilização com gradiente moderno
- `script.js` - Lógica de funcionamento e comunicação com o backend

## Como Usar

### 1. Abrir o Front-end

```bash
# Navegue até a pasta do projeto e abra o arquivo index.html em um navegador
# Você pode usar um servidor local ou abrir diretamente no navegador
```

### 2. Endpoints Esperados do Backend

O front-end comunica com os seguintes endpoints:

#### Login
```
POST /api/auth/login
Body: { email: string, password: string }
Response: { token: string } ou { jwtToken: string }
```

#### Cadastro
```
POST /api/users/register
Body: { name: string, email: string, password: string }
Response: { id: number, name: string, email: string }
```

#### Perfil do Usuário
```
GET /api/users/profile
Headers: { Authorization: "Bearer {token}" }
Response: { id: number, name: string, email: string, createdAt: string }
```

### 3. Configuração

Para alterar a URL do servidor, edite a variável `API_BASE_URL` em `script.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080/api'; // Altere conforme necessário
```

## Design

- **Cores**: Gradiente roxo-azul moderno
- **Fontes**: Segoe UI (padrão do Windows)
- **Layout**: Card centralizado com sombra
- **Responsividade**: Otimizado para telas a partir de 320px

## Fluxo do Usuário

1. **Tela de Login** → Insira email e senha
2. **Tela de Cadastro** → Crie uma nova conta
3. **Dashboard** → Veja suas informações após autenticar
4. **Logout** → Saia da conta quando necessário

## Recursos Técnicos

- Armazenamento de token no `localStorage`
- Comunicação com API via `fetch`
- Transições suaves entre telas
- Validação de formulários HTML5
- Tratamento de erros com mensagens amigáveis
- Suporte a navegação com tecla Enter

## Notas de Segurança

⚠️ **IMPORTANTE**: O token é armazenado no `localStorage`. Para produção:
- Considere usar `httpOnly` cookies em vez de `localStorage`
- Implemente refresh token
- Valide tokens no backend
- Use HTTPS em produção

## Testes

### Teste de Cadastro
1. Clique em "Cadastre-se"
2. Preencha nome, email e senha
3. Verifique se aparece mensagem de sucesso

### Teste de Login
1. Faça login com as credenciais da conta criada
2. Deverá ir para o dashboard
3. Informações do perfil devem aparecer

### Teste de Logout
1. Clique no botão "Sair"
2. Volte para a tela de login
3. LocalStorage deve estar vazio

---

Desenvolvido com ❤️ em HTML, CSS e JavaScript puro.
