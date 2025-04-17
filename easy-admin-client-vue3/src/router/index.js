import {createRouter, createWebHistory} from 'vue-router';
import Login from '../views/Login.vue';
import Users from '../views/Users.vue';

// 定义路由配置数组
const routes = [
    {
        // 根路径重定向到登录页面
        path: '/',
        redirect: '/login'
    },
    {
        // 登录页面路由
        path: '/login',
        component: Login
    },
    {
        // 用户管理页面路由
        path: '/users',
        component: Users
    }
];

// 创建路由实例
const router = createRouter({
    // 使用 HTML5 History 模式
    history: createWebHistory(),
    routes
});

// 全局前置守卫，在每次路由跳转前执行
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

export default router;