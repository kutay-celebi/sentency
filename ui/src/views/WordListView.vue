<script lang="ts" setup>
import SntList from "@/components/core/SntList.vue";
import SntListItem from "@/components/core/SntListItem.vue";
import { onBeforeMount, ref, watch } from "vue";
import useApi from "@/api";
import { useAuthStore } from "@/stores";
import { PageResponse, UserWordPageRequest, UserWordResponse } from "@/module/service";
import { useDateUtility } from "@/composable/date-utility";
import SntPagination from "@/components/core/SntPagination.vue";

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
  <div>
    <snt-pagination v-model="query.page" :total="words?.totalPage"> </snt-pagination>
    <snt-list>
      <snt-list-item v-for="word in words?.content" :key="word.id">
        <div>{{ word.word }}</div>
        <div>
          <span class="text-bold">Last Review:</span>
          <span style="margin-left: 0.5rem">{{
            word.lastReview ? dateUtility.dateLongFormat(word.lastReview) : "-"
          }}</span>
        </div>
      </snt-list-item>
    </snt-list>
  </div>
</template>

<style lang="scss" scoped></style>
