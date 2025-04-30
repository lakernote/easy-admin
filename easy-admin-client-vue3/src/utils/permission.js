import {useAuthStore} from '@/stores/useAuthStore'

/**
 * 检查当前用户是否有权限访问该路由
 * @param requiredRoles Array<string> - 所需角色如 ['admin', 'editor']
 */
export function hasPermission(requiredRoles) {
    const authStore = useAuthStore()
    if (!requiredRoles) return true // 如果未配置权限，则允许访问
    return requiredRoles.some(role => authStore.roles.includes(role))
}

/**
 * 全局前置路由守卫，在每次路由跳转前执行 用于权限控制
 * 全局前置守卫逻辑
 * @param router
 */
export function setupPermissionGuard(router) {
    router.beforeEach((to, from, next) => {
        const authStore = useAuthStore()

        if (to.meta.requiresAuth && !authStore.isLoggedIn) {
            // 未登录跳转登录页
            next({path: '/login', query: {redirect: to.fullPath}})
        } else if (to.meta.roles && !hasPermission(to.meta.roles)) {
            // 无权限访问，跳回首页或提示
            next({path: '/403'}) // 或者 '/dashboard'
        } else {
            // 允许正常跳转
            next()
        }
    })
}