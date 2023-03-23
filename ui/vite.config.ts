import { fileURLToPath, URL } from "node:url";

import { defineConfig, ProxyOptions } from "vite";
import vue from "@vitejs/plugin-vue";
import Icons from "unplugin-icons/vite";

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  let proxy: Record<string, ProxyOptions> = {};
  if (mode == "dev") {
    proxy = {
      "/api": {
        target: "http://localhost:8080",
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    };
  }

  return {
    plugins: [vue(), Icons({ compiler: "vue3" })],
    resolve: {
      alias: {
        "@": fileURLToPath(new URL("./src", import.meta.url)),
      },
    },
    server: {
      proxy,
    },
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: `@import "@/styles/variables.scss";`,
        },
      },
    },
  };
});
