<template>
  <div :class="[`${props.type}`, `${props.type}-bg`, 'snt-alert']">
    <div class="snt-alert_icon">
      <ri-error-warning-fill v-if="type === 'error'" />
      <ri-information-line v-if="type === 'primary'" />
    </div>
    <div class="snt-alert_wrapper">
      <div class="snt-alert_title">
        <slot name="title"></slot>
      </div>
      <div class="snt-alert_content">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import type { PropType } from "vue";
import RiErrorWarningFill from "~icons/ri/error-warning-fill";
import RiInformationLine from "~icons/ri/information-line";

type AlertType = "primary" | "secondary" | "error";
const props = defineProps({
  type: {
    type: String as PropType<AlertType>,
    default: () => "primary",
  },
});
</script>

<style lang="scss" scoped>
.snt-alert {
  color: var(--color-text-secondary);
  padding: 1rem;
  border-radius: 4px;
  display: flex;
  align-items: center;

  &_wrapper {
    //display: flex;
    padding: 0 1rem;
  }

  &_title {
    //margin-bottom: 0.5rem;
    font-weight: 700;
  }

  &_content {
  }
}
</style>
