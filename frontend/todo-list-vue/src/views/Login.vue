<template>
  <div class="login-container">
    <div class="login-box">
      <h1 class="login-fonte-principal">Login</h1>
      <form @submit.prevent="login">
        <div class="input-group">
          <label for="username" class="login-fonte">Usuário</label>
          <input type="text" id="username" v-model="username" required placeholder="Digite seu usuário" />
        </div>
        <div class="input-group">
          <label for="password" class="login-fonte">Senha</label>
          <input type="password" id="password" v-model="password" required placeholder="Digite sua senha" />
        </div>
        <button type="submit" :disabled="loading">
          {{ loading ? "Entrando..." : "Entrar" }}
        </button>
      </form>
      <p id="naotemconta">Não tem uma conta? <router-link to="/register" class="hypertexto-register"><i>Cadastre-se</i></router-link></p>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

export default {
  name: 'Login',
  setup() {
    const router = useRouter();
    const username = ref('');
    const password = ref('');
    const loading = ref(false);
    const errorMessage = ref('');

    const login = async () => {
      loading.value = true;
      errorMessage.value = '';

      try {
        const formData = new URLSearchParams();
        formData.append('username', username.value);
        formData.append('password', password.value);

        const response = await axios.post('http://localhost:8000/login', formData);

        // Armazena o token de autenticação
        localStorage.setItem('user', JSON.stringify(response.data));

        // Redireciona para o Dashboard após o login
        router.push('/dashboard');
      } catch (error) {
        // Exibe a mensagem de erro amigável
        if (error.response) {
          errorMessage.value = 'Usuário ou senha inválidos.';
        } else {
          errorMessage.value = 'Erro ao conectar ao servidor!';
        }
      } finally {
        loading.value = false;
      }
    };

    return { username, password, login, loading, errorMessage };
  },
};
</script>

<style scoped>
/* Estilização geral */
.hypertexto-register {
  color: black;
}

.hypertexto-register:hover {
  animation: fadeIn 0.5s forwards ease-out;
  transform: translateY(20px);
  opacity: 0;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0)
  }
}

.login-fonte {
  font-family: sans-serif;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url('/images/background-login.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
}

/* Caixa de login */
.login-box {
  background: rgb(255, 255, 255);
  padding: 20px 30px;
  /*border-radius: 10px; */
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 300px;
  font-family: sans-serif;
  font-size: large;  
}

/* Estilo dos inputs */
.input-group {
  margin-bottom: 15px;
  text-align: left;
}

.input-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

#naotemconta{
  font-family: sans-serif;
}

.input-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 10px;
  font-size: 14px;
  transition: 0.3s;
}

.input-group input:focus {
  border-color: #007bff;
  outline: none;
}

/* Botão de login */
button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  border: none;
  color: white;
  font-size: 16px;
  border-radius: 5px;
  cursor: pointer;
  transition: 0.3s;
  font-family: sans-serif;
}

button:hover {
  background-color: #0056b3;
}

button:disabled {
  background-color: #aaa;
  cursor: not-allowed;
}

/* Mensagem de erro */
.error-message {
  color: red;
  margin-top: 10px;
  font-size: 14px;
}
</style>
