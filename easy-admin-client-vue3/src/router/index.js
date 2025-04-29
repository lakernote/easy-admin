import {createRouter, createWebHistory} from 'vue-router';
import Login from '../views/Login.vue';
import Layout from '../components/Layout.vue';
import Users from "@/views/Users.vue";
import {ElMessage} from "element-plus";
// 定义路由配置数组
const routes = [
    {
        path: '/',  // 根路径对应的路由配置
        redirect: '/workspace', // 显式重定向到 /workspace
        component: Layout,      // 该路由对应的组件为 Layout，通常 Layout 组件作为应用的整体布局，包含侧边栏、导航栏等
        // 子路由配置数组，这些子路由会在 Layout 组件的 <router-view> 中渲染
        children: [
            {
                // 子路由的路径为空字符串，表示当访问根路径时默认显示该子路由对应的组件
                path: 'workspace',
                // 该子路由的名称为 'Home'，可用于路由导航时通过名称跳转
                name: 'Home',
                // meta中的属性是自己扩展的，用于存储路由的元信息，showInMenu 用于控制是否在菜单中显示该路由仅一级菜单需要配置
                meta: {title: '工作空间', icon: 'House', showInMenu: true},
                // 使用懒加载的方式引入 Home 组件，只有在访问该路由时才会加载对应的组件，提高应用性能
                // '@' 是项目中配置的别名，通常指向 src 目录
                component: () => import('@/views/Home.vue'),
                children: [
                    {
                        path: 'users', // 实际上是 /system/users
                        name: 'Users',
                        meta: {title: '用户管理', icon: 'User'},
                        component: Users
                    }
                ]
            },
            {
                // 系统管理页面的路由路径，当访问该路径时会显示对应的组件
                path: 'system', // 系统管理的子路由路径,实际上是 /system
                meta: {title: '系统管理', icon: 'Setting', showInMenu: true},
                name: 'System',
                // 该路由对应的组件为 Users，该组件负责展示用户管理相关的内容
                children: [
                    {
                        path: 'users', // 实际上是 /system/users
                        name: 'Users',
                        meta: {title: '用户管理', icon: 'User'},
                        component: Users
                    },
                    {
                        path: 'dicts', // 实际上是 /system/dicts
                        name: 'Dicts',
                        meta: {title: '字典管理', icon: 'Plus'},
                        component: () => import('@/views/Dicts.vue') // 使用懒加载的方式引入 Dicts 组件
                    }
                ]
            }
        ]
    },
    {
        // 重定向到登录页面
        path: '/401',
        redirect: '/login'
    },
    {
        // 登录页面路由
        path: '/login',
        component: Login
    },
    // 404 路由，必须放在最后一个
    {
        path: '/:pathMatch(.*)*',  // 匹配所有未定义的路由 通配符路由必须放在路由配置数组的最后，以确保它只在其他路由都不匹配时才生效
        name: 'NotFound',
        component: () => import('@/views/NotFound.vue'),  // 懒加载 NotFound 组件 减少初始加载时间
    }
];

// 创建路由实例
const router = createRouter({
    // 使用 HTML5 History 模式
    history: createWebHistory(),
    routes
});

// 全局前置路由守卫，在每次路由跳转前执行 用于权限控制
router.beforeEach((to, from, next) => {
    // 从本地存储中获取登录状态
    const isLoggedIn = localStorage.getItem('isLoggedIn');
    // 如果用户试图访问非登录页面且未登录
    if (to.path !== '/login' && !isLoggedIn) {
        // 强制跳转到登录页面
        next('/login');
    } else {
        // 允许正常跳转
        next();
    }
});

// 路由错误处理
router.onError((error) => {
    // 处理路由错误，例如打印错误信息
    console.error('Router Error:', error);
    ElMessage.error('页面加载失败，请稍后再试');
});

export default router;