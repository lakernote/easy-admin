<template>
  <div>
    <el-row class="tac">
      <el-col :span="4">
        <el-menu
            default-active="Home"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            router
        >
          <template v-for="route in routes" :key="route.name">
            <el-sub-menu v-if="route.children" :index="route.name">
              <template #title>
                <el-icon><component :is="icons[route.meta.icon]" /></el-icon>
                <span>{{ route.meta.title }}</span>
              </template>
              <template v-for="child in route.children" :key="child.name">
                <el-menu-item :index="child.name">
                  <el-icon><component :is="icons[child.meta.icon]" /></el-icon>
                  <span>{{ child.meta.title }}</span>
                </el-menu-item>
              </template>
            </el-sub-menu>
            <el-menu-item v-else :index="route.name">
              <el-icon><component :is="icons[route.meta.icon]" /></el-icon>
              <span>{{ route.meta.title }}</span>
            </el-menu-item>
          </template>
        </el-menu>
      </el-col>
      <el-col :span="20">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ name: 'Home' }">首页</el-breadcrumb-item>
          <template v-for="(crumb, index) in breadcrumbList" :key="index">
            <el-breadcrumb-item :to="{ name: crumb.name }">{{ crumb.meta.title }}</el-breadcrumb-item>
          </template>
        </el-breadcrumb>
        <router-view />
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import {ref, watch} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import {House, List, Plus, User} from '@element-plus/icons-vue';
import router from '../router';

const route = useRoute();
const routerInstance = useRouter();
const routes = router.getRoutes().filter(r => r.path.startsWith('/'));
const icons = {
  House,
  User,
  List,
  Plus
};

const breadcrumbList = ref([]);

const generateBreadcrumb = () => {
  const currentRoute = route.matched;
  const breadcrumbs = [];
  currentRoute.forEach(r => {
    if (r.name!== 'Home') {
      breadcrumbs.push(r);
    }
  });
  breadcrumbList.value = breadcrumbs;
};

watch(
    () => route.name,
    () => {
      generateBreadcrumb();
    },
    { immediate: true }
);

const handleOpen = (key, keyPath) => {
  console.log(key, keyPath);
};

const handleClose = (key, keyPath) => {
  console.log(key, keyPath);
};
</script>

<style scoped>
</style>