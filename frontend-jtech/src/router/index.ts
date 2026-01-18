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
          name: 'home',
          component: HomeView
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue'),
      meta: { requiresAuth: false }
    }
  ],
})

router.beforeEach((to) => {
  const userStore = useUserStore()


  if (!to.meta.requiresAuth) {
    if (to.name === 'login' && userStore.isAuthenticated) {
      return { name: 'home' }
    }
    return true
  }

  if (!userStore.isAuthenticated) {
    return { name: 'login' }
  }

  return true
})


export default router
