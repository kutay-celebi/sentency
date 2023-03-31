import { createRouter, createWebHistory } from "vue-router";
import LoginView from "@/views/login/LoginView.vue";
import MainLayout from "@/layout/MainLayout.vue";
import HomeView from "@/views/HomeView.vue";
import ErrorView from "@/views/ErrorView.vue";
import SentenceView from "@/views/SentenceView.vue";
import UserWordView from "@/views/UserWordView.vue";
import WordListView from "@/views/WordListView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/",
      name: "layout",
      component: MainLayout,
      children: [
        {
          path: "/",
          component: HomeView,
          name: "home",
          meta: {
            auth: true,
          },
        },
        {
          path: "/user-word/:id",
          component: UserWordView,
          name: "user-word",
          meta: {
            auth: true,
          },
        },
        {
          path: "/sentence/:wordid?",
          component: SentenceView,
          name: "sentence",
          meta: {
            auth: true,
          },
        },
        {
          path: "/word-list",
          component: WordListView,
          name: "word-list",
          meta: {
            auth: true,
          },
        },
        {
          path: "/error",
          component: ErrorView,
          name: "error",
        },
      ],
    },
  ],
});

export default router;
