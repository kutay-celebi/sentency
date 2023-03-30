<script lang="ts" setup>
import { computed } from "vue";
import { PageItem } from "@/components/types";

const pagesItems = computed<PageItem[]>(() => {
  const start = internalModel.value - props.stepFromMiddle;
  const end = internalModel.value + props.stepFromMiddle;

  let pages: PageItem[] = [{ label: 1, active: internalModel.value === 1, clickable: true }];

  if (internalModel.value > 3 && internalModel.value - props.stepFromMiddle !== 2) {
    pages.push({ label: "...", active: false, clickable: false });
  }

  for (let i = start; i <= end; i++) {
    if (i > 1 && i < props.total) {
      pages.push({ label: i, active: internalModel.value === i, clickable: true });
    }
  }

  if (
    internalModel.value + props.stepFromMiddle < props.total &&
    internalModel.value + props.stepFromMiddle !== props.total - 1
  ) {
    pages.push({ label: "...", active: false, clickable: false });
  }

  if (props.total > 1) {
    pages.push({ label: props.total, active: internalModel.value === props.total, clickable: true });
  }

  return pages;
});

const internalModel = computed({
  get() {
    return props.modelValue;
  },
  set(newVal) {
    emits("update:modelValue", newVal);
  },
});

const onPageClick = (page: PageItem) => {
  internalModel.value = Number(page.label);
};

const emits = defineEmits(["update:modelValue"]);
const props = defineProps({
  modelValue: {
    type: Number,
    default: 1,
  },
  total: {
    type: Number,
    required: true,
  },
  stepFromMiddle: {
    type: Number,
    default: 2,
  },
});
</script>
<template>
  <div class="snt-pagination">
    <div
      v-for="page in pagesItems"
      :key="page.label"
      :class="['snt-pagination-item', { active: page.active }, { 'snt-pagination-item-disable': !page.clickable }]"
      @click="onPageClick(page)"
    >
      {{ page.label }}
    </div>
  </div>
</template>

<style lang="scss" scoped>
.snt-pagination {
  display: flex;
  justify-content: center;

  &-item {
    padding: 4px 8px;
    margin: 0 0.125rem;
    border-radius: 4px;
    cursor: pointer;
    border: 1px solid var(--color-border-main);
    transition: 0.3s ease background-color;
    user-select: none;

    &:hover {
      background-color: var(--color-hover);
    }

    &-disable {
      cursor: auto;
      padding: 0;
      border: 0;
    }

    &.active {
      background-color: var(--color-primary);
      color: var(--color-text-secondary);
    }
  }
}
</style>
