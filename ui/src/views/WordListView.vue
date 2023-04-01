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

const api = useApi();
const authStore = useAuthStore();
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
  await api.userWord.query(query.value).then((resp) => {
    words.value = resp.data;
  });
};

onBeforeMount(() => {
  fetchUserWords();
});
</script>

<template>
  <div v-if="words">
    <snt-pagination v-model="query.page" :total="words.totalPage"> </snt-pagination>
    <snt-list>
      <snt-list-item v-for="word in words.content" :key="word.id">
        <div>{{ word.word }}</div>
        <div>
          <span class="text-bold">Last Review:</span>
          <span style="margin-left: 0.5rem">
            {{ word.lastReview ? dateUtility.dateLongFormat(word.lastReview) : "-" }}
          </span>
        </div>
        <span class="word-count"> Count: {{ word.count }} </span>
      </snt-list-item>
    </snt-list>
  </div>
  <snt-alert v-else type="primary"> No words have been added to the list yet. </snt-alert>
</template>

<style lang="scss" scoped>
.word-count {
  position: absolute;
  top: 0;
  right: 0;
}
</style>
