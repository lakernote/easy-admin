import {defineStore} from 'pinia'
import {ref} from 'vue'

export const useAuthStore = defineStore('auth', () => {   // 状态
    const isLoggedIn = ref(false)
    const tokenName = ref(null)
    const tokenValue = ref(null)

    // 登录方法
    function login(tokenNameVal, tokenValueVal) {
        isLoggedIn.value = true
        tokenName.value = tokenNameVal
        tokenValue.value = tokenValueVal
        persistAuthState()
    }

    // 登出方法
    function logout() {
        isLoggedIn.value = false
        tokenName.value = null
        tokenValue.value = null
        localStorage.removeItem('auth')
    }

    // 持久化到 localStorage
    function persistAuthState() {
        localStorage.setItem('auth', JSON.stringify({
            isLoggedIn: isLoggedIn.value,
            tokenName: tokenName.value,
            tokenValue: tokenValue.value
        }))
    }

    // 从 localStorage 恢复状态
    function restoreAuthState() {
        const saved = localStorage.getItem('auth')
        if (saved) {
            const data = JSON.parse(saved)
            isLoggedIn.value = data.isLoggedIn
            tokenName.value = data.tokenName
            tokenValue.value = data.tokenValue
        }
    }

    return {isLoggedIn, tokenName, tokenValue, login, logout, restoreAuthState}
})