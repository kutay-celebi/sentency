import axios from "axios";
import { useAuthStore } from "@/stores";
import router from "@/router";
// import { useRouter } from "vue-router";

const axiosInstance = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

axiosInstance.interceptors.request.use(
  (req) => {
    const authStore = useAuthStore();
    if (req && req.headers && authStore.token) {
      req.headers.Authorization = `Bearer ${authStore.token}`;
    }
    return req;
  },
  (rej) => {
    // @ts-ignore
    console.warn(rej);
  }
);

axiosInstance.interceptors.response.use(
  (resp) => {
    // console.log(resp.data);
    return Promise.resolve(resp);
  },
  async (err) => {
    if (err) {
      const response = err.response;
      if (response?.status === 401) {
        const authStore = useAuthStore();
        authStore.logout();
        await router.push("/login");
      }
    }
    return Promise.reject(err);
  }
);

export default axiosInstance;
