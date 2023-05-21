import axiosInstance from "@/module/axios";
import { AxiosResponse } from "axios";
import { JwtResponse } from "@/types/service-types";

export const loginWithGoogle = (authCode: string | undefined): Promise<AxiosResponse<JwtResponse>> => {
  return axiosInstance({
    url: `/auth/login/google/`,
    params: { "auth-code": authCode },
  });
};

export const login = (username: string, password: string): Promise<AxiosResponse<JwtResponse>> => {
  return axiosInstance({
    url: `/auth/login`,
    method: "post",
    data: {
      username,
      password,
    },
  });
};
