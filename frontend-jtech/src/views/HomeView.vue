<template>


  <div v-if="taskStore.tasks" class="w-full max-w-  7xl mx-auto p-6">
    <div class="flex justify-end mb-6">
      <button @click="openModal = true" class="flex-1 px-4 py-2 rounded-lg bg-blue-500 hover:bg-blue-600
                     text-white text-sm font-medium transition-colors duration-200
                     focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2
                     flex items-center justify-center gap-2 cursor-pointer">
        <span>+</span>
        <span>Adicionar Tarefa</span>
      </button>
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div v-for="task in taskStore.tasksComputed" :key="task.id" class="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700
               rounded-xl shadow-sm hover:shadow-lg transition-all duration-300
               overflow-hidden">
        <div class="p-6 border-b border-gray-100 dark:border-gray-700">
          <h3 class="text-lg font-semibold text-gray-900 dark:text-white line-clamp-2">
            {{ task.title }}
          </h3>
        </div>

        <div class="p-6 space-y-4">
          <div class="flex items-center justify-between">


            <button @click="openModalView(task)" class="px-4 py-2 rounded-lg bg-blue-500 hover:bg-blue-600
                     text-white text-sm font-medium transition-colors duration-200
                     focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2
                     flex items-center justify-center gap-2 cursor-pointer">
              <span class="material-icons text-white">
                visibility
              </span>
              <span>Ver Detalhes</span>
            </button>

            <div class="flex items-center gap-2">
              <span class="text-sm font-medium"
                :class="task.completed ? 'text-green-600 dark:text-green-400' : 'text-gray-500 dark:text-gray-400'">
                {{ task.completed ? 'Ativo' : 'Inativo' }}
              </span>
            </div>
            <ToggleSwitch v-model="task.completed" @update:modelValue="() => onToggle(task.id)" />
          </div>

          <div class="flex gap-2">
            <button @click="openEditModal(task)" class="flex-1 px-4 py-2 rounded-lg bg-blue-500 hover:bg-blue-600
                     text-white text-sm font-medium transition-colors duration-200
                     focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2
                     flex items-center justify-center gap-2 cursor-pointer">
              <span>✏️</span>
              <span>Editar</span>
            </button>

            <button @click="deleteTask(task.id)" class="flex-1 px-4 py-2 rounded-lg bg-red-500 hover:bg-red-600
                     text-white text-sm font-medium transition-colors duration-200
                     focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-offset-2
                     flex items-center justify-center gap-2 cursor-pointer">
              <span>🗑️</span>
              <span>Apagar</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div v-else class="flex items-center justify-center min-h-[400px]">
    <div class="text-center space-y-3">
      <div
        class="inline-block h-8 w-8 animate-spin rounded-full border-4 border-solid border-blue-500 border-r-transparent">
      </div>
      <p class="text-gray-600 dark:text-gray-400">Carregando tarefas...</p>
    </div>
  </div>

  <div v-if="taskStore.tasks && taskStore.tasksComputed.length === 0"
    class="flex items-center justify-center min-h-[400px]">
    <div class="text-center space-y-3">
      <p class="text-xl text-gray-600 dark:text-gray-400">📝 Nenhuma tarefa encontrada</p>
      <p class="text-sm text-gray-500 dark:text-gray-500">Crie sua primeira tarefa para começar</p>
    </div>
  </div>



  <BaseModal :open="openModal" @close="openModal = false">
    <template #title>
      Criar tarefa
    </template>

    <div class="space-y-4">
      <p class="text-gray-600">
        Informe o título da tarefa:
      </p>

      <div>
        <label class="block text-sm font-medium text-gray-700 mb-1">
          Título
        </label>

        <input v-model="title" type="text" placeholder="Ex: Estudar Vue 3" class="w-full px-4 py-2 border rounded-md
               focus:ring focus:ring-blue-300 focus:outline-none" />
      </div>
    </div>

    <template #footer>
      <button class="px-4 py-2 rounded-md bg-gray-200 cursor-pointer" @click="openModal = false">
        Cancelar
      </button>

      <button class="px-4 py-2 rounded-md bg-blue-600 text-white disabled:opacity-50 cursor-pointer" :disabled="!title"
        @click="createTask">
        Salvar
      </button>
    </template>
  </BaseModal>

  <BaseModal :open="openModalEdit" @close="openModalEdit = false">
    <template #title>
      Editar tarefa
    </template>

    <div class="space-y-4">
      <p class="text-gray-600">
        Titulo da tarefa Original: {{ title }}
      </p>

      <div>
        <label class="block text-sm font-medium text-gray-700 mb-1">
          Título
        </label>

        <input v-model="title" type="text" placeholder="Ex: Estudar Vue 3" class="w-full px-4 py-2 border rounded-md
               focus:ring focus:ring-blue-300 focus:outline-none" />
      </div>
    </div>

    <template #footer>
      <button class="px-4 py-2 rounded-md bg-gray-200 cursor-pointer" @click="openModalEdit = false">
        Cancelar
      </button>

      <button class="px-4 py-2 rounded-md bg-blue-600 text-white disabled:opacity-50 cursor-pointer" :disabled="!title"
        @click="updateTask">
        Salvar
      </button>
    </template>
  </BaseModal>

  <BaseModal :open="openViewModal" @close="openViewModal = false">

    <div class="space-y-4">
      <p class="text-gray-600">
        {{ title }}
      </p>


      <span class="flex-1 px-4 py-2 rounded-lg text-white text-sm font-medium transition-colors duration-200
                     focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2
                     flex items-center justify-center gap-2 cursor-pointer"
        :class="status ? 'bg-green-500 hover:bg-green-600' : 'bg-gray-500 hover:bg-gray-600'">
        <span class="material-icons text-base">
          {{ status ? 'check_circle' : 'pause_circle' }}
        </span>
        {{ status ? 'Ativo' : 'Inativo' }}
      </span>
    </div>

    <template #footer>
      <button class="px-4 py-2 rounded-md bg-gray-200 cursor-pointer" @click="openViewModal = false">
        Fechar
      </button>
    </template>
  </BaseModal>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import ToggleSwitch from '@/components/ToggleSwitch.vue'
