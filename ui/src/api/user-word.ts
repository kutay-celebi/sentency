import axiosInstance from "@/module/axios";
import { AxiosResponse } from "axios";
import {
  PageResponse,
  UserWordDifficultyRequest,
  UserWordPageRequest,
  UserWordRequest,
  UserWordResponse,
} from "@/module/service";

export const removeFromList = (id: string): Promise<AxiosResponse<UserWordResponse>> => {
  return axiosInstance({
    url: `/user-word/${id}`,
    method: "delete",
  });
};

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

export const query = (payload: UserWordPageRequest): Promise<AxiosResponse<PageResponse<UserWordResponse>>> => {
  return axiosInstance({
    url: `/user-word/query`,
    method: "post",
    data: payload,
  });
};
