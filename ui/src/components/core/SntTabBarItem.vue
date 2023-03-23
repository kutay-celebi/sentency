<script lang="ts" setup>
import { useRouter } from "vue-router";

const props = defineProps({
  to: {
    type: String,
    required: true,
  },
});

const router = useRouter();
const goPage = () => {
  router.push(props.to);
};
</script>

<template>
  <div class="snt-tabbar-item primary primary-bg hoverable text-large" @click="goPage">
    <slot />
  </div>
</template>

<style lang="scss" scoped>
.snt-tabbar-item {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  color: var(--color-text-secondary);
  cursor: pointer;
}
</style>
