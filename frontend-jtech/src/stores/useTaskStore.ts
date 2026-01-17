import { tasksFindAll } from '@/services/tasks'
import type { UserWithTasks } from '@/types/tasks.type'
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useTaskStore = defineStore('tasks', () => {
    const tasks = ref<UserWithTasks | null>(null)

    const tasksComputed = computed(() => tasks.value?.tasks || [])

    const fetchTasks = async () => {
        try {
            const response = await tasksFindAll()
            tasks.value = response
        } catch (error) {
            console.error('Erro ao buscar tarefas:', error)
        }
    }

    return {
        tasks,
        tasksComputed,
        fetchTasks
    }
})