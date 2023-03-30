<script lang="ts" setup></script>
<template>
  <li class="snt-list-item">
    <div>
      <slot></slot>
    </div>
    <div>
      <slot name="action"></slot>
    </div>
  </li>
</template>

<style lang="scss" scoped>
.snt-list-item {
  display: flex;
  border: 1px solid var(--color-border-main);
  border-radius: 4px;
  padding: 0.25rem;
  margin: 0.125rem 0;
  cursor: pointer;
  transition: 0.3s ease background-color;
  &:hover {
    background-color: var(--color-hover);
  }
}
</style>
