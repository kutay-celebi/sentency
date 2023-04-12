<script lang="ts" setup>
import type { PropType } from "vue";
import { computed, ref } from "vue";
import type { WordResponse, WordSynonymAntonymResponse } from "@/module/service";
import SntTabView from "@/components/core/SntTabView.vue";
import type { SntTab } from "@/components/types";

const tabs = ref<SntTab[]>([
  { label: "Definitions", key: "definitions" },
  { label: "Synonyms", key: "Synonyms" },
  { label: "Examples", key: "examples" },
]);
const activeTab = ref("definitions");

const examples = computed<string[]>(() =>
  props.word?.definitions
    .filter((def) => def.examples)
    .map((def) => def.examples.filter((ex) => ex))
    .flat()
);
const synonyms = computed<WordSynonymAntonymResponse[]>(() =>
  props.word?.definitions
    .filter((def) => def.synonyms)
    .map((def) => def.synonyms)
    .flat()
    .filter(
      (value, index, self) =>
        index === self.findIndex((t) => t.definition === value.definition && t.word === value.word)
    )
);

const props = defineProps({
  word: {
    type: Object as PropType<WordResponse>,
    required: true,
  },
});
</script>
<template>
  <div class="word-title">{{ word.word.toUpperCase() }}</div>
  <snt-tab-view v-model="activeTab" :tabs="tabs"></snt-tab-view>
  <div class="definition-container">
    <transition name="slide" mode="out-in">
      <div v-if="activeTab === 'definitions'">
        <div v-for="definition in word.definitions" :key="definition.id" class="definition">
          <i class="text-bold">{{ definition.phrases.en.partOfSpeech }}:</i>
          {{ definition.phrases.tr.definition }}
        </div>
      </div>
      <div v-else-if="activeTab === 'examples'">
        <div v-for="(ex, idx) in examples" :key="`ex-${idx}`" class="definition">
          {{ ex }}
        </div>
      </div>
      <div v-else-if="activeTab === 'Synonyms'">
        <div v-for="(syn, idx) in synonyms" :key="`syn-${idx}`" class="definition">
          <span class="text-bold">{{ syn.word }}</span> <span v-if="syn.definition">- {{ syn.definition }}</span>
        </div>
      </div>
    </transition>
  </div>
</template>

<style lang="scss" scoped>
.word-title {
  text-align: center;
  font-weight: 700;
  font-size: 18px;
  text-decoration: underline;
}

.definition-container {
  height: 40vh;
  overflow: hidden;
  overflow-y: auto;
}

.definition {
  border: 1px solid var(--color-border-main);
  border-radius: 4px;
  padding: 0.25rem;
  margin: 0.125rem 0;
}
</style>
