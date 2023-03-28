import axiosInstance from "@/module/axios";
import { AxiosResponse } from "axios";
import { UserWordDifficultyRequest, UserWordRequest, UserWordResponse } from "@/module/service";

export const fetchNextReview = (userWordId: string): Promise<AxiosResponse<UserWordResponse>> => {
  return axiosInstance({
    url: `/user-word/${userWordId}/next-review`,
  });
};

export const fetchUserWord = (userWordId: string): Promise<AxiosResponse<UserWordResponse>> => {
  return axiosInstance({
    url: `/user-word/${userWordId}`,
  });
};

export const adjustDifficulty = (payload: UserWordDifficultyRequest): Promise<AxiosResponse<UserWordResponse>> => {
  return axiosInstance({
    url: `/user-word/difficulty`,
    method: "put",
    data: payload,
  });
};

export const addToList = (payload: UserWordRequest): Promise<AxiosResponse<UserWordResponse>> => {
  return axiosInstance({
    url: `/user-word`,
    method: "post",
    data: payload,
  });
};
