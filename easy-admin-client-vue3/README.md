### 开发环境准备

1.安装 [Node.js](https://nodejs.org/)

```cmd
# 检查版本
node -v
npm -v
```

2.Intellj Idea安装vue插件，插件名称`Vue.js`

### 创建一个Vue应用

#### 使用Intellj Idea创建

1.打开 IntelliJ IDEA，点击 “File” -> “New” -> “Project”。

2.选择vue.js，右侧填写项目名称，选择项目位置，然后点击 “创建”。

3.项目初始化完成，可执行以下命令：

 ```cmd
cd easy-admin-client-vue3
npm install
npm run dev
 ```

4.浏览器访问

```
 VITE v6.3.1  ready in 3071 ms

  ➜  Local:   http://localhost:5173/
  ➜  Network: use --host to expose
  ➜  Vue DevTools: Open http://localhost:5173/__devtools__/ as a separate window
  ➜  Vue DevTools: Press Alt(⌥)+Shift(⇧)+D in App to toggle the Vue DevTools

```

#### 使用npm创建

1.在打算创建项目的目录。在命令行中运行以下命令

```cmd
npm create vue@latest
```

这一指令将会安装并执行 [create-vue](https://github.com/vuejs/create-vue)，它是 Vue 官方的项目脚手架工具。你将会看到一些诸如
TypeScript 和测试支持之类的可选功能提示

如果不确定是否要开启某个功能，你可以**直接按下回车键选择** `No`。

在项目被创建后，通过以下步骤安装依赖并启动开发服务器：

```cmd
cd <your-project-name>
npm install
npm run dev
```

当你准备将应用发布到生产环境时，请运行

```cmd
npm run build
```

此命令会在 `./dist` 文件夹中为你的应用创建一个生产环境的构建版本。

### 目录结构

```text
my-vue3-project/
├── node_modules/          # 项目依赖包安装目录
├── public/                # 静态资源目录，不会被 Vite 处理，直接复制到构建后的目录
│   └── index.html         # 项目的入口 HTML 文件
├── src/                   # 源代码目录
│   ├── assets/            # 静态资源目录，如图片、样式文件等，会被 Vite 处理
│   ├── components/        # 组件目录，存放项目中的 Vue 组件
│   ├── views/             # 视图目录，存放页面级别的 Vue 组件
│   ├── App.vue            # 根组件
│   ├── main.js            # 项目入口文件
│   └── router/            # 路由配置目录
│       └── index.js       # 路由配置文件
├── .gitignore             # Git 忽略文件配置
├── package.json           # 项目配置文件，记录项目信息和依赖
├── package-lock.json      # 锁定依赖版本的文件
└── vite.config.js         # Vite 配置文件
```

### 组件

#### Element  Plus

ElementPlus 是基于 Vue 3 开发的一套高质量、美观且功能丰富的 UI
组件库。它提供了众多常用的界面组件，例如按钮、输入框、表格、弹窗等，这些组件具有统一的设计风格，能够帮助开发者快速搭建出专业、美观且易用的用户界面。

1.安装element-plus

```cmd
npm install element-plus --save
```

2.使用element-plus

首先，在 src/main.js 文件中引入 Element Plus：

```cmd
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)
app.use(ElementPlus)
//app.use(ElementPlus, { size: 'small', zIndex: 3000 })
```

在项目中使用 Element Plus 组件了。例如，在 src/components/HelloWorld.vue 文件中，我们可以添加一个 Button 组件

```cmd
<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <el-button type="primary">点击我</el-button>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: '欢迎使用 Element Plus!'
    }
  }
}
</script>
```

#### Vue Router

Vue Router 是 Vue 官方的客户端路由解决方案，用于实现单页面应用（SPA）的路由功能。它允许开发者通过定义路由规则，将不同的 URL
路径映射到对应的 Vue 组件，从而实现页面的切换和导航。

1.安装

```cmd
npm install vue-router@4
```

2.配置路由

在 `src` 目录下创建 `router` 文件夹，并在其中创建 `index.js` 文件，示例代码如下：

```javascript
import {createRouter, createWebHistory} from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/about',
        name: 'About',
        component: About
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
```

这里定义了两个路由，分别对应 `Home` 和 `About` 组件。

3.在项目中使用路由
在 `src/main.js` 中引入并使用路由：

```javascript
import {createApp} from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(router)
app.mount('#app')
```

### 调试

1.代码中添加debugger;
> 1.在代码中需要调试的地方添加 `debugger;` 语句。
> 2.在浏览器中打开开发者工具，切换到 Sources 面板。
>
2.使用浏览器调试：sourcemap需启用
3.vs code 调试：先开启node服务，后启用vs code的调试模式

### 本地开发和生产环境配置

在项目根目录下创建不同的环境变量文件：
.env.development：用于开发环境。
.env.production：用于生产环境。

在 .env.development 文件中添加如下内容：

```plaintext
VITE_BASE_URL = 'http://localhost:8080'
VITE_TIMEOUT = 5000
```

在 .env.production 文件中添加如下内容，假设生产环境的基础 URL 为 http://example.com：

```plaintext
VITE_BASE_URL = 'http://example.com'
VITE_TIMEOUT = 5000
```

修改 main.js 文件，使用环境变量来设置 axios 的基础 URL 和超时时间。

```javascript
// 使用环境变量设置 axios 的基础 URL
axios.defaults.baseURL = import.meta.env.VITE_BASE_URL;
// 使用环境变量设置 请求超时时间
axios.defaults.timeout = parseInt(import.meta.env.VITE_TIMEOUT);
```

运行 vite / npm run dev 启动开发服务器，Vite 会自动加载 .env.development 文件中的环境变量。可以在package.json找到

运行 vite build / npm run build 构建生产环境，Vite 会自动加载 .env.production 文件中的环境变量。可以在package.json找到

### 部署nginx

将 dist 文件夹中的所有文件上传到 Nginx 服务器的指定目录，例如 /var/www/html

```nginx
server {
    listen 80;
    server_name your_domain_or_ip;

    root /var/www/html;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }
}
```