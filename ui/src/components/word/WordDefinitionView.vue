<script lang="ts" setup>
import type { PropType } from "vue";
import type { WordResponse } from "@/module/service";
import SntTabView from "@/components/core/SntTabView.vue";
import { computed, ref } from "vue";
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
const synonyms = computed<string[]>(() =>
  props.word?.definitions
    .filter((def) => def.synonyms)
    .map((def) => def.synonyms.filter((syn) => syn))
    .flat()
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
  <transition name="slide" mode="out-in">
    <div v-if="activeTab === 'definitions'">
      <div v-for="definition in word.definitions" :key="definition.id" class="definition">
        <i class="part-of-speech">{{ definition.partOfSpeech }}:</i>
        {{ definition.definitionTr }}
      </div>
    </div>
    <div v-else-if="activeTab === 'examples'">
      <div v-for="(ex, idx) in examples" :key="`ex-${idx}`" class="definition">
        {{ ex }}
      </div>
    </div>
    <div v-else-if="activeTab === 'Synonyms'">
      <div v-for="(syn, idx) in synonyms" :key="`syn-${idx}`" class="definition">
        {{ syn }}
      </div>
    </div>
  </transition>
</template>

<style lang="scss" scoped>
.word-title {
  text-align: center;
  font-weight: 700;
  font-size: 18px;
  text-decoration: underline;
}
.definition {
  border: 1px solid var(--color-border-main);
  border-radius: 4px;
  padding: 0.25rem;
  margin: 0.125rem 0;
}

.part-of-speech {
  font-weight: 700;
}
</style>
