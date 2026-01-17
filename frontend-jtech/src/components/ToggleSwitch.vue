<template>
    <button role="switch" :aria-checked="modelValue" @click="$emit('update:modelValue', !modelValue)" class="relative inline-flex h-6 w-11 items-center rounded-full
           transition-colors duration-300 focus:outline-none" :class="modelValue
            ? 'bg-green-500'
            : 'bg-slate-400 dark:bg-slate-600'">
        <span class="inline-block h-4 w-4 transform rounded-full bg-white
             transition-transform duration-300" :class="modelValue ? 'translate-x-6' : 'translate-x-1'" />
    </button>
</template>

<script setup>
defineProps({
    modelValue: {
        type: Boolean,
        required: true
    }
})
</script>
