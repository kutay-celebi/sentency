import { computed, ref } from "vue";
import { defineStore } from "pinia";
import { useLocalStorage } from "@vueuse/core";

export const useCounterStore = defineStore("counter", () => {
  const count = ref(0);
  const doubleCount = computed(() => count.value * 2);
  function increment() {
    count.value++;
  }

  return { count, doubleCount, increment };
});

export const useAuthStore = defineStore("auth", {
  state: () => {
    const auth = useLocalStorage("auth", {
      token: "",
      userId: "",
    });
    return auth.value;
  },
  actions: {
    loginSuccess(payload: any) {
      this.token = payload.token;
      this.userId = payload.userId;
    },
    logout() {
      this.token = "";
      this.userId = "";
    },
  },
});

export const useNavigation = defineStore("nav", {
  state: () => {
    const nav = useLocalStorage("nav", {
      path: "/",
    });
    return nav.value;
  },
  actions: {
    persistCurrent(path: string) {
      this.path = path;
    },
  },
});
