<template>
  <div>
    <word-definition-view v-if="word" :word="word" />

    <h3 class="text-center">
      Create Sentence with <span v-if="word">{{ word.word }}</span>
    </h3>
    <snt-form ref="sentenceForm">
      <snt-input v-model="sentenceRequest.sentence" type="textarea" :rules="sentenceRules"></snt-input>
    </snt-form>

    <div class="sentence-actions">
      <snt-button :loading="isLoading" @click="translate"><ri-chat-check-line /> Check Sentence</snt-button>
    </div>

    <div v-if="sentenceRequest.sentenceTr" class="sentence-translation">
      <div class="sentence-translation-title">You wanted to say:</div>
      <div class="sentence-translation-result">{{ sentenceRequest.sentenceTr }}</div>
      <snt-button :loading="isLoading" color="success" @click="addSentence">
        <ri-checkbox-circle-line /> Exactly
      </snt-button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import RiCheckboxCircleLine from "~icons/ri/checkbox-circle-line";
import RiChatCheckLine from "~icons/ri/chat-check-line";
import { onBeforeMount, onMounted, ref } from "vue";
import type {
  SentencePersistResponse,
  SentenceRequest,
  SentenceTranslateResponse,
  UserWordResponse,
  WordResponse,
} from "@/module/service";
import axiosInstance from "@/module/axios";
import { useAuthStore } from "@/stores";
import type { AxiosResponse } from "axios";
import SntInput from "@/components/core/SntInput.vue";
import SntButton from "@/components/core/SntButton.vue";
import SntForm from "@/components/core/SntForm.vue";
import WordDefinitionView from "@/components/word/WordDefinitionView.vue";
import { useRoute, useRouter } from "vue-router";
import useUserWordApi from "@/api/user-word";

const isLoading = ref<boolean>(false);
const userWord = ref<UserWordResponse>();
const word = ref<WordResponse>();
const auth = useAuthStore();
const route = useRoute();
const router = useRouter();
const sentenceRequest = ref<SentenceRequest>({ userId: auth.userId, wordId: "", sentence: "" });
const sentenceForm = ref();
const sentenceRules = [(val: string) => !!val || "Sentence should not be empty."];
const userWordApi = useUserWordApi();

const addSentence = async () => {
  isLoading.value = true;
  await axiosInstance.post("/sentence", sentenceRequest.value).then((resp: AxiosResponse<SentencePersistResponse>) => {
    isLoading.value = false;
    router.push(`/user-word/${userWord.value?.id}`);
  });
};

const translate = async () => {
  const isValid = sentenceForm.value.validate();
  if (!isValid) {
    return;
  }
  sentenceRequest.value.sentenceTr = undefined;
  isLoading.value = true;
  await axiosInstance
    .get("/sentence/translate", { params: { sentence: sentenceRequest.value.sentence } })
    .then((resp: AxiosResponse<SentenceTranslateResponse>) => {
      sentenceRequest.value.sentenceTr = resp.data.translation;
    })
    .finally(() => {
      isLoading.value = false;
    });
};

const fetchWord = async () => {
  await axiosInstance.get(`/word/id/${userWord.value?.wordId}`).then((resp: AxiosResponse<WordResponse>) => {
    word.value = resp.data;
  });
};

const fetchUserWord = () => {
  userWordApi.fetchUserWord((route.params.wordid as string) || auth.userId).then((response) => {
    userWord.value = response.data;
    sentenceRequest.value.wordId = userWord.value?.wordId;
  });
};

onMounted(async () => {
  fetchUserWord();
  await fetchWord();
});
</script>

<style lang="scss" scoped>
.sentence-actions {
  margin: 1rem 0;
  display: flex;
  justify-content: center;
}

.sentence-translation {
  text-align: center;
  border: 1px solid var(--color-border-main);
  border-radius: 4px;
  padding: 1rem;

  &-result {
  }

  &-title {
    color: var(--color-success);
  }
  .snt-button {
    margin-top: 1rem;
  }
}
</style>
