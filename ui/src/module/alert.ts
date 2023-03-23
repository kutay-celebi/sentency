import type { App, InjectionKey, Plugin, Ref } from "vue";
import { inject, ref } from "vue";

interface Alert {
  title: string;
  msg: string;
}

interface AlertProvide {
  showInfo: (title: string, msg: string) => void;
  accept: () => void;
  isAlertShow: Ref<boolean>;
}
export const AlertProvideKey: InjectionKey<AlertProvide> = Symbol.for("alert-plugin");

export const AlertPlugin: Plugin = (app: App) => {
  const alert = ref<Alert>();
  const isAlertShow = ref(false);

  const showInfo = (title: string, msg: string) => {
    alert.value = { title: title, msg: msg };
    isAlertShow.value = true;
  };

  const accept = () => {
    alert.value = undefined;
    isAlertShow.value = false;
  };

  app.provide(AlertProvideKey, {
    showInfo,
    accept,
    isAlertShow,
  });
};

export const useAlert = () => {
  const alert = inject(AlertProvideKey, undefined);

  if (!alert) {
    return {
      showInfo: (title: string, msg: string) => {},
    };
  }

  return {
    showInfo: alert.showInfo,
  };
};
