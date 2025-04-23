<template>
  <div class="layout-container">
    <!-- 使用 Element Plus 的Container 布局容器 组件 https://element-plus.org/zh-CN/component/container.html -->
    <el-container>
      <!-- 1.顶部导航栏 -->
      <el-header class="business-header">
        <!-- 使用 el-row 和 el-col 进行布局 -->
        <el-row :gutter="0" class="header-row">
          <!-- 1.1 左侧标题和logo span12 是占用一半 满是24-->
          <el-col :span="12" class="header-left">
            <div class="logo-container">
              <!-- 可以替换成你的实际logo -->
              <el-icon :size="24" class="logo-icon">
                <Monitor/>
              </el-icon>
              <span class="logo-text">企业管理系统</span>
            </div>
          </el-col>

          <!-- 1.2 右侧用户信息 -->
          <el-col :span="12" class="header-right">
            <!-- 1.2.1 通知图标 -->
            <el-badge :value="3" class="notification-badge">
              <el-icon :size="18">
                <Bell/>
              </el-icon>
            </el-badge>
            <!-- 1.2.2 使用Element Plus的下拉菜单组件 -->
            <el-dropdown trigger="click">
              <!-- 用户信息显示区域 - 包含昵称和头像 -->
              <div class="user-info">
                <span class="user-name">管理员</span>
                <!-- 用户头像组件 -->
                <el-avatar :size="32" class="business-avatar">A</el-avatar>
              </div>

              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-icon>
                      <User/>
                    </el-icon>
                    <span>个人中心</span>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-icon>
                      <Key/>
                    </el-icon>
                    <span>修改密码</span>
                  </el-dropdown-item>
                  <!-- 退出登录选项 - 带分隔线和点击事件 -->
                  <el-dropdown-item divided @click="handleLogout">
                    <el-icon>
                      <SwitchButton/>
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
      <el-container style="min-height: calc(100vh - 60px);">
        <!-- 2.1 左侧边栏 -->
        <el-aside width="220px" class="business-aside">
          <!-- row和col用于布局，col支持24列 -->
          <el-row>
            <el-col :span="24">
              <el-menu
                  default-active="Home"
                  class="business-menu"
                  @open="handleOpen"
                  @close="handleClose"
                  @select="handleSelect"
                  router
                  background-color="#001529"
                  text-color="#b3b3b3"
                  active-text-color="#ffffff"
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
        <el-container class="right-container">
          <!-- 2.2.1 主体内容区域 -->
          <el-main class="business-main">
            <!-- 2.2.1.1 面包屑导航 -->
            <div class="breadcrumb-container">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ name: 'Home' }">首页</el-breadcrumb-item>
                <template v-for="(crumb, index) in breadcrumbList" :key="index">
                  <el-breadcrumb-item>{{ crumb.meta.title }}</el-breadcrumb-item>
                </template>
              </el-breadcrumb>
            </div>
            <!-- 2.2.1.2 路由视图 -->
            <div class="content-card">
              <router-view/>
            </div>
          </el-main>
          <!-- 2.2.2 底部区域 -->
          <el-footer class="business-footer">
            <div class="footer-content">
              © 2025 企业管理系统 版权所有
            </div>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {ref, watch} from 'vue';
import {useRoute} from 'vue-router';
import {Bell, House, Key, List, Monitor, Plus, Setting, SwitchButton, User} from '@element-plus/icons-vue';
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
  Plus,
  Setting
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
    localStorage.removeItem('tokenName');
    localStorage.removeItem('tokenValue');
    // 跳转到登录页
    router.push('/login');
  }).catch(() => {
    // 用户取消操作
  });
};
</script>

<style scoped>
/* 整体容器样式 */
.layout-container {
  height: 100vh; /* 设置高度为100vh，确保占满整个视口 */
  font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
}

