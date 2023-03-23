<script lang="ts" setup>
import SvgSpinnersPulse2 from "~icons/svg-spinners/pulse-2";

defineProps({
  loading: {
    type: Boolean,
    default: false,
  },
});
</script>

<template>
  <!--  <snt-overlay v-model="internalModel">-->
  <div v-if="loading" class="snt-loading">
    <svg-spinners-pulse2 />
    <slot />
  </div>
  <!--  </snt-overlay>-->
</template>

<style lang="scss" scoped>
.snt-loading {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(44, 44, 44, 0.5);
  color: var(--color-text-secondary);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.25rem;
}
</style>
