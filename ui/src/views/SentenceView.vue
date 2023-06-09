<template>
  <div v-if="!isWordNotFound && !isPageLoading">
    <word-definition-view v-if="word" :word="word" />

    <h3 class="text-center">
      Create Sentence with <span v-if="word">{{ word.word }}</span>
    </h3>
    <snt-form ref="sentenceForm">
      <snt-input v-model="sentenceRequest.sentence" type="textarea" :rules="sentenceRules"></snt-input>
    </snt-form>

    <div class="sentence-actions">
      <snt-button :loading="isLoading" @click="translate">
        <ri-chat-check-line />
        Check Sentence
      </snt-button>
    </div>

    <div v-if="sentenceRequest.sentenceTr" class="sentence-translation">
      <div class="sentence-translation-title">You wanted to say:</div>
      <div class="sentence-translation-result">{{ sentenceRequest.sentenceTr }}</div>
      <snt-button :loading="isLoading" color="success" @click="addSentence">
        <ri-checkbox-circle-line />
        Exactly
      </snt-button>
    </div>
  </div>
  <snt-alert v-if="isWordNotFound && !isPageLoading" type="error"> No word has been added to the list yet</snt-alert>
</template>

<script lang="ts" setup>
import RiCheckboxCircleLine from "~icons/ri/checkbox-circle-line";
import RiChatCheckLine from "~icons/ri/chat-check-line";
import { onMounted, ref } from "vue";
import type { SentenceRequest, UserWordResponse, WordResponse } from "@/types/service-types";
import { useAuthStore } from "@/stores";
import SntInput from "@/components/core/SntInput.vue";
import SntButton from "@/components/core/SntButton.vue";
import SntForm from "@/components/core/SntForm.vue";
import WordDefinitionView from "@/components/word/WordDefinitionView.vue";
import { useRoute, useRouter } from "vue-router";
import useApi from "@/api";
import { AxiosError } from "axios";
import SntAlert from "@/components/core/SntAlert.vue";

const auth = useAuthStore();
const route = useRoute();
const router = useRouter();
const api = useApi();

const isLoading = ref<boolean>(false);
const isPageLoading = ref<boolean>(false);
const isWordNotFound = ref<boolean>(false);

const sentenceForm = ref();
const sentenceRequest = ref<SentenceRequest>({ userId: auth.userId, wordId: "", sentence: "" });
const sentenceRules = [(val: string) => !!val || "Sentence should not be empty."];
const userWord = ref<UserWordResponse>();
const word = ref<WordResponse>();

const addSentence = async () => {
  isLoading.value = true;
  await api.sentence
    .addSentence(sentenceRequest.value)
    .then(() => {
      router.push(`/user-word/${userWord.value?.id}`);
    })
    .finally(() => {
      isLoading.value = false;
    });
};

const translate = async () => {
  const isValid = sentenceForm.value.validate();
  if (!isValid) {
    return;
  }
  sentenceRequest.value.sentenceTr = undefined;
  isLoading.value = true;
  await api.sentence
    .translate(sentenceRequest.value.sentence)
    .then((resp) => {
      sentenceRequest.value.sentenceTr = resp.data.translation;
    })
    .finally(() => {
      isLoading.value = false;
    });
};

const fetchWord = async () => {
  await api.word.fetchWord(userWord.value?.wordId).then((resp) => {
    word.value = resp.data;
  });
};

const fetchNextReview = async () => {
  isWordNotFound.value = false;

  if (route.params.wordid) {
    await api.userWord
      .fetchUserWord(route.params.wordid as string)
      .then((response) => {
        userWord.value = response.data;
        sentenceRequest.value.wordId = userWord.value?.wordId;
      })
      .catch((err: AxiosError) => {
        if (err.status === 404) {
          isWordNotFound.value = true;
        }
      });
  } else {
    await api.userWord
      .fetchNextReview(auth.userId)
      .then((response) => {
        userWord.value = response.data;
        sentenceRequest.value.wordId = userWord.value?.wordId;
      })
      .catch((err: AxiosError) => {
        if (err.status === 404) {
          isWordNotFound.value = true;
        }
      });
  }
};

onMounted(async () => {
  isPageLoading.value = true;
  await fetchNextReview();
  await fetchWord().finally(() => {
    isPageLoading.value = false;
  });
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
