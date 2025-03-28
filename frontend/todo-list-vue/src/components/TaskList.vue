<template>
  <div>
    <ul>
      <li v-for="task in tasks" :key="task.id">
        {{ task.name }} <button @click="deleteTask(task.id)">Excluir</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'TaskList',
  props: {
    tasks: Array, // Lista de tarefas
  },
  methods: {
    deleteTask(id) {
      this.$emit('deleteTask', id); // Emite o evento para o pai (Dashboard)
    },
  },
};
</script>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 10px 0;
}

button {
  margin-left: 10px;
  color: red;
}
</style>
