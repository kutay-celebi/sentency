import axiosInstance from "@/module/axios";
import { AxiosResponse } from "axios";
import { WordResponse } from "@/module/service";

export const fetchWord = (wordId: string | undefined): Promise<AxiosResponse<WordResponse>> => {
  return axiosInstance({
    url: `/word/id/${wordId}`,
  });
};
