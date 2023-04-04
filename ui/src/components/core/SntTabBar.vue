<script lang="ts" setup></script>

<template>
  <div class="snt-tabbar">
    <slot />
  </div>
</template>

<style lang="scss" scoped>
.snt-tabbar {
  display: flex;
  position: fixed;
  bottom: calc(env(safe-area-inset-bottom));
  width: 100%;
  height: 40px;
  border-top: 1px solid var(--color-border-main);
}
</style>
