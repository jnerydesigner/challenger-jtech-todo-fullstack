import router from '@/router'
import { useUserStore } from '@/stores/user.store'
import axios from 'axios'

export const api = axios.create({
  baseURL: 'http://localhost:4444',
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true
})

api.interceptors.response.use(
  res => res,
  error => {
    if (
      error.response?.status === 401 &&
      !error.config?.url?.includes('/auth/login')
    ) {
      const userStore = useUserStore()
      userStore.clearUser()
      router.push({ name: 'login' })
    }

    return Promise.reject(error)
  }
)

