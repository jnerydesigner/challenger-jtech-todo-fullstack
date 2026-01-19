import { createRouter, createWebHistory } from 'vue-router'

import DefaultLayout from '@/layouts/DefaultLayout.vue'
import HomeView from '@/views/HomeView.vue'
import { useUserStore } from '@/stores/user.store'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: DefaultLayout,
      meta: { requiresAuth: true },
      children: [
        {
          path: '',
          name: 'dashboard',
          component: HomeView
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/create-user',
      name: 'create-user',
      component: () => import('@/views/CreateUserView.vue'),
      meta: { requiresAuth: false }
    }
  ],
})

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  const requiresAuth = to.meta.requiresAuth

  if (requiresAuth) {
    if (userStore.isAuthenticated && userStore.user) {
      next()
    } else {
      try {
        await userStore.loadSession()
        next()
      } catch (error) {
        next({ name: 'login', query: { redirect: to.fullPath } })
      }
    }
  } else {
    if (to.name === 'login' && userStore.isAuthenticated && userStore.user) {
      next({ name: 'dashboard' })
    } else {
      next()
    }
  }
})

export default router
