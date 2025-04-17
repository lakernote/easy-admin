<template>
  <el-menu :default-active="activeMenu" mode="horizontal" @select="handleMenuSelect">
    <el-menu-item index="/users">用户管理</el-menu-item>
    <el-menu-item index="logout">登出</el-menu-item>
  </el-menu>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import {useRouter} from 'vue-router';

const router = useRouter();
const activeMenu = ref('');

const handleMenuSelect = (index: string) => {
  if (index === 'logout') {
    // 清除登录状态
    localStorage.removeItem('isLoggedIn');
    // 跳转到登录页面
    router.push('/login');
  } else {
    router.push(index);
  }
};
</script>

<style scoped>
.el-menu {
  margin-bottom: 20px;
}
</style>