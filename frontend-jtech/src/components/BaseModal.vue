<template>
  <transition name="fade">
    <div v-if="open" class="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
      <div class="bg-white rounded-xl shadow-lg w-full max-w-md p-6">


        <div class="flex justify-between items-center mb-4">
          <h2 class="text-lg font-semibold">
            <slot name="title" />
          </h2>

          <button class="text-gray-400 hover:text-gray-600" @click="$emit('close')">
            ✕
          </button>
        </div>

        <div class="mb-6">
          <slot />
        </div>

        <div class="flex justify-end gap-2">
          <slot name="footer" />
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup lang="ts">
defineProps({
  open: {
    type: Boolean,
    required: true
  }
})
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
