import axios from 'axios'
import {ElMessage} from 'element-plus'
import router from '@/router' // 引入 vue-router

// 创建实例
const request = axios.create({
    baseURL: import.meta.env.VITE_BASE_URL, // 代理时前缀，或你后端API根地址 // 使用环境变量设置 axios 的基础 URL
    timeout: parseInt(import.meta.env.VITE_TIMEOUT) // 使用环境变量设置 请求超时时间
})

// 请求拦截器：自动带 token
request.interceptors.request.use(config => {
    const tokenName = localStorage.getItem('tokenName')
    const tokenValue = localStorage.getItem('tokenValue')

    if (tokenName && tokenValue) {
        config.headers[tokenName] = tokenValue
    }

    return config
}, error => {
    return Promise.reject(error)
})

// 响应拦截器：统一错误处理
request.interceptors.response.use(
    response => response,
    error => {
        const status = error.response?.status
        if (status === 401) {
            ElMessage.error('未登录或登录过期，请重新登录')
            // 如果未授权，跳转到登录页面
            router.push('/login');
        } else if (status === 403) {
            ElMessage.error('无权限访问')
            router.push('/403') // 自定义无权限页
        } else {
            ElMessage.error(error.response?.data?.message || '请求失败')
        }
        return Promise.reject(error)
    }
)

export default request
