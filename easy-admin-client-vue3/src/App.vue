<template>
  <!-- 定义根组件模板，显示路由对应的视图 -->
  <div id="app">
    <!-- 路由视图，用于显示当前路由对应的组件 -->
    <router-view/>
  </div>
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
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>