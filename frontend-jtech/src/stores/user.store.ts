import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { UserLoginType } from '@/types/user-login.type'

export type User = {
  id: string
  name: string
  email: string
}

export const useUserStore = defineStore('user', () => {
  const user = ref<User | null>(null)

  const isAuthenticated = computed(() => !!user.value)

  function setUser(payload: UserLoginType) {
    user.value = {
      id: payload.id,
      name: payload.name,
      email: payload.email,
    }
  }

  function clearUser() {
    user.value = null
  }

  return {
    user,
    isAuthenticated,
    setUser,
    clearUser,
  }
})
