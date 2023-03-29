<script lang="ts" setup>
import { onBeforeMount, PropType, ref } from "vue";
import { UseEventBusReturn } from "@vueuse/core";
import { Notification } from "@/module/notification";

const props = defineProps({
  bus: {
    type: Object as PropType<UseEventBusReturn<Notification, any>>,
    required: true,
  },
});

const notifications = ref<Notification[]>([]);

onBeforeMount(() => {
  props.bus?.on((msg) => {
    notifications.value.push(msg);
  });
});
</script>

<template>
  <teleport to="body">
    <div>{{ notifications }}</div>
  </teleport>
</template>

<style lang="scss" scoped></style>
