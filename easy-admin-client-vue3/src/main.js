import {createApp} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router';
import axios from 'axios';

// 创建 Vue 应用实例
const app = createApp(App);

// 安装 ElementPlus 组件库
app.use(ElementPlus);
// 安装路由插件
app.use(router);

// 可以将 axios 挂载到全局，方便在组件中使用
app.config.globalProperties.$axios = axios;
// 设置 axios 的基础 URL
axios.defaults.baseURL = 'http://localhost:8080';
// 设置 请求超时时间
axios.defaults.timeout = 10000;
// 设置请求拦截器
axios.interceptors.request.use(
    config => {
        // 在请求发送之前做一些处理，比如添加 token
        const tokenValue = localStorage.getItem('tokenValue');
        const tokenName = localStorage.getItem('tokenName');
        if (tokenValue) {
            config.headers[`${tokenName}`] = `${tokenValue}`;
        }
        return config;
    },
    error => {
        // 处理请求错误
        return Promise.reject(error);
    }
);
// 设置响应拦截器
axios.interceptors.response.use(
    response => {
        // 处理响应数据
        return response.data;
    },
    error => {
        // 处理响应错误
        if (error.response.status === 401) {
            // 如果未授权，跳转到登录页面
            router.push({name: 'Login'});
        }
        return Promise.reject(error);
    }
);

// 将应用挂载到 DOM 节点上
app.mount('#app');