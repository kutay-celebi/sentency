import type { InjectionKey, Ref } from "vue";

export interface FormItem {
  validate: () => string | null;
}

export interface FormProvider {
  addFormItem: (item: FormItem) => void;
  isValid: Ref<boolean>;
}
export const FormInjectionKey: InjectionKey<FormProvider> = Symbol.for("snt-form");

export interface SntTab {
  key: string;
  label: string;
}

export interface PageItem {
  active: boolean;
  label: string | number;
  clickable: boolean;
}
