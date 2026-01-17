<template>
    <div class="min-h-screen flex items-center justify-center bg-gray-100">
        <div class="w-full max-w-md bg-white rounded-xl shadow-lg p-8">
            <h2 class="text-2xl font-bold text-center mb-6">Task Manager</h2>

            <form @submit.prevent="handleLogin" class="space-y-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700">
                        Email
                    </label>
                    <input v-model="email" type="email" required
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
                    class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition cursor-pointer"
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

<script setup>
import { login } from '@/services/login'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const email = ref('')
const password = ref('')
const loading = ref(false)
const error = ref(null)

async function handleLogin() {
    error.value = null
    loading.value = true

    try {
        const response = await login(email.value, password.value);
        localStorage.setItem('user_name', response.name)
        localStorage.setItem('user_email', response.email)

        router.push({ name: 'home' })
    } catch (e) {
        error.value = 'Email ou senha inválidos'
    } finally {
        loading.value = false
    }
}
</script>
