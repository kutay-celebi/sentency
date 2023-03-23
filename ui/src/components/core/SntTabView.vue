<script lang="ts" setup>
import { computed, PropType } from "vue";
import type { SntTab } from "@/components/types";

const internalModel = computed({
  get() {
    if (props.modelValue) {
      return props.modelValue;
    }
    return props.tabs[0].key;
  },
  set(newVal) {
    emits("update:modelValue", newVal);
  },
});

const onTabClick = (key: string) => {
  internalModel.value = key;
};

const props = defineProps({
  modelValue: {
    type: String,
    required: true,
  },
  tabs: {
    type: Array as PropType<SntTab[]>,
    required: true,
  },
});

const emits = defineEmits(["update:modelValue"]);
</script>

<template>
  <div class="snt-tabview">
    <div class="snt-tabview_tabs">
      <div
        v-for="tab in tabs"
        :key="tab.key"
        :class="['snt-tabview_tab', { 'snt-tabview_active-tab': tab.key === internalModel }]"
        @click="() => onTabClick(tab.key)"
      >
        {{ tab.label }}
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.snt-tabview {
  &_tabs {
    display: flex;
    overflow: hidden;
    border-radius: 4px;
    margin-bottom: 0.5rem;
  }

  &_tab {
    width: 100%;
    text-align: center;
    padding: 0.25rem 0;
    color: var(--color-text);
    font-weight: 700;
    border-left: 1px solid #fff;
    cursor: pointer;
    &:first-child {
      border-left: 0;
    }
  }

  &_active-tab {
    //background-color: var(--color-primary-hover);
    color: var(--color-primary);
    border-bottom: 1px solid var(--color-primary);
  }
}
</style>
