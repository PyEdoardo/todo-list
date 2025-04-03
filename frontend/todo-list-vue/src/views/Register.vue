<template>
  <div class="background">
    <div class="register-container">
      <h1>Cadastro</h1>
      <form @submit.prevent="register">
        <div class="input-group">
          <label for="username">Usuário</label>
          <input type="text" id="username" v-model="username" required />
        </div>

        <div class="input-group">
          <label for="password">Senha</label>
          <input
            type="password"
            id="password"
            v-model="password"
            :class="{ 'input-error': passwordMismatch }"
            required
          />
        </div>

        <div class="input-group">
          <label for="confirmPassword">Confirmar Senha</label>
          <input
            type="password"
            id="confirmPassword"
            v-model="confirmPassword"
            :class="{ 'input-error': passwordMismatch }"
            required
          />
          <p v-if="passwordMismatch" class="error-message">As senhas não coincidem</p>
        </div>

        <button type="submit">Cadastrar</button>
      </form>

      <p class="login-link">
        Já tem uma conta? <router-link to="/login">Faça login</router-link>
      </p>
      </div>
    </div>
</template>

<script>
import axios from 'axios';
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';

export default {
  name: 'Register',
  setup() {
    const router = useRouter();
    const username = ref('');
    const password = ref('');
    const confirmPassword = ref('');

    // Computed para verificar se as senhas não batem
    const passwordMismatch = computed(() => password.value && confirmPassword.value && password.value !== confirmPassword.value);

    const register = async () => {
      if (passwordMismatch.value) {
        return;
      }
      try {
        await axios.post('http://localhost:8000/register', {
          username: username.value,
          password: password.value,
        });
        alert('Cadastro bem-sucedido!');
        router.push('/login'); // Redireciona para login após o cadastro
      } catch (error) {
        alert('Erro ao cadastrar: ' + error.response.data);
      }
    };

    return { username, password, confirmPassword, register, passwordMismatch };
  },
};
</script>

<style scoped>
/* Container principal */

.background {
  background-image: url('/images/background-login.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
}

.register-container {
  width: 100%;
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* Título */
h1 {
  font-size: 24px;
  color: #333;
}

/* Grupo de inputs */
.input-group {
  padding: 10px 10px 10px 0px;
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
  justify-content: center;
  text-align: left;
}

/* Rótulos */
label {
  font-size: 14px;
  font-weight: bold;
  color: #555;
  margin-bottom: 5px;
  
}

/* Campos de entrada */
input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
  transition: 0.3s;
}

/* Efeito de foco */
input:focus {
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
  outline: none;
}

/* Estilo para erro */
.input-error {
  border-color: #ff4d4d;
  background-color: #ffe6e6;
}

/* Mensagem de erro */
.error-message {
  color: #ff4d4d;
  font-size: 14px;
  margin-top: 5px;
}

/* Botão de envio */
button {
  width: 100%;
  background: #007bff;
  color: #fff;
  padding: 10px;
  font-size: 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: 0.3s;
}

button:hover {
  background: #0056b3;
}

/* Link para login */
.login-link {
  margin-top: 15px;
  font-size: 14px;
}

.login-link a {
  color: #007bff;
  text-decoration: none;
  font-weight: bold;
}

.login-link a:hover {
  text-decoration: underline;
}
</style>
