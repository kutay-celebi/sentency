import axiosInstance from "@/module/axios";
import { AxiosResponse } from "axios";
import { WordResponse } from "@/types/service-types";

export const fetchWord = (wordId: string | undefined): Promise<AxiosResponse<WordResponse>> => {
  return axiosInstance({
    url: `/word/id/${wordId}`,
  });
};

export const searchWord = (word: string | undefined): Promise<AxiosResponse<WordResponse>> => {
  return axiosInstance({
    url: `/word/${word}`,
  });
};
