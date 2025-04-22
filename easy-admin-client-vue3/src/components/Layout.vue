<template>
  <div>
    <!-- 使用 Element Plus 的Container 布局容器 组件 https://element-plus.org/zh-CN/component/container.html -->
    <el-container>
      <!-- 1.顶部导航栏 -->
      <el-header>
        <!-- 使用 el-row 和 el-col 进行布局 -->
        <el-row :gutter="0" class="header-row">
          <!-- 1.1 左侧标题 span12 是占用一半 满是24-->
          <el-col :span="12" class="header-left">
            <span>顶部导航栏</span>
          </el-col>

          <!-- 1.2 右侧用户信息 -->
          <el-col :span="12" class="header-right">
            <!-- 使用Element Plus的下拉菜单组件 -->
            <el-dropdown trigger="click">
              <!-- 用户信息显示区域 - 包含昵称和头像 -->
              <div class="user-info">
                <span class="user-name">用户昵称</span>
                <!-- 用户头像组件 -->
                <el-avatar :size="32" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
              </div>

              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>个人中心</el-dropdown-item>
                  <el-dropdown-item>修改密码</el-dropdown-item>
                  <!-- 退出登录选项 - 带分隔线和点击事件 -->
                  <el-dropdown-item divided @click="handleLogout">
                    <el-icon>
                      <!-- 退出图标 -->
                      <Switch/>
                    </el-icon>
                    退出登录
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-header>
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
            <!-- 2.2.1.1 面包屑导航 -->
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ name: 'Home' }">首页</el-breadcrumb-item>
              <template v-for="(crumb, index) in breadcrumbList" :key="index">
                <el-breadcrumb-item :to="{ name: crumb.name }">{{ crumb.meta.title }}</el-breadcrumb-item>
              </template>
            </el-breadcrumb>
            <!-- 2.2.1.2 路由视图 -->
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
import {House, List, Plus, Switch, User} from '@element-plus/icons-vue';
import router from '../router';
import {ElMessageBox} from "element-plus";

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

// 添加退出登录处理函数
const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 清除登录状态
    localStorage.removeItem('isLoggedIn');
    // 跳转到登录页
    router.push('/login');
  }).catch(() => {
    // 用户取消操作
  });
};
</script>

<style scoped>
/* 顶部导航栏样式 */
.el-header {
  background-color: #b3c0d1; /* 设置背景色 */
  color: #333; /* 设置文字颜色 */
  line-height: 50px; /* 设置行高为50px，使文字垂直居中 */
  height: 50px; /* 设置导航栏高度为50px */
  padding: 0 15px; /* 设置左右内边距为15px，上下为0 */
}

/* 顶部行布局 */
.header-row {
  height: 100%; /* 使行高度占满el-header的高度 */
}

/* 左侧标题样式 */
.header-left {
  display: flex; /* 使用flex布局 */
  align-items: center; /* 使子元素垂直居中对齐 */
  font-weight: bold; /* 文字加粗显示 */
}

/* 右侧用户信息样式 */
.header-right {
  display: flex; /* 使用flex布局 */
  justify-content: flex-end; /* 子元素靠右对齐 */
  align-items: center; /* 子元素垂直居中对齐 */
}

/* 用户信息样式 */
.user-info {
  display: flex; /* 使用flex布局，使头像和昵称并排显示 */
  align-items: center; /* 垂直居中对齐 */
  cursor: pointer; /* 鼠标悬停时显示指针样式，提示可点击 */
}

.user-name {
  margin-right: 8px; /* 在昵称右侧添加8px的间距，与头像分开 */
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