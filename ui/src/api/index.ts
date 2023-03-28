import * as userWord from "./user-word";
import * as word from "./word";
import * as sentence from "./sentence";

const useApi = () => {
  return {
    userWord,
    word,
    sentence,
  };
};
export default useApi;
