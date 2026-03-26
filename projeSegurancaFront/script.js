// Configurações
const API_BASE_URL = 'http://localhost:8080/login';
let userEmail = localStorage.getItem('userEmail');

// Verificar se o usuário está autenticado ao carregar a página
document.addEventListener('DOMContentLoaded', () => {
    const savedEmail = localStorage.getItem('userEmail');
    if (savedEmail) {
        // Ir direto para o dashboard sem verificar primeira (a sessão deve estar ativa)
        switchScreen('dashboardScreen');
        loadUserInfo(savedEmail);
    }
});

// Trocar entre telas
function switchScreen(screenId) {
    // Remover classe active de todas as telas
    document.querySelectorAll('.screen').forEach(screen => {
        screen.classList.remove('active');
    });

    // Adicionar classe active à tela selecionada
    const targetScreen = document.getElementById(screenId);
    if (targetScreen) {
        targetScreen.classList.add('active');
    }

    // Limpar mensagens de erro/sucesso
    clearMessages();
}

// Limpar mensagens de erro e sucesso
function clearMessages() {
    document.querySelectorAll('.error-message, .success-message').forEach(msg => {
        msg.classList.remove('show');
        msg.textContent = '';
    });
}

// Mostrar mensagem de erro
function showError(screenId, message) {
    const errorDiv = document.getElementById(`${screenId.replace('Screen', 'Error')}`);
    if (errorDiv) {
        errorDiv.textContent = message;
        errorDiv.classList.add('show');
    }
}

// Mostrar mensagem de sucesso
function showSuccess(screenId, message) {
    const successDiv = document.getElementById(`${screenId.replace('Screen', 'Success')}`);
    if (successDiv) {
        successDiv.textContent = message;
        successDiv.classList.add('show');
    }
}

// Formulário de Login
document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include', // Enviar e receber cookies (JSESSIONID)
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        if (response.ok) {
            // Login bem-sucedido, servidor enviou JSESSIONID no cookie
            userEmail = email;
            localStorage.setItem('userEmail', email);

            // Carregar informações do usuário e ir para dashboard
            await loadUserInfo(email);
            switchScreen('dashboardScreen');

            // Limpar formulário
            document.getElementById('loginForm').reset();
        } else {
            const text = await response.text();
            showError('login', text || 'Erro ao fazer login. Verifique suas credenciais.');
        }
    } catch (error) {
        showError('login', 'Erro ao conectar com o servidor. Verifique se o backend está rodando em localhost:8080');
        console.error('Erro:', error);
    }
});

// Formulário de Cadastro
document.getElementById('registerForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const name = document.getElementById('registerName').value;
    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;
    // Gerar username a partir do email (primeira parte antes do @)
    const username = email.split('@')[0];

    try {
        const response = await fetch(`${API_BASE_URL}/criarUsuario`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include', // Enviar cookies
            body: JSON.stringify({
                username: username,
                name: name,
                email: email,
                password: password
            })
        });

        if (response.ok || response.status === 201) {
            showSuccess('register', 'Cadastro realizado com sucesso! Faça login para continuar.');
            document.getElementById('registerForm').reset();
            
            // Aguardar um pouco e voltar para login
            setTimeout(() => {
                switchScreen('loginScreen');
            }, 2000);
        } else {
            const error = await response.text();
            showError('register', error || 'Erro ao cadastrar. Tente novamente.');
        }
    } catch (error) {
        showError('register', 'Erro ao conectar com o servidor');
        console.error('Erro:', error);
    }
});

// Carregar informações do usuário
async function loadUserInfo(email) {
    try {
        const emailToUse = email || userEmail || localStorage.getItem('userEmail');
        
        if (!emailToUse) {
            console.warn('Email não fornecido');
            return;
        }

        const response = await fetch(`${API_BASE_URL}/buscarUser/${emailToUse}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include' // Enviar JSESSIONID cookie
        });

        if (response.ok) {
            const data = await response.json();
            
            // Atualizar informações na tela
            document.getElementById('userUsername').textContent = data.username || 'N/A';
            document.getElementById('userName').textContent = data.name || 'N/A';
            document.getElementById('userEmail').textContent = data.email || 'N/A';
            
            // Formatar data se existir
            if (data.createdAt) {
                const date = new Date(data.createdAt);
                document.getElementById('userCreatedAt').textContent = date.toLocaleString('pt-BR');
            } else {
                document.getElementById('userCreatedAt').textContent = 'N/A';
            }
        } else if (response.status === 401 || response.status === 403) {
            // Sessão expirou ou não autorizado
            console.warn(`Erro ${response.status}: Sessão pode ter expirado`);
            // Mostrar informações locais como fallback
            document.getElementById('userEmail').textContent = emailToUse;
            document.getElementById('userUsername').textContent = 'N/A';
            document.getElementById('userName').textContent = 'Usuário';
            document.getElementById('userCreatedAt').textContent = 'N/A';
        } else {
            // Mostrar informações armazenadas localmente como fallback
            document.getElementById('userEmail').textContent = emailToUse;
            document.getElementById('userUsername').textContent = 'N/A';
            document.getElementById('userName').textContent = 'Usuário';
            document.getElementById('userCreatedAt').textContent = 'N/A';
        }
    } catch (error) {
        console.error('Erro ao carregar informações do usuário:', error);
        // Mostrar informações armazenadas localmente como fallback
        const emailToUse = email || userEmail || localStorage.getItem('userEmail');
        if (emailToUse) {
            document.getElementById('userEmail').textContent = emailToUse;
        }
    }
}

// Logout
function logout() {
    // Remover dados armazenados (o cookie JSESSIONID será removido pelo navegador)
    localStorage.removeItem('userEmail');
    userEmail = null;

    // Voltar para login
    switchScreen('loginScreen');

    // Limpar formulários
    document.getElementById('loginForm').reset();
    document.getElementById('registerForm').reset();

    // Limpar informações do usuário
    document.getElementById('userUsername').textContent = '-';
    document.getElementById('userName').textContent = '-';
    document.getElementById('userEmail').textContent = '-';
    document.getElementById('userCreatedAt').textContent = '-';

    clearMessages();
    showSuccess('login', 'Você saiu da conta');
}

// Alternar entre telas com Enter
document.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        const activeScreen = document.querySelector('.screen.active');
        const form = activeScreen?.querySelector('form');
        if (form) {
            form.dispatchEvent(new Event('submit'));
        }
    }
});
