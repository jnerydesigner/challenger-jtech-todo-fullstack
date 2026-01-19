<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100">
    <div class="w-full max-w-md bg-white rounded-xl shadow-lg p-8">
      <h2 class="text-2xl font-bold text-center mb-6">Task Manager</h2>

      <form @submit.prevent="handleCreateUser" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-700">
            Email
          </label>
          <input v-model="email" type="email" required
            class="mt-1 w-full px-4 py-2 border rounded-md focus:ring focus:ring-blue-300" />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">
            Nome
          </label>
          <input v-model="name" type="text" required
            class="mt-1 w-full px-4 py-2 border rounded-md focus:ring focus:ring-blue-300" />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">
            Senha
          </label>
          <input v-model="password" type="password" required
            class="mt-1 w-full px-4 py-2 border rounded-md focus:ring focus:ring-blue-300" />
        </div>



        <button type="submit"
          class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition cursor-pointer disabled:opacity-50 disabled:cursor-not-allowed"
          :disabled="loading">
          {{ loading ? 'Entrando...' : 'Entrar' }}
        </button>

        <p v-if="error" class="text-red-500 text-sm text-center">
          {{ error }}
        </p>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import type { AxiosError } from 'axios'
import { createUser } from '@/services/user'

const router = useRouter()

const email = ref('')
const password = ref('')
const name = ref('')
const loading = ref(false)
const error = ref('')

async function handleCreateUser() {
  error.value = ''
  loading.value = true

  try {
    await createUser(name.value, email.value, password.value)

    router.push({ name: 'login' })
  } catch (e) {
    const errorObj = e as AxiosError
    console.error('Erro no cadastro:', errorObj)

    if (errorObj.response?.status === 401 || errorObj.response?.status === 403) {
      error.value = 'Email ou senha inválidos'
    } else if (errorObj.response?.status === 500) {
      error.value = 'Erro no servidor. Tente novamente.'
    } else if (!errorObj.response) {
      error.value = 'Erro de conexão. Verifique sua internet.'
    } else {
      error.value = 'Erro ao fazer login. Tente novamente.'
    }
  } finally {
    loading.value = false
  }
}
</script>
