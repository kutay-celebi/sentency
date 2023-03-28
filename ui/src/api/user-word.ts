import axiosInstance from "@/module/axios";
import { AxiosResponse } from "axios";
import { UserWordResponse } from "@/module/service";

const useUserWordApi = () => {
  const fetchUserWord = (userId: string): Promise<AxiosResponse<UserWordResponse>> => {
    return axiosInstance({
      url: `/user-word/${userId}/next-review`,
    });
  };

  return {
    fetchUserWord,
  };
};

export default useUserWordApi;
