<script lang="ts" setup>
import SntList from "@/components/core/SntList.vue";
import SntListItem from "@/components/core/SntListItem.vue";
import { onBeforeMount, ref, watch } from "vue";
import useApi from "@/api";
import { useAuthStore } from "@/stores";
import { PageResponse, UserWordPageRequest, UserWordResponse } from "@/module/service";
import { useDateUtility } from "@/composable/date-utility";
import SntPagination from "@/components/core/SntPagination.vue";
import SntAlert from "@/components/core/SntAlert.vue";
import SntButton from "@/components/core/SntButton.vue";

const api = useApi();
const authStore = useAuthStore();

const isLoading = ref<boolean>(false);

const words = ref<PageResponse<UserWordResponse>>();
const dateUtility = useDateUtility();

const query = ref<UserWordPageRequest>({
  page: 1,
  size: 10,
  userId: {
    value: authStore.userId,
  },
  sorts: [
    {
      field: "nextReview",
      direction: "asc",
    },
  ],
});

watch(query.value, () => {
  fetchUserWords();
});

const fetchUserWords = async () => {
  isLoading.value = true;
  await api.userWord
    .query(query.value)
    .then((resp) => {
      resp.data.content.forEach((word) => (word.showContext = false));
      words.value = resp.data;
    })
    .finally(() => {
      isLoading.value = false;
    });
};

const onClickWord = (word: UserWordResponse) => {
  word.showContext = !word.showContext;
};

const removeReviewList = async (word: UserWordResponse) => {
  isLoading.value = true;
  await api.userWord
    .removeFromList(word.id)
    .then((resp) => {
      word.showContext = false;
      word.isActive = resp.data.isActive;
    })
    .finally(() => {
      isLoading.value = false;
    });
};

onBeforeMount(() => {
  fetchUserWords();
});
</script>

<template>
  <div v-if="words">
    <snt-pagination v-model="query.page" :total="words.totalPage" />
    <snt-list ref="listRef">
      <snt-list-item v-for="word in words.content" :key="word.id" @click="() => onClickWord(word)">
        <div v-if="word.showContext">
          <snt-button color="error" :loading="isLoading" @click.stop="() => removeReviewList(word)">
            Remove From Review List
          </snt-button>
        </div>
        <div v-else>
          <div>{{ word.word }}</div>
          <div style="display: flex">
            <div>
              <span class="text-bold">Last Review:</span>
              <span style="margin-left: 0.5rem">
                {{ word.lastReview ? dateUtility.dateLongFormat(word.lastReview) : "-" }}
              </span>
            </div>
            <div style="margin-left: 1rem">
              <span class="text-bold">Active:</span>
              <span style="margin-left: 0.5rem">
                {{ word.isActive ? "Active" : "Inactive " }}
              </span>
            </div>
          </div>
          <span class="word-count"> Count: {{ word.count }} </span>
        </div>
      </snt-list-item>
    </snt-list>
  </div>
  <snt-alert v-if="!words && !isLoading" type="primary"> No words have been added to the list yet.</snt-alert>
</template>

<style lang="scss" scoped>
.word-count {
  position: absolute;
  top: 0;
  right: 0;
}
</style>
