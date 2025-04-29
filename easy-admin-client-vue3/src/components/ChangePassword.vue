<template>
  <div class="change-password-container">
    <el-card style="max-width: 600px;">
      <el-form label-width="100px" :model="form" :rules="rules" ref="formRef">
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input v-model="form.oldPassword" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="form.newPassword" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {ElMessage} from 'element-plus';

const form = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
});

const rules = {
  oldPassword: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在6到20个字符之间', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== form.value.newPassword) {
          callback(new Error('两次输入密码不一致'));
        } else {
          callback();
        }
      },
      trigger: 'blur'
    }
  ]
};

const submitForm = () => {
  ElMessage.success('密码修改成功');
};
</script>

<style scoped>
.change-password-container {
  padding: 20px;
}
</style>