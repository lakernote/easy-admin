import {createApp} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router';

// 创建 Vue 应用实例
const app = createApp(App);

// 安装 ElementPlus 组件库
app.use(ElementPlus);
// 安装路由插件
app.use(router);

// 将应用挂载到 DOM 节点上
app.mount('#app');