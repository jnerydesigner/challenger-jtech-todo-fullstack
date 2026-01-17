<template>
  <div v-if="taskStore.tasks" class="w-full max-w-7xl mx-auto p-6">
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <div v-for="task in taskStore.tasksComputed" :key="task.id" class="bg-(--surface) border border-(--border)
               rounded-xl shadow-sm hover:shadow-md transition">
        <div class="grid grid-cols-[1fr_auto] items-center gap-6 p- min-h-[120px] flex-col">
          <div class="flex flex-row gap-1">
            <h3 class="text-lg font-semibold text-(--text) flex-1 flex justify-center items-center">
              {{ task.title }}
            </h3>

            <div class="flex items-center gap-4 flex-col p-2">
              <span class="text-sm text-(--text-muted)">
                {{ task.completed ? 'Ativo' : 'Inativo' }}
              </span>

              <ToggleSwitch v-model="task.completed" />

              <button @click="editTask(task.id)" class="px-4 py-2 rounded-md bg-(--primary)
                     text-white text-sm hover:bg-(--primary-hover)
                     transition">✏️ Editar</button>
              <button @click="deleteTask(task.id)" class="px-4 py-2 rounded-md bg-(--danger)
                     text-white text-sm hover:bg-(--danger-hover)
                     transition">🗑️ Apagar</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div v-else class="p-6 text-center">
    Carregando tarefas...
  </div>
</template>


<script setup lang="ts">
import { onMounted } from 'vue'
import ToggleSwitch from '@/components/ToggleSwitch.vue'
import { useTaskStore } from '@/stores/useTaskStore'

const taskStore = useTaskStore()

onMounted(async () => {
  try {
    taskStore.fetchTasks()
    console.log(taskStore.tasks)
  } catch (e) {
    console.error(e)
  }
})

function editTask(id: string) {
  console.log('Editar task', id)
}

function deleteTask(id: string) {
  if (!taskStore.tasks) return

}
</script>
