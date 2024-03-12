# Easyadmin

> **有用的话请鼓励❤️下作者，右上角☝️watch、star、fork三连点🙏🙏🙏一波**

## 🌵介绍

后端：基于 **SpringBoot3（JDK17）+MybatiPlus+Snaker+Mysql8**技术。

前端：[vue-element-plus-admin](https://element-plus-admin-doc.cn/)



## 前端 vue-element-plus-admin

官网：https://element-plus-admin-doc.cn/

Repo:

- https://github.com/kailong321200875/vue-element-plus-admin

- https://gitee.com/kailong110120130/vue-element-plus-admin



[vue-element-plus-admin](https://github.com/kailong321200875/vue-element-plus-admin) 是一个基于 [element-plus](https://element-plus.org/) 免费开源的中后台模版。使用了最新的 [Vue3](https://github.com/vuejs/vue-next)，[Vite](https://github.com/vitejs/vite)，[Typescript](https://www.typescriptlang.org/)等主流技术开发，开箱即用的中后台前端解决方案。

## 环境准备

本地环境需要安装 [Pnpm](https://pnpm.io/)、[Node.js](http://nodejs.org/) 和 [Git](https://git-scm.com/)

为什么使用 [Pnpm](https://pnpm.io/)，而不是用其他包管理器，大家可以搜索一下，这里就不做过多的阐述了。

**注意**

- [Node.js](http://nodejs.org/) 版本要求`14.x`以上，这里推荐 `16.x` 及以上。

## IDEA插件

- [Iconify IntelliSense](https://marketplace.visualstudio.com/items?itemName=antfu.iconify) - Iconify 图标插件
- [unocss](https://marketplace.visualstudio.com/items?itemName=antfu.unocss) - unocss 提示插件
- [I18n-ally](https://marketplace.visualstudio.com/items?itemName=Lokalise.i18n-ally) - i18n 插件
- [Volar](https://gitee.com/link?target=https%3A%2F%2Fmarketplace.visualstudio.com%2Fitems%3FitemName%3Djohnsoncodehk.volar) - vue 开发必备
- [ESLint](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint) - 脚本代码检查
- [Prettier](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode) - 代码格式化
- [Stylelint](https://marketplace.visualstudio.com/items?itemName=stylelint.vscode-stylelint) - css 格式化
- [DotENV](https://marketplace.visualstudio.com/items?itemName=mikestead.dotenv) - .env 文件 高亮

## 安装

### 安装 Node.js

如果您电脑未安装[Node.js](https://nodejs.org/en/)，请安装它，推荐 `18.x` 及以上

**验证**

```sh
# 验证 npm 是否安装成功
npm -v

# 验证 node 是否安装成功
node -v
```

如果你需要同时存在多个 `node` 版本，可以使用 [Nvm](https://github.com/nvm-sh/nvm) 或者其他工具进行 Node.js 进行版本管理。

### 安装依赖

#### Pnpm 安装

推荐使用 [Pnpm](https://pnpm.io/)进行依赖安装（若其他包管理器安装不了需要自行处理）。

如果未安装 `Pnpm`，可以用下面命令来进行全局安装

```sh
# 全局安装 pnpm
npm i -g pnpm

# 验证
pnpm -v
```

#### 安装依赖

**在项目根目录下(client文件夹 下)**，打开命令窗口执行，耐心等待安装完成即可

```sh
# 安装依赖
pnpm i
```

## 启动项目

**当依赖安装完成后**，执行以下命令即可启动项目：

```sh
pnpm run dev
```

## 项目学习

1.关于后端返回Json结构，可以去参考mock文件夹下数据。

2.全局请求的拦截器在src/axios/config.ts

```typescript
// 拦截请求
defaultRequestInterceptors ...
// 拦截响应
const defaultResponseInterceptors = (response: AxiosResponse) => {
  if (response?.config?.responseType === 'blob') {
    // 如果是文件流，直接过
    return response
  } else if (response.data.code === SUCCESS_CODE) {
    return response.data
  } else {
    // 全局默认，异常拦截器
    ElMessage.error(response?.data?.message)
    if (response?.data?.code === 401 || response.status === 401) {
      const userStore = useUserStoreWithOut()
      userStore.logout()
    }
  }
}
```



### 登录

前端入口：LoginForm.vue - loginApi(formdata)

后端接口：/api/v1/login

### 登录完成后获取当前用户的可视菜单列表

前端入口：LoginForm.vue

```javascript
   const res = await loginApi(formData)

        if (res) {
            getRole()
```

后端入口：

/sys/menu/mytree











