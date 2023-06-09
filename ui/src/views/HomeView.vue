<script lang="ts" setup>
import SntInput from "@/components/core/SntInput.vue";
import { ref } from "vue";
import SntButton from "@/components/core/SntButton.vue";
import type { ErrorResponse, UserWordRequest, UserWordResponse, WordResponse } from "@/types/service-types";
import WordDefinitionView from "@/components/word/WordDefinitionView.vue";
import RiSearch2Line from "~icons/ri/search-2-line";
import RiAddCircleLine from "~icons/ri/add-circle-line";
import RiEditLine from "~icons/ri/edit-line";
import RiArrowLeftCircleLine from "~icons/ri/arrow-left-circle-line";
import { useAuthStore } from "@/stores";
import SntStatus from "@/components/core/SntStatus.vue";
import { useRouter } from "vue-router";
import useApi from "@/api";
import { AxiosResponse } from "axios";
import SntAlert from "@/components/core/SntAlert.vue";

const router = useRouter();
const api = useApi();
const auth = useAuthStore();

const loading = ref(false);
const showStatus = ref(false);

const errorResponse = ref<ErrorResponse | undefined>();
const userWordResponse = ref<UserWordResponse | undefined>(undefined);
const searchWordModel = ref();
const searchWordResponse = ref<WordResponse>();

const closeSuccess = () => {
  showStatus.value = false;
  searchWordModel.value = undefined;
  searchWordResponse.value = undefined;
};

const createSentence = () => {
  router.push({ name: "sentence", params: { wordid: userWordResponse.value?.id } });
};
const addToList = async () => {
  loading.value = true;
  await api.userWord
    .addToList({
      wordId: searchWordResponse.value?.id,
      userId: auth.userId,
    } as UserWordRequest)
    .then((resp) => {
      showStatus.value = true;
      userWordResponse.value = resp.data;
    })
    .finally(() => {
      loading.value = false;
    });
};

const searchWord = async () => {
  loading.value = true;
  errorResponse.value = undefined;
  searchWordResponse.value = undefined;
  await api.word
    .searchWord(searchWordModel.value)
    .then((resp) => {
      searchWordResponse.value = resp.data;
    })
    .catch((err: AxiosResponse<ErrorResponse>) => {
      errorResponse.value = err.data;
    })
    .finally(() => {
      loading.value = false;
    });
};
</script>

<template>
  <div>
    <div v-if="!showStatus">
      <div class="search-word-input">
        <div>Search for a word...</div>
        <snt-input v-model="searchWordModel"></snt-input>
        <snt-button :loading="loading" @click="searchWord">
          <ri-search2-line />
          Search
        </snt-button>
      </div>
      <div class="action-bar">
        <snt-button v-if="searchWordResponse" :loading="loading" color="success" @click="addToList">
          <ri-add-circle-line />
          Add To List
        </snt-button>
      </div>
      <word-definition-view v-if="searchWordResponse" :word="searchWordResponse" />
      <snt-alert v-if="errorResponse" type="error">
        <template #title> {{ errorResponse.code }} </template>
        {{ errorResponse.errors[0] }}
      </snt-alert>
    </div>

    <snt-status v-if="showStatus" message="The word has been successfully added to the work list.">
      <snt-button :loading="loading" @click="closeSuccess">
        <ri-arrow-left-circle-line />
        BACK
      </snt-button>
      <snt-button :loading="loading" @click="createSentence">
        <ri-edit-line />
        CREATE SENTENCE
      </snt-button>
    </snt-status>
  </div>
</template>

<style lang="scss" scoped>
.search-word-input {
  text-align: center;
  margin: 1rem 0;
}

.snt-input {
  margin: 0.5rem 0 !important;
}

.snt-button {
  margin: 0 0.125rem;
}

.action-bar {
  margin: 0.25rem 0;
  display: flex;
  justify-content: center;
}
</style>
