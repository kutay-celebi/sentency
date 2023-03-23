<script lang="ts" setup>
import RiCheckboxCircleLine from "~icons/ri/checkbox-circle-line";
import RiErrorWarningLine from "~icons/ri/error-warning-line";
import type {PropType} from "vue";

defineProps({
  type: {
    type: String as PropType<"success" | "info" | "error">,
    default: () => "success",
  },
  message: {
    type: String,
    required: true,
  },
});
</script>
<template>
  <div class="snt-status">
    <div :class="['snt-status_content', `${type}-text`]">
      <div class="snt-status_msg">
        <ri-checkbox-circle-line v-if="type === 'success'"/>
        <ri-error-warning-line v-if="type === 'error'"/>
        {{ message }}
      </div>

      <div class="snt-status_actions">
        <slot/>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.snt-status {
  display: flex;
  justify-content: center;
  align-items: center;

  &_content {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  &_msg {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 1.5rem;
    text-align: center;
  }

  &_actions {
    padding: 1rem 0;
  }
}
</style>
