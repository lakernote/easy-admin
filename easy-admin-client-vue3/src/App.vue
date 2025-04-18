<template>
  <!-- 路由视图，用于显示当前路由对应的组件 -->
  <router-view/>
</template>

<script setup>
import {onMounted} from 'vue';
import {useRouter} from 'vue-router';

const router = useRouter();
// 在组件挂载时检查登录状态
onMounted(() => {
  // 获取本地存储中的登录状态
  const isLoggedIn = localStorage.getItem('isLoggedIn');
  // 如果没有登录状态，则跳转到登录页面
  if (!isLoggedIn) {
    router.push('/login');
  }
});
</script>

<style scoped>
html, body, #app {
  height: 100%; /* 确保根容器高度为 100% */
  margin: 0;
}
</style>