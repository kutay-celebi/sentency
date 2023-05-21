import axiosInstance from "@/module/axios";
import { AxiosResponse } from "axios";
import {
  PageResponse,
  UserWordDifficultyRequest,
  UserWordPageRequest,
  UserWordRequest,
  UserWordResponse,
} from "@/types/service-types";

export const removeFromList = (id: string): Promise<AxiosResponse<UserWordResponse>> => {
  return axiosInstance({
    url: `/user-word/${id}`,
    method: "delete",
  });
};

export const fetchNextReview = (userId: string): Promise<AxiosResponse<UserWordResponse>> => {
  return axiosInstance({
    url: `/user-word/${userId}/next-review`,
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
