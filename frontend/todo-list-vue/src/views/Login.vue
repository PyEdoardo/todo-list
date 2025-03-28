<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div class="input-group">
        <label for="username">Usuário</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div class="input-group">
        <label for="password">Senha</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">Entrar</button>
    </form>
    <p>Não tem uma conta? <router-link to="/register">Cadastre-se</router-link></p>
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
    
    const login = async () => {
      try {
        const formData = new URLSearchParams();
        formData.append('username', username.value);
        formData.append('password', password.value);
        
        const response = await axios.post('http://localhost:8000/login', formData);
        
        // Armazena o token de autenticação ou a resposta
        localStorage.setItem('user', JSON.stringify(response.data)); // Exemplo de armazenamento
        
        // Redireciona para o Dashboard após o login
        router.push('/dashboard');
      } catch (error) {
        // Exibe a mensagem de erro, se houver
        if (error.response) {
          alert('Erro ao fazer login: ' + error.response.data.detail);
        } else {
          alert('Erro de conexão!');
        }
      }
    };

    return { username, password, login };
  },
};
</script>

<style scoped>
.login-container {
  text-align: center;
  margin-top: 50px;
}
.input-group {
  margin-bottom: 10px;
}
</style>
