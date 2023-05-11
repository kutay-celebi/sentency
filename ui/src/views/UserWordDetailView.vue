<script lang="ts" setup>
import { useRoute } from "vue-router";
import { onBeforeMount, ref } from "vue";
import useApi from "@/api";
import {
  PageResponse,
  QueryItem,
  SentencePageQueryRequest,
  SentenceResponse,
  UserWordResponse,
} from "@/module/service";
import SntCard from "@/components/core/SntCard.vue";
import SntAlert from "@/components/core/SntAlert.vue";
import SntLoading from "@/components/core/SntLoading.vue";
import SolarMinusSquareOutline from "~icons/solar/minus-square-outline";
import SolarSquareDoubleAltArrowDownOutline from "~icons/solar/square-double-alt-arrow-down-outline";
import SolarSquareDoubleAltArrowUpOutline from "~icons/solar/square-double-alt-arrow-up-outline";
import { useDateUtility } from "@/composable/date-utility";
import { useAuthStore } from "@/stores";
import SntListItem from "@/components/core/SntListItem.vue";
import SntList from "@/components/core/SntList.vue";
import SntPagination from "@/components/core/SntPagination.vue";

const api = useApi();
const route = useRoute();
const dateUtility = useDateUtility();
const authStore = useAuthStore();

const sentenceQuery = ref<SentencePageQueryRequest>();
const sentencePage = ref<PageResponse<SentenceResponse>>();
const userWord = ref<UserWordResponse>();
const loading = ref(false);

onBeforeMount(async () => {
  loading.value = true;
  await api.userWord
    .fetchUserWord(route.params.id as string)
    .then((resp) => {
      userWord.value = resp.data;
      sentenceQuery.value = {
        userId: {
          operator: QueryItem.OPERATOR_EQUAL,
          value: authStore.userId,
        },
        wordId: {
          operator: QueryItem.OPERATOR_EQUAL,
          value: userWord.value?.wordId as string,
        },
      };
    })
    .finally(() => {
      loading.value = false;
    });

  if (sentenceQuery.value) {
    await api.sentence.query(sentenceQuery.value).then((resp) => {
      sentencePage.value = resp.data;
    });
  }
});
</script>
<template>
  <transition appear name="fade">
    <snt-alert v-if="!loading && !userWord"></snt-alert>
    <snt-card v-else-if="userWord">
      <template #title>
        {{ userWord.word }}
        <solar-square-double-alt-arrow-down-outline v-if="userWord.difficulty == 'EASY'" class="word-difficulty easy" />
        <solar-minus-square-outline v-if="userWord?.difficulty == 'MEDIUM'" class="word-difficulty medium" />
        <solar-square-double-alt-arrow-up-outline v-if="userWord?.difficulty == 'HARD'" class="word-difficulty hard" />
      </template>
      <div class="word-summary">
        <div class="word-summary-item">
          Reviewed <b>{{ userWord?.count }}</b> time(s). It will be reviewed again on
          <b>{{ dateUtility.dateLongFormat(userWord?.nextReview) }}</b> at the earliest.
        </div>
      </div>
      <div v-if="sentenceQuery && sentencePage">
        <snt-pagination v-model="sentenceQuery.page" :total="sentencePage.totalPage" />
        <snt-list ref="listRef">
          <snt-list-item v-for="sentence in sentencePage.content" :key="sentence.id">
            {{ sentence.sentence }}
          </snt-list-item>
        </snt-list>
      </div>
    </snt-card>
    <snt-loading v-else :loading="loading" />
  </transition>
</template>

<style lang="scss" scoped>
.word-summary {
  &-item {
    //margin: 0 0.5rem;
  }
}

.word-difficulty {
  vertical-align: bottom;

  &.easy {
    color: var(--color-success);
  }

  &.medium {
    color: var(--color-warn);
  }

  &.hard {
    color: var(--color-error);
  }
}
</style>
