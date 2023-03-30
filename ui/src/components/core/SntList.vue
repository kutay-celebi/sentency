<script lang="ts" setup>
import SntLoading from "@/components/core/SntLoading.vue";
</script>
<template>
  <ul>
    <snt-loading></snt-loading>
    <slot></slot>
  </ul>
</template>

<style lang="scss" scoped>
ul {
  list-style: none;
  padding: 0;
}
</style>
