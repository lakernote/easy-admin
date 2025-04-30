import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
    plugins: [
        vue(),// 使用 Vue 3 插件
        vueDevTools(), // 使用 Vue DevTools 插件
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        },
    },
    build: {
        rollupOptions: {
            output: {
                // 拆分包，防止打包后index.js太大
                manualChunks: {
                    // 第三方库单独打包
                    vendor: ['vue', 'vue-router', 'axios'],
                    // Element Plus 单独打包
                    elementPlus: ['element-plus'],
                    // 图标库可选提取
                    elementPlusIcons: ['@element-plus/icons-vue']
                }
            }
        }
    }
})
