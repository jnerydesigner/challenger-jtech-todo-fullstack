import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { UserLoginType } from '@/types/user-login.type'
import { api } from '@/services/api'

export type User = {
  id: string
  name: string
  email: string
}

export const useUserStore = defineStore('user', () => {
  const user = ref<User | null>(null)
  const isAuthenticated = ref(false)
  const loading = ref(false)

  async function loadSession() {
    loading.value = true
    try {
      const { data } = await api.get('/user/info-me')
      user.value = data
      isAuthenticated.value = true
    } catch {
      user.value = null
      isAuthenticated.value = false
    } finally {
      loading.value = false
    }
  }

  function setUser(payload: UserLoginType) {
    user.value = {
      id: payload.id,
      name: payload.name,
      email: payload.email,
    }
  }

  function clearUser() {
    user.value = null
    isAuthenticated.value = false
  }


  return {
    user,
    isAuthenticated,
    setUser,
    clearUser,
    loadSession
  }
}, {
  persist: true,
})
