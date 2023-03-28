<template>
  <div class="word-review">
    <div v-if="word" class="word-review_word-info">
      <div class="word-review_word-title">
        {{ word.word.toUpperCase() }}
      </div>
    </div>
    <div v-if="userWordResponse" class="word-review_stats">
      <table>
        <tr>
          <td class="table-field">Review Count</td>
          <td class="table-field">:</td>
          <td>{{ userWordResponse.count }}</td>
        </tr>
        <tr>
          <td class="table-field">Last Review</td>
          <td class="table-field">:</td>
          <td>{{ dateLongFormat(userWordResponse.lastReview) }}</td>
        </tr>
        <tr>
          <td class="table-field">Next Review</td>
          <td class="table-field">:</td>
          <td>{{ dateLongFormat(userWordResponse.nextReview) }}</td>
        </tr>
        <tr>
          <td class="table-field">Difficulty</td>
          <td class="table-field">:</td>
          <td>{{ userWordResponse.difficulty }}</td>
        </tr>
      </table>
    </div>
    <div class="word-review_actions">
      <snt-button color="success" @click="adjustDifficulty('EASY')">Easy</snt-button>
      <snt-button @click="adjustDifficulty('MEDIUM')">Medium</snt-button>
      <snt-button color="error" @click="adjustDifficulty('HARD')">Hard</snt-button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { useRoute } from "vue-router";
import { onBeforeMount, ref } from "vue";
import type { Difficulty, UserWordResponse, WordResponse } from "@/module/service";
import SntButton from "@/components/core/SntButton.vue";
import router from "@/router";
import { useDateUtility } from "@/composable/date-utility";
import useApi from "@/api";

let { dateLongFormat } = useDateUtility();
const route = useRoute();
const userWordResponse = ref<UserWordResponse>();
const word = ref<WordResponse>();
const api = useApi();

const adjustDifficulty = (difficulty: Difficulty) => {
  if (!userWordResponse.value) {
    return;
  }

  api.userWord
    .adjustDifficulty({
      userWordId: userWordResponse.value.id,
      difficulty: difficulty,
    })
    .then(() => {
      router.push("/");
    });
};

onBeforeMount(async () => {
  await api.userWord.fetchUserWord(route.params.id as string).then((response) => {
    userWordResponse.value = response.data;
  });
  await api.word.fetchWord(userWordResponse.value?.wordId).then((resp) => {
    word.value = resp.data;
  });
});
</script>

<style lang="scss" scoped>
.word-review {
  &_word-info {
    display: flex;
    justify-content: center;
  }

  &_word-title {
    font-size: 18px;
    font-weight: 700;
  }

  &_stats {
    width: 100%;

    table {
      width: 100%;
    }
  }

  &_actions {
    display: flex;
    justify-content: space-between;
    margin: 1rem 0;
  }
}

.table-field {
  font-weight: 700;
}
</style>
