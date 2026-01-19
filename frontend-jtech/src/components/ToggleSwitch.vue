<template>
  <button role="switch" type="button" :aria-checked="modelValue" @click="$emit('update:modelValue', !modelValue)" class="relative inline-flex h-7 w-12 items-center rounded-full
           transition-all duration-300 ease-in-out focus:outline-none
           focus:ring-2 focus:ring-offset-2 cursor-pointer shadow-sm" :class="[
            modelValue
              ? 'bg-green-500 hover:bg-green-600 focus:ring-green-500'
              : 'bg-gray-300 hover:bg-gray-400 focus:ring-gray-400 dark:bg-gray-600 dark:hover:bg-gray-500'
          ]">
    <span class="inline-block h-5 w-5 transform rounded-full bg-white
             transition-all duration-300 ease-in-out shadow-md"
      :class="modelValue ? 'translate-x-6' : 'translate-x-1'" />
  </button>
</template>

<script setup lang="ts">
defineProps<{
  modelValue: boolean
}>()

defineEmits<{
  'update:modelValue': [value: boolean]
}>()
</script>
