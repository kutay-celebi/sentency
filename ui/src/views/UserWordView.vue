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
import axiosInstance from "@/module/axios";
import type { Difficulty, UserWordDifficultyRequest, UserWordResponse, WordResponse } from "@/module/service";
import SntButton from "@/components/core/SntButton.vue";
import router from "@/router";
import { useDateUtility } from "@/composable/date-utility";

let { dateLongFormat } = useDateUtility();
const route = useRoute();
const userWordResponse = ref<UserWordResponse>();
const word = ref<WordResponse>();

const adjustDifficulty = (difficulty: Difficulty) => {
  axiosInstance
    .put("/user-word/difficulty", {
      userWordId: userWordResponse.value?.id,
      difficulty: difficulty,
    } as UserWordDifficultyRequest)
    .then(() => {
      router.push("/");
    });
};

onBeforeMount(async () => {
  await axiosInstance.get(`/user-word/${route.params.id}`).then((response) => {
    userWordResponse.value = response.data;
  });
  await axiosInstance.get(`/word/id/${userWordResponse.value?.wordId}`).then((resp) => {
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
