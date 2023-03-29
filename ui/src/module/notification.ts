import { createApp, getCurrentInstance, inject, InjectionKey, nextTick, Plugin } from "vue";
import { EventBusKey, useEventBus } from "@vueuse/core";
import SntNotificationContainer from "@/components/core/SntNotificationContainer.vue";

export const useNotification = () => {
  const notification = getCurrentInstance() ? inject(NotificationInjectionKey, undefined) : undefined;

  if (notification) {
    return notification;
  }
  const error = (msg: string, title?: string, caption?: string) => {
    NotificationEventBus.emit({ message: msg, title: title, caption: caption, type: "error" });
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

  const error = (msg: string, title?: string, caption?: string) => {
    bus.emit({ message: msg, title: title, caption: caption, type: "error" });
  };

  app.provide(NotificationInjectionKey, {
    error,
  });
};

const NotificationBusKey: EventBusKey<Notification> = Symbol("notification-bus");
export const NotificationInjectionKey: InjectionKey<NotificationContext> = Symbol.for("notification-plugin");
export const NotificationEventBus = useEventBus(NotificationBusKey);

export interface Notification {
  id?: string | number;
  title?: string;
  message: string;
  caption?: string;
  type: "error" | "info";
}

interface NotificationContext {
  error: (msg: string, title?: string, caption?: string) => void;
}
