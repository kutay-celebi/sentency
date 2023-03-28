import axiosInstance from "@/module/axios";
import { AxiosResponse } from "axios";
import { SentencePersistResponse, SentenceRequest, SentenceTranslateResponse, WordResponse } from "@/module/service";

export const translate = (sentence: string | undefined): Promise<AxiosResponse<SentenceTranslateResponse>> => {
  return axiosInstance({
    url: `/sentence/translate`,
    params: { sentence },
  });
};

export const addSentence = (sentence: SentenceRequest): Promise<AxiosResponse<SentencePersistResponse>> => {
  return axiosInstance({
    url: `/sentence`,
    method: "post",
    data: { ...sentence },
  });
};
