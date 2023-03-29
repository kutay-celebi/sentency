import { createApp, getCurrentInstance, inject, InjectionKey, nextTick, Plugin } from "vue";
import { EventBusKey, useEventBus } from "@vueuse/core";
import SntNotificationContainer from "@/components/core/SntNotificationContainer.vue";

export const useNotification = () => {
  const notification = getCurrentInstance() ? inject(NotificationInjectionKey, undefined) : undefined;

  if (notification) {
    return notification;
  }
  const error = (msg: string, title?: string) => {
    NotificationEventBus.emit({ message: msg, title: title, type: "error" });
  };

  return {
    error,
  };
};

export const NotificationPlugin: Plugin = async (app, options) => {
  const bus = useEventBus(NotificationBusKey);

  await nextTick(() => {
    const app = createApp(SntNotificationContainer, { bus });
    app.mount(document.createElement("div"));
  });

  const error = (msg: string, title?: string) => {
    bus.emit({ message: msg, title: title, type: "error" });
  };

  app.provide(NotificationInjectionKey, {
    error,
  });
};

const NotificationBusKey: EventBusKey<Notification> = Symbol("notification-bus");
export const NotificationInjectionKey: InjectionKey<NotificationContext> = Symbol.for("notification-plugin");
export const NotificationEventBus = useEventBus(NotificationBusKey);

export interface Notification {
  title?: string;
  message: string;
  type: "error" | "info";
}

interface NotificationContext {
  error: (msg: string, title?: string) => void;
}
