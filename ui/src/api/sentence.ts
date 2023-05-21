import axiosInstance from "@/module/axios";
import { AxiosResponse } from "axios";
import {
  PageResponse,
  SentencePageQueryRequest,
  SentencePersistResponse,
  SentenceRequest,
  SentenceResponse,
  SentenceTranslateResponse,
} from "@/types/service-types";

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

export const query = (query: SentencePageQueryRequest): Promise<AxiosResponse<PageResponse<SentenceResponse>>> => {
  return axiosInstance({
    url: `/sentence/query`,
    method: "post",
    data: { ...query },
  });
};
