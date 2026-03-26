# 📝 Guia de Uso - Postman (Session-Based Auth)

## 🔑 Fluxo de Autenticação

### 1️⃣ **CRIAR USUÁRIO** (Público)
```
POST http://localhost:8080/login/criarUsuario
Content-Type: application/json

{
  "username": "lucas",
  "email": "lucas@example.com",
  "password": "Senha123!",
  "name": "Lucas Xavier"
}
```

✅ **Resposta**: `Usuário criado com sucesso`

---

### 2️⃣ **FAZER LOGIN** (Público - Cria Sessão)
```
POST http://localhost:8080/login
Content-Type: application/json

{
  "email": "lucas@example.com",
  "password": "Senha123!"
}
```

✅ **Resposta**: `{"message": "Login realizado com sucesso"}`

⚠️ **IMPORTANTE**: Após este login, o Postman automaticamente:
- Recebe um `Set-Cookie: JSESSIONID=...` 
- Envia automaticamente esse cookie nas próximas requisições
- Autoriza acessos aos endpoints protegidos

---

### 3️⃣ **BUSCAR INFORMAÇÕES DO USUÁRIO** (Protegido - Requer Sessão)

**Opção A: Imediatamente após login (Postman mantém cookie)**
```
GET http://localhost:8080/login/buscarUser/lucas@example.com
```

✅ **Resposta**:
```json
{
  "username": "lucas",
  "email": "lucas@example.com",
  "name": "Lucas Xavier"
}
```

**Opção B: Em outra aba sem fazer login**
```
GET http://localhost:8080/login/buscarUser/lucas@example.com
```

❌ **Resposta**: `401 Unauthorized` (requer autenticação)

---

## 🎯 Configuração do Postman

### Opção 1: Usar Cookies Automaticamente (Recomendado)
1. Abra `Postman Preferences` → `Cookies`
2. Habilite `Automatically persist cookies`
3. Login uma vez
4. Todas as requisições subsequentes usarão a sessão

### Opção 2: Copia e Cola de Cookies
1. Após login, abra a aba `Cookies` na resposta
2. Copie o `JSESSIONID`
3. Em outra requisição, vá em `Headers` e adicione:
   ```
   Cookie: JSESSIONID=xxx
   ```

---

## 📊 Endpoints Resumidos

| Método | Endpoint | Autenticação | Descrição |
|--------|----------|--------------|-----------|
| POST | `/login/criarUsuario` | ❌ Não | Criar novo usuário |
| POST | `/login` | ❌ Não | Fazer login (cria sessão) |
| GET | `/login/buscarUser/{email}` | ✅ Sim | Buscar dados do usuário |

---

## 🔍 Debug - Verificar Sessão

Se receber `401 Unauthorized`, verifique:

1. **Fez login primeiro?** → Execute `POST /login`
2. **Postman está enviando cookie?** → Abra DevTools (F12) → Network → Procure por `JSESSIONID`
3. **Cookie expirou?** → Faça login novamente
4. **URL correta?** → Verifique se é `localhost:8080` (não 8081, 3000, etc)

---

## 🚀 Fluxo Completo de Teste

```bash
# 1. Criar usuário
curl -X POST http://localhost:8080/login/criarUsuario \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@test.com","password":"Test123!","name":"Test User"}'

# 2. Fazer login (salva cookie em arquivo)
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"Test123!"}' \
  -c cookies.txt

# 3. Usar cookie em requisição protegida
curl -X GET http://localhost:8080/login/buscarUser/test@test.com \
  -b cookies.txt
```

---

## ✅ Tudo Pronto!

Seu projeto agora funciona com **autenticação por sessão HTTP**:
- ✅ Login cria sessão
- ✅ Endpoints protegidos requerem sessão ativa
- ✅ Sem tokens, apenas cookies padrão HTTP

