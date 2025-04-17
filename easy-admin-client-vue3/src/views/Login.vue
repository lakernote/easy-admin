<template>
  <!-- 登录页面容器 -->
  <div class="login-container">
    <!-- 登录表单，绑定登录表单数据，提交时调用 handleLogin 方法 -->
    <el-form
        :model="loginForm"
        :rules="rules"
        ref="formRef"
        label-width="70px"
        @submit.prevent="handleLogin">
      <!-- 用户名输入项 -->
      <el-form-item label="用户名" prop="username">
        <!-- 用户名输入框，绑定登录表单的用户名数据 -->
        <el-input v-model="loginForm.username"
                  placeholder="请输入用户名"
                  clearable/>
      </el-form-item>
      <!-- 密码输入项 -->
      <el-form-item label="密码" prop="password">
        <el-input v-model="loginForm.password"
                  type="password"
                  placeholder="请输入密码"
                  show-password/>
      </el-form-item>
      <!-- 登录按钮 -->
      <el-form-item>
        <el-button type="primary"
                   @click="handleLogin"
                   :loading="loading">登录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import {useRouter} from 'vue-router';
import {ElMessage} from "element-plus";
import axios from 'axios';

// 引用表单实例，用于表单验证
const formRef = ref<InstanceType<typeof import('element-plus')['ElForm']>>();
const loading = ref(false);
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
      axios.post('/sys/auth/v1/login', loginForm.value)
          .then(response => {
            if (response.success) {
              localStorage.setItem('isLoggedIn', 'true');
              // 这里可以存储 token 等信息
              localStorage.setItem('tokenName', response.data.tokenName);
              localStorage.setItem('tokenValue', response.data.tokenValue);
              router.push('/users');
            } else {
              ElMessage.error(response.data.message);
            }
          })
          .catch(error => {
            ElMessage.error("网络错误，请稍后再试");
          });
      loading.value = false;
    }
  });
};
</script>

<style scoped>
.login-container {
  width: 300px;
  margin: 0 auto;
  margin-top: 100px;
  padding: 20px;
  border: 1px solid #5bd0c4;
  border-radius: 8px;
  background-color: #ebf0f8;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}
</style>