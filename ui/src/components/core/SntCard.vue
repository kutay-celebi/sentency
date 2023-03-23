<template>
  <div class="snt-card">
    <div class="snt-card_title text-large text-bold text-center">
      <slot name="title"></slot>
    </div>
    <div class="snt-card_content">
      <slot />
    </div>
    <div v-if="$slots.actions" class="snt-card_actions">
      <slot name="actions"></slot>
    </div>
  </div>
</template>

<script lang="ts" setup></script>

<style lang="scss" scoped>
.snt-card {
  width: 100%;

  &_title {
    padding: 0.25rem;
    //border-bottom: 1px solid var(--color-border-main);
  }

  &_content {
    padding: 2rem;
  }

  &_actions {
    padding: 1rem 0;
  }
}
</style>
