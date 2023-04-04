import { createApp } from "vue";
import { createPinia } from "pinia";
import vue3GoogleLogin from "vue3-google-login";
import App from "./App.vue";
import router from "./router";

import "./styles/index.scss";
import { useAuthStore } from "@/stores";
import { NotificationPlugin } from "@/module/notification";

const app = createApp(App);

app.use(vue3GoogleLogin, {
  clientId: import.meta.env.VITE_GOOGLE_CLIENT_ID,
});

app.use(createPinia());
app.use(router);
app.use(NotificationPlugin);

router.beforeEach((to, from, next) => {
  if (to.meta.auth) {
    if (useAuthStore().token) {
      next();
      return;
    } else {
      next("/login");
    }
  }
  next();
});

app.mount("#app");
