<template>
  <header class="w-full bg-slate-700 shadow-md">
    <div class="max-w-[1200px] mx-auto px-6 py-4 flex items-center justify-between">

      <div class="flex items-center gap-3">
        <div
          class="w-10 h-10 rounded-lg bg-gradient-to-br from-indigo-400 to-purple-500 flex items-center justify-center text-white text-lg">
          📋
        </div>
        <span class="text-xl font-semibold text-white">
          TaskManager
        </span>
      </div>

      <div class="relative" ref="dropdownRef">
        <div class="flex items-center gap-4 cursor-pointer select-none" @click="toggleMenu">
          <div class="text-right">
            <p class="text-sm font-semibold text-white">
              {{ userStore.user?.name }}
            </p>
            <p class="text-xs text-slate-300">
              {{ userStore.user?.email }}
            </p>
          </div>

          <div class="w-10 h-10 rounded-full bg-gradient-to-br from-indigo-400 to-purple-500
                   flex items-center justify-center text-white font-semibold">
            {{ initials }}
          </div>
        </div>

        <transition name="fade">
          <div v-if="isOpen"
            class="absolute right-0 mt-3 w-40 bg-white rounded-lg shadow-lg border border-slate-200 z-50">
            <button @click="logoutApp" class="w-full text-left px-4 py-3 text-sm text-slate-700
                     hover:bg-slate-100 rounded-lg cursor-pointer">
              🚪 Logout
            </button>
          </div>
        </transition>
      </div>

    </div>
  </header>
</template>


<script setup lang="ts">
import { onMounted, onBeforeUnmount, ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { getInitials } from '@/helper/get-initials'
import { logout } from '@/services/logout'
import { useUserStore } from '@/stores/user.store'

const userStore = useUserStore()
const router = useRouter()

const initials = computed(() => getInitials(userStore.user?.name || ''))

const isOpen = ref(false)
const dropdownRef = ref<HTMLElement | null>(null)

function toggleMenu() {
  isOpen.value = !isOpen.value
}

function closeMenu(event: MouseEvent) {
  if (
    dropdownRef.value &&
    !dropdownRef.value.contains(event.target as Node)
  ) {
    isOpen.value = false
  }
}

function logoutApp() {
  logout()

  userStore.clearUser()

  router.push('/login')
}

onMounted(() => {
  document.addEventListener('click', closeMenu)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', closeMenu)
})
</script>
