<template>
  <div :class="['snt-button', props.color, `${props.color}-bg`, { loading: loading }, 'hoverable']">
    <slot />
    <snt-loading :loading="props.loading"></snt-loading>
  </div>
</template>

<script lang="ts" setup>
import type { PropType } from "vue";
import SntLoading from "@/components/core/SntLoading.vue";

type ThemeColor = "primary" | "secondary" | "success" | "error";

const props = defineProps({
  color: {
    type: String as PropType<ThemeColor>,
    default: "primary",
  },
  loading: Boolean,
});
</script>

<style lang="scss" scoped>
.snt-button {
  border-radius: 4px;
  padding: 0.5rem 1rem;
  color: var(--color-text-secondary);
  cursor: pointer;
  user-select: none;
  display: inline-flex;
  text-align: center;
  letter-spacing: 3px;
  align-items: center;
  overflow: hidden;
  justify-content: center;
}

.loading {
  pointer-events: none;
}
</style>
