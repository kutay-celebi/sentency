<template>
  <div class="login-container">
    <div class="login-wrapper">
      <snt-card class="login-card">
        <template #title>
          <img src="@/assets/logo.png" style="margin-top: 3rem" />
        </template>

        <GoogleLogin
          :callback="handleSignSuccess"
          prompt
          :button-config="{ shape: 'pill', type: 'standard', theme: 'filled_black', text: 'continue_with' }"
        />
        <snt-alert v-if="errors" type="error">
          <template #title>{{ errors.code }}</template>
          <span v-for="(err, idx) in errors.errors" :key="idx">{{ err }}</span>
        </snt-alert>

        <snt-form ref="loginForm">
          <snt-input v-model="loginModel.username" label="Email" :rules="loginFormRules.username"></snt-input>
          <snt-input
            v-model="loginModel.password"
            label="Password"
            type="password"
            :rules="loginFormRules.password"
          ></snt-input>
        </snt-form>

        <div class="login-card_actions">
          <snt-button style="width: 100%" @click="login">LOGIN</snt-button>
        </div>
      </snt-card>
    </div>
  </div>
</template>

<script lang="ts" setup>
import SntCard from "@/components/core/SntCard.vue";
import SntInput from "@/components/core/SntInput.vue";
import { ref } from "vue";
import SntButton from "@/components/core/SntButton.vue";
import SntForm from "@/components/core/SntForm.vue";

import type { ErrorResponse } from "@/types/service-types";
import SntAlert from "@/components/core/SntAlert.vue";
import { useAuthStore } from "@/stores";
import { useRouter } from "vue-router";
import type { AxiosError } from "axios";
import useApi from "@/api";

const auth = useAuthStore();
const router = useRouter();

const loginFormRules = {
  username: [
    (v: string) => !!v || "E-mail must not be empty",
    (v: string) => /.+@.+\..+.$/.test(v) || "Must be proper e-mail",
  ],
  password: [(v: string) => !!v || "Password must not be empty"],
};
const loginModel = ref({
  username: "test@test.com",
  password: "test",
});

const errors = ref<ErrorResponse>();
const loginForm = ref();

const api = useApi();

const handleSignSuccess = async (result: any) => {
  await api.auth
    .loginWithGoogle(result.credential)
    .then((resp) => {
      auth.loginSuccess(resp.data);
      router.push("/");
    })
    .catch((err: AxiosError<ErrorResponse>) => {
      errors.value = err.response?.data;
    });
};

const login = async () => {
  const isValid = loginForm.value.validate();
  if (!isValid) {
    return;
  }

  await api.auth
    .login(loginModel.value.username, loginModel.value.password)
    .then((resp) => {
      auth.loginSuccess(resp.data);
      router.push("/");
    })
    .catch((err: AxiosError<ErrorResponse>) => {
      errors.value = err.response?.data;
    });
};
</script>

<style scoped lang="scss">
.login {
  &-container {
    display: flex;
    justify-content: center;
    background-color: var(--color-bg-dark);
    height: 100vh;
    color: var(--color-white-mute);
  }

  &-wrapper {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    padding: 1rem;
  }

  &-card {
    background-color: var(--color-white-mute);
    color: var(--color-text);
    border: 1px solid var(--color-border-main);
    border-radius: 4px;
    text-align: center;

    @media (min-width: 1024px) {
      max-width: 400px;
    }

    &_actions {
      margin-top: 1.5rem;
    }
  }
}
</style>
