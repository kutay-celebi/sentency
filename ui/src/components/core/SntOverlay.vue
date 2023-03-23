<script lang="ts" setup>
import { computed } from "vue";

const internalModel = computed({
  get() {
    return props.modelValue;
  },
  set(newVal) {
    emits("update:modelValue", newVal);
  },
});

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
});

const emits = defineEmits(["update:modelValue"]);
</script>

<template>
  <teleport to="body">
    <div v-if="internalModel" class="snt-overlay">
      <slot />
    </div>
  </teleport>
</template>

<style lang="scss" scoped>
.snt-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(44, 44, 44, 0.25);
}
</style>
