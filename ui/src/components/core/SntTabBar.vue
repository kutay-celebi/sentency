<script lang="ts" setup>
import SntTabBarItem from "@/components/core/SntTabBarItem.vue";
</script>

<template>
  <div class="snt-tabbar">
    <slot />
  </div>
</template>

<style lang="scss" scoped>
.snt-tabbar {
  display: flex;
  position: fixed;
  bottom: 0;
  bottom: calc(env(safe-area-inset-bottom));
  width: 100%;
  height: 3rem;

  border-top: 1px solid var(--color-border-main);
}
</style>
