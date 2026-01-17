<template>
  <div class="min-h-screen w-full bg-neutral-900 flex justify-center items-center flex-col">
    <HeaderView />
    <div class="w-full max-w-[1300px] min-h-screen bg-gray-500 p-2">
      <RouterView />
    </div>
  </div>
</template>

<script setup lang="ts">
import HeaderView from '@/components/HeaderView.vue';
import { RouterView } from 'vue-router'
</script>