/* 顶部导航栏样式 - 商务风格 */
.business-header {
  background-color: #ffffff; /* 设置背景色 */
  color: #333333; /* 设置文字颜色 */
  line-height: 60px; /* 设置行高为60px，使文字垂直居中 */
  height: 60px; /* 设置导航栏高度为60px */
  padding: 0 20px; /* 设置左右内边距为20px，上下为0 */
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  position: relative; /* 设置相对定位 */
  z-index: 10; /* 设置z-index，确保在其他元素之上 */
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

/* Logo容器样式 */
.logo-container {
  display: flex;
  align-items: center;
}

.logo-icon {
  color: #1890ff;
  margin-right: 8px;
}


.logo-text {
  font-size: 18px;
  font-weight: 600;
  color: #001529;
}

/* 右侧用户信息样式 */
.header-right {
  display: flex; /* 使用flex布局 */
  justify-content: flex-end; /* 子元素靠右对齐 */
  align-items: center; /* 子元素垂直居中对齐 */
}

/* 通知徽章样式 */
.notification-badge {
  margin-right: 20px; /* 设置右侧外边距为20px */
  cursor: pointer;
  display: inline-flex; /* 这行，使用flex布局 */
  align-items: center; /* 垂直居中对齐 */
  vertical-align: middle; /* 这行，使图标和头像在同一水平线上 */
}

/* 用户信息样式 */
.user-info {
  display: flex; /* 使用flex布局，使头像和昵称并排显示 */
  align-items: center; /* 垂直居中对齐 */
  cursor: pointer; /* 鼠标悬停时显示指针样式，提示可点击 */
  padding: 0 4px; /* 设置左右内边距为4px */
}

.user-name {
  margin-right: 8px; /* 在昵称右侧添加8px的间距，与头像分开 */
  font-size: 14px;
}

/* 头像样式 */
.business-avatar {
  background-color: #1890ff;
  color: #ffffff;
}

/* 左侧边栏样式 */
.business-aside {
  background-color: #001529;
  box-shadow: 2px 0 8px 0 rgba(29, 35, 41, 0.05);
  position: relative;
  z-index: 9;
  transition: width 0.3s;
  overflow: hidden;
}

/* 菜单样式 */
.business-menu {
  border-right: none;
}

.business-menu :deep(.el-sub-menu__title) {
  height: 50px;
  line-height: 50px;
}

.business-menu :deep(.el-menu-item) {
  height: 50px;
  line-height: 50px;
}


/* 主内容区域样式 */
.right-container {
  background-color: #f0f2f5; /* 设置背景色 */
  display: flex; /* 使用flex布局 */
  flex-direction: column; /* 垂直排列子元素 */
}

.business-main {
  padding: 20px; /* 设置内边距为20px */
  flex: 1; /* 使主内容区域占满剩余空间 */
  overflow-y: auto; /* 允许垂直滚动 */
}

/* 面包屑容器样式 */
.breadcrumb-container {
  margin-bottom: 16px; /* 设置底部外边距为16px */
  padding: 8px 0; /* 上下内边距为8px */
}

/* 路由内容卡片样式 */
.content-card {
  background-color: #ffffff; /* 设置背景色 */
  border-radius: 4px; /* 设置圆角为4px */
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.03); /* 添加阴影效果 */
  padding: 24px; /* 设置内边距为24px */
}

/* 页脚样式 - 商务风格 */
.business-footer {
  background-color: #ffffff; /* 设置背景色 */
  color: #666666; /* 设置文字颜色 */
  height: 48px; /* 设置高度为48px */
  line-height: 48px; /* 设置行高为48px，使文字垂直居中 */
  text-align: center; /* 居中对齐 */
  padding: 0; /* 上下内边距为0 */
  box-shadow: 0 -1px 4px rgba(0, 0, 0, 0.03); /* 添加阴影效果 */
}

.footer-content {
  font-size: 12px; /* 设置字体大小为12px */
}
</style>