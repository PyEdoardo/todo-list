<template>
  <div class="dashboard">
    <h1>Todo List</h1>
    <task-list :tasks="tasks" @deleteTask="deleteTask" />
    <input v-model="newTask" placeholder="Nova tarefa" />
    <button @click="addTask">Adicionar</button>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import TaskList from '../components/TaskList.vue';

export default {
  name: 'Dashboard',
  components: {
    TaskList,
  },
  setup() {
    const tasks = ref([]);
    const newTask = ref('');
    const userId = 1;  // Usuário de exemplo, você pode pegar isso do login.

    // Função para buscar tarefas no backend
    const fetchTasks = async () => {
      const response = await axios.get(`http://localhost:8000/todos/${userId}`);
      tasks.value = response.data;
    };

    // Função para adicionar tarefa
    const addTask = async () => {
      if (newTask.value.trim()) {
        await axios.post('http://localhost:8000/todos/', { name: newTask.value, user_id: userId });
        newTask.value = '';
        fetchTasks();
      }
    };

    // Função para excluir tarefa
    const deleteTask = async (id) => {
      await axios.delete(`http://localhost:8000/todos/${id}`);
      fetchTasks();
    };

    onMounted(fetchTasks);

    return { tasks, newTask, addTask, deleteTask };
  },
};
</script>

<style scoped>
.dashboard {
  text-align: center;
  margin-top: 20px;
}
</style>
