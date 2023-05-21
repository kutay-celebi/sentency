import axios, { AxiosError } from "axios";
import { useAuthStore } from "@/stores";
import { useNotification } from "@/module/notification";
import { ErrorResponse } from "@/types/service-types";

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
    return Promise.resolve(resp);
  },
  async (err: AxiosError) => {
    if (err) {
      const response = err.response;
      if (response?.status === 401) {
        const authStore = useAuthStore();
        authStore.logout();
      }
      if (response && response.data && response.status === 500) {
        const notification = useNotification();
        notification.error(
          (response.data as ErrorResponse).errors[0],
          (response.data as ErrorResponse).code,
          `Error ID: ${(response.data as ErrorResponse).uuid}`
        );
      }
      return Promise.reject(response);
    }
    return Promise.reject();
  }
);

export default axiosInstance;
