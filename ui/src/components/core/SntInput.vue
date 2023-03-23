<template>
  <div :class="['snt-input', { 'snt-input_active': isActive }, { 'snt-input_error': !!error }, { dense: isDense }]">
    <div class="snt-input_input">
      <div v-if="props.label" :class="['snt-input_label']">
        {{ props.label }}
      </div>
      <input
        v-if="type !== 'textarea'"
        v-model="internalModel"
        :type="props.type"
        @focus="onInputFocus"
        @blur="onInputBlur"
      />
      <textarea
        v-if="type === 'textarea'"
        v-model="internalModel"
        :type="props.type"
        :rows="2"
        @focus="onInputFocus"
        @blur="onInputBlur"
      />
    </div>
    <div class="snt-input_error-msg">
      <div>{{ error }}</div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import type { PropType } from "vue";
import { computed, inject, onMounted, ref } from "vue";
import { FormInjectionKey } from "@/components/types";

let form = inject(FormInjectionKey, undefined);
const isFocused = ref(false);
const isActive = computed(() => isFocused.value || internalModel.value);
const error = ref<string | null>();

const isDense = computed(() => !props.label);

const onInputFocus = () => {
  isFocused.value = true;
};
const onInputBlur = () => {
  isFocused.value = false;
};

const internalModel = computed({
  get() {
    return props.modelValue;
  },
  set(newVal) {
    if (props.rules) {
      error.value = props.rules
        .map((rule) => rule(newVal as any))
        .filter((err) => typeof err === "string")[0] as string;
    }
    emits("update:modelValue", newVal);
  },
});

const props = defineProps({
  modelValue: null,
  label: {
    type: String,
    default: undefined,
  },
  rules: {
    type: Array as PropType<((val: string) => string | boolean)[]>,
    default: () => [],
  },
  type: {
    type: String,
    default: () => "text",
  },
});
const emits = defineEmits(["update:modelValue"]);

const validateInput = () => {
  if (!props.rules) {
    return null;
  }
  const err = props.rules
    .map((rule) => rule(internalModel.value as any))
    .filter((err) => typeof err === "string")[0] as string;
  error.value = err;
  return err;
};

onMounted(() => {
  if (form) {
    form.addFormItem({
      validate: validateInput,
    });
  }
});
</script>

<style lang="scss" scoped>
.snt-input {
  margin-top: 1.75rem;
  &.dense {
    margin: 0;
  }

  textarea,
  input {
    outline: none;
    background-color: unset;
    width: 100%;
    border: 0;
    resize: none;
  }

  &_label {
    position: absolute;
    transition: transform 0.3s ease;
  }

  &_input {
    border: 1px solid var(--color-border-main);
    padding: 0.25rem;
    border-radius: 4px;
  }

  &_error {
    &-msg {
      font-size: 12px;
      color: var(--color-error) !important;
    }

    .snt-input_label {
      color: var(--color-error) !important;
    }

    .snt-input_input {
      border-color: var(--color-error) !important;
    }
  }

  &_active {
    .snt-input_label {
      transform: translateY(calc(-1rem - 50%));
      color: var(--color-active);
    }

    .snt-input_input {
      border-color: var(--color-active);
    }
  }
}
</style>
