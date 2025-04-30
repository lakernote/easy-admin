import {createApp} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router';
import {createPinia} from 'pinia'
// 路由权限控制
import {setupPermissionGuard} from './utils/permission'
// 恢复登录状态
import {useAuthStore} from './stores/useAuthStore'

// 创建 Pinia 实例
const pinia = createPinia()
// 创建 Vue 应用实例
const app = createApp(App);

// 挂载 Pinia 到 Vue 应用
app.use(pinia)
setupPermissionGuard(router)
// 安装 ElementPlus 组件库
app.use(ElementPlus);
// 安装路由插件
app.use(router);

// 将应用挂载到 DOM 节点上
app.mount('#app');

useAuthStore().restoreAuthState()