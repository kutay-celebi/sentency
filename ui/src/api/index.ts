import * as userWord from "./user-word";
import * as word from "./word";
import * as sentence from "./sentence";
import * as auth from "./auth";

const useApi = () => {
  return {
    userWord,
    word,
    sentence,
    auth,
  };
};
export default useApi;
