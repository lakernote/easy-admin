<template>
  <!-- 登录页面容器 -->
  <!-- 1.根容器：flex 布局竖直水平居中，占满整个视口高度 -->
  <div class="login-container">
    <div class="login-card">
      <!-- 品牌区域 -->
      <div class="brand-area">
        <img
            :src="logoSvg"
            alt="企业Logo"
            class="logo"
            width="120"
            height="120"
        />
        <h3 class="brand-name">laker企业管理平台</h3>
        <p class="slogan">专业·高效·安全</p>
      </div>

      <!-- 登录表单，绑定登录表单数据，提交时调用 handleLogin 方法 -->
      <el-form
          :model="loginForm"
          :rules="rules"
          ref="formRef"
          label-width="80px"
          class="login-form"
          @submit.prevent="handleLogin">
        <!-- 用户名输入项 -->
        <el-form-item label="用户名" prop="username">
          <!-- 用户名输入框，绑定登录表单的用户名数据 -->
          <el-input v-model="loginForm.username"
                    placeholder="请输入用户名"
                    class="input-with-border"
                    clearable/>
        </el-form-item>
        <!-- 密码输入项 -->
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password"
                    type="password"
                    placeholder="请输入密码"
                    class="input-with-border"
                    show-password/>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item class="button-container">
          <el-button type="primary"
                     @click="handleLogin"
                     class="login-button"
                     :loading="loading">登录
          </el-button>
        </el-form-item>
      </el-form>

    </div>
    <!-- 版权信息 -->
    <div class="copyright">
      © 2025 laker科技有限公司 保留所有权利
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import {useRouter} from 'vue-router';
import {ElMessage} from "element-plus";
import {login} from '@/api/login';

// 引用表单实例，用于表单验证
const formRef = ref<InstanceType<typeof import('element-plus')['ElForm']>>();
const loading = ref(false);
// 嵌入商务风格LOGO（简洁线条图标）
const logoSvg = 'data:image/svg+xml;base64,' + btoa(`
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 200 200" fill="none">
  <path d="M100 20C144.182 20 180 55.818 180 100C180 144.182 144.182 180 100 180C55.818 180 20 144.182 20 100C20 55.818 55.818 20 100 20Z"
        stroke="#1a478e" stroke-width="12" stroke-linecap="round"/>
  <path d="M60 100H140" stroke="#1a478e" stroke-width="12" stroke-linecap="round"/>
  <path d="M100 60V140" stroke="#1a478e" stroke-width="12" stroke-linecap="round"/>
  <path d="M80 80L120 120" stroke="#1a478e" stroke-width="12" stroke-linecap="round"/>
  <path d="M120 80L80 120" stroke="#1a478e" stroke-width="12" stroke-linecap="round"/>
</svg>
`);
// 登录表单数据，包含用户名和密码
// ref函数用于创建响应式数据
const loginForm = ref({
  // 设置默认用户名和密码
  username: 'laker',
  password: 'lakernote'
});

const rules = {
  username: [
    {required: true, message: "用户名不能为空", trigger: "blur"},
    {min: 3, max: 20, message: "用户名长度应在3到20个字符之间", trigger: "blur"},
  ],
  password: [
    {required: true, message: "密码不能为空", trigger: "blur"},
    {min: 6, message: "密码长度不能少于6个字符", trigger: "blur"},
  ],
};

// 获取路由实例，用于页面跳转
const router = useRouter();

// 处理登录逻辑的方法
const handleLogin = () => {
  // 对表单进行验证
  formRef.value?.validate((valid) => {
    if (valid) {
      loading.value = true;
      // 如果表单验证通过，发送登录请求
      login(loginForm.value.username, loginForm.value.password)
          .then(response => {
            if (response.data.success) {
              localStorage.setItem('isLoggedIn', 'true');
              // 这里可以存储 token 等信息
              localStorage.setItem('tokenName', response.data.data.tokenName);
              localStorage.setItem('tokenValue', response.data.data.tokenValue);
              router.push('/');
            } else {
              ElMessage.error(response.data.message);
            }
          })
      loading.value = false;
    }
  });
};
</script>

<style scoped>
.login-container {
  display: flex; /* 使用 flex 布局，让内容垂直水平居中 */
  flex-direction: column; /* 子元素纵向排列 */
  justify-content: center; /* 垂直居中 */
  align-items: center; /* 水平居中 */
  height: 100vh; /* 占满整个屏幕高度 */
  padding: 20px; /* 内边距防止贴边 */
  box-sizing: border-box;
  background-color: #f5f7fa;
}

.login-card {
  width: 100%;
  max-width: 480px;
  padding: 40px 50px;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 12px 36px rgba(26, 71, 142, 0.12);
  font-family: 'Segoe UI', 'Microsoft YaHei', sans-serif;
  box-sizing: border-box;
}

.brand-area {
  text-align: center;
  margin-bottom: 30px;
}

.logo {
  width: 120px;
  height: 120px;
  object-fit: contain;
  margin-bottom: 20px;
}

.brand-name {
  font-size: 24px;
  font-weight: 600;
  color: #1a478e;
  margin: 0;
}

.slogan {
  font-size: 14px;
  color: #666666;
  margin-top: 8px;
}

.login-form {
  width: 100%;
}

.el-form-item {
  margin-bottom: 28px;
}

.el-form-item__label {
  font-size: 16px;
  color: #333333;
  font-weight: 500;
}

.input-with-border {
  border-radius: 8px;
  border: 1px solid #e0e0e0;
  font-size: 16px;
  transition: border-color 0.3s ease;

  &:focus {
    border-color: #1a478e;
    box-shadow: 0 0 0 2px rgba(26, 71, 142, 0.1);
  }
}

.el-input__inner {
  padding: 14px 18px;
}

.button-container {
  margin-top: 30px;
  text-align: center;
}

.login-button {
  width: 100%;
  height: 52px;
  border-radius: 8px;
  font-size: 18px;
  font-weight: 600;
  background-color: #1a478e;
  border-color: #1a478e;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #153a7a;
    border-color: #153a7a;
  }
}

.copyright {
  text-align: center;
  margin-top: 40px;
  color: #999999;
  font-size: 14px;
}

/* 表单验证错误样式 */
.el-form-item.is-error .el-input__inner {
  border-color: #f5222d;
}

.el-form-item__error {
  margin-top: 10px;
  font-size: 14px;
  color: #f5222d;
  line-height: 1.5;
}
</style>