import { useTaskStore } from '@/stores/useTaskStore'
import { ref } from 'vue'
import BaseModal from '@/components/BaseModal.vue'

const taskStore = useTaskStore()
const openModal = ref(false)
const openModalEdit = ref(false)
const openViewModal = ref(false)
const title = ref('')
const titleOriginal = ref('')
const editingTaskId = ref('')
const status = ref(false)

onMounted(async () => {
  try {
    await taskStore.fetchTasks()
    console.log(taskStore.tasks)
  } catch (e) {
    console.error('Erro ao carregar tarefas:', e)
  }
})

async function onToggle(taskId: string) {
  try {
    await taskStore.toogleStatusTask(taskId)
  } catch (e) {
    console.error('Erro ao alterar status:', e)
  }
}

function openEditModal(task: any) {
  openModalEdit.value = true
  title.value = task.title
  titleOriginal.value = task.title
  editingTaskId.value = task.id
}

async function openModalView(task: any) {
  console.log(task)
  openViewModal.value = true
  title.value = task.title
  status.value = task.completed
}

async function deleteTask(id: string) {
  if (!taskStore.tasks) return

  if (confirm('Tem certeza que deseja apagar esta tarefa?')) {
    try {
      await taskStore.deleteTaskStore(id)
    } catch (e) {
      console.error('Erro ao deletar tarefa:', e)
    }
  }
}

async function createTask() {
  if (!title.value) return

  try {
    await taskStore.createTask(title.value)
    openModal.value = false
    title.value = ''
  } catch (e) {
    console.error('Erro ao criar tarefa:', e)
  }
}

async function updateTask() {
  if (!title.value) return

  try {
    await taskStore.updateTaskStore(editingTaskId.value, title.value)
    openModalEdit.value = false
    title.value = ''
    editingTaskId.value = ''
  } catch (e) {
    console.error('Erro ao atualizar tarefa:', e)
  }
}
</script>
