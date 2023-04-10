<template>
  <div class="main-layout">
    <nav>
      <div class="sentency-logo">
        <img src="@/assets/logo.png" />
      </div>
    </nav>
    <main class="main">
      <router-view />
    </main>
    <snt-tab-bar>
      <snt-tab-bar-item to="/">
        <ri-search-line />
      </snt-tab-bar-item>
      <snt-tab-bar-item to="/sentence">
        <ri-pencil-line />
      </snt-tab-bar-item>
      <snt-tab-bar-item to="/word-list">
        <ri-list-check2 />
      </snt-tab-bar-item>
      <snt-tab-bar-item to="#">
        <ri-account-circle-line />
      </snt-tab-bar-item>
    </snt-tab-bar>
  </div>
</template>

<script lang="ts" setup>
import SntTabBar from "@/components/core/SntTabBar.vue";
import SntTabBarItem from "@/components/core/SntTabBarItem.vue";
import RiPencilLine from "~icons/ri/pencil-line";
import RiListCheck2 from "~icons/ri/list-check-2";
import RiSearchLine from "~icons/ri/search-line";
import RiAccountCircleLine from "~icons/ri/account-circle-line";
</script>

<style lang="scss" scoped>
.main-layout {
  display: flex;
  flex-direction: column;
  padding: 40px 0;
  height: 100%;
  overflow: hidden;
  position: relative;
}

.main {
  position: relative;
  padding: 0.5rem;
  height: calc(100svh - 80px);
  overflow-y: auto;
}

nav {
  height: 40px;
  background-color: var(--color-primary);
  display: flex;
  align-items: center;
  overflow: hidden;
  position: fixed;
  width: 100%;
  top: calc(env(safe-area-inset-bottom));
}

.go-back {
  display: flex;
  align-items: center;
  font-size: 2rem;
  color: var(--color-text-secondary);
}

.sentency-logo {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;

  img {
    width: 30px;
  }
}
</style>
