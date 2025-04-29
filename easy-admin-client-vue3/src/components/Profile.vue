<template>
  <div class="profile-container">
    <el-card>
      <el-form label-width="100px">
        <el-form-item label="用户名">
          <span class="profile-text">管理员</span>
        </el-form-item>
        <el-form-item label="邮箱">
          <span class="profile-text">admin@example.com</span>
        </el-form-item>
        <el-form-item label="手机号">
          <span class="profile-text">+86 138-XXXX-XXXX</span>
        </el-form-item>
        <el-form-item label="角色">
          <span class="profile-text">超级管理员</span>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup></script>

<style scoped>
.profile-container {
  padding: 20px;
}

.profile-text {
  font-size: 14px;
  color: #555;
}
</style>