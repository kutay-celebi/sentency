<script lang="ts" setup>
import { onBeforeMount, PropType, ref } from "vue";
import { UseEventBusReturn } from "@vueuse/core";
import { Notification } from "@/module/notification";
import RiCloseCircleLine from "~icons/ri/close-circle-line";

const id = ref(0);

const props = defineProps({
  bus: {
    type: Object as PropType<UseEventBusReturn<Notification, any>>,
    required: true,
  },
});

const notifications = ref<Notification[]>([]);

const closeNotification = (notification: Notification) => {
  notifications.value = notifications.value.filter((n) => n.id !== notification.id);
};

const showNotification = (notification: Notification) => {
  notification.id = id.value++;
  notifications.value.push(notification);
};

onBeforeMount(() => {
  props.bus?.on(showNotification);
});
</script>

<template>
  <teleport to="body">
    <div class="notification-container">
      <TransitionGroup name="scroll-xr-transition" appear>
        <div
          v-for="(notification, idx) in notifications"
          :key="`notification-${idx}`"
          :class="['notification-item', 'shadow', `${notification.type}-bg`]"
        >
          <div class="notification-content">
            <div v-if="notification.title" class="text-extra-bold">
              {{ notification.title }}
            </div>
            <div>
              {{ notification.message }}
            </div>
            <div class="notification-caption text-x-small">
              {{ notification.caption }}
            </div>
          </div>
          <div class="notification-icon">
            <ri-close-circle-line @click="() => closeNotification(notification)" />
          </div>
        </div>
      </TransitionGroup>
    </div>
  </teleport>
</template>

<style lang="scss" scoped>
.notification {
  &-container {
    width: 100%;
    position: absolute;
    top: 0;
    padding: 1rem;
  }

  &-item {
    display: flex;
    padding: 1rem;
    //background-color: var(--color-primary);
    border-radius: 4px;
    color: var(--color-text-secondary);
    margin: 0.5rem 0;
  }

  &-content {
    width: 100%;
  }

  &-caption {
  }

  &-icon {
    display: flex;
    align-items: center;
    svg {
      cursor: pointer;
    }
  }
}

.scroll-xr-transition-leave-active,
.scroll-xr-transition-enter-active {
  transition: all 0.5s ease;
  transform: translateY(0);
}

.scroll-xr-transition-enter-from,
.scroll-xr-transition-leave-to {
  transform: translateY(-30px);
  opacity: 0;
}
</style>
