<template>
  <div>
    <!-- 使用 Element Plus 的Container 布局容器 组件 https://element-plus.org/zh-CN/component/container.html -->
    <el-container>
      <!-- 1.顶部导航栏 -->
      <el-header>顶部导航栏</el-header>
      <!-- 2.顶部下面区域 -->
      <!-- 减去的固定值，可按需调整 撑满下部区域 -->
      <el-container style="min-height: calc(100vh - 50px);">
        <!-- 2.1 左侧边栏 -->
        <el-aside width="200px">
          <!-- row和col用于布局，col支持24列 -->
          <el-row>
            <el-col :span="24">
              <el-menu
                  default-active="Home"
                  class="el-menu-vertical-demo"
                  @open="handleOpen"
                  @close="handleClose"
                  @select="handleSelect"
                  router
              >
                <!-- 循环遍历路由配置 -->
                <template v-for="route in routes" :key="route.name">
                  <!-- 如果路由有子路由，渲染为 el-sub-menu -->
                  <el-sub-menu v-if="route.children" :index="route.name">
                    <template #title>
                      <el-icon>
                        <!-- 根据路由元信息中的图标名称渲染图标 -->
                        <component :is="icons[route.meta.icon]"/>
                      </el-icon>
                      <!-- 显示路由的标题 -->
                      <span>{{ route.meta.title }}</span>
                    </template>
                    <!-- 循环遍历子路由 -->
                    <template v-for="child in route.children" :key="child.name">

                      <el-menu-item :index="child.name">
                        <el-icon>
                          <!-- 根据子路由元信息中的图标名称渲染图标 -->
                          <component :is="icons[child.meta.icon]"/>
                        </el-icon>
                        <!-- 显示子路由的标题 -->
                        <span>{{ child.meta.title }}</span>
                      </el-menu-item>
                    </template>
                  </el-sub-menu>
                  <!-- 如果路由没有子路由，渲染为 el-menu-item -->
                  <el-menu-item v-else :index="route.name">
                    <el-icon>
                      <!-- 根据路由元信息中的图标名称渲染图标 -->
                      <component :is="icons[route.meta.icon]"/>
                    </el-icon>
                    <!-- 显示路由的标题 -->
                    <span>{{ route.meta.title }}</span>
                  </el-menu-item>
                </template>
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <!-- 2.2 右侧内容区域 -->
        <el-container>
          <!-- 2.2.1 主体内容区域 -->
          <el-main>
            <!-- 面包屑导航 -->
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ name: 'Home' }">首页</el-breadcrumb-item>
              <template v-for="(crumb, index) in breadcrumbList" :key="index">
                <el-breadcrumb-item :to="{ name: crumb.name }">{{ crumb.meta.title }}</el-breadcrumb-item>
              </template>
            </el-breadcrumb>
            <!-- 路由视图 -->
            <router-view/>
          </el-main>
          <!-- 2.2.2 底部区域 -->
          <el-footer>底部导航栏</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {ref, watch} from 'vue';
import {useRoute} from 'vue-router';
import {House, List, Plus, User} from '@element-plus/icons-vue';
import router from '../router';

const route = useRoute();
// 获取路由信息，过滤出需要的路由供应菜单使用
const routes = router.getRoutes().filter(route => {
  return route.meta && route.meta.title && route.meta.icon && route.meta.showInMenu;
});
const icons = {
  House,
  User,
  List,
  Plus
};

const breadcrumbList = ref([]);

const generateBreadcrumb = () => {
  // 获取当前路由的所有匹配路由
  const currentRoute = route.matched.filter(r => r.meta && r.meta.title); // 只保留有 title 的路由
  const breadcrumbs = [];
  // 遍历匹配的路由，生成面包屑
  for (const item of currentRoute) {
    // 排除根路由（path 为空的首页），避免重复
    if (item.path === '/' || item.path === '') continue;
    breadcrumbs.push(item);
  }
  breadcrumbList.value = breadcrumbs;
};

// 监听路由变化，更新面包屑
watch(
    () => route.matched, // 监听路由变化
    () => { // 回调函数
      generateBreadcrumb();
    },
    {immediate: true} // 立即执行一次
);

// 处理菜单项的打开事件
const handleOpen = (key, keyPath) => {
  console.log(key, keyPath);
};

// 处理菜单项的关闭事件
const handleClose = (key, keyPath) => {
  console.log(key, keyPath);
};

// 处理菜单项的选择事件,使用 router.push 跳转到对应的路由 根据 name
const handleSelect = (key, keyPath) => {
  console.log(key, keyPath);
  router.push({name: key});
};
</script>

<style scoped>

/* 顶部导航栏样式 */
.el-header {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 50px;
  height: 50px;
}

/* 侧边栏样式 */
.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

/* 页脚样式 */
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 40px;
  height: 40px;
  flex-shrink: 0; /* 防止页脚收缩 */
}
</style>