<template>
  <form>
    <slot />
  </form>
</template>

<script lang="ts" setup>
import { provide, ref } from "vue";
import { FormInjectionKey, FormItem } from "@/components/types";

const isValid = ref();
const items = ref<FormItem[]>([]);

provide(FormInjectionKey, {
  isValid: isValid,
  addFormItem: (item) => items.value.push(item),
});

const validate = () => {
  const errors = items.value.map((item) => item.validate()).filter((item) => !!item);
  return errors.length === 0;
};

defineExpose({
  validate,
});
</script>

<style scoped></style>
