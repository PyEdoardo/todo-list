<template>
    <div class="register-container">
      <h2>Cadastro</h2>
      <form @submit.prevent="register">
        <div class="input-group">
          <label for="username">Usuário</label>
          <input type="text" id="username" v-model="username" required />
        </div>
        <div class="input-group">
          <label for="password">Senha</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <div class="input-group">
          <label for="confirmPassword">Confirmar Senha</label>
          <input
            type="password"
            id="confirmPassword"
            v-model="confirmPassword"
            required
          />
        </div>
        <button type="submit">Cadastrar</button>
      </form>
      <p>Já tem uma conta? <router-link to="/login">Faça login</router-link></p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  
  export default {
    name: 'Register',
    setup() {
      const router = useRouter();
      const username = ref('');
      const password = ref('');
      const confirmPassword = ref('');
  
      const register = async () => {
        if (password.value !== confirmPassword.value) {
          alert('As senhas não coincidem');
          return;
        }
        try {
          const response = await axios.post('http://localhost:8000/register', {
            username: username.value,
            password: password.value,
          });
          alert('Cadastro bem-sucedido!');
          router.push('/login'); // Redireciona para login após o cadastro
        } catch (error) {
          alert('Erro ao cadastrar: ' + error.response.data);
        }
      };
  
      return { username, password, confirmPassword, register };
    },
  };
  </script>
  
  <style scoped>
  .register-container {
    text-align: center;
    margin-top: 50px;
  }
  .input-group {
    margin-bottom: 10px;
  }
  </style>
  