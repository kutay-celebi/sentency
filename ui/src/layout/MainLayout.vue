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
        <ri-search2-line />
      </snt-tab-bar-item>
      <snt-tab-bar-item to="/sentence">
        <ri-pencil-line />
      </snt-tab-bar-item>
      <snt-tab-bar-item to="#">
        <ri-list-check2 />
      </snt-tab-bar-item>
      <snt-tab-bar-item to="#">
        <ri-user5-line />
      </snt-tab-bar-item>
    </snt-tab-bar>
  </div>
</template>

<script lang="ts" setup>
import SntTabBar from "@/components/core/SntTabBar.vue";
import SntTabBarItem from "@/components/core/SntTabBarItem.vue";
import RiUser5Line from "~icons/ri/user-5-line";
import RiPencilLine from "~icons/ri/pencil-line";
import RiListCheck2 from "~icons/ri/list-check-2";
import RiSearch2Line from "~icons/ri/search-2-line";
</script>

<style lang="scss" scoped>
.main-layout {
  display: flex;
  flex-direction: column;
  height: calc(100svh - 3rem);
  overflow: hidden;
  position: relative;
}

.main {
  position: relative;
  padding: 0.5rem;
  height: calc(100vh - 3rem);
  flex: 1;
}

nav {
  height: 3rem;
  background-color: var(--color-primary);
  display: flex;
  align-items: center;
  overflow: hidden;
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